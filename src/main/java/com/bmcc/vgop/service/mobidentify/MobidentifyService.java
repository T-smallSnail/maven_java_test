package com.bmcc.vgop.service.mobidentify;

import com.bmcc.vgop.web.mobidentify.vo.RespObj;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface MobidentifyService {

    RespObj bjMobileIndetify(Map<String,String> paramMap, HttpServletRequest request);
}
