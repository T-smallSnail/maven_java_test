package com.bmcc.vgop.web.usergroup.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bmcc.vgop.service.usergroupIf.UserGroupService;
import com.bmcc.vgop.web.usergroup.vo.UserGroupReq;
import com.bmcc.vgop.web.usergroup.vo.UserGroupResp;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

/**
 *提供用户所属群体的查询
 */
@RestController
public class GroupQryController {
	
	private final static Logger logger = (Logger) LoggerFactory.getLogger(GroupQryController.class);
	
	@Autowired
	UserGroupService userGroupService;
	@RequestMapping(value = "/ugroupquery", produces = { "application/json;charset=UTF-8" }, method = {RequestMethod.POST, RequestMethod.GET })
	public UserGroupResp sub(@RequestBody(required = false) UserGroupReq req) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		logger.info(mapper.writeValueAsString(req));
		UserGroupResp resp = userGroupService.userGroupQuery(req);
		return resp;
	}

}
