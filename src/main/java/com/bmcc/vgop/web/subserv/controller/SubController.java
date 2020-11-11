package com.bmcc.vgop.web.subserv.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bmcc.vgop.service.rightIf.RightService;
import com.bmcc.vgop.web.subserv.vo.SubReq;
import com.bmcc.vgop.web.subserv.vo.SubResp;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

/**
 *用户权益订购接口，为北京本地免费的139邮箱用户权益和云视界用户权益，提供白名单查询、业务订购的统一接口。
 */
@RestController
public class SubController {

	private final static Logger logger = (Logger) LoggerFactory.getLogger(SubController.class);

	@Autowired
	RightService rightService;

	@RequestMapping(value = "/userright", produces = { "application/json;charset=UTF-8" }, method = {RequestMethod.POST, RequestMethod.GET })
	public SubResp sub(@RequestBody(required = false) SubReq req) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		logger.info(mapper.writeValueAsString(req));
		SubResp resp = rightService.subProcess(req);
		return resp;
	}

}
