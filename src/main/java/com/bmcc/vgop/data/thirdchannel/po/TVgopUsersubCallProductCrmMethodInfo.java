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
 * @date ：Created in 2019/12/17 10:55
 * @description :
 */
@Entity
@Table(name = "t_vgop_usersub_call_product_crm_method_info")
public class TVgopUsersubCallProductCrmMethodInfo {
    private int id;
    private String offerId;
    private String offerName;
    private Short type;
    private String method;
    private Timestamp createTime;
    private Short isValid;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "offer_id")
    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    @Basic
    @Column(name = "offer_name")
    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    @Basic
    @Column(name = "type")
    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    @Basic
    @Column(name = "method")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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
        TVgopUsersubCallProductCrmMethodInfo that = (TVgopUsersubCallProductCrmMethodInfo) o;
        return id == that.id &&
                Objects.equals(offerId, that.offerId) &&
                Objects.equals(offerName, that.offerName) &&
                Objects.equals(type, that.type) &&
                Objects.equals(method, that.method) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(isValid, that.isValid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, offerId, offerName, type, method, createTime, isValid);
    }
}
