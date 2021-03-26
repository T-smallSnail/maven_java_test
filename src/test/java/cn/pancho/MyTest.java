package cn.pancho;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bmcc.vgop.common.object.web.http.HttpRequestObj;
import com.bmcc.vgop.common.utils.date.DateUtils;
import com.bmcc.vgop.common.utils.encrypt.AesUtils;
import com.bmcc.vgop.common.utils.encrypt.MD5Utils;
import com.bmcc.vgop.common.utils.encrypt.SignUtils;
import com.bmcc.vgop.common.utils.encrypt.TripleDES;
import com.bmcc.vgop.common.utils.http.HttpUtils;
import com.bmcc.vgop.common.utils.string.JsonUtils;
import com.bmcc.vgop.common.utils.string.StringUtils;
import org.elasticsearch.common.logging.JsonThrowablePatternConverter;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：pancho
 * @date ：Created in 2019/7/29 14:32
 * @description :
 */
public class MyTest {

    @Test
    public void aes() throws Exception {
        String appId = "100001";
        String text = appId + "2616140552099";
        String appKey = "12ccb97166b44f85a04338d043e9ef90";
        String aseKey = MD5Utils.encode(appKey).toUpperCase().substring(3, 19);
        String Authorization = AesUtils.encryptBase64Mode(aseKey, text);
        System.out.println("aesKey=" + aseKey);
        System.out.println("Authorization==" + Authorization);
    }

    /**
     * 解密Authorization
     *
     * @return : void
     * @author : pancho
     * @date : 2020/3/18 15:55
     */
    @Test
    public void deaes() throws Exception {

        String appKey = "19671de239d14608b41b6039187208d0";
        String aseKey = MD5Utils.encode(appKey).toUpperCase().substring(3, 19);

        String text = "dEhgtNJ8675Hf2e3hMHhf/2MsvDfV+60C9K47QfyMW0=";
        String s = AesUtils.decryptBase64Mode(aseKey, text);
        System.out.println(s);
    }

    /**
     * 5G消息手机号登陆
     *
     * @return : void
     * @author : pancho
     * @date : 2020/11/11 18:38
     */
    @Test
    public void fiveGMessage() throws Exception {

        String phone_activityId = "157100775747";
        String s = AesUtils.encrypUrlSafeBase64Mode("a6b2e60262a5465397cf197f2afaa45f", phone_activityId);
        String encode = URLEncoder.encode(s, "UTF-8");
        String decode = URLDecoder.decode(encode, "UTF-8");

        System.out.println("encode=" + encode);
        System.out.println("decode=" + decode);

        String decryptBase64Mode = AesUtils.decryptBase64Mode("a6b2e60262a5465397cf197f2afaa45f", decode);
        System.out.println("decryptBase64Mode=" + decryptBase64Mode);

    }

    @Test
    public void timw111111() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, String> signMap = new HashMap();
        signMap.put("appId", "20210304");
        signMap.put("msisdn", "15810860391");
        signMap.put("type", "1");
        signMap.put("offerCode", "111004265820");
        signMap.put("requestTime", "20191211162020");
        signMap.put("validCode", "112057");


        String signChe = SignUtils.getSign("1234", signMap);
        System.out.println("=======" + signChe);

    }

    @Test
    public void timeTest() {
        String s = TripleDES.encryptAndBase64("15810860391-1", "dfkh_K18h,9_chinamobile_".getBytes());
        System.out.println("加密后======" + s);
        String phone = TripleDES.decryptAndBase64(s, "dfkh_K18h,9_chinamobile_".getBytes());
        System.out.println("解密后===" + phone);
    }

    @Test
    public void timddfsg() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, String> signMap = new HashMap();
        signMap.put("appId", "1234");
        signMap.put("requestTime", "20191211162020");
        signMap.put("accessToken", "C95C569CAF4AD203C04EB9F682C39B8D");

        String signChe = SignUtils.getSign("1234", signMap);
        System.out.println("=======" + signChe);

    }


    @Test
    public void timw2222() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        List<String> argvList = new ArrayList();
        argvList.add("phone");
        argvList.add("1");
        argvList.add("1");
        argvList.add("mktId");
        argvList.add("1");

        System.out.println("11111=" + argvList.get(3));

        argvList.set(0, "158");
        argvList.set(3, "123");


        System.out.println("222=" + argvList.get(3));

    }


    /**
     * 乐淘结构数据解析
     * @author : pancho
     * @date : 2021/3/18 19:01
     * @return : void
     */
    @Test
    public void sf() throws UnsupportedEncodingException {

        String expTime = "{\"message\":\"操作成功\",\"code\":0,\"data\":[{\"coupon\":{\"couponManageId\":\"822124108882952192\",\"otherOrderId\":\"12342400012134300017\",\"couponName\":\"微信10元代金券【北京乐淘测试】\",\"orderId\":\"822124108841009152\",\"validityUse\":\"2021.04.02\",\"couponCode\":\"17493115\",\"activeCode\":\"\"},\"recharge\":{\"rstCode\":\"90\",\"rstMsg\":\"充值接口调用失败，返回信息:appid与openid不匹配\"}}]}";

        Map<String, Object> jsonMap = JsonUtils.getJsonMap(expTime);
        String message = jsonMap.get("message").toString();
        Integer code = Integer.valueOf(jsonMap.get("code").toString());
        List<Map<String,Object>> data = (List<Map<String, Object>>) jsonMap.get("data");


        Map<String,String> rechargeMap = (Map<String, String>) data.get(0).get("recharge");
        String rstCode = rechargeMap.get("rstCode");
        String rstMsg = rechargeMap.get("rstMsg");

        Map<String,String> couponMap = (Map<String, String>) data.get(0).get("coupon");
        String orderId = couponMap.get("orderId");
        String otherOrderId = couponMap.get("otherOrderId");
        String couponManageId = couponMap.get("couponManageId");
        String couponName = couponMap.get("couponName");
        String couponCode = couponMap.get("couponCode");
        String activeCode = couponMap.get("activeCode");
        String validityUse = couponMap.get("validityUse");

        System.out.println("data==" + data);
    }

    /**
     * 参数签名(乐淘调用恒通接口)
     *
     * @author : pancho
     * @date : 2021/3/18 19:02
     * @param : args
     * @return : void
     */
    @Test
    public void cyzSign() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Long time = System.currentTimeMillis();
        Map<String, String> signMap = new HashMap();
        signMap.put("appId", "20210304");
        signMap.put("apiVersion", "v1.0");
        signMap.put("timestamp", time.toString());
        //signMap.put("timestamp", "1616140552099");
        signMap.put("appId", "20210304");
        signMap.put("otherOrderId", "19999400012156344530019190");
        signMap.put("proIdent", "100042");
        signMap.put("mobilePhone", "15810860391");
        signMap.put("ip", "12");
        signMap.put("openid", "21243re34gdft45rtg");
        signMap.put("source", "miniprogram");

        String signChe = SignUtils.getCyzlSign("TESTLETAO", signMap);
        System.out.println("当前时间戳=======" + time);
        System.out.println("sign=======" + signChe);
    }

    @Test
    public void cyzSignTest() throws Exception {
        Long time = System.currentTimeMillis();
        Map<String, String> signMap = new HashMap();
        signMap.put("appId", "20210304");
        signMap.put("apiVersion", "v1.0");
        signMap.put("timestamp", time.toString());

        signMap.put("appId", "20210304");
        signMap.put("otherOrderId", "199998740006703435");
        signMap.put("proIdent", "100042");
        signMap.put("mobilePhone", "15810860391");
        //signMap.put("ip", "12.43.123.42");
        signMap.put("source", "miniprogram");
        signMap.put("openid", "oAhQb4yGFQN4wpUdPZ6b62g0w0wI");
        signMap.put("wxAppId", "wx00f920a2b8e30d24");


        String signChe = SignUtils.getCyzlSign("TESTLETAO", signMap);
        //System.out.println("当前时间戳=======" + time);
        System.out.println("sign=======" + signChe);

        //请求头
        Map<String, String> propertyMap = new HashMap<>();
        propertyMap.put("Content-Type","application/json;charset=UTF-8");
        propertyMap.put("accessToken","MjAyMTAzMDQ6VEVTVExFVEFPOmUyNmZjNmIzNWRkODQyM2JhZWI3M2NkYjVjYzY1MzNj");
        propertyMap.put("appId","20210304");
        propertyMap.put("apiVersion","v1.0");
        propertyMap.put("timestamp",time.toString());
        propertyMap.put("sign",signChe);


        //请求参数
        Map<String, Object> post = new HashMap<>();
        post.put("appId", signMap.get("appId"));
        post.put("otherOrderId", signMap.get("otherOrderId"));
        post.put("proIdent", signMap.get("proIdent"));
        post.put("mobilePhone", signMap.get("mobilePhone"));
        post.put("ip", signMap.get("ip"));
        post.put("source", signMap.get("source"));
        post.put("openid", signMap.get("openid"));
        post.put("wxAppId", signMap.get("wxAppId"));

        String postData = JsonUtils.getJsonStr(post);
        String url = " https://etctest.cyzl.com/api/v1/clien/openapi/immediateGetRecharge";

        HttpRequestObj httpRequestObj = new HttpRequestObj(url, null, postData, 10000, 30000, propertyMap);
        String response = HttpUtils.postByHttps(httpRequestObj);

        System.out.println("返回结果======" + response);
    }



    @Test
    public void getgdfgfh() throws UnsupportedEncodingException, NoSuchAlgorithmException, ParseException {
        Map<String, Object> map = new HashMap<>();
        map.put("dsf", true);

        String jsonStr = JsonUtils.getJsonStr(map);
        Map<String, Object> jsonMap = JsonUtils.getJsonMap(jsonStr);
        Boolean dsf = (Boolean) jsonMap.get("dsf");


        String type = (String) null;
        if (type!=null&&type.equals("1")) {

        }else{
            System.out.println("12");
        }

        System.out.println(dsf);

    }


    /**
     * 获取uuid
     * @author : pancho
     * @date : 2021/3/24 15:55
     * @return : void
     */
    @Test
    public void getUUID() {
        for (int i = 0; i < 2; i++) {
            System.out.println(StringUtils.getUUID());
        }

    }


}
