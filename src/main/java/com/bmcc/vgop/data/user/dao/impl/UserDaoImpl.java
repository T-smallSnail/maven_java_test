package com.bmcc.vgop.data.user.dao.impl;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bmcc.vgop.data.common.db.impl.GenericDaoDb2;
import com.bmcc.vgop.data.user.dao.UserDao;
import com.bmcc.vgop.data.user.dao.po.UserRight;

@SuppressWarnings("unchecked")
@Repository
@Transactional(value = "db2TransactionManager")
public class UserDaoImpl extends GenericDaoDb2<UserRight,String> implements UserDao {
	
	/**
	 * 是否属于权益用户
	 * @return
	 */
	@Override
	public boolean isRightUser(String phone) {
		return this.get(phone)==null?false:true;
	}

}
