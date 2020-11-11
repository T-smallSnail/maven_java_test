package com.bmcc.vgop.data.rightlog.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_right_log")
public class RightLog {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Long id;

	@Column(name = "phone")
	private String phone;

	@Column(name = "transId")
	private String transId;

	@Column(name = "channelId")
	private String channelId;

	@Column(name = "Bizs")
	private String Bizs;

	@Column(name = "create_time")
	private Date create_time;

	@Column(name = "resp_vgop_msg")
	private String resp_vgop_msg;

	@Column(name = "resp_offerId_retCode")
	private String resp_offerId_retCode;

	@Column(name = "resp_time")
	private Date resp_time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getBizs() {
		return Bizs;
	}

	public void setBizs(String bizs) {
		Bizs = bizs;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getResp_vgop_msg() {
		return resp_vgop_msg;
	}

	public void setResp_vgop_msg(String resp_vgop_msg) {
		this.resp_vgop_msg = resp_vgop_msg;
	}

	public String getResp_offerId_retCode() {
		return resp_offerId_retCode;
	}

	public void setResp_offerId_retCode(String resp_offerId_retCode) {
		this.resp_offerId_retCode = resp_offerId_retCode;
	}

	public Date getResp_time() {
		return resp_time;
	}

	public void setResp_time(Date resp_time) {
		this.resp_time = resp_time;
	}

}
