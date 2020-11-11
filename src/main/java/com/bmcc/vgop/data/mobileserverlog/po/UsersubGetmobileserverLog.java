package com.bmcc.vgop.data.mobileserverlog.po;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UsersubGetmobileserverLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_vgop_usersub_phone_substitution_log", catalog = "usersub")

public class UsersubGetmobileserverLog implements java.io.Serializable {

	// Fields

	private Long id;
	private String appid;
	private String queryCode;
	private String phone;
	private String offerid;
	private String requestParamTime;
	private String sign;
	private Timestamp requestCrmTime;
	private Timestamp responseCrmTime;
	private String retCode;
	private String sysError;
	private String returnJson;
	private Timestamp requestTime;
	private Timestamp responseTime;

	// Constructors

	/** default constructor */
	public UsersubGetmobileserverLog() {
	}

	/** minimal constructor */
	public UsersubGetmobileserverLog(Timestamp requestCrmTime) {
		this.requestCrmTime = requestCrmTime;
	}

	/** full constructor */
	public UsersubGetmobileserverLog(String appid, String queryCode, String phone, String offerid, String requestParamTime,
			String sign, Timestamp requestCrmTime, Timestamp responseCrmTime, String retCode, String returnJson,Timestamp requestTime,Timestamp responseTime) {
		this.appid = appid;
		this.queryCode = queryCode;
		this.phone = phone;
		this.offerid = offerid;
		this.requestParamTime = requestParamTime;
		this.sign = sign;
		this.requestCrmTime = requestCrmTime;
		this.responseCrmTime = responseCrmTime;
		this.retCode = retCode;
		this.returnJson = returnJson;
		this.requestTime = requestTime;
		this.responseTime = responseTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "appid", length = 100)

	public String getAppid() {
		return this.appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	@Column(name = "queryCode", length = 100)

	public String getQueryCode() {
		return this.queryCode;
	}

	public void setQueryCode(String queryCode) {
		this.queryCode = queryCode;
	}

	@Column(name = "phone", length = 100)

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "offerid", length = 100)

	public String getOfferid() {
		return this.offerid;
	}

	public void setOfferid(String offerid) {
		this.offerid = offerid;
	}

	@Column(name = "requestParamTime", length = 100)

	public String getRequestParamTime() {
		return requestParamTime;
	}

	public void setRequestParamTime(String requestParamTime) {
		this.requestParamTime = requestParamTime;
	}
	
	@Column(name = "sign", length = 1000)

	public String getSign() {
		return this.sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Column(name = "requestCrmTime",length = 26)

	public Timestamp getRequestCrmTime() {
		return this.requestCrmTime;
	}

	public void setRequestCrmTime(Timestamp requestCrmTime) {
		this.requestCrmTime = requestCrmTime;
	}

	@Column(name = "responseCrmTime", length = 26)

	public Timestamp getResponseCrmTime() {
		return this.responseCrmTime;
	}

	public void setResponseCrmTime(Timestamp responseCrmTime) {
		this.responseCrmTime = responseCrmTime;
	}

	@Column(name = "retCode", length = 10)

	public String getRetCode() {
		return this.retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	@Column(name = "sysError", length = 500)
	public String getSysError() {
		return sysError;
	}

	public void setSysError(String sysError) {
		this.sysError = sysError;
	}

	@Column(name = "returnJson", length = 1000)

	public String getReturnJson() {
		return this.returnJson;
	}

	public void setReturnJson(String returnJson) {
		this.returnJson = returnJson;
	}
	
	@Column(name = "requestTime", length = 26)
	
	public Timestamp getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	
	@Column(name = "responseTime", length = 26)
	
	public Timestamp getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Timestamp responseTime) {
		this.responseTime = responseTime;
	}
}