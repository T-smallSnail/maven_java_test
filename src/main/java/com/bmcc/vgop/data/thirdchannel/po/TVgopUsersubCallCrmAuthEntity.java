package com.bmcc.vgop.data.thirdchannel.po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/10 15:10
 * @description :
 */
@Entity
@Table(name = "t_vgop_usersub_call_crm_auth")
//@IdClass(TVgopUsersubCallCrmAuthEntityPK.class)
public class TVgopUsersubCallCrmAuthEntity implements Serializable {
    private String appId;
    private String offerId;
    private short infType;
    private Short isOneTime;
    private Integer expiresTime;
    private Timestamp createTime;
    private Short isValid;

    @Id
    @Column(name = "app_id")
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Id
    @Column(name = "offer_id")
    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    @Id
    @Column(name = "inf_type")
    public short getInfType() {
        return infType;
    }

    public void setInfType(short infType) {
        this.infType = infType;
    }

    @Basic
    @Column(name = "is_one_time")
    public Short getIsOneTime() {
        return isOneTime;
    }

    public void setIsOneTime(Short isOneTime) {
        this.isOneTime = isOneTime;
    }

    @Basic
    @Column(name = "expires_time")
    public Integer getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(Integer expiresTime) {
        this.expiresTime = expiresTime;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "is_valid")
    public Short getIsValid() {
        return isValid;
    }

    public void setIsValid(Short isValid) {
        this.isValid = isValid;
    }

}
