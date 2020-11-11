package com.bmcc.vgop.data.thirdchannel.po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "t_vgop_usersub_call_crm_channel_info")
public class TVgopUsersubCallCrmChannelInfoEntity {
    private String appId;
    private String appName;
    private String opcode;
    private String oppwd;
    private String oporgid;
    private String md5Salt;
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

    @Basic
    @Column(name = "app_name")
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Basic
    @Column(name = "opcode")
    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    @Basic
    @Column(name = "oppwd")
    public String getOppwd() {
        return oppwd;
    }

    public void setOppwd(String oppwd) {
        this.oppwd = oppwd;
    }

    @Basic
    @Column(name = "oporgid")
    public String getOporgid() {
        return oporgid;
    }

    public void setOporgid(String oporgid) {
        this.oporgid = oporgid;
    }

    @Basic
    @Column(name = "md5_salt")
    public String getMd5Salt() {
        return md5Salt;
    }

    public void setMd5Salt(String md5Salt) {
        this.md5Salt = md5Salt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TVgopUsersubCallCrmChannelInfoEntity that = (TVgopUsersubCallCrmChannelInfoEntity) o;
        return Objects.equals(appId, that.appId) &&
                Objects.equals(appName, that.appName) &&
                Objects.equals(opcode, that.opcode) &&
                Objects.equals(oppwd, that.oppwd) &&
                Objects.equals(oporgid, that.oporgid) &&
                Objects.equals(md5Salt, that.md5Salt) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(isValid, that.isValid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId, appName, opcode, oppwd, oporgid, md5Salt, createTime, isValid);
    }
}
