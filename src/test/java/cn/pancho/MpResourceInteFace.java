package cn.pancho;

import com.bmcc.vgop.common.consts.http.HttpConsts;
import com.bmcc.vgop.common.object.web.http.HttpRequestObj;
import com.bmcc.vgop.common.utils.encrypt.AesUtils;
import com.bmcc.vgop.common.utils.encrypt.MD5Utils;
import com.bmcc.vgop.common.utils.http.HttpUtils;
import com.bmcc.vgop.common.utils.string.JsonUtils;
import com.bmcc.vgop.common.utils.uniquetoken.UniqueTokenUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：pancho
 * @date ：Created in 2021/6/9 10:56
 * @description :资源中心接口
 */
public class MpResourceInteFace {

    //至真充值流量券
    @Test
    public void getResource() throws Exception {
        String mpTranId = System.currentTimeMillis() + "";
        String appId = "100003";
        String host = "andmarket.bmcc.com.cn";
        String appKey = "8f2bf182747a460cac32eca523b2ec29";
        String credential = "4fcfea440fbb4005a9edbfdcc3e73ae3";
        String url = "http://172.30.232.58:9000/mprc_web_test/s/mpResource/getResource";


        String authorization = MyTest.aes(appId, appKey);

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json;charset=UTF-8");
        headerMap.put("credential", credential);
        headerMap.put("Authorization", authorization);
        headerMap.put("appId", appId);
        headerMap.put("Host", host);


        // 获取订单中心‘生成订单’接口，header信息
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("ar_code", "18");
        requestMap.put("mp_tran_id", mpTranId);
        requestMap.put("phone", "15810860391");
//        requestMap.put("wxAppId", "wxAppId");
//        requestMap.put("openid", "openid");


        // 请求订单中心‘生成订单’接口
        HttpRequestObj httpRequestObj = new HttpRequestObj(url, null, JsonUtils.getJsonStr(requestMap), 3000, 3000, headerMap);
        String response = HttpUtils.postByhttp(httpRequestObj);

        System.out.println("返回结果==" + response);
    }





    /**
     * 乐淘查询资源列表
     * @author : pancho
     * @date : 2021/6/9 14:40
     * @return : void
     */
    @Test
    public void findResourceList()throws Exception{
        String mpTranId = System.currentTimeMillis() + "";
        String appId = "100004";
        String host = "andmarket.bmcc.com.cn";
        String appKey = "595b8d9c2eec4285a5d61d84a29db8a9";
        String credential = "72bdae4aa57348669cd49586eda84a3f";
        String url = "http://172.30.232.58:9000/mprc_web_test/s/mpResource/findResourceList";


        String authorization = MyTest.aes(appId, appKey);

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json;charset=UTF-8");
        headerMap.put("credential", credential);
        headerMap.put("Authorization", authorization);
        headerMap.put("appId", appId);
        headerMap.put("Host", host);


        // 获取订单中心‘生成订单’接口，header信息
        Map<String, Object> requestMap = new HashMap<String, Object>();

        requestMap.put("mp_tran_id", mpTranId);
        requestMap.put("c_id", "3");
        requestMap.put("phone", "15810860391");
//        requestMap.put("r_type", 3);
//        requestMap.put("openid", "openid");


        // 请求订单中心‘生成订单’接口
        HttpRequestObj httpRequestObj = new HttpRequestObj(url, null, JsonUtils.getJsonStr(requestMap), 3000, 3000, headerMap);
        String response = HttpUtils.postByhttp(httpRequestObj);

        System.out.println("返回结果==" + response);
    }





    /**
     * 乐淘使用资源
     * @author : pancho
     * @date : 2021/6/9 14:40
     * @return : void
     */
    @Test
    public void useResource()throws Exception{
        String mpTranId = System.currentTimeMillis() + "";
        String appId = "100004";
        String host = "andmarket.bmcc.com.cn";
        String appKey = "595b8d9c2eec4285a5d61d84a29db8a9";
        String credential = "72bdae4aa57348669cd49586eda84a3f";
        String url = "http://172.30.232.58:9000/mprc_web_test/s/mpResource/useResource";


        String authorization = MyTest.aes(appId, appKey);

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json;charset=UTF-8");
        headerMap.put("credential", credential);
        headerMap.put("Authorization", authorization);
        headerMap.put("appId", appId);
        headerMap.put("Host", host);


        // 获取订单中心‘生成订单’接口，header信息
        Map<String, Object> requestMap = new HashMap<String, Object>();

        requestMap.put("mp_tran_id", mpTranId);
        requestMap.put("c_id", "3");
        requestMap.put("phone", "15810860391");
        requestMap.put("cdkey", "1623209903689");
        requestMap.put("openid", "openid");


        // 请求订单中心‘生成订单’接口
        HttpRequestObj httpRequestObj = new HttpRequestObj(url, null, JsonUtils.getJsonStr(requestMap), 3000, 3000, headerMap);
        String response = HttpUtils.postByhttp(httpRequestObj);

        System.out.println("返回结果==" + response);
    }




}
