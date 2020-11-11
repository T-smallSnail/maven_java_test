package com.bmcc.vgop.data.consts.po;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UsersubConstantId entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_vgop_usersub_phone_substitution_conf", catalog = "usersub")
public class PhoneSubstitutionConf implements java.io.Serializable {

	// Fields

	private String appid;
	private String ips;
	private String md5Key;
	private Integer counts;
	private Integer is_valid;
	private Timestamp created_time;


	// Property accessors
	@Id
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "appid", nullable = false, length = 50)
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	@Column(name = "ips", length = 500)
	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}
	@Column(name = "md5_key", length = 10)
	public String getMd5Key() {
		return md5Key;
	}

	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}

	@Column(name = "counts", length = 5)
	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

	@Column(name = "is_valid", length = 5)
	public Integer getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(Integer is_valid) {
		this.is_valid = is_valid;
	}

	@Column(name = "created_time")
	public Timestamp getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Timestamp created_time) {
		this.created_time = created_time;
	}

	
}