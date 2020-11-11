package com.bmcc.vgop.data.usergroup.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bmcc.vgop.data.common.db.impl.GenericDaoDb2;
import com.bmcc.vgop.data.usergroup.UserGroupDao;
import com.bmcc.vgop.data.usergroup.po.UserGroupQueryConfig;
import com.bmcc.vgop.data.usergroup.po.UserGroupQueryLog;
import com.bmcc.vgop.web.usergroup.vo.QueryObj;
import com.bmcc.vgop.web.usergroup.vo.Results;
import com.bmcc.vgop.web.usergroup.vo.UserGroupReq;
import com.bmcc.vgop.web.usergroup.vo.UserGroupResp;

@SuppressWarnings("unchecked")
@Repository
@Transactional(value = "db2TransactionManager")
public class UserGroupDaoImpl extends GenericDaoDb2<UserGroupQueryConfig, String> implements UserGroupDao {

	@Override
	public List<UserGroupQueryConfig> getGroupQueryConfig() {
		return this.getHibernateSession().createQuery("from UserGroupQueryConfig where isValid = 1 ").list();
	}

	@Override
	public String getRetBySql(String sql,String phone) {

       Query query = this.getHibernateSession().createNativeQuery(sql);
       query.setParameter(1, phone);
       List list = query.list();
       if(list.size()>0) {
    	   return String.valueOf(list.get(0));
       }else {
    	   return "0";
       }
	}

	@Override
	public UserGroupQueryLog saveRightLog(UserGroupReq req) {
		UserGroupQueryLog log = new UserGroupQueryLog();
		log.setPhone(req.getPhone());
		log.setTransId(req.getTransId());
		log.setChannelId(req.getChannelId());
		StringBuilder sbu = new StringBuilder(80);
		for (QueryObj obj : req.getQueryObj()) {
			sbu.append(obj.getCode()).append(",");
		}
		log.setCode(sbu.toString());
		log.setCreate_time(new Date());
		this.getHibernateSession().save(log);
		return log;
	}


	@Override
	public void updateGroupQueryLog(UserGroupResp resp, UserGroupQueryLog log) {
		
		log.setRespVgopMsg(resp.getMsg());
		log.setResp_time(new Date());
		StringBuilder sbu = new StringBuilder(50);
		if( resp.getResults()!=null) {
			for (Results r : resp.getResults()) {
				sbu.append(r.getCode()).append("|").append(r.getGroupid()).append(",");
			}
		}
		log.setRespGroupid(sbu.toString());
		this.getHibernateSession().update(log);
	}

}
