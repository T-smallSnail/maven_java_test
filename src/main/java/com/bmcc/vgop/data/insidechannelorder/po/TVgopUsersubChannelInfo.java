package com.bmcc.vgop.data.insidechannelorder.po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "t_vgop_usersub_channel_info", catalog = "usersub")
public class TVgopUsersubChannelInfo implements Serializable {
    private String appId;
    private String name;
    private String kongCredential;
    private String appKey;
    private String descr;
    private Timestamp createTime;
    private Integer isValid;

    @Id
    @Column(name = "app_id")
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "kong_credential")
    public String getKongCredential() {
        return kongCredential;
    }

    public void setKongCredential(String kongCredential) {
        this.kongCredential = kongCredential;
    }

    @Basic
    @Column(name = "app_key")
    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Basic
    @Column(name = "descr")
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
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
    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TVgopUsersubChannelInfo that = (TVgopUsersubChannelInfo) o;
        return Objects.equals(appId, that.appId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(kongCredential, that.kongCredential) &&
                Objects.equals(appKey, that.appKey) &&
                Objects.equals(descr, that.descr) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(isValid, that.isValid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId, name, kongCredential, appKey, descr, createTime, isValid);
    }
}
