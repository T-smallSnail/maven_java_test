package com.bmcc.vgop.data.insidechannelorder.impl;

import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.insidechannelorder.TVgopUsersubServerInsideChannelOrderProductLogDao;
import com.bmcc.vgop.data.insidechannelorder.po.TVgopUsersubChannelInfo;
import com.bmcc.vgop.data.insidechannelorder.po.TVgopUsersubServerInsideChannelOrderProductLog;
import org.springframework.stereotype.Repository;

/**
 * @author ：pancho
 * @date ：Created in 2020/4/22 17:45
 * @description :
 */
@Repository
public class TVgopUsersubServerInsideChannelOrderProductLogDaoImpl extends GenericDaoMysql<TVgopUsersubServerInsideChannelOrderProductLog, Integer> implements TVgopUsersubServerInsideChannelOrderProductLogDao {


    @Override
    public void insert(TVgopUsersubServerInsideChannelOrderProductLog orderProductLog) {
        this.save(orderProductLog);
    }
}
