package com.bmcc.vgop.data.thirdchannel.impl;

import com.bmcc.vgop.data.common.db.impl.GenericDaoMysql;
import com.bmcc.vgop.data.thirdchannel.SendMsgLogDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallSendSmsLogEntity;
import org.springframework.stereotype.Repository;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/10 15:30
 * @description :记录发送短信日志
 */
@Repository
public class SendMsgLogDaoImpl extends GenericDaoMysql<TVgopUsersubCallSendSmsLogEntity, Integer> implements SendMsgLogDao {


    @Override
    public void insertLog(TVgopUsersubCallSendSmsLogEntity sendSmsLog) {
        save(sendSmsLog);
    }
}
