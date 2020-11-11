package com.bmcc.vgop.service.thirdchannel.impl;

import ch.qos.logback.classic.Logger;
import com.bmcc.vgop.common.consts.DataConsts;
import com.bmcc.vgop.common.consts.ThirdChannelConsts;
import com.bmcc.vgop.common.utils.date.DateUtils;
import com.bmcc.vgop.common.utils.encrypt.SignUtils;
import com.bmcc.vgop.common.utils.log.LogUtils;
import com.bmcc.vgop.common.utils.string.JsonUtils;
import com.bmcc.vgop.data.crmopenapi.CrmOpenApiDao;
import com.bmcc.vgop.data.crmopenapi.po.TVgopAndMarketUseCrmApiLog;
import com.bmcc.vgop.data.thirdchannel.CheckOrderBusinessLogDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmChannelInfoEntity;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCheckOrderBusinessLogEntity;
import com.bmcc.vgop.service.thirdchannel.ThirdChannelCheckOrderBusinessService;
import com.bmcc.vgop.webclient.service.crmopenapi.CrmOpenApiService;
import com.bmcc.vgop.webclient.service.crmopenapi.obj.sub.CheckSubRetObj;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：pancho
 * @date ：Created in 2020/3/3 15:50
 * @description :
 */
@Service
public class ThirdChannelCheckOrderBusinessServiceImpl implements ThirdChannelCheckOrderBusinessService {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(ThirdChannelCheckOrderBusinessServiceImpl.class);

    @Autowired
    CrmOpenApiService crmOpenApiService;

    @Autowired
    CrmOpenApiDao crmOpenApiDao;

    @Autowired
    CheckOrderBusinessLogDao checkOrderBusinessLogDao;

    @Override
    @Transactional(value = "mysqlTransactionManager", readOnly = false)
    public String checkOrderBusiness(String param) {
        //封装接口返回数据
        Map<String, Object> returnMap = new HashMap();
        //检验签名数据
        Map<String, String> signMap = new HashMap();
        // 新建接口调用日志
        TVgopUsersubCheckOrderBusinessLogEntity checkOrderBusiness = new TVgopUsersubCheckOrderBusinessLogEntity();


        try {
            //解析参数
            Map<String, Object> jsonMap = JsonUtils.getJsonMap(param);
            String appId = jsonMap.get("appId").toString();
            String msisdn = jsonMap.get("msisdn").toString();
            String offerCode = jsonMap.get("offerCode").toString();
            String requestTime = jsonMap.get("requestTime").toString();
            String sign = jsonMap.get("sign").toString();


            //记录日志数据
            checkOrderBusiness.setAppid(appId);
            checkOrderBusiness.setMsisdn(msisdn);
            checkOrderBusiness.setOfferCode(offerCode);
            checkOrderBusiness.setRequestTime(requestTime);
            checkOrderBusiness.setSign(sign);


            //判断AppId是否有效
            TVgopUsersubCallCrmChannelInfoEntity channel = DataConsts.THIRD_CHANNEL_MSG.get(appId);
            if (channel == null) {
                return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.INVALID_APPID, "无效appid"));
            }

            //检验签名是否正确
            signMap.put("appId", appId);
            signMap.put("msisdn", msisdn);
            signMap.put("offerCode", offerCode);
            signMap.put("requestTime", requestTime);
            String md5Key = channel.getMd5Salt();
            String signChe = SignUtils.getSign(md5Key, signMap);
            if (!signChe.equals(sign)) {
                return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.INVALID_SIGN, "签名无效"));
            }


            //调用crm查询接口接口
            String functionName = null;
            String source = "usersub";
            CheckSubRetObj retObj = crmOpenApiService.order_check_001(msisdn, offerCode, source);

            //记录crm调用日志
            TVgopAndMarketUseCrmApiLog log = new TVgopAndMarketUseCrmApiLog();
            BeanUtils.copyProperties(retObj.getLogObj(), log);
            crmOpenApiDao.saveLog(log);


            //查询失败返回数据
            if (retObj.getIsOrder().equals("N")) {
                if(retObj.getLogObj().getCrmOpapiRespCode()==null){
                    returnMap.put("retCode", ThirdChannelConsts.OTHER_ERROR);
                    returnMap.put("retMsg", "crm接口调用异常！");
                }else{
                    returnMap.put("retCode", retObj.getLogObj().getCrmOpapiRespCode());
                    returnMap.put("dealMsg", retObj.getLogObj().getCrmOpapiRespDesc());
                }
                return JsonUtils.getJsonStr(returnMap);
            }


            //查询成功返回数据
            returnMap.put("retCode", ThirdChannelConsts.SUCCESS);
            returnMap.put("retMsg", "可订购");
            return JsonUtils.getJsonStr(returnMap);

        } catch (Exception e) {
            logger.info("业务订购校验接口出错！");
            LogUtils.printException(logger, e);
            checkOrderBusiness.setExceptionMsg(e.toString());

            return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.SYSTEM_ERROR, "系统异常"));

        } finally {
            checkOrderBusiness.setRetCode(Integer.valueOf(returnMap.get("retCode").toString()));
            if (returnMap.get("retMsg") != null) {
                checkOrderBusiness.setRetMsg(returnMap.get("retMsg").toString());
            }

            if (returnMap.get("dealMsg") != null) {
                checkOrderBusiness.setDealMsg(returnMap.get("dealMsg").toString());
            }
            checkOrderBusiness.setReturnTime(DateUtils.getNowTimeStamp());
            checkOrderBusinessLogDao.insertLog(checkOrderBusiness);
        }


    }

    private Map initErrorMap(Map<String, Object> map, Integer retCode, String retMsg) {
        map.put("retCode", retCode);
        map.put("retMsg", retMsg);
        return map;
    }
}
