package com.bmcc.vgop.data.thirdchannel.impl;

import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.thirdchannel.CrmAuthDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmAuthEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/10 16:48
 * @description :
 */
@Repository
public class CrmAuthDaoImpl extends GenericDaoMysql<TVgopUsersubCallCrmAuthEntity,String> implements CrmAuthDao {
    @Override
    public TVgopUsersubCallCrmAuthEntity getCrmAuth(String appId, String offerId, Integer type) {
        Session hibernateSession = this.getHibernateSession();
        String hql = "from TVgopUsersubCallCrmAuthEntity where isValid = 1 and appId=:appId and offerId = :offerId and infType =:type";
        Query query = hibernateSession.createQuery(hql);
        query.setParameter("appId",appId);
        query.setParameter("offerId",offerId);
        query.setParameter("type",Short.valueOf(type.toString()));

        return (TVgopUsersubCallCrmAuthEntity) query.uniqueResult();
    }


}
