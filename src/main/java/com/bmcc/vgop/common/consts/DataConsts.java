package com.bmcc.vgop.common.consts;

import com.bmcc.vgop.common.consts.vo.OfferIdObj;
import com.bmcc.vgop.data.consts.po.PhoneSubstitutionConf;
import com.bmcc.vgop.data.mobidentify.po.TVgopUsersubMobileIdentifyChannelStypeRef;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmChannelInfoEntity;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmProductInfoEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/11 10:51
 * @description :数据常量初始化
 */
public class DataConsts {


    /**
     * usersub系统常量
     * @author : pancho
     * @date : 2020/4/22 22:09 
     * @param : null
     * @return : null
     */
    public static final Map<String, String> USERSUB_CONTS = new HashMap<>();


    /**
     * 第三方渠道同一订购接口相关的常量
     * <p>
     * 第三方渠道信息
     * crm产品信息
     */
    public static final Map<String, TVgopUsersubCallCrmChannelInfoEntity> THIRD_CHANNEL_MSG = new HashMap<>();
    public static final Map<String, TVgopUsersubCallCrmProductInfoEntity> CRM_PRODUCT_MSG = new HashMap<>();

    /**
     * 使用接口
     * 用户权益订购接口，为北京本地免费的139邮箱用户权益和云视界用户权益，提供白名单查询、业务订购的统一接口。
     */
    public static final Map<String, OfferIdObj> BIZCODE_OFFERID = new HashMap<String, OfferIdObj>();


    /**
     * 使用接口
     * 提供用户所属群体的查询
     */
    public static final Map<String, String> GROUP_CODE_SQL = new HashMap<String, String>();

    /**
     * 使用接口
     * 手机置换
     */
    public static final Map<String, PhoneSubstitutionConf> PHONE_CONF = new HashMap<String, PhoneSubstitutionConf>();


    //北京移动手机号识别，初始化第三方第三方渠道与查询类型之间的关系
    public static final List<TVgopUsersubMobileIdentifyChannelStypeRef> MOBILE_IDENTIFY_REF_LIST = new ArrayList();
    //北京移动手机号识别，初始化加载第三方渠道与盐值对应关系
    public static final Map<String, String> MOBILE_IDENTIFY_SIGN_MAP = new HashMap<>(16);

}
