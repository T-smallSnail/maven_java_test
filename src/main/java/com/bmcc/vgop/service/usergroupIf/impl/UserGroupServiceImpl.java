package com.bmcc.vgop.service.usergroupIf.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmcc.vgop.common.consts.DataConsts;
import com.bmcc.vgop.common.utils.log.LogUtils;
import com.bmcc.vgop.data.usergroup.UserGroupDao;
import com.bmcc.vgop.data.usergroup.po.UserGroupQueryLog;
import com.bmcc.vgop.service.usergroupIf.UserGroupService;
import com.bmcc.vgop.web.usergroup.vo.QueryObj;
import com.bmcc.vgop.web.usergroup.vo.Results;
import com.bmcc.vgop.web.usergroup.vo.UserGroupReq;
import com.bmcc.vgop.web.usergroup.vo.UserGroupResp;

import ch.qos.logback.classic.Logger;

@Service
public class UserGroupServiceImpl implements UserGroupService {
	private final static Logger logger = (Logger) LoggerFactory.getLogger(UserGroupServiceImpl.class);
	
	@Autowired
	private UserGroupDao userGroupDao;

	private boolean isRightCode(UserGroupReq req) {
		boolean ret = true;
		if(req.getQueryObj().length<1) {
			ret = false;
		}else {
			for(QueryObj query:req.getQueryObj()){
				if(DataConsts.GROUP_CODE_SQL.get(query.getCode())==null){
					ret = false;
					break;
				}
			}
		}
		return ret;
	}
	
	private boolean isRightParam(UserGroupReq req) {
		boolean ret = true;
		if(req.getPhone()==null||req.getPhone().length()!=11){
			ret = false;
			return ret;
		}
		if(req.getChannelId()==null||req.getChannelId().length()>15){
			ret = false;
			return ret;
		}
		if(req.getTransId()==null||req.getTransId().length()>8){
			ret = false;
			return ret;
		}
		return ret;
	}
	
	@Override
	public UserGroupResp userGroupQuery(UserGroupReq req) {
		UserGroupResp resp = new UserGroupResp();
		if(req==null){
			resp.setMsg("2");
			return resp;
		}
		
		if(!this.isRightParam(req)){
			resp.setMsg("2");
			return resp;
		}
		
		if(!this.isRightCode(req)){
			resp.setMsg("3");
			return resp;
		}
		
		UserGroupQueryLog log =userGroupDao.saveRightLog(req);
		QueryObj[] queryArr = req.getQueryObj();
		List<Results> resultList = new ArrayList<Results>();
		Results result = null;
		String sql = null;
		
		try {
			for(QueryObj query:queryArr) {
				sql = DataConsts.GROUP_CODE_SQL.get(query.getCode());
				result = new Results();
				result.setCode(query.getCode());
				result.setGroupid(userGroupDao.getRetBySql(sql, req.getPhone()));
				resultList.add(result);
			}
			resp.setResults(resultList.toArray(new Results[]{}));
			resp.setMsg("0");
		} catch (Exception e) {
			LogUtils.printException(logger, e);
			resp.setMsg("1");
		}
		
		userGroupDao.updateGroupQueryLog(resp, log);
		return resp;
	}

}
