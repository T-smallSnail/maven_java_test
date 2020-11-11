package com.bmcc.vgop.data.mobileserverlog;

import org.hibernate.Session;

import com.bmcc.vgop.data.mobileserverlog.po.UsersubGetmobileserverLog;


public interface MobileServerLogDao {


	public void save(UsersubGetmobileserverLog mobileServerLogDao);
	/**
	 * 获取调用次数
	 * @param appId
	 * @return
	 */
	public int getSizeOfAppid(String appId);
	/**
	 * 获取session
	 */
    public Session getCurrentSession();
}
