package com.bmcc.vgop.data.thirdchannel;

import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmChannelInfoEntity;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmAuthEntity;

import java.util.List;

public interface CrmChannelInfoDao {

    List<TVgopUsersubCallCrmChannelInfoEntity> getThirdChannelMsg();

    List<TVgopUsersubCallCrmAuthEntity> getThirdChannelAuthMsg();

    List<TVgopUsersubCallCrmAuthEntity> getThirdChannelTokenMsg();
}
