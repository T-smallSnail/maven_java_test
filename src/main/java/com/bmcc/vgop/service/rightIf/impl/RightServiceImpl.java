package com.bmcc.vgop.service.rightIf.impl;

import ch.qos.logback.classic.Logger;
import com.bmcc.vgop.common.consts.DataConsts;
import com.bmcc.vgop.data.rightlog.RightLogDao;
import com.bmcc.vgop.data.user.dao.UserDao;
import com.bmcc.vgop.service.crmsc.CrmScService;
import com.bmcc.vgop.service.rightIf.RightService;
import com.bmcc.vgop.web.subserv.vo.Bizs;
import com.bmcc.vgop.web.subserv.vo.SubReq;
import com.bmcc.vgop.web.subserv.vo.SubResp;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RightServiceImpl implements RightService {
	private final static Logger logger = (Logger) LoggerFactory.getLogger(RightServiceImpl.class);

	@Autowired
	RightLogDao logDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	CrmScService crmScService;
	
	private boolean isRightBiz(SubReq req) {
		boolean ret = true;
		if(req.getBizs().length<1) {
			ret = false;
		}else {
			for(Bizs biz:req.getBizs()){
				if(DataConsts.BIZCODE_OFFERID.get(biz.getBizCode())==null){
					ret = false;
					break;
				}
			}
		}
		return ret;
	}
	
	private boolean isRightParam(SubReq req) {
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
	public SubResp subProcess(SubReq req) {
	    return null;
    }

}
