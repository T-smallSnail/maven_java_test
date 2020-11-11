package com.bmcc.vgop.data.consts.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.consts.ConstantDao;
import com.bmcc.vgop.data.consts.po.PhoneSubstitutionConf;


@Repository
@Transactional(value="mysqlTransactionManager",readOnly=false)
public class ConstantDaoImpl extends GenericDaoMysql<PhoneSubstitutionConf, String> implements ConstantDao {

	@Override
	public List<PhoneSubstitutionConf> geUsersubConstant() {
		
		return this.getAll();
	}

	



}
