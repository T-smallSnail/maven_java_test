package cn.pancho;


import com.bmcc.vgop.common.utils.date.DateUtils;
import com.bmcc.vgop.common.utils.encrypt.AesUtils;
import com.bmcc.vgop.common.utils.encrypt.MD5Utils;
import com.bmcc.vgop.common.utils.encrypt.SignUtils;
import com.bmcc.vgop.common.utils.encrypt.TripleDES;
import com.bmcc.vgop.common.utils.string.StringUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
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
        String appId = "100002";
        String text = appId + "1616154480000";
        String appKey = "958ad24fd7fa474ba7ee4435addfd808";
        String aseKey = MD5Utils.encode(appKey).toUpperCase().substring(3, 19);
        String Authorization = AesUtils.encryptBase64Mode(aseKey, text);
        System.out.println("aesKey=" + aseKey);
        System.out.println("Authorization=="+Authorization);
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
     * @author : pancho
     * @date : 2020/11/11 18:38
     * @return : void
     */
    @Test
    public void fiveGMessage() throws Exception {

        String phone_activityId = "157100775747";
        String s = AesUtils.encrypUrlSafeBase64Mode("a6b2e60262a5465397cf197f2afaa45f", phone_activityId);
        String encode = URLEncoder.encode(s, "UTF-8");
        String decode = URLDecoder.decode(encode, "UTF-8");

        System.out.println("encode="+encode);
        System.out.println("decode="+decode);

        String decryptBase64Mode = AesUtils.decryptBase64Mode("a6b2e60262a5465397cf197f2afaa45f", decode);
        System.out.println("decryptBase64Mode="+decryptBase64Mode);

    }

    @Test
    public void timw111111() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, String> signMap = new HashMap();
        signMap.put("appId", "1234");
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

        System.out.println("11111="+argvList.get(3));

        argvList.set(0,"158");
        argvList.set(3,"123");


        System.out.println("222=" + argvList.get(3));

    }


    @Test
    public void sf() throws UnsupportedEncodingException {

        String expTime= "20200731235959";
        Date parse = DateUtils.parse(expTime, DateUtils.DATETIME_YYYYMMDDHHMMSS);
        if(parse.after(new Date())){
            System.out.println("1111111");
        }else{
            System.out.println("000000000");
        }



    }

    @Test
    public void getgdfgfh() throws UnsupportedEncodingException, NoSuchAlgorithmException, ParseException {
        Map<String, Object> map = new HashMap<>();
        map.put("dsf","sf");

        String phone = (String) map.get("phone");
        System.out.println(phone);

    }


    @Test
    public void getUUID() {
        for (int i = 0; i < 4; i++) {
            System.out.println(StringUtils.getUUID());
        }

    }



}
