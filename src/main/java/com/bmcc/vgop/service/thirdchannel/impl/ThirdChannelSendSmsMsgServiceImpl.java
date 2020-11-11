package com.bmcc.vgop.service.thirdchannel.impl;

import ch.qos.logback.classic.Logger;
import com.bmcc.vgop.common.consts.DataConsts;
import com.bmcc.vgop.common.consts.ThirdChannelConsts;
import com.bmcc.vgop.common.data.jedis.JedisClient;
import com.bmcc.vgop.common.utils.date.DateUtils;
import com.bmcc.vgop.common.utils.encrypt.SignUtils;
import com.bmcc.vgop.common.utils.log.LogUtils;
import com.bmcc.vgop.common.utils.string.JsonUtils;
import com.bmcc.vgop.common.utils.string.StringUtils;
import com.bmcc.vgop.common.utils.wassms.SmSender;
import com.bmcc.vgop.data.thirdchannel.CrmAuthDao;
import com.bmcc.vgop.data.thirdchannel.SendMsgLogDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmAuthEntity;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmChannelInfoEntity;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallSendSmsLogEntity;
import com.bmcc.vgop.service.thirdchannel.ThirdChannelRedisKey;
import com.bmcc.vgop.service.thirdchannel.ThirdChannelSendSmsMsgService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/10 17:05
 * @description :下发第三方渠道登陆短信
 */
@Service
public class ThirdChannelSendSmsMsgServiceImpl implements ThirdChannelSendSmsMsgService {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(ThirdChannelSendSmsMsgServiceImpl.class);

    @Autowired
    SendMsgLogDao sendMsgLogDao;

    @Autowired
    CrmAuthDao crmAuthDao;

    @Autowired
    JedisClient JedisClient;

    @Override
    @Transactional(value = "mysqlTransactionManager", readOnly = false)
    public String sendSmsMsg(String param) {
        //封装返回数据
        Map<String, Object> returnMap = new HashMap();
        //检验签名数据
        Map<String, String> signMap = new HashMap();
        // 新建接口调用日志
        TVgopUsersubCallSendSmsLogEntity sendSmsLog = new TVgopUsersubCallSendSmsLogEntity();

        try {
            //解析参数
            Map<String, Object> jsonMap = JsonUtils.getJsonMap(param);
            String appId = jsonMap.get("appId").toString();
            String msisdn = jsonMap.get("msisdn").toString();
            Integer type = Integer.valueOf(jsonMap.get("type").toString());
            String offerCode = jsonMap.get("offerCode").toString();
            String requestTime = jsonMap.get("requestTime").toString();
            String sign = jsonMap.get("sign").toString();

            //记录日志数据
            sendSmsLog.setAppid(appId);
            sendSmsLog.setMsisdn(msisdn);
            sendSmsLog.setType(type);
            sendSmsLog.setOfferCode(offerCode);
            sendSmsLog.setRequestTime(requestTime);
            sendSmsLog.setSign(sign);

            //判断AppId是否有效
            TVgopUsersubCallCrmChannelInfoEntity channel = DataConsts.THIRD_CHANNEL_MSG.get(appId);
            if (channel == null) {
                return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.OTHER_ERROR, "无效appid"));
            }

            //检验签名是否正确
            signMap.put("appId", appId);
            signMap.put("msisdn", msisdn);
            signMap.put("type", type.toString());
            signMap.put("offerCode", offerCode);
            signMap.put("requestTime", requestTime);
            String md5Key = channel.getMd5Salt();
            String signChe = SignUtils.getSign(md5Key, signMap);
            if (!signChe.equals(sign)) {
                return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.INVALID_SIGN, "签名无效"));
            }

            //判断该app是否有操作该产品的权限
            TVgopUsersubCallCrmAuthEntity isAuth = crmAuthDao.getCrmAuth(appId, offerCode, type);
            if (isAuth == null) {
                return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.APPID_NO_AUTHORITY, "appid无权限申请此项操作"));
            }

            //判断Ip发送短信的总数是否已经超标
            /*String validCodeCountKey = RedisKeyConsts.USERSUB_VALIDCODE_COUNT + appId;
            Boolean bl = verifyValidCodeCount(validCodeCountKey);
            if (bl) {

                returnMap.put("retCode", ThirdChannelConsts.OTHER_ERROR);
                returnMap.put("retMsg", "发送短信的总数已超标");
            }*/


            //3发送短信
            String validCode = StringUtils.getRandom(6);
            String message = "尊敬的用户，您本次短信验证码是"+validCode+"，有效期1分钟，感谢您的使用！";
            SmSender.sendMsg(msisdn, message, 0);


            //将验证码放入redis中，设置一分钟有效
            String validCodeKey = ThirdChannelRedisKey.validCodeRedisKey(appId, type, offerCode, msisdn);
            JedisClient.set(validCodeKey, validCode);
            JedisClient.expire(validCodeKey, ThirdChannelConsts.VALIDCODE_EXPIRE);

            returnMap.put("retCode", ThirdChannelConsts.SUCCESS);
            returnMap.put("retMsg", "成功");

            return JsonUtils.getJsonStr(returnMap);

        } catch (Exception e) {
            logger.info("向第三方平台下发鉴权短信出错！");
            LogUtils.printException(logger, e);
            sendSmsLog.setExceptionMsg(e.toString());

            return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.SYSTEM_ERROR, "系统异常"));

        } finally {
            sendSmsLog.setRetCode(Integer.valueOf(returnMap.get("retCode").toString()));
            sendSmsLog.setRetMsg(returnMap.get("retMsg").toString());
            sendSmsLog.setReturnTime(DateUtils.getNowTimeStamp());
            sendMsgLogDao.insertLog(sendSmsLog);
           
        }


    }

    private Boolean verifyValidCodeCount(String validCodeCountKey) {
        String count = JedisClient.get(validCodeCountKey);
        if(count==null){
            JedisClient.setex(validCodeCountKey,ThirdChannelConsts.VALIDCODE_COUNT_EXPIRE,"1");
            return false;
        }else{
            //if()
        }
        return true;
    }

    private Map initErrorMap(Map<String, Object> map ,Integer retCode,String retMsg) {
		map.put("retCode", retCode);
		map.put("retMsg", retMsg);
		return map;
	}
}
