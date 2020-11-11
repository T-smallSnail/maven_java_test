package com.bmcc.vgop.web.subserv.vo;

public class SubReq {

	private String phone;
	private String transId;
	private String channelId;
	private Bizs[] bizs;
	
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

	public Bizs[] getBizs() {
		return bizs;
	}

	public void setBizs(Bizs[] bizs) {
		this.bizs = bizs;
	}

	

}
