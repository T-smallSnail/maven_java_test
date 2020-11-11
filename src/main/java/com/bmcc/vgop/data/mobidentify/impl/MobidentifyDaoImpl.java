package com.bmcc.vgop.data.mobidentify.impl;

import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.mobidentify.MobidentifyDao;
import com.bmcc.vgop.data.mobidentify.po.TVgopUsersubMobileIdentifyAccessLog;
import com.bmcc.vgop.data.mobidentify.po.TVgopUsersubMobileIdentifyChannelConfig;
import com.bmcc.vgop.data.mobidentify.po.TVgopUsersubMobileIdentifyChannelStypeRef;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MobidentifyDaoImpl extends GenericDaoMysql<Object, Integer> implements MobidentifyDao {
//    @Override
//    @Transactional(value = "mysqlTransactionManager")
//    public List<TVgopUsersubMobileIdentifyConsts> initSysConsts() {
//        List<TVgopUsersubMobileIdentifyConsts> constsList = getHibernateSession()
//                .createQuery("from TVgopUsersubMobileIdentifyConsts c where c.isValid=1").list();
//        return constsList;
//    }

    @Override
    @Transactional(value = "mysqlTransactionManager")
    public List<TVgopUsersubMobileIdentifyChannelStypeRef> initChannelStypeRef() {
        List<TVgopUsersubMobileIdentifyChannelStypeRef> refList = getHibernateSession()
                .createQuery("from TVgopUsersubMobileIdentifyChannelStypeRef r where r.isValid=1").list();
        return refList;
    }

    @Override
    @Transactional(value = "mysqlTransactionManager")
    public List<TVgopUsersubMobileIdentifyChannelConfig> initChannelSignSalt() {
        List<TVgopUsersubMobileIdentifyChannelConfig> configList = getHibernateSession()
                .createQuery("from TVgopUsersubMobileIdentifyChannelConfig c where c.isValid=1").list();
        return configList;
    }

    @Override
    public void saveAccessLog(TVgopUsersubMobileIdentifyAccessLog accessLog) {
        getHibernateSession().save(accessLog);
    }
}
