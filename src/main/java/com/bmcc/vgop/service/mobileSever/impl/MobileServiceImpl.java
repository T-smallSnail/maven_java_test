package com.bmcc.vgop.service.mobileSever.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bmcc.vgop.common.consts.DataConsts;
import com.bmcc.vgop.common.utils.date.DateUtils;
import com.bmcc.vgop.common.utils.encrypt.MD5Utils;
import com.bmcc.vgop.common.utils.encrypt.SignUtils;
import com.bmcc.vgop.common.utils.http.HttpUtils;
import com.bmcc.vgop.common.utils.string.JsonUtils;
import com.bmcc.vgop.data.consts.po.PhoneSubstitutionConf;
import com.bmcc.vgop.data.crmopenapi.CrmOpenApiDao;
import com.bmcc.vgop.data.crmopenapi.po.TVgopAndMarketUseCrmApiLog;
import com.bmcc.vgop.data.mobileserverlog.MobileServerLogDao;
import com.bmcc.vgop.data.mobileserverlog.po.UsersubGetmobileserverLog;
import com.bmcc.vgop.service.mobileSever.MobileService;
import com.bmcc.vgop.webclient.service.crmopenapi.CrmOpenApiService;
import com.bmcc.vgop.webclient.service.crmopenapi.obj.qry009.AttrInfo;
import com.bmcc.vgop.webclient.service.crmopenapi.obj.qry009.OfferInfo;
import com.bmcc.vgop.webclient.service.crmopenapi.obj.qry009.Qry009RetObj;

import ch.qos.logback.classic.Logger;

@Service
@Transactional(value = "mysqlTransactionManager")
public class MobileServiceImpl implements MobileService {
	@Autowired
	MobileServerLogDao mobileServerLogDao;
	
	@Autowired
	CrmOpenApiDao crmOpenApiDao;

	@Autowired
	CrmOpenApiService crmOpenApiService;

	private final static Logger logger = (Logger) LoggerFactory.getLogger(MobileServiceImpl.class);

	// 成功返回状态值
	private static final int RESULT_SUCC = 0;
	// 签名校验失败
	private static final int SIGN_ERRO = 1;
	// ip校验失败
	private static final int IPADDR_ERRO = 2;
	// 接口次数调用超过20
	private static final int FREQUENCY_LIMIT = 3;
	// appId不存在
	private static final int APPID_NOT_EXIT = 4;
	// 调用能开接口异常
	private static final int POST_NENGKAI_ERRO = 2001;
	// 接口异常
	private static final int SYSO_ERRO = 9999;

	@SuppressWarnings("finally")
	@Override
	public String getMobileInfo(String para, HttpServletRequest request) {
		//返回String
		String resultJson = "";
		// 封装返回参数
		Map<String, Object> map = new HashMap<String, Object>();
		// 新建接口调用日志
		UsersubGetmobileserverLog serverLog = new UsersubGetmobileserverLog();
		try {
			
			// 解析入参
			//JSONObject obj = new JSONObject().fromObject(para);
			Map<String,String> obj = JsonUtils.getJsonMap(para);
			String appId = obj.get("appId");
			String queryCode = obj.get("queryCode");
			String phone = obj.get("phone");
			String offerid = obj.get("offerId");
			String requestParamTime = obj.get("requestTime");
			String sign = obj.get("sign");

			serverLog.setAppid(appId);
			serverLog.setQueryCode(queryCode);
			serverLog.setPhone(phone);
			serverLog.setOfferid(offerid);
			serverLog.setRequestParamTime(requestParamTime);
			serverLog.setSign(sign);
			serverLog.setRequestTime(DateUtils.dateToTimestamp(new Date()));

			PhoneSubstitutionConf conf = DataConsts.PHONE_CONF.get(appId);
			if (null == conf) {
				map.put("retCode", APPID_NOT_EXIT);
				resultJson = JsonUtils.getJsonStr(map);
				return resultJson;
			}
			
			// IP白名单检验&appId校验
			String ipList = conf.getIps();
			String ipAddress = HttpUtils.getIpAddress(request);
			logger.info("获取到访问ip地址:"+ipAddress);
			/*if (!ipList.contains(ipAddress)) {
				map.put("retCode", IPADDR_ERRO);
				resultJson = JsonUtils.getJsonStr(map);
				return resultJson;
			}*/

			// 签名校验
			obj.remove("sign");
			String mysign = SignUtils.getSign(conf.getMd5Key(), obj);
			if (!sign.equals(mysign)) {
				map.put("retCode", SIGN_ERRO);
				resultJson = JsonUtils.getJsonStr(map);
				return resultJson;
			}

			// 调用次数达校验
			int size = mobileServerLogDao.getSizeOfAppid(appId);
			int max = conf.getCounts();
			if (size > max) {
				map.put("retCode", FREQUENCY_LIMIT);
				resultJson = JsonUtils.getJsonStr(map);
				return resultJson;
			}
			// 调用接口
			serverLog.setRequestCrmTime(DateUtils.dateToTimestamp(new Date()));
			try {
				TVgopAndMarketUseCrmApiLog log = new TVgopAndMarketUseCrmApiLog();
				Qry009RetObj resultData = crmOpenApiService.qry_009(serverLog.getPhone(), serverLog.getOfferid(), "");
				//复制返回对象到log
				BeanUtils.copyProperties(log, resultData.getLogObj());
				crmOpenApiDao.saveLog(log);
				
				serverLog.setResponseCrmTime(DateUtils.dateToTimestamp(new Date()));
				if ("0".equals(resultData.getCode())) {
					OfferInfo[] offerInfoList = resultData.getOfferInfoArr();
					//判断返回值非空
					if(null==offerInfoList||offerInfoList.length<=0){
						map.put("retCode", RESULT_SUCC);
						map.put("attrList", new ArrayList<>());
						resultJson = JsonUtils.getJsonStr(map);
						return resultJson;
					}
					List<Map<String, String>> list = new ArrayList<>();
					for (OfferInfo offer : offerInfoList) {
						AttrInfo[] attrInfoList = offer.getAttrInfoArr();
						Map<String, String> mapList = new LinkedHashMap<>();
                        for(AttrInfo attrInfo :attrInfoList){
                        	//判断时间是否有效
                        	if("151000035525".equals(attrInfo.getAttrId())){
                        		long effTime = DateUtils.parse(attrInfo.getEffTime(), DateUtils.DATETIME_YYYY_MM_DD_HH_MM_SS).getTime();
                        	    long exfTime = DateUtils.parse(attrInfo.getExfTime(), DateUtils.DATETIME_YYYY_MM_DD_HH_MM_SS).getTime();
                        	    long nowTime = System.currentTimeMillis();
                        	    if(nowTime<effTime||nowTime>exfTime){
                        	    	map.put("retCode", RESULT_SUCC);
            						map.put("attrList", new ArrayList<>());
            						resultJson = JsonUtils.getJsonStr(map);
            						return resultJson;
                        	    }
                        	}
                        	//根据attrId确定返回值
                        	if("151000035524".equals(attrInfo.getAttrId())){
                        		mapList.put("brand", attrInfo.getAttrValue());
                        	}
                        	if("151000035523".equals(attrInfo.getAttrId())){
                        		mapList.put("type", attrInfo.getAttrValue());
                        	}
                        	if("151000035522".equals(attrInfo.getAttrId())){
                        		mapList.put("IMEI", attrInfo.getAttrValue());
                        	}
                        }
						list.add(mapList);
					}
					// 调用成功封装返回list
					map.put("retCode", RESULT_SUCC);
					map.put("attrList", list);
				} else {
					map.put("retCode", POST_NENGKAI_ERRO);
				}
			} catch (Exception e) {
				e.printStackTrace();
				map.put("retCode", POST_NENGKAI_ERRO);
			}
			
			resultJson = JsonUtils.getJsonStr(map);
			return resultJson;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("retCode", SYSO_ERRO);
			serverLog.setSysError(e.getMessage()+"");
			return JsonUtils.getJsonStr(map);
		} finally {
			// 保存日志
			serverLog.setRetCode(map.get("retCode").toString());
			serverLog.setReturnJson(resultJson);
			serverLog.setResponseTime(DateUtils.dateToTimestamp(new Date()));
			mobileServerLogDao.save(serverLog);
		}

	}

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		List<String> names = new ArrayList<>();
		Map<String, String> map = new LinkedHashMap<>();
		map.put("appId", "12345");
		map.put("queryCode", "12345");
		map.put("phone", "13810933637");
		map.put("offerId", "111004265660");
		map.put("requestTime", "20190108112000");
		names.addAll(map.keySet());
		System.out.println(names);
		Collections.sort(names);
		System.out.println(names);
		String signKey = "1234";
		String sign = "1234";

		for (String key : names) {
			sign += key + map.get(key);
		}
		sign = sign + signKey;
		System.out.println(sign);
		System.out.println(MD5Utils.encode(sign));
		map.put("sign", MD5Utils.encode(sign));
		String result = "";
		System.out.println("请求接口入参:"+map);
		try {
//			result = HttpUtils.postByHttps("https://221.179.129.243/usersub/sjzh/qryimei", "", JsonUtils.getJsonStr(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("请求接口出参:"+result);
	}


}
