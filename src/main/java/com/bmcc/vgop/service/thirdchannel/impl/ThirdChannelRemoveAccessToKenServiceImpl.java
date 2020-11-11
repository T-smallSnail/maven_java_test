package com.bmcc.vgop.service.thirdchannel.impl;

import ch.qos.logback.classic.Logger;
import com.bmcc.vgop.common.consts.DataConsts;
import com.bmcc.vgop.common.consts.ThirdChannelConsts;
import com.bmcc.vgop.common.data.jedis.JedisClient;
import com.bmcc.vgop.common.utils.date.DateUtils;
import com.bmcc.vgop.common.utils.encrypt.SignUtils;
import com.bmcc.vgop.common.utils.log.LogUtils;
import com.bmcc.vgop.common.utils.string.JsonUtils;
import com.bmcc.vgop.data.thirdchannel.AccessTokenLogDao;
import com.bmcc.vgop.data.thirdchannel.RemoveAccessTokenDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallAccessTokenLogEntity;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmChannelInfoEntity;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallRemoveAccessTokenLog;
import com.bmcc.vgop.service.thirdchannel.ThirdChannelRedisKey;
import com.bmcc.vgop.service.thirdchannel.ThirdChannelRemoveAccessTokenService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：pancho
 * @date ：Created in 2019/12/10 17:19
 * @description :作废令牌
 */
@Service
public class ThirdChannelRemoveAccessToKenServiceImpl implements ThirdChannelRemoveAccessTokenService {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(ThirdChannelRemoveAccessToKenServiceImpl.class);

    @Autowired
    JedisClient JedisClient;

    @Autowired
    AccessTokenLogDao accessTokenLogDao;

    @Autowired
    RemoveAccessTokenDao removeAccessTokenDao;

    @Override
    @Transactional(value = "mysqlTransactionManager",readOnly = false)
    public String removeToken(String param) {
        
        //封装接口返回数据
        Map<String, Object> returnMap = new HashMap();
        //检验签名数据
        Map<String, String> signMap = new HashMap();
        //接口日志
        TVgopUsersubCallRemoveAccessTokenLog removeAccessTokenLog = new TVgopUsersubCallRemoveAccessTokenLog();
        
        try {
            //解析参数
            Map<String, Object> jsonMap = JsonUtils.getJsonMap(param);
            String appId = jsonMap.get("appId").toString();
            String requestTime = jsonMap.get("requestTime").toString();
            String accessToken = jsonMap.get("accessToken").toString();
            String sign = jsonMap.get("sign").toString();

            
            //记录日志数据
            removeAccessTokenLog.setAppid(appId);
            removeAccessTokenLog.setAccessToken(accessToken);
            removeAccessTokenLog.setRequestTime(requestTime);
            removeAccessTokenLog.setSign(sign);

            //判断AppId是否有效
            TVgopUsersubCallCrmChannelInfoEntity channel = DataConsts.THIRD_CHANNEL_MSG.get(appId);
            if (channel == null) {
                return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.INVALID_APPID, "无效appid!"));
            }

            //检验签名是否正确
            signMap.put("appId", appId);
            signMap.put("requestTime", requestTime);
            signMap.put("accessToken", accessToken);
            String md5Key = channel.getMd5Salt();
            String signChe = SignUtils.getSign(md5Key, signMap);
            if (!signChe.equals(sign)) {
                return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.INVALID_SIGN, "签名无效!"));
            }

            //查询令牌授权表
            TVgopUsersubCallAccessTokenLogEntity accessTokenLog = accessTokenLogDao.getAccessToken(appId,accessToken);
            if(accessTokenLog==null){
                return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.OTHER_ERROR, "令牌无效"));
            }
            removeAccessTokenLog.setGetTokenId(accessTokenLog.getId());
            String tokenKey = ThirdChannelRedisKey.accessTokenRedisKey(appId,accessTokenLog.getType(),accessTokenLog.getOfferCode(),accessTokenLog.getMsisdn());
            JedisClient.del(tokenKey);
            //返回数据
            returnMap.put("retCode", ThirdChannelConsts.SUCCESS);
            returnMap.put("retMsg", "成功");

            return JsonUtils.getJsonStr(returnMap);

        } catch (Exception e) {
            logger.info("第三方平台调用作废令牌接口出错！");
            LogUtils.printException(logger, e);
            removeAccessTokenLog.setExceptionMsg(e.toString());

            return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.SYSTEM_ERROR, "系统异常"));

        } finally {
            removeAccessTokenLog.setRetCode(Integer.valueOf(returnMap.get("retCode").toString()));
            removeAccessTokenLog.setRetMsg(returnMap.get("retMsg").toString());
            removeAccessTokenLog.setReturnTime(DateUtils.getNowTimeStamp());

            removeAccessTokenDao.insertLog(removeAccessTokenLog);
            
        }


    }

    private Map initErrorMap(Map<String, Object> map ,Integer retCode,String retMsg) {
        map.put("retCode", retCode);
        map.put("retMsg", retMsg);
        return map;
    }
}
