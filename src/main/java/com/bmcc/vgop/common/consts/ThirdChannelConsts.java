package com.bmcc.vgop.common.consts;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/10 17:40
 * @description :第三方渠道统一订购接口返回值常量
 */
public class ThirdChannelConsts {

    /**
     * 0：成功
     * 1：无效appid
     * 2：appid无权限申请此项操作
     * 3：签名无效
     * 4：access_token无效
     * 998：其它
     * 999：系统异常
     */
    public final static Integer SUCCESS = 0;
    public final static Integer INVALID_APPID = 1;
    public final static Integer APPID_NO_AUTHORITY = 2;
    public final static Integer INVALID_SIGN = 3;
    public final static Integer INVALID_TOKEN = 4;
    public final static Integer OTHER_ERROR = 998;
    public final static Integer SYSTEM_ERROR = 999;

    /**
     * 验证码失效时间1分钟
     */
    public final static Integer VALIDCODE_EXPIRE = 60;

    /**
     * appId在24小时最大发送短信数量
     */
    public final static Integer VALIDCODE_COUNT = 20000;

    /**
     * appId发送短信数量统计有效期24小时
     */
    public final static Integer VALIDCODE_COUNT_EXPIRE = 86400;

    /**
     * 接口类型
     * @author : pancho
     * @date : 2019/12/10 16:31
     * @param : null
     * @return : null
     */
    public final static Integer TYPE_ORDER = 1;
    public final static Integer TYPE_SEARCH = 2;
    public final static Integer TYPE_UNORDER = 3;

    /**
     * 令牌有效期
     * 1：单次有效
     * 0：非单次有效
     */
    public final static Short INDATE_ONE_TIME = 1;
    public final static Short INDATE_MORE_TIMES = 0;


    /**
     * 1001:appId不存在
     * 1002:查询类型不合法
     * 1003:签名失败
     * 1004:交易流水号不合法
     * 1005:参数校验失败
     * 2001:非指定查询类型用户
     * 2002:指定查询类型用户
     * 3001:无效数据
     */
    public static final String APPID_IS_NOT_EXIST = "1001";
    public static final String TYPE_ILLEGAL = "1002";
    public static final String SIGN_FAILURE = "1003";
//  public static final String TRAID_ILLEGAL= "1004";
    public static final String PARAM_ILLEGAL = "1005";
    public static final String NOT_SELECT_USER = "2001";
    public static final String SELECT_USER = "2002";
    public static final String INVALID_DATA = "3001";
}
