package com.bmcc.vgop.data.thirdchannel.impl;

import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.thirdchannel.CheckOrderBusinessLogDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCheckOrderBusinessLogEntity;
import org.springframework.stereotype.Repository;

/**
 * @author ：pancho
 * @date ：Created in 2020/3/4 18:52
 * @description :
 */
@Repository
public class CheckOrderBusinessLogDaoImpl extends GenericDaoMysql<TVgopUsersubCheckOrderBusinessLogEntity, Integer> implements CheckOrderBusinessLogDao {

    @Override
    public void insertLog(TVgopUsersubCheckOrderBusinessLogEntity oderCrmLog) {
        this.save(oderCrmLog);
    }
}
