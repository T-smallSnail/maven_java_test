package com.bmcc.vgop.data.thirdchannel.impl;

import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.thirdchannel.CrmProdunctInfoDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmProductInfoEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/10 16:03
 * @description :crm产品信息表
 */
@Repository
@Transactional(value = "mysqlTransactionManager")
public class CrmProdunctInfoDaoImpl extends GenericDaoMysql<TVgopUsersubCallCrmProductInfoEntity, String> implements CrmProdunctInfoDao {


    @Override
    public List<TVgopUsersubCallCrmProductInfoEntity> getAllProduct() {

        return this.getHibernateSession().createQuery("from TVgopUsersubCallCrmProductInfoEntity where isValid = 1 ").list();

    }
}
