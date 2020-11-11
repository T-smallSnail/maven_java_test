package com.bmcc.vgop.data.thirdchannel;

import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallProductCrmMethodInfo;

public interface ProductCrmMethodDao {

    TVgopUsersubCallProductCrmMethodInfo searchCrmMethod(String offerCode, Integer type);
}
