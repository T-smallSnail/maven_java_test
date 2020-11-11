package com.bmcc.vgop.data.crmopenapi.impl;

import org.springframework.stereotype.Repository;

import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.crmopenapi.CrmOpenApiDao;
import com.bmcc.vgop.data.crmopenapi.po.TVgopAndMarketUseCrmApiLog;
@Repository("crmOpenApiDao")
public class CrmOpenApiDaoImpl extends GenericDaoMysql<CrmOpenApiDaoImpl, String> implements CrmOpenApiDao {

	@Override
	public void saveLog(TVgopAndMarketUseCrmApiLog log) {
		if(log!=null&&log.getCrmOpapiRespDesc()!=null&&log.getCrmOpapiRespDesc().length()>150){
			log.setCrmOpapiRespDesc(log.getCrmOpapiRespDesc().substring(0,150));
		}
		this.getHibernateSession().save(log);
	}

}
