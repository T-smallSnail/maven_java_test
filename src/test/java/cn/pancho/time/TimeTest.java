package cn.pancho.time;

import com.bmcc.vgop.common.utils.date.DateUtils;
import com.bmcc.vgop.common.utils.encrypt.AesUtils;
import com.bmcc.vgop.common.utils.encrypt.MD5Utils;
import com.bmcc.vgop.common.object.web.http.HttpRequestObj;
import com.bmcc.vgop.common.utils.http.HttpUtils;
import com.bmcc.vgop.common.utils.string.JsonUtils;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author ：pancho
 * @date ：Created in 2019/5/7 10:41
 * @description :时间测试
 */
public class TimeTest {


    @Test
    public void timeday(){
        System.out.println(DateUtils.format(new Date(),DateUtils.DATE_YYYYMMDD)+getOrderNum());

        String as = MessageFormat.format("adj{0}","hhh");
        System.out.println(as);
    }


    public String getOrderNum() {
        try {
            Thread.sleep(1L);
        } catch (InterruptedException var6) {
        }

        int passLength = 4;
        StringBuffer buffer = null;
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        r.setSeed((new Date()).getTime());
        buffer = new StringBuffer("0123456789");
        int range = buffer.length();

        for(int i = 0; i < passLength; ++i) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }

        return sb.toString();
    }



    public static void main(String args[]) throws Exception {
        String url = "https://pre-ott-api.youku.com/ottopen/beijingcmcc/order/confirm";
        String orderCheckInfAppKey = "60ee6f1aae8dd56aac5e2ed5e6998649";
        String thirdPartyAesKey = MD5Utils.encode(orderCheckInfAppKey).toUpperCase().substring(3, 19);
        String thirdPartyAppId = "00683930f9a78ec5";

        //
        Map<String, String> postData = new HashMap();
        postData.put("appId", thirdPartyAppId);
        postData.put("phone", "13810132294");
        postData.put("tranId", "123333333333333");
        postData.put("authorization", AesUtils.encryptBase64Mode(thirdPartyAesKey, thirdPartyAppId + System.currentTimeMillis()));


        Map<String, String> propertyMap = new HashMap<>();
        propertyMap.put("Content-Type", "application/json;charset=UTF-8");



        System.out.println("接口地址为：" + url);
        System.out.println("接口请求参数:" + JsonUtils.getJsonStr(postData));

        HttpRequestObj httpRequestObj = new HttpRequestObj(url, null, JsonUtils.getJsonStr(postData), 10000, 30000, propertyMap);
        String response;
        if (url.startsWith("https")) {
            response = HttpUtils.postByHttps(httpRequestObj);
        } else {
            response = HttpUtils.postByhttp(httpRequestObj);
        }


        System.out.println("接口返回结果:" + response);
    }
}
