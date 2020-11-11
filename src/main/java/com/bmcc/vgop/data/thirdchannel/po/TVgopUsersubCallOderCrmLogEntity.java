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
@Table(name = "t_vgop_usersub_call_oder_crm_log")
public class TVgopUsersubCallOderCrmLogEntity {
    private int id;
    private String appid;
    private String msisdn;
    private String subId;
    private String offerCode;
    private String requestTime;
    private String serialCode;
    private String extendData;
    private String accessToken;
    private Integer retCode;
    private String retMsg;
    private String dealMsg;
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
    @Column(name = "subId")
    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
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
    @Column(name = "serialCode")
    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    @Basic
    @Column(name = "extendData")
    public String getExtendData() {
        return extendData;
    }

    public void setExtendData(String extendData) {
        this.extendData = extendData;
    }

    @Basic
    @Column(name = "accessToken")
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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
    @Column(name = "dealMsg")
    public String getDealMsg() {
        return dealMsg;
    }

    public void setDealMsg(String dealMsg) {
        this.dealMsg = dealMsg;
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
        TVgopUsersubCallOderCrmLogEntity that = (TVgopUsersubCallOderCrmLogEntity) o;
        return id == that.id &&
                Objects.equals(appid, that.appid) &&
                Objects.equals(msisdn, that.msisdn) &&
                Objects.equals(subId, that.subId) &&
                Objects.equals(offerCode, that.offerCode) &&
                Objects.equals(requestTime, that.requestTime) &&
                Objects.equals(serialCode, that.serialCode) &&
                Objects.equals(extendData, that.extendData) &&
                Objects.equals(accessToken, that.accessToken) &&
                Objects.equals(retCode, that.retCode) &&
                Objects.equals(retMsg, that.retMsg) &&
                Objects.equals(dealMsg, that.dealMsg) &&
                Objects.equals(returnTime, that.returnTime) &&
                Objects.equals(exceptionMsg, that.exceptionMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appid, msisdn, subId, offerCode, requestTime, serialCode, extendData, accessToken, retCode, retMsg, dealMsg, returnTime, exceptionMsg);
    }
}
