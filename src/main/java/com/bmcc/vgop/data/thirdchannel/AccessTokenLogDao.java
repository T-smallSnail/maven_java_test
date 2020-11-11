package com.bmcc.vgop.data.thirdchannel;

import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallAccessTokenLogEntity;

public interface AccessTokenLogDao {
    void insertLog(TVgopUsersubCallAccessTokenLogEntity accessTokenLog);

    TVgopUsersubCallAccessTokenLogEntity getAccessToken(String appId, String accessToken);
}
