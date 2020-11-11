package com.bmcc.vgop.data.mobidentify.po;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "t_vgop_usersub_mobile_identify_channel_config", catalog = "usersub")
public class TVgopUsersubMobileIdentifyChannelConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "app_id")
    private String appId;

    @Column(name = "name")
    private String name;

    @Column(name = "sign_salt")
    private String signSalt;

    @Column(name = "descr")
    private String descr;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "is_valid")
    private Integer isValid;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSignSalt() {
        return signSalt;
    }

    public void setSignSalt(String signSalt) {
        this.signSalt = signSalt;
    }
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "TVgopUsersubMobileIdentifyChannelConfig{" +
            "appId=" + appId +
            ", name=" + name +
            ", signSalt=" + signSalt +
            ", descr=" + descr +
            ", createTime=" + createTime +
            ", isValid=" + isValid +
        "}";
    }
}
