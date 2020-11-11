package com.bmcc.vgop.data.thirdchannel.impl;

import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.thirdchannel.RemoveAccessTokenDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallRemoveAccessTokenLog;
import org.springframework.stereotype.Repository;

/**
 * @author ：pancho
 * @date ：Created in 2019/12/16 17:50
 * @description :作废令牌
 */
@Repository
public class RemoveAccessTokenDaoImpl extends GenericDaoMysql<TVgopUsersubCallRemoveAccessTokenLog,Integer> implements RemoveAccessTokenDao {
    @Override
    public void insertLog(TVgopUsersubCallRemoveAccessTokenLog removeAccessTokenLog) {
        this.save(removeAccessTokenLog);
    }
}
