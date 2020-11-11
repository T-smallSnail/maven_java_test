package com.bmcc.vgop.data.insidechannelorder.po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author ：pancho
 * @date ：Created in 2020/4/22 17:34
 * @description :
 */
@Entity
@Table(name = "t_vgop_usersub_server_inside_channel_order_product_log", catalog = "usersub")
public class TVgopUsersubServerInsideChannelOrderProductLog implements Serializable {
    private int id;
    private String credential;
    private String appId;
    private String authorization;
    private String offerid;
    private String tranId;
    private String phone;
    private Timestamp requestTime;
    private Timestamp responseTime;
    private Boolean success;
    private String code;
    private String msg;
    private String data;
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
    @Column(name = "credential")
    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    @Basic
    @Column(name = "appId")
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "Authorization")
    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    @Basic
    @Column(name = "offerid")
    public String getOfferid() {
        return offerid;
    }

    public void setOfferid(String offerid) {
        this.offerid = offerid;
    }

    @Basic
    @Column(name = "tranId")
    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "request_time")
    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    @Basic
    @Column(name = "response_time")
    public Timestamp getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Timestamp responseTime) {
        this.responseTime = responseTime;
    }

    @Basic
    @Column(name = "success")
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Basic
    @Column(name = "data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Basic
    @Column(name = "exception_msg")
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
        TVgopUsersubServerInsideChannelOrderProductLog that = (TVgopUsersubServerInsideChannelOrderProductLog) o;
        return id == that.id &&
                Objects.equals(credential, that.credential) &&
                Objects.equals(appId, that.appId) &&
                Objects.equals(authorization, that.authorization) &&
                Objects.equals(offerid, that.offerid) &&
                Objects.equals(tranId, that.tranId) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(requestTime, that.requestTime) &&
                Objects.equals(responseTime, that.responseTime) &&
                Objects.equals(success, that.success) &&
                Objects.equals(code, that.code) &&
                Objects.equals(msg, that.msg) &&
                Objects.equals(data, that.data) &&
                Objects.equals(exceptionMsg, that.exceptionMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, credential, appId, authorization, offerid, tranId, phone, requestTime, responseTime, success, code, msg, data, exceptionMsg);
    }
}
