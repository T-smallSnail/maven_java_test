package cn.pancho.crm;

import com.asiainfo.encrypt.utils.RSAUtils;
import com.asiainfo.encrypt.utils.SecurityUtils;
import com.bmcc.vgop.common.utils.date.DateUtils;
import com.bmcc.vgop.common.object.web.http.HttpRequestObj;
import com.bmcc.vgop.common.utils.http.HttpUtils;
import com.bmcc.vgop.webclient.common.consts.crmopenapi.AbilityCode;
import com.bmcc.vgop.webclient.service.crmopenapi.obj.CrmApiResp;
import com.bmcc.vgop.webclient.service.crmopenapi.utils.AbilityParamUtils;
import com.bmcc.vgop.webclient.service.crmopenapi.utils.EncryptUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrmTestInMain {
    //测试
//    public static String ABILITY_URL = "http://10.4.61.68:20110/oppf";
    public static String ABILITY_URL = "http://10.7.5.94:20110/oppf";
    //      public static String ABILITY_URL = "http://10.7.5.94:20410/oppf";
    public static String FORMAT = "xml";
    public static String APP_ID = "609152";
    public static String SEC_KEY = "814da0756a157e374393b10c16f4f48d";
    public static String PUB_KEY = "MGcwDQYJKoZIhvcNAQEBBQADVgAwUwJMAJW6qaR6QdspJQ0EB0VT1+6C+S8m6rddnZczRgCUkf473ClNgXJAAyPtEwAR6+yRK0p8lHPCdaDbkfc17tSQ5081kJmQk20V8qUZzQIDAQAB";
    public static String SIGN_TYPE = "RSA";
    public static String ENCRYPT_TYPE = "AES";

    //生产
//	  public static String ABILITY_URL =  "http://service.osp.bmcc.com.cn:20255/oppf";
//	  public static String FORMAT = "xml";
//	  public static String APP_ID = "501483";
//	  public static String SEC_KEY = "f823ee2f08c69c6e54146ddfc49b47e1";
//	  public static String PUB_KEY = "MGcwDQYJKoZIhvcNAQEBBQADVgAwUwJMAN52n5VPUeh9hfvgufKPZeCudZZV0cZEtL6YD10Ph4QleKJA3pm6AmbNzQB/2iSqwESKxeLodFurve003Ec1oP2t4PTA/yBWQgF14QIDAQAB";
//	  public static String SIGN_TYPE = "RSA";
//	  public static String ENCRYPT_TYPE = "AES";


    public static void main(String[] args) throws Exception {
        String mm = "qth1345u321405143obtj2o43t";
        String random = "vt24kvt23nltnv32nyl32ny";
        CrmApiResp resp = null;

        System.out.println("请求的url:" + ABILITY_URL);
//		resp = commonApiService(AbilityCode.OPF_SPT_DZQFF_UPD_001, AbilityParamUtils.get_OPF_SPT_DZQFF_UPD_001_Xml("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"));
//        resp = commonApiService(AbilityCode.OPF_SO_PERSON_MOBILIMEICH_QRY_001, AbilityParamUtils.get_OPF_SO_PERSON_MOBILIMEICH_QRY_001_Xml("158011346765"));

        //crm订购
        resp = commonApiService(AbilityCode.OPF_SO_PERSON_VAS_CREATE_015, AbilityParamUtils.get_OPF_SO_PERSON_VAS_CREATE_015_Xml("15810860391", "111004266197"));
        System.out.println("respCode=" + resp.getRespCode());
        System.out.println("respDesc=" + resp.getRespDesc());
        System.out.println("result=" + resp.getResult());
    }

    public static String get_OPF_EC_WEIXIN_LOGIN_CHECK_001(String mm, String random) {
        return "<MM>" + mm + "</MM>" + "<RANDOM>" + random + "</RANDOM>";
    }

    public static CrmApiResp commonApiService(String method, String busiParam) throws Exception {
        Date date = new Date();
        String timestamp = DateUtils.format(date, DateUtils.DATETIME_YYYYMMDDHHMMSS);
        String busiSerial = "2015060611223320345443"; // 暂时写死

        busiParam = getPostBusiParam(busiParam);
        String sign = getSign(method, busiParam, timestamp, busiSerial);
        String systemParam = getUrlParam(sign, method, timestamp, busiSerial);
        String response = HttpUtils.postByhttp(new HttpRequestObj(ABILITY_URL, systemParam, busiParam, 3000, 5000, null));
        System.out.println("调用能开返回报文:" + response);
        return getCrmApiResp(response);
    }

    public static String getPostBusiParam(String busiXml) throws Exception {
        String busiParam = "<AICRMSERVICE>" + "<PUBINFO>" + "<OUTSN/>" + " <SYSID>99990001</SYSID>" + "<SYSPWD>7c6a180b36896a0a8c02787eeafb0e4c</SYSPWD>" + "<OPCODE>BJCRMVGOP</OPCODE>" + "<OPPWD>7c6a180b36896a0a8c02787eeafb0e4c</OPPWD>" + "<OPORGID>1</OPORGID>" + "<ESBSN>cpcrm3^14181349930000000001</ESBSN>" + "<TRANSACTIONTIME>20141225150401124</TRANSACTIONTIME>" + "<INTERFACEID>100000000001</INTERFACEID>" + "<CHNLTYPE>1</CHNLTYPE>" + "</PUBINFO>" + "<REQUEST>" + "<BUSIPARAMS>" + busiXml + "</BUSIPARAMS>" + "</REQUEST>" + "</AICRMSERVICE>";
        // System.out.println("send业务xml：" + busiParam);
        if (ENCRYPT_TYPE != null && !ENCRYPT_TYPE.equals("")) {
            String dataSecret = "RSA".equals(ENCRYPT_TYPE) ? PUB_KEY : SEC_KEY; // 应用钥匙
            // ,RSA采用应用公钥,AES采用应用私钥
            busiParam = encryptBusiXml(busiParam, ENCRYPT_TYPE, dataSecret);
        }
        // System.out.println("业务xml加密后：" + busiParam);
        return busiParam;
    }

    public static String getSign(String method, String busiParam, String timestamp, String busiSerial) throws Exception {
        String dataSecret = "RSA".equals(SIGN_TYPE) ? PUB_KEY : SEC_KEY; // 应用钥匙,RSA采用应用公钥,SHA采用密钥
        String sign = EncryptUtils.crmOpenApiSign(getSystemParamMap(method, timestamp, busiSerial), busiParam, "RSAWithMD5", dataSecret);
        return sign;
    }

    private static String getUrlParam(String sign, String method, String timeStamp, String busiSerial) {
        String systemParam = "timestamp=" + timeStamp + "&operId=10000&busiSerial=" + busiSerial + "&appId=" + APP_ID + "&accessToken=2cb147ec-1652-4d56-8dc5-5e3d4509aae3&method=" + method + "&format=" + FORMAT + "&version=1.1.3&openId=12000&sign=" + sign;
        return systemParam;
    }

    private static CrmApiResp getCrmApiResp(String xml) throws Exception {
        if (xml == null || "".equals(xml)) {
            return null;
        }
        StringBuilder xmlLog = new StringBuilder(1000);
        xmlLog.append("crm_open_api_resp_start****\r\n" + xml + "\r\n****end");
        try {
            CrmApiResp resp = new CrmApiResp();
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();

            Element respCode = root.element("respCode");
            if (respCode != null) {
                resp.setRespCode(respCode.getText());
            }

            Element respDesc = root.element("respDesc");
            if (respDesc != null) {
                resp.setRespDesc(respDesc.getText());
            }

            Element result = root.element("result");
            if (result != null) {
                if (result.getText() != null && !result.getText().equals("")) {
                    resp.setResult(SecurityUtils.decodeAES256HexUpper(result.getText(), SecurityUtils.decodeHexUpper(SEC_KEY)));
                    xmlLog.append("\r\n解密result：" + resp.getResult());
                }
            }
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CrmApiResp();
    }


    private static String encryptBusiXml(String busiParam, String encryptType, String dataSecret) throws Exception {
        // 去掉头顶的<xml....?>
        busiParam = busiParam.replaceAll("<\\?xml\\s*version=\"1.0\"\\s*encoding=\"(.*?)\"\\?>", "");
        busiParam = busiParam.replaceAll("[\\n\\r]", ""); // 换行替换

        String encryptionContent = "";
        try {
            if (encryptType.endsWith("RSA")) {
                encryptionContent = RSAUtils.encryptByPublicKey(busiParam, dataSecret);
            } else if (encryptType.endsWith("AES")) {
                encryptionContent = SecurityUtils.encodeAES256HexUpper(busiParam, SecurityUtils.decodeHexUpper(dataSecret));
            } else {
                encryptionContent = busiParam;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encryptionContent;
    }

    

    private static Map<String, String> getSystemParamMap(String method, String timestamp, String busiSerial) {
        Map<String, String> sysParam = new HashMap<String, String>();
        sysParam.put("method", method);
        sysParam.put("format", FORMAT);
        sysParam.put("appId", APP_ID);
        sysParam.put("operId", "10000");
        sysParam.put("openId", "12000");
        sysParam.put("version", "1.1.3");
        sysParam.put("accessToken", "2cb147ec-1652-4d56-8dc5-5e3d4509aae3");
        sysParam.put("timestamp", timestamp);
        sysParam.put("busiSerial", busiSerial);
        return sysParam;
    }
}
