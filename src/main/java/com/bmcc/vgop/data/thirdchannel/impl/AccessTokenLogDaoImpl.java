package com.bmcc.vgop.data.thirdchannel.impl;

import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.thirdchannel.AccessTokenLogDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallAccessTokenLogEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/10 15:48
 * @description :授权令牌日志
 */
@Repository
public class AccessTokenLogDaoImpl extends GenericDaoMysql<TVgopUsersubCallAccessTokenLogEntity, Integer> implements AccessTokenLogDao {


    @Override
    public void insertLog(TVgopUsersubCallAccessTokenLogEntity accessTokenLog) {
        this.save(accessTokenLog);
    }

    @Override
    public TVgopUsersubCallAccessTokenLogEntity getAccessToken(String appId, String accessToken) {
        Session hibernateSession = this.getHibernateSession();
        String hql = "from TVgopUsersubCallAccessTokenLogEntity where appid=:appId and accessToken = :accessToken";
        Query query = hibernateSession.createQuery(hql);
        query.setParameter("appId",appId);
        query.setParameter("accessToken",accessToken);
        Object obj = query.uniqueResult();
        if (obj==null){
            return null;
        }

        return (TVgopUsersubCallAccessTokenLogEntity) obj;
    }
}
