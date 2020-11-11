package com.bmcc.vgop.service.thirdchannel.impl;

import ch.qos.logback.classic.Logger;
import com.bmcc.vgop.common.consts.DataConsts;
import com.bmcc.vgop.common.consts.ThirdChannelConsts;
import com.bmcc.vgop.common.data.jedis.JedisClient;
import com.bmcc.vgop.common.utils.date.DateUtils;
import com.bmcc.vgop.common.utils.encrypt.MD5Utils;
import com.bmcc.vgop.common.utils.encrypt.SignUtils;
import com.bmcc.vgop.common.utils.log.LogUtils;
import com.bmcc.vgop.common.utils.string.JsonUtils;
import com.bmcc.vgop.data.thirdchannel.AccessTokenLogDao;
import com.bmcc.vgop.data.thirdchannel.CrmAuthDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallAccessTokenLogEntity;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmAuthEntity;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmChannelInfoEntity;
import com.bmcc.vgop.service.thirdchannel.ThirdChannelGetAccessTokenService;
import com.bmcc.vgop.service.thirdchannel.ThirdChannelRedisKey;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/10 17:07
 * @description :第三方渠道获取令牌
 */
@Service
public class ThirdChannelGetAccessTokenServiceImpl implements ThirdChannelGetAccessTokenService {

	private final static Logger logger = (Logger) LoggerFactory.getLogger(ThirdChannelGetAccessTokenServiceImpl.class);

	@Autowired
	CrmAuthDao crmAuthDao;

	@Autowired
    JedisClient JedisClient;

	@Autowired
	AccessTokenLogDao accessTokenLogDao;

	@Override
	@Transactional(value = "mysqlTransactionManager", readOnly = false)
	public String getAccessToken(String param) {

		// 封装返回数据
		Map<String, Object> returnMap = new HashMap();
		// 检验签名数据
		Map<String, String> signMap = new HashMap();
		// 新建接口调用日志
		TVgopUsersubCallAccessTokenLogEntity accessTokenLog = new TVgopUsersubCallAccessTokenLogEntity();

		try {
			// 解析参数
			Map<String, Object> jsonMap = JsonUtils.getJsonMap(param);
			String appId = jsonMap.get("appId").toString();
			String msisdn = jsonMap.get("msisdn").toString();
			Integer type = Integer.valueOf(jsonMap.get("type").toString());
			String offerCode = jsonMap.get("offerCode").toString();
			String requestTime = jsonMap.get("requestTime").toString();
			String validCode = jsonMap.get("validCode").toString();
			String sign = jsonMap.get("sign").toString();

			// 记录日志数据
			accessTokenLog.setAppid(appId);
			accessTokenLog.setMsisdn(msisdn);
			accessTokenLog.setType(type);
			accessTokenLog.setOfferCode(offerCode);
			accessTokenLog.setRequestTime(requestTime);
			accessTokenLog.setValidCode(validCode);
			accessTokenLog.setSign(sign);

			// 判断AppId是否有效
			TVgopUsersubCallCrmChannelInfoEntity channel = DataConsts.THIRD_CHANNEL_MSG.get(appId);
			if (channel == null) {
				return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.INVALID_APPID, "无效appid"));
			}

			// 检验签名是否正确
			signMap.put("appId", appId);
			signMap.put("msisdn", msisdn);
			signMap.put("type", type.toString());
			signMap.put("offerCode", offerCode);
			signMap.put("requestTime", requestTime);
			signMap.put("validCode", validCode);
			String md5Key = channel.getMd5Salt();
			String signChe = SignUtils.getSign(md5Key, signMap);
			if (!signChe.equals(sign)) {
				return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.INVALID_SIGN, "签名无效"));
			}

			// 判断该app是否有操作该产品的权限
			TVgopUsersubCallCrmAuthEntity isAuth = crmAuthDao.getCrmAuth(appId, offerCode, type);
			if (isAuth == null) {
				return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.APPID_NO_AUTHORITY, "appid无权限申请此项操作"));
			}

			// TODO
			// 检验验证码是否有效
			String phoneValidCodeKey = ThirdChannelRedisKey.validCodeRedisKey(appId, type, offerCode, msisdn);
			String validCodeRedis = JedisClient.get(phoneValidCodeKey);

			if (validCodeRedis == null || !validCode.equals(validCodeRedis)) {
				return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.OTHER_ERROR, "验证码无效"));
			}
			JedisClient.del(phoneValidCodeKey);

			// 生成令牌
			String accessToken = getAccessToken(appId, msisdn, type.toString());
			Short isOneTime = isAuth.getIsOneTime();
			Integer expiresTime = isAuth.getExpiresTime();
			String tokenKey = ThirdChannelRedisKey.accessTokenRedisKey(appId, type, offerCode, msisdn);
			JedisClient.set(tokenKey, accessToken);
			JedisClient.expire(tokenKey, expiresTime);
			// 返回数据
			returnMap.put("retCode", ThirdChannelConsts.SUCCESS);
			returnMap.put("retMsg", "成功");
			returnMap.put("accessToken", accessToken);
			returnMap.put("expires_in", expiresTime);
			returnMap.put("time_valid", isOneTime);
			return JsonUtils.getJsonStr(returnMap);
		} catch (Exception e) {
			logger.info("第三方平台获取授权令牌出错！");
			LogUtils.printException(logger, e);
			accessTokenLog.setExceptionMsg(e.toString());
			return JsonUtils.getJsonStr(initErrorMap(returnMap, ThirdChannelConsts.SYSTEM_ERROR, "系统异常"));
		} finally {
			accessTokenLog.setRetCode(Integer.valueOf(returnMap.get("retCode").toString()));
			accessTokenLog.setRetMsg(returnMap.get("retMsg").toString());
			if (returnMap.get("accessToken") != null) {
				accessTokenLog.setAccessToken(returnMap.get("accessToken").toString());
				accessTokenLog.setExpiresIn(Integer.valueOf(returnMap.get("expires_in").toString()));
				accessTokenLog.setTimeValid(Integer.valueOf(returnMap.get("time_valid").toString()));
			}
			accessTokenLog.setReturnTime(DateUtils.getNowTimeStamp());
			accessTokenLogDao.insertLog(accessTokenLog);
		}

	}

	// 生成令牌
	private String getAccessToken(String appId, String phone, String type) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String formatDate = DateUtils.format(new Date(), DateUtils.DATETIME_YYYYMMDDHHMMSS);
		return MD5Utils.encode(appId + phone + type + formatDate);
	}
	
	private Map initErrorMap(Map<String, Object> map ,Integer retCode,String retMsg) {
		map.put("retCode", retCode);
		map.put("retMsg", retMsg);
		return map;
	}

}
