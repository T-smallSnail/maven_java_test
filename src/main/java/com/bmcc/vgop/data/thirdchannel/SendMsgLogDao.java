package com.bmcc.vgop.data.thirdchannel;

import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallSendSmsLogEntity;

public interface SendMsgLogDao {
    void insertLog(TVgopUsersubCallSendSmsLogEntity sendSmsLog);
}
