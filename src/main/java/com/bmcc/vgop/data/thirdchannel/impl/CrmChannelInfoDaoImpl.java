package com.bmcc.vgop.data.thirdchannel.impl;


import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.thirdchannel.CrmChannelInfoDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmChannelInfoEntity;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmAuthEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/10 15:48
 * @description :第三方渠道信息
 */
@Repository
@Transactional(value = "mysqlTransactionManager")
public class CrmChannelInfoDaoImpl extends GenericDaoMysql<TVgopUsersubCallCrmChannelInfoEntity, String> implements CrmChannelInfoDao {


    @Override
    public List<TVgopUsersubCallCrmChannelInfoEntity> getThirdChannelMsg() {

        return this.getHibernateSession().createQuery("from TVgopUsersubCallCrmChannelInfoEntity where isValid = 1 ").list();

    }

    @Override
    public List<TVgopUsersubCallCrmAuthEntity> getThirdChannelAuthMsg() {
        return this.getHibernateSession().createQuery("from TVgopUsersubCallCrmAuthEntity where isValid = 1 ").list();

    }

    @Override
    public List<TVgopUsersubCallCrmAuthEntity> getThirdChannelTokenMsg() {
        return this.getHibernateSession().createQuery("from TVgopUsersubCallCrmAuthEntity where isValid = 1 ").list();

    }
}
