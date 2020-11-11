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
@Table(name = "t_vgop_usersub_mobile_identify_stype_config", catalog = "usersub")
public class TVgopUsersubMobileIdentifyStypeConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "s_type")
    private String sType;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "descr")
    private String descr;

    @Column(name = "is_valid")
    private Integer isValid;

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "TVgopUsersubMobileIdentifyStypeConfig{" +
            "sType=" + sType +
            ", createTime=" + createTime +
            ", descr=" + descr +
            ", isValid=" + isValid +
        "}";
    }
}
