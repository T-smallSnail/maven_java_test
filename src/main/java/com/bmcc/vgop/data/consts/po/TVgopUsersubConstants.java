package com.bmcc.vgop.data.consts.po;

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
 * @date ：Created in 2020/4/22 21:45
 * @description :
 */
@Entity
@Table(name = "t_vgop_usersub_constants", catalog = "usersub")
public class TVgopUsersubConstants implements Serializable {

    private String constKey;
    private String constValue;
    private String comment;
    private Timestamp createTime;
    private Integer isValld;

    @Basic
    @Id
    @Column(name = "const_key")
    public String getConstKey() {
        return constKey;
    }

    public void setConstKey(String constKey) {
        this.constKey = constKey;
    }

    @Basic
    @Column(name = "const_value")
    public String getConstValue() {
        return constValue;
    }

    public void setConstValue(String constValue) {
        this.constValue = constValue;
    }

    @Basic
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
    @Column(name = "is_valld")
    public Integer getIsValld() {
        return isValld;
    }

    public void setIsValld(Integer isValld) {
        this.isValld = isValld;
    }



    @Override
    public int hashCode() {
        return Objects.hash(constKey, constValue, comment, createTime, isValld);
    }
}
