package com.bmcc.vgop.data.thirdchannel.po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/10 15:10
 * @description :
 */
@Entity
@Table(name = "t_vgop_usersub_call_send_sms_log")
public class TVgopUsersubCallSendSmsLogEntity {
    private int id;
    private String appid;
    private String msisdn;
    private Integer type;
    private String offerCode;
    private String requestTime;
    private String sign;
    private Integer retCode;
    private String retMsg;
    private Timestamp returnTime;
    private String exceptionMsg;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "appid")
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @Basic
    @Column(name = "msisdn")
    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Basic
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "offerCode")
    public String getOfferCode() {
        return offerCode;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    @Basic
    @Column(name = "requestTime")
    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    @Basic
    @Column(name = "sign")
    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Basic
    @Column(name = "retCode")
    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    @Basic
    @Column(name = "retMsg")
    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    @Basic
    @Column(name = "return_time")
    public Timestamp getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Timestamp returnTime) {
        this.returnTime = returnTime;
    }

    @Basic
    @Column(name = "ExceptionMsg")
    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TVgopUsersubCallSendSmsLogEntity that = (TVgopUsersubCallSendSmsLogEntity) o;
        return id == that.id &&
                Objects.equals(appid, that.appid) &&
                Objects.equals(msisdn, that.msisdn) &&
                Objects.equals(type, that.type) &&
                Objects.equals(offerCode, that.offerCode) &&
                Objects.equals(requestTime, that.requestTime) &&
                Objects.equals(sign, that.sign) &&
                Objects.equals(retCode, that.retCode) &&
                Objects.equals(retMsg, that.retMsg) &&
                Objects.equals(returnTime, that.returnTime) &&
                Objects.equals(exceptionMsg, that.exceptionMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appid, msisdn, type, offerCode, requestTime, sign, retCode, retMsg, returnTime, exceptionMsg);
    }
}
