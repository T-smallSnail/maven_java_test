package com.bmcc.vgop.service.thirdchannel;

import com.bmcc.vgop.common.consts.RedisKeyConsts;

/**
 * @author ：pancho
 * @date ：Created in 2019/12/10 11:28
 * @description :第三方渠道订购相关redis
 */
public class ThirdChannelRedisKey {


    /**
     * 第三方渠道发送手机号验证码
     * Redis结构：String
     * 有效期：1分钟有效
     */
    public static String validCodeRedisKey(String appId, Integer type, String offerCode, String msisdn) {

        return RedisKeyConsts.USERSUB_VALIDCODE + appId + "_" + type + "_" + offerCode + "_" + msisdn;
    }


    /**
     * 保存第三方渠道的授权令牌
     * @author : pancho
     * @date : 2019/12/10 14:31
     * @param : appId
     * @param : type
     * @param : offerCode
     * @param : msisdn
     * @return : java.lang.String
     */
    public static String accessTokenRedisKey(String appId, Integer type, String offerCode, String msisdn) {

        return RedisKeyConsts.USERSUB_ACCESS_TOKEN + msisdn + "_" + appId + "_" + offerCode + "_" + type;
    }

}
