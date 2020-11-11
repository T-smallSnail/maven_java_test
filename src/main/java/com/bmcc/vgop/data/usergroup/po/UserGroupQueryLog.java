package com.bmcc.vgop.data.usergroup.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_USER_GROUP_QUERY_LOG")
public class UserGroupQueryLog {
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

	@Column(name = "code")
	private String code;

	@Column(name = "create_time")
	private Date create_time;

	@Column(name = "resp_vgop_msg")
	private String respVgopMsg;

	@Column(name = "resp_groupid")
	private String respGroupid;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getRespVgopMsg() {
		return respVgopMsg;
	}

	public void setRespVgopMsg(String respVgopMsg) {
		this.respVgopMsg = respVgopMsg;
	}

	public String getRespGroupid() {
		return respGroupid;
	}

	public void setRespGroupid(String respGroupid) {
		this.respGroupid = respGroupid;
	}

	public Date getResp_time() {
		return resp_time;
	}

	public void setResp_time(Date resp_time) {
		this.resp_time = resp_time;
	}
}
