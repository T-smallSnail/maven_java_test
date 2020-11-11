package com.bmcc.vgop.service.mobidentify.impl;

import ch.qos.logback.classic.Logger;
import com.bmcc.vgop.common.consts.DataConsts;
import com.bmcc.vgop.common.consts.RedisKeyConsts;
import com.bmcc.vgop.common.consts.ThirdChannelConsts;
import com.bmcc.vgop.common.data.jedis.JedisClient;
import com.bmcc.vgop.common.utils.date.DateUtils;
import com.bmcc.vgop.common.utils.date.LocalDateUtils;
import com.bmcc.vgop.common.utils.encrypt.SignUtils;
import com.bmcc.vgop.common.utils.log.LogUtils;
import com.bmcc.vgop.common.utils.string.JsonUtils;
import com.bmcc.vgop.common.utils.string.StringUtils;
import com.bmcc.vgop.data.mobidentify.MobidentifyDao;
import com.bmcc.vgop.data.mobidentify.po.TVgopUsersubMobileIdentifyAccessLog;
import com.bmcc.vgop.data.mobidentify.po.TVgopUsersubMobileIdentifyChannelStypeRef;
import com.bmcc.vgop.service.mobidentify.MobidentifyService;
import com.bmcc.vgop.web.mobidentify.vo.RespObj;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@Transactional(value = "mysqlTransactionManager")
public class MobidentifyServiceImpl implements MobidentifyService {

    @Autowired
    JedisClient JedisClient;
    @Autowired
    MobidentifyDao mobidentifyDao;

    private static Logger logger = (Logger) LoggerFactory.getLogger(MobidentifyServiceImpl.class);


    @Override
    public RespObj bjMobileIndetify(Map<String, String> paramMap, HttpServletRequest request) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("北京移动手机号识别接口，请求参数:" + paramMap);

        RespObj respObj = new RespObj();

        String phone = paramMap.get("phone");
        String appId = paramMap.get("appId");
        String traId = paramMap.get("traId");
        String type = paramMap.get("type");
        String requestTime = paramMap.get("requestTime");
        String sign = paramMap.get("sign");

        respObj.setDescr("");

        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(traId) || StringUtils.isEmpty(type) || StringUtils.isEmpty(requestTime) || StringUtils.isEmpty(sign)) {
            respObj.setRetCode(ThirdChannelConsts.PARAM_ILLEGAL);
            stringBuilder.append(",接口返回:" + JsonUtils.getJsonStr(respObj));
            logger.info(stringBuilder.toString());
            return respObj;
        }

        TVgopUsersubMobileIdentifyAccessLog accessLog = new TVgopUsersubMobileIdentifyAccessLog();

        try {
            //appId是否有效
            String salt = DataConsts.MOBILE_IDENTIFY_SIGN_MAP.get(appId);
            if (StringUtils.isEmpty(salt)) {
                respObj.setRetCode(ThirdChannelConsts.APPID_IS_NOT_EXIST);
                return respObj;
            }
            // 签名校验
            paramMap.remove("sign");
            String mysign = SignUtils.getSign(salt, paramMap).toUpperCase();
            if (!sign.toUpperCase().equals(mysign)) {
                respObj.setRetCode(ThirdChannelConsts.SIGN_FAILURE);
                return respObj;
            }
            //签名测试通过，开始记录日志
            accessLog.setPhone(phone);
            accessLog.setAppId(appId);
            accessLog.setTraId(traId);
            accessLog.setType(Integer.valueOf(type));
            accessLog.setReqTime(LocalDateUtils.parseStringToDateTime(requestTime, DateUtils.DATETIME_YYYYMMDDHHMMSS));
            accessLog.setSign(sign);
            accessLog.setCreateTime(LocalDateTime.now());

            //查询类型是否有效
            boolean isValid = false;
            for (TVgopUsersubMobileIdentifyChannelStypeRef ref : DataConsts.MOBILE_IDENTIFY_REF_LIST) {
                if (appId.equals(ref.getAppId()) && type.equals(ref.getsType())) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                setAccessLog(accessLog, respObj, ThirdChannelConsts.TYPE_ILLEGAL);
                return respObj;
            }
//            //一定时间内同一traId不允许多次请求
//            String redisKey = DataConsts.PROJECT_NAME + ":" + traId;
//            long resCode = JedisClient.setnx(redisKey, "1");
//            if (resCode == 0) {
//                setAccessLog(accessLog, respObj, ThirdChannelConsts.TRAID_ILLEGAL, "");
//                return respObj;
//            } else {
//                JedisClient.expire(redisKey, Integer.parseInt(DataConsts.MOBILE_IDENTIFY_CONSTS_MAP.get("bilibili_traId_invalid_time")));
//            }
            //判断是否是北京移动
            isSelectType(accessLog, respObj, phone);
        } catch (Exception e) {
            LogUtils.printException(logger, e);
            accessLog.setSysError(e.getMessage());
            stringBuilder.append(",接口调用出现异常，异常信息为：" + e.getMessage());
            setAccessLog(accessLog, respObj, ThirdChannelConsts.INVALID_DATA);
        } finally {
            stringBuilder.append(",接口返回:" + JsonUtils.getJsonStr(respObj));
            logger.info(stringBuilder.toString());
            if (!ThirdChannelConsts.SIGN_FAILURE.equals(respObj.getRetCode()) && !ThirdChannelConsts.APPID_IS_NOT_EXIST.equals(respObj.getRetCode())) {
                mobidentifyDao.saveAccessLog(accessLog);
            }
        }
        return respObj;
    }


    /**
     * 判断手机号状态
     * 1=转入，2=转出，3=销号
     *
     * @param accessLog
     * @param respObj
     * @param phone
     */
    private void isSelectType(TVgopUsersubMobileIdentifyAccessLog accessLog, RespObj respObj, String phone) {
        String reCode;
        String type = getType(0, phone);
        //获取到手机号状态
        if (StringUtils.isEmpty(type) || "3".equals(type)) {
            reCode = isPrefix(phone) == true ? ThirdChannelConsts.SELECT_USER : ThirdChannelConsts.NOT_SELECT_USER;
        } else if ("1".equals(type)) {
            reCode = ThirdChannelConsts.SELECT_USER;
        } else {
            reCode = ThirdChannelConsts.NOT_SELECT_USER;
        }
        setAccessLog(accessLog, respObj, reCode);
    }

    private void setAccessLog(TVgopUsersubMobileIdentifyAccessLog accessLog, RespObj respObj, String retCode) {
        respObj.setRetCode(retCode);
        accessLog.setRespTime(LocalDateTime.now());
        accessLog.setRetCode(retCode);
    }


    /**
     * 是否在北京移动号段内
     *
     * @param phone
     * @return
     */
    private boolean isPrefix(String phone) {
        //若非离网用户，先判断前7位号段
        if (isExist(0, RedisKeyConsts.MOBILE_BJ_PHONE_PREFIX, phone.substring(0, 7))) {
            return true;
        } else {
            //前7位号段不存在，再去判断前9位
            if (isExist(0, RedisKeyConsts.MOBILE_BJ_PHONE_PREFIX, phone.substring(0, 9))) {
                return true;
            } else {
                return false;
            }

        }
    }

    /**
     * 判断数据是否存在
     *
     * @param index 指定数据库
     * @param key   键
     * @param field
     * @return
     */
    private boolean isExist(int index, String key, String field) {
        Jedis jedis = null;
        Boolean exists = false;
        try {
            jedis = JedisClient.getJedis();
            jedis.select(index);
            //判断是否存在手机号以及号段是否存在
            exists = jedis.hexists(key, field);
        } catch (Exception e) {
            LogUtils.printException(logger, e);
        } finally {
            jedis.close();
        }
        return exists;
    }

    /**
     * 指定数据库
     * 获取到手机号最新状态
     *
     * @param index
     * @param phone
     * @return
     */
    private String getType(int index, String phone) {
        Jedis jedis = null;
        String params = null;
        try {
            jedis = JedisClient.getJedis();
            jedis.select(index);
            //判断是否存在手机号以及号段是否存在
            params = jedis.hget(RedisKeyConsts.MOBILE_OPERATOR_CHANGE, phone);
        } catch (Exception e) {
            LogUtils.printException(logger, e);
        } finally {
            jedis.close();
        }

        if (StringUtils.isEmpty(params)) {
            return "";
        } else {
            String[] mobileType = params.split("_");
            return mobileType[1];
        }
    }
}
