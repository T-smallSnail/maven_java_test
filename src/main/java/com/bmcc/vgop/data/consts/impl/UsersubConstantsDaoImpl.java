package com.bmcc.vgop.data.consts.impl;

import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.consts.UsersubConstantsDao;
import com.bmcc.vgop.data.consts.po.TVgopUsersubConstants;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：pancho
 * @date ：Created in 2020/4/22 21:54
 * @description :usersub系统常量
 */
@Repository
public class UsersubConstantsDaoImpl extends GenericDaoMysql<TVgopUsersubConstants, String> implements UsersubConstantsDao {


    @Override
    public List<TVgopUsersubConstants> getAllValidConsts() {
        Session hibernateSession = this.getHibernateSession();
        String hql = "from TVgopUsersubConstants where isValld=1";
        Query query = hibernateSession.createQuery(hql);

        List<TVgopUsersubConstants> list = query.list();

        return list;
    }
}
