package com.bmcc.vgop.data.mobidentify.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author vgop
 * @since 2019-10-29
 */
@Entity
@Table(name = "t_vgop_usersub_mobile_identify_channel_stype_ref", catalog = "usersub")
public class TVgopUsersubMobileIdentifyChannelStypeRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "app_id")
    private String appId;
    @Id
    @Column(name = "s_type")
    private String sType;

    @Column(name = "is_valid")
    private Integer isValid;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }
    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "TVgopUsersubMobileIdentifyChannelStypeRef{" +
            "appId=" + appId +
            ", sType=" + sType +
            ", isValid=" + isValid +
        "}";
    }
}
