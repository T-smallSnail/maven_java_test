package com.bmcc.vgop.data.mobidentify;

import com.bmcc.vgop.data.mobidentify.po.TVgopUsersubMobileIdentifyAccessLog;
import com.bmcc.vgop.data.mobidentify.po.TVgopUsersubMobileIdentifyChannelConfig;
import com.bmcc.vgop.data.mobidentify.po.TVgopUsersubMobileIdentifyChannelStypeRef;

import java.util.List;

public interface MobidentifyDao {

    //初始化系统常量信息
//    List<TVgopUsersubMobileIdentifyConsts> initSysConsts();

    //初始化渠道方与查询类型之间对应关系
    List<TVgopUsersubMobileIdentifyChannelStypeRef> initChannelStypeRef();

    //初始化渠道和盐值之间对应关系
    List<TVgopUsersubMobileIdentifyChannelConfig> initChannelSignSalt();

    //保存日志表
    void saveAccessLog(TVgopUsersubMobileIdentifyAccessLog accessLog);
}
