package com.bmcc.vgop.data.thirdchannel.po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/10 15:10
 * @description :
 */
@Entity
@Table(name = "t_vgop_usersub_call_crm_product_info")
public class TVgopUsersubCallCrmProductInfoEntity {
    private String offerId;
    private String proName;
    private Timestamp createTime;
    private Short isValid;

    @Id
    @Column(name = "offer_id")
    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    @Basic
    @Column(name = "pro_name")
    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
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
