package com.bmcc.vgop.data.mobileserverlog.impl;

import java.util.Date;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bmcc.vgop.common.utils.date.DateUtils;
import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.mobileserverlog.MobileServerLogDao;
import com.bmcc.vgop.data.mobileserverlog.po.UsersubGetmobileserverLog;

@Repository
public class MobileServerLogDaoImpl extends GenericDaoMysql<UsersubGetmobileserverLog, Long> implements MobileServerLogDao {

	@Override
	public void save(UsersubGetmobileserverLog mobileServerLogDao) {
		this.getHibernateSession().save(mobileServerLogDao);

	}

	@Override
	public synchronized int getSizeOfAppid(String appId) {
		String date = DateUtils.format(new Date(), DateUtils.DATE_YYYY_MM_DD);
		String beginTime = date + " 00:00:00";
		String endTime = date + " 23:59:59";
		String sql = "select count(1) from t_vgop_usersub_phone_substitution_log m where m.appid="+appId+" and m.requestTime between '"+beginTime+"' and '"+endTime+"'";
		return Integer.valueOf(this.getHibernateSession().createNativeQuery(sql).uniqueResult().toString());

	}

	@Override
	public Session getCurrentSession() {
		return this.getHibernateSession();
	}

}
