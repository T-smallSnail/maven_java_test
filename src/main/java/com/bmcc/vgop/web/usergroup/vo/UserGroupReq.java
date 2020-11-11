package com.bmcc.vgop.web.usergroup.vo;

public class UserGroupReq {
	private String phone;
	private String transId;
	private String channelId;
	private QueryObj[] queryObj;

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

	public QueryObj[] getQueryObj() {
		return queryObj;
	}

	public void setQueryObj(QueryObj[] queryObj) {
		this.queryObj = queryObj;
	}
}
