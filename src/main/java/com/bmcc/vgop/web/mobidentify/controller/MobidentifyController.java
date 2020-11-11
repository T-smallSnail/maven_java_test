package com.bmcc.vgop.web.mobidentify.controller;

import com.bmcc.vgop.common.consts.http.HttpConsts;
import com.bmcc.vgop.common.utils.string.JsonUtils;
import com.bmcc.vgop.service.mobidentify.MobidentifyService;
import com.bmcc.vgop.web.mobidentify.vo.RespObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bmcc.vgop.common.data.jedis.JedisClient;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/mobile/type")
public class MobidentifyController {

    @Autowired
    JedisClient JedisClient;
    @Autowired
    MobidentifyService mobidentifyService;

    @RequestMapping(value = "/query",produces = HttpConsts.CONTENT_TYPE_JSON)
    @ResponseBody
    public String mobileIdentify(@RequestBody Map<String, String> paramMap, HttpServletRequest request){
        RespObj respObj = mobidentifyService.bjMobileIndetify(paramMap, request);
        return JsonUtils.getJsonStr(respObj);
    }

}
