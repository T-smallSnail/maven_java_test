package com.bmcc.vgop.data.thirdchannel.impl;

import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.thirdchannel.OrderCrmLogDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallOderCrmLogEntity;
import org.springframework.stereotype.Repository;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/10 15:56
 * @description :记录订购crm产品日志
 */
@Repository
public class OrderCrmLogDaoImpl extends GenericDaoMysql<TVgopUsersubCallOderCrmLogEntity,Integer> implements OrderCrmLogDao {

    @Override
    public void insertLog(TVgopUsersubCallOderCrmLogEntity oderCrmLog) {
        this.save(oderCrmLog);
    }

}
