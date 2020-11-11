package com.bmcc.vgop.data.thirdchannel;

import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmAuthEntity;

public interface CrmAuthDao {

    TVgopUsersubCallCrmAuthEntity getCrmAuth(String appId, String offerId, Integer type);


}
