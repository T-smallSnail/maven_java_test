package com.bmcc.vgop.data.mobidentify.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author vgop
 * @since 2019-10-29
 */
@Entity
@Table(name = "t_vgop_usersub_mobile_identify_access_log", catalog = "usersub")
public class TVgopUsersubMobileIdentifyAccessLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "app_id")
    private String appId;

    @Column(name = "tra_id")
    private String traId;

    /**
     * 查询类型
            1：查询是否为B站免流联名卡用户
            
     */
    @Column(name = "type")
    private Integer type;

    @Column(name = "req_time")
    private LocalDateTime reqTime;

    @Column(name = "sign")
    private String sign;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "resp_time")
    private LocalDateTime respTime;

    @Column(name = "ret_code")
    private String retCode;

    @Column(name = "sys_error")
    private String sysError;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getTraId() {
        return traId;
    }

    public void setTraId(String traId) {
        this.traId = traId;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public LocalDateTime getReqTime() {
        return reqTime;
    }

    public void setReqTime(LocalDateTime reqTime) {
        this.reqTime = reqTime;
    }
    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public LocalDateTime getRespTime() {
        return respTime;
    }

    public void setRespTime(LocalDateTime respTime) {
        this.respTime = respTime;
    }
    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }
    public String getSysError() {
        return sysError;
    }

    public void setSysError(String sysError) {
        this.sysError = sysError;
    }

    @Override
    public String toString() {
        return "TVgopUsersubMobileIdentifyAccessLog{" +
            "id=" + id +
            ", phone=" + phone +
            ", appId=" + appId +
            ", traId=" + traId +
            ", type=" + type +
            ", reqTime=" + reqTime +
            ", sign=" + sign +
            ", createTime=" + createTime +
            ", respTime=" + respTime +
            ", retCode=" + retCode +
            ", sysError=" + sysError +
        "}";
    }
}
