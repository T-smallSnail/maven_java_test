package com.bmcc.vgop.web.imeiqry.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bmcc.vgop.service.mobileSever.MobileService;


/**
 * 手机置换，imei查询
 *
 */
@Controller
public class ImeiQryController {

	@Autowired
	MobileService mobileService;
	
	@RequestMapping(value = "/sjzh/qryimei",method = {RequestMethod.POST},produces = "text/json;charset=utf-8")
	@ResponseBody
	public String getMobileInfo(@RequestBody String para,HttpServletRequest request){
		
		
		String mobileInfo = mobileService.getMobileInfo(para,request);
		
		return mobileInfo;
	
		
	}
	

}
