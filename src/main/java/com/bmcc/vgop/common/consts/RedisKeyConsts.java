package com.bmcc.vgop.common.consts;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/12 10:08
 * @description :redis键
 */
public class RedisKeyConsts {

    /**
     * 第三方渠道发送手机号验证码
     * Redis结构：String
     * 有效期：1分钟有效
     */
    public static final String USERSUB_VALIDCODE = "usersub:thirdchannel:validCode:";

    /**
     * 第三方渠道唯一订单流水号
     * Redis结构：String
     */
    public static final String USERSUB_SERIALCODE = "usersub:thirdchannel:serialCode:";

    /**
     * 第三方渠道24小时内限制发送短信总数
     *
     */
    public static final String USERSUB_VALIDCODE_COUNT = "usersub:thirdchannel:serialCodecount:";

    /**
     * 第三方渠道的令牌
     * 结构：String
     * key：手机号+appId+offerCode+接口类型
     * value：accessToken
     * 有效期：根据令牌的有效期
     */
    public static final String USERSUB_ACCESS_TOKEN = "usersub:thirdchannel:accessToken:";

    /**
     * 北京移动入网、离网、销号用户
     * Key：{mobile}:operator:change
     * field：phone
     * value：20191107142534_1 (时间戳_手机号转网方式)
     */
     public static final String MOBILE_OPERATOR_CHANGE = "{mobile}:operator:change";

    /**
     * 北京移动7位，9位号段
     * 结构:Hash
     * key:{mobile}:bj:phone:prefix
     * field:手机号
     * value:1
     */
    public static final String MOBILE_BJ_PHONE_PREFIX = "{mobile}:bj:phone:prefix";

}
