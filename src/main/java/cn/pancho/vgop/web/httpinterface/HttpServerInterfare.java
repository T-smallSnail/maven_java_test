package cn.pancho.vgop.web.httpinterface;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ：pancho
 * @date ：Created in 2019/12/13 16:49
 * @description :基于http协议的接口
 *
 */
public class HttpServerInterfare {


    /**
     * 接口采用post请求
     * 数据传输为Json格式
     * 安全上请求方采用md5Key对进行加密签名，接收方验证数据正确性
     * @param param
     * @return
     */
    @RequestMapping(value = "/sendSmsMsg", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public String sendSmsMsg(@RequestBody String param) {
        return null;
    }
}
