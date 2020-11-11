package com.bmcc.vgop.data.insidechannelorder.impl;

import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.insidechannelorder.TVgopUsersubChannelInfoDao;
import com.bmcc.vgop.data.insidechannelorder.po.TVgopUsersubChannelInfo;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmAuthEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author ：pancho
 * @date ：Created in 2020/4/22 17:41
 * @description :
 */
@Repository
public class TVgopUsersubChannelInfoDaoImpl extends GenericDaoMysql<TVgopUsersubChannelInfo, String> implements TVgopUsersubChannelInfoDao {


    @Override
    public String getAppKey(String appId) {

        Session hibernateSession = this.getHibernateSession();
        String hql = "from TVgopUsersubChannelInfo where isValid = 1 and appId=:appId ";
        Query query = hibernateSession.createQuery(hql);
        query.setParameter("appId",appId);

        TVgopUsersubChannelInfo channelInfo = (TVgopUsersubChannelInfo) query.uniqueResult();
        if(channelInfo == null){
            return null;
        }

        return channelInfo.getAppKey();
    }
}
