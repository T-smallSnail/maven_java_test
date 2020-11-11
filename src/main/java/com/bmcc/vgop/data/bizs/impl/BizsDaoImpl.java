package com.bmcc.vgop.data.bizs.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bmcc.vgop.data.bizs.BizsDao;
import com.bmcc.vgop.data.bizs.po.BizOfferid;
import com.bmcc.vgop.data.common.db.impl.GenericDaoDb2;

@SuppressWarnings("unchecked")
@Repository
@Transactional(value = "db2TransactionManager")
public class BizsDaoImpl extends GenericDaoDb2<BizOfferid,String> implements BizsDao{

    /**
     * 获取所有code与offerid的对应
     */
	@Override
	public List<BizOfferid> getBizOfferIds() {
		return this.getAll();
	}

}
