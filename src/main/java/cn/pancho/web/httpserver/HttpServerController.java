package cn.pancho.web.httpserver;

import com.bmcc.vgop.common.utils.string.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：pancho
 * @date ：Created in 2020/11/12 16:50
 * @description :Http服务端接口
 */
@RestController
@RequestMapping("/httpserver")
public class HttpServerController {

    private static ObjectMapper mapper = new ObjectMapper();


    /**
     * 接口采用post请求
     * 数据传输为Json格式
     * @param param
     * @return
     */
    @RequestMapping(value = "/test", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public String test(@RequestBody String param) {

        //解析参数
        try {
            Map<String, Object> jsonMap = mapper.readValue(param, Map.class);
            String appId = jsonMap.get("appId").toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return "success";
    }

}
