package com.bmcc.vgop.service.insidechannelorder.impl;

import ch.qos.logback.classic.Logger;
import com.bmcc.vgop.common.consts.DataConsts;
import com.bmcc.vgop.common.consts.InsideChannelConts;
import com.bmcc.vgop.common.utils.date.DateUtils;
import com.bmcc.vgop.common.utils.encrypt.AesUtils;
import com.bmcc.vgop.common.utils.encrypt.MD5Utils;
import com.bmcc.vgop.common.utils.encrypt.TripleDES;
import com.bmcc.vgop.common.object.web.http.HttpRequestObj;
import com.bmcc.vgop.common.utils.http.HttpUtils;
import com.bmcc.vgop.common.utils.log.LogUtils;
import com.bmcc.vgop.common.utils.string.JsonUtils;
import com.bmcc.vgop.common.utils.string.StringUtils;
import com.bmcc.vgop.data.insidechannelorder.TVgopUsersubChannelInfoDao;
import com.bmcc.vgop.data.insidechannelorder.TVgopUsersubServerInsideChannelOrderProductLogDao;
import com.bmcc.vgop.data.insidechannelorder.po.TVgopUsersubServerInsideChannelOrderProductLog;
import com.bmcc.vgop.service.insidechannelorder.InsideChannelOrderProductService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：pancho
 * @date ：Created in 2020/4/22 16:56
 * @description :内部渠道订购
 */
@Service
public class InsideChannelOrderProductServiceImpl implements InsideChannelOrderProductService {

    private static Logger logger = (Logger) LoggerFactory.getLogger(InsideChannelOrderProductServiceImpl.class);

    @Autowired
    TVgopUsersubChannelInfoDao usersubChannelInfoDao;

    @Autowired
    TVgopUsersubServerInsideChannelOrderProductLogDao orderProductLogDao;

    @Override
    @Transactional(value = "mysqlTransactionManager", readOnly = false)
    public String orderProduct(String param, HttpServletRequest request) {

        // 封装返回数据
        Map<String, Object> returnMap = new HashMap();
        // 返回成功的数据
        Map<String, Object> data = new HashMap();

        // 和彩云订单确认接口参数
        Map<String, String> postData = new HashMap();

        // 新建接口调用日志
        TVgopUsersubServerInsideChannelOrderProductLog orderProductLog = new TVgopUsersubServerInsideChannelOrderProductLog();
        orderProductLog.setRequestTime(DateUtils.getNowTimeStamp());

        try {
            String credential = request.getHeader("credential");
            String appId = request.getHeader("appId");
            String authorization = request.getHeader("Authorization");

            orderProductLog.setCredential(credential);
            orderProductLog.setAppId(appId);
            orderProductLog.setAuthorization(authorization);

            //查看appId是否存在
            //从数据库中查出该appId对应的密钥appKey
            String appKey = usersubChannelInfoDao.getAppKey(appId);
            if (appKey == null) {
                return returnJson(returnMap, false, "CE999002", "AppId不存在！");
            }

            //计算aesKey
            String aesKey = MD5Utils.encode(appKey).substring(3, 19);


            //先校验密钥是否正确
            //校验用户的appId是否合法：取解密字符串前6位与appId比较
            String authorizationDecode;
            try {
                authorizationDecode = AesUtils.decryptBase64Mode(aesKey, authorization);
            } catch (Exception ae) {
                orderProductLog.setExceptionMsg(ae.toString());
                return returnJson(returnMap, false, "CE999004", "Authorization解密失败！");
            }
            String appIdsub = authorizationDecode.substring(0, 6);
            if (!appId.equals(appIdsub)) {
                return returnJson(returnMap, false, "CE999002", "AppId校验失败！");
            }
            //Authorization有效时间300秒(由于服务器时间有误，设置成30分钟有效）
            Long longTime = Long.parseLong(authorizationDecode.substring(6));
            Long time = System.currentTimeMillis();
            int miao = (int) ((time - longTime) / (1000 * 60));
            if (miao > 30) {
                return returnJson(returnMap, false, "CE999002", "Authorization超时！");
            }


            //解析请求参数
            Map<Object, Object> jsonMap = JsonUtils.getJsonMap(param);
            String phone = null;
            String offerId = null;
            String tranId = null;
            try {
                phone = (String) jsonMap.get("phone");
                offerId = (String) jsonMap.get("offerid");
                tranId = (String) jsonMap.get("tranId");
                orderProductLog.setPhone(phone);
                orderProductLog.setTranId(offerId);
                orderProductLog.setTranId(tranId);
            } catch (Exception ae) {
                orderProductLog.setExceptionMsg(ae.toString());
                return returnJson(returnMap, false, "CE999001", "接口请求参数格式有误，请检查！");
            }

            if (StringUtils.isEmpty(tranId) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(offerId)) {
                return returnJson(returnMap, false, "CE999001", "接口请求参数数量不足，请检查！");
            }


            //调用和彩云订单确认接口
            String url = DataConsts.USERSUB_CONTS.get(InsideChannelConts.ANDCLOUD_INTERFACE_URL);
            String aesKeycloud = DataConsts.USERSUB_CONTS.get(InsideChannelConts.ANDCLOUD_INTERFACE_AES_KEY);
            String appIdcloud = null;

            String aesPhone = AesUtils.encryptBase64Mode(aesKeycloud, phone);


            postData.put("appId", appIdcloud);
            postData.put("timestamp", DateUtils.format(new Date(), DateUtils.DATETIME_YYYYMMDDHHMMSS));
            postData.put("phone", aesPhone);
            postData.put("transid", tranId);

            logger.info("接口地址为：" + url);
            logger.info("接口请求参数:" + JsonUtils.getJsonStr(postData));


            HttpRequestObj httpRequestObj = new HttpRequestObj(url, null, JsonUtils.getJsonStr(postData), 10000, 30000, null);
            String response = HttpUtils.postByHttps(httpRequestObj);

            logger.info("接口返回结果:" + response);
            Map<String, Object> respMap = JsonUtils.getJsonMap(response);
            String code = respMap.get("code").toString();
            String msg = respMap.get("msg").toString();
            if (!code.equals("0")) {
                return returnJson(returnMap, false, code, msg);
            }

            String orderInfo = respMap.get("orderInfo").toString();
            Map<String, String> orderInfoMap = JsonUtils.getJsonMap(orderInfo);
            String status = orderInfoMap.get("status");

            returnMap.put("success", true);
            returnMap.put("code", status);
            if (status.equals("0")) {
                returnMap.put("msg", "未订购");
            } else if (status.equals("1")) {
                returnMap.put("msg", "订购成功");
            } else if (status.equals("2")) {
                returnMap.put("msg", "订购失败");
            }

            return JsonUtils.getJsonStr(returnMap);

        } catch (Exception e) {
            LogUtils.printException(logger, e);
            logger.info("和彩云订购接口出错：{}", e.getMessage());
            orderProductLog.setExceptionMsg(e.toString());
            return returnJson(returnMap, false, "CE999999", "平台出现未知异常！");

        } finally {

            //保存接口日志
            orderProductLog.setResponseTime(DateUtils.getNowTimeStamp());
            orderProductLog.setSuccess((Boolean) returnMap.get("success"));
            if (returnMap.get("code") != null) {
                orderProductLog.setCode((String) returnMap.get("code"));
                orderProductLog.setMsg((String) returnMap.get("msg"));
            }
            orderProductLogDao.insert(orderProductLog);

        }

    }


    private String returnJson(Map<String, Object> returnMap, boolean success, String code, String msg) {
        returnMap.put("success", success);
        returnMap.put("code", code);
        returnMap.put("msg", msg);
        return JsonUtils.getJsonStr(returnMap);


    }
}
