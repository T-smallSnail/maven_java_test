package com.bmcc.vgop.data.thirdchannel.impl;

import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.thirdchannel.ProductCrmMethodDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallProductCrmMethodInfo;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author ：pancho
 * @date ：Created in 2019/12/17 10:57
 * @description :产品对应的crm接口
 */
@Repository
public class ProductCrmMethodDaoImpl extends GenericDaoMysql<TVgopUsersubCallProductCrmMethodInfo,Integer> implements ProductCrmMethodDao {

    @Override
    public TVgopUsersubCallProductCrmMethodInfo searchCrmMethod(String offerId, Integer type) {
        Session hibernateSession = this.getHibernateSession();
        String hql = "from TVgopUsersubCallProductCrmMethodInfo where offerId=:offerId and type =:type and isValid = 1";
        Query query = hibernateSession.createQuery(hql);
        query.setParameter("offerId",offerId);
        query.setParameter("type",Short.valueOf(type.toString()));
        Object obj = query.uniqueResult();
        if(obj==null){
            return null;
        }

        return (TVgopUsersubCallProductCrmMethodInfo) obj;

    }


}
