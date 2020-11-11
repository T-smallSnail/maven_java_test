package com.bmcc.vgop.service.thirdchannel.impl;

import ch.qos.logback.classic.Logger;
import com.bmcc.vgop.common.consts.DataConsts;
import com.bmcc.vgop.common.consts.RedisKeyConsts;
import com.bmcc.vgop.common.consts.ThirdChannelConsts;
import com.bmcc.vgop.common.data.jedis.JedisClient;
import com.bmcc.vgop.common.utils.date.DateUtils;
import com.bmcc.vgop.common.utils.log.LogUtils;
import com.bmcc.vgop.common.utils.string.JsonUtils;
import com.bmcc.vgop.data.crmopenapi.CrmOpenApiDao;
import com.bmcc.vgop.data.crmopenapi.po.TVgopAndMarketUseCrmApiLog;
import com.bmcc.vgop.data.thirdchannel.CrmAuthDao;
import com.bmcc.vgop.data.thirdchannel.OrderCrmLogDao;
import com.bmcc.vgop.data.thirdchannel.ProductCrmMethodDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmAuthEntity;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmChannelInfoEntity;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallOderCrmLogEntity;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallProductCrmMethodInfo;
import com.bmcc.vgop.service.thirdchannel.ThirdChannelOrderCrmProductService;
import com.bmcc.vgop.service.thirdchannel.ThirdChannelRedisKey;
import com.bmcc.vgop.webclient.service.crmopenapi.CrmOpenApiService;
import com.bmcc.vgop.webclient.service.crmopenapi.obj.sub.SubRetObj;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/10 17:09
 * @description :从第三方渠道订购CRM产品
 */
@Service
public class ThirdChannelOrderCrmProductServiceImpl implements ThirdChannelOrderCrmProductService {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(ThirdChannelOrderCrmProductServiceImpl.class);

    @Autowired
    CrmAuthDao crmAuthDao;

    @Autowired
    JedisClient JedisClient;

    @Autowired
    CrmOpenApiDao crmOpenApiDao;

    @Autowired
    OrderCrmLogDao orderCrmLogDao;

    @Autowired
    CrmOpenApiService crmOpenApiService;

    @Autowired
    ProductCrmMethodDao productCrmMethodDao;

    @Override
    @Transactional(value = "mysqlTransactionManager", readOnly = false)
    public String orderCrmProduct(String param) {
        //封装接口返回数据
        Map<String, Object> returnMap = new HashMap();
        //检验签名数据
        Map<String, String> signMap = new HashMap();
        // 新建接口调用日志
        TVgopUsersubCallOderCrmLogEntity orderCrmLog = new TVgopUsersubCallOderCrmLogEntity();


        try {
            //解析参数
            Map<String, Object> jsonMap = JsonUtils.getJsonMap(param);
            String appId = jsonMap.get("appId").toString();
            String msisdn = jsonMap.get("msisdn").toString();
            String subId = jsonMap.get("subId").toString();
            String offerCode = jsonMap.get("offerCode").toString();
            String requestTime = jsonMap.get("requestTime").toString();
            String serialCode = jsonMap.get("serialCode").toString();
            String accessToken = jsonMap.get("accessToken").toString();
            Object extendData1 = jsonMap.get("extendData");
            String extendData = null;
            if(extendData1!=null){
                extendData = extendData1.toString();
            }



            //记录日志数据
            orderCrmLog.setAppid(appId);
            orderCrmLog.setMsisdn(msisdn);
            orderCrmLog.setSubId(subId);
            orderCrmLog.setOfferCode(offerCode);
            orderCrmLog.setRequestTime(requestTime);
            orderCrmLog.setSerialCode(serialCode);
            orderCrmLog.setAccessToken(accessToken);
            orderCrmLog.setExtendData(extendData);


            //判断AppId是否有效
            TVgopUsersubCallCrmChannelInfoEntity channel = DataConsts.THIRD_CHANNEL_MSG.get(appId);
            if (channel == null) {
                return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.INVALID_APPID, "无效appid"));
            }

            //判断该app是否有操作该产品的权限（订购）
            TVgopUsersubCallCrmAuthEntity isAuth = crmAuthDao.getCrmAuth(appId, offerCode, ThirdChannelConsts.TYPE_ORDER);
            if (isAuth == null) {
                return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.APPID_NO_AUTHORITY, "appid无权限申请此项操作"));
            }

            //判断令牌是否正确
            Short isOneTime = isAuth.getIsOneTime();
            String tokenKey = ThirdChannelRedisKey.accessTokenRedisKey(appId, ThirdChannelConsts.TYPE_ORDER, offerCode, msisdn);
            String accessRedisValue = JedisClient.get(tokenKey);
            if (accessRedisValue == null) {
                return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.OTHER_ERROR, "access_toke已失效"));
            }
            if (isOneTime.equals(ThirdChannelConsts.INDATE_MORE_TIMES)) {
                if (!accessRedisValue.equals(accessToken)) {
                    return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.INVALID_TOKEN, "access_token无效"));
                }
            } else {
                if (!accessRedisValue.equals(accessToken)) {
                    return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.INVALID_TOKEN, "access_token无效"));
                }
                JedisClient.del(tokenKey);
            }

            //判断serialCode是否重复
            String serialCodeKey = RedisKeyConsts.USERSUB_SERIALCODE + ":" + appId;
            String serialCodeRedis = JedisClient.get(serialCodeKey);
            if (serialCodeRedis != null && serialCodeRedis.equals(serialCode)) {
                return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.OTHER_ERROR, "serialCode重复"));
            }

            //调用crm接口
            String functionName = null;
            SubRetObj retObj = null;
            String source = "usersub";
            TVgopUsersubCallProductCrmMethodInfo crmMethod = productCrmMethodDao.searchCrmMethod(offerCode, ThirdChannelConsts.TYPE_ORDER);
            if(crmMethod==null){
                throw new Exception("未查询到该产品对应接口类型！");
            }

            functionName = crmMethod.getMethod();
            if (functionName.equals("subPro_015")) {

                //调用crm接口
                retObj = crmOpenApiService.subPro_015(msisdn, offerCode, source);

                //记录crm调用日志
                TVgopAndMarketUseCrmApiLog log = new TVgopAndMarketUseCrmApiLog();
                BeanUtils.copyProperties(retObj.getLogObj(),log);
                crmOpenApiDao.saveLog(log);

            } else if (functionName.equals("subPro_001")) {

            }

            JedisClient.set(serialCodeKey, serialCode);


            if (!retObj.isSucc()) {
                if(retObj.getLogObj().getCrmOpapiRespCode()==null){
                    returnMap.put("retCode", ThirdChannelConsts.OTHER_ERROR);
                    returnMap.put("retMsg", "crm接口调用异常！");
                }else{
                    returnMap.put("retCode", retObj.getLogObj().getCrmOpapiRespCode());
                    returnMap.put("dealMsg", retObj.getLogObj().getCrmOpapiRespDesc());
                }
                return JsonUtils.getJsonStr(returnMap);

            }


            //返回数据
            returnMap.put("retCode", ThirdChannelConsts.SUCCESS);
            returnMap.put("retMsg", "成功");
            return JsonUtils.getJsonStr(returnMap);


        } catch (Exception e) {
            logger.info("第三方平台调用订购接口出错！");
            LogUtils.printException(logger, e);
            orderCrmLog.setExceptionMsg(e.toString());

            return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.SYSTEM_ERROR, "系统异常"));

        } finally {
            orderCrmLog.setRetCode(Integer.valueOf(returnMap.get("retCode").toString()));
            if (returnMap.get("retMsg") != null) {
                orderCrmLog.setDealMsg(returnMap.get("retMsg").toString());
            }
            if (returnMap.get("dealMsg") != null) {
                orderCrmLog.setDealMsg(returnMap.get("dealMsg").toString());
            }
            orderCrmLog.setReturnTime(DateUtils.getNowTimeStamp());
            orderCrmLogDao.insertLog(orderCrmLog);
        }



    }

    private Map initErrorMap(Map<String, Object> map ,Integer retCode,String retMsg) {
        map.put("retCode", retCode);
        map.put("retMsg", retMsg);
        return map;
    }
}
