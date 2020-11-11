package com.bmcc.vgop.data.crmopenapi.po;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TVgopAndMarketUseCrmApiLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_vgop_usersub_use_crm_api_log")
public class TVgopAndMarketUseCrmApiLog implements java.io.Serializable {

	// Fields

	private Long id;
	private String phone;
	private String offerId;
	private String method;
	private String crmOpapiRespCode;
	private String crmOpapiRespDesc;
	private String serviceRespCode;
	private String serviceRespMessage;
	private String serviceRespOrderId;
	private String serviceRespBusiSerialNo;
	private String reqServiceXml;
	private String respXml;
	private String respResult;
	private Timestamp requestTime;
	private Timestamp respTime;
	private String error;
	private String source;
	

	// Constructors

	/** default constructor */
	public TVgopAndMarketUseCrmApiLog() {
	}

	/** full constructor */
	public TVgopAndMarketUseCrmApiLog(String phone, String offerId,String method, String crmOpapiRespCode, String crmOpapiRespDesc, String serviceRespCode, String serviceRespMessage, String serviceRespOrderId, String serviceRespBusiSerialNo, String reqServiceXml, String respXml, String respResult, Timestamp requestTime, Timestamp respTime, String error,String source) {
		this.phone = phone;
		this.method = method;
		this.crmOpapiRespCode = crmOpapiRespCode;
		this.crmOpapiRespDesc = crmOpapiRespDesc;
		this.serviceRespCode = serviceRespCode;
		this.serviceRespMessage = serviceRespMessage;
		this.serviceRespOrderId = serviceRespOrderId;
		this.serviceRespBusiSerialNo = serviceRespBusiSerialNo;
		this.reqServiceXml = reqServiceXml;
		this.respXml = respXml;
		this.respResult = respResult;
		this.requestTime = requestTime;
		this.respTime = respTime;
		this.error = error;
		this.offerId = offerId;
		this.source = source;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PHONE")

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "method")

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(name = "crm_opapi_respCode")

	public String getCrmOpapiRespCode() {
		return this.crmOpapiRespCode;
	}

	public void setCrmOpapiRespCode(String crmOpapiRespCode) {
		this.crmOpapiRespCode = crmOpapiRespCode;
	}

	@Column(name = "crm_opapi_respDesc")

	public String getCrmOpapiRespDesc() {
		return this.crmOpapiRespDesc;
	}

	public void setCrmOpapiRespDesc(String crmOpapiRespDesc) {
		this.crmOpapiRespDesc = crmOpapiRespDesc;
	}

	@Column(name = "service_resp_code")

	public String getServiceRespCode() {
		return this.serviceRespCode;
	}

	public void setServiceRespCode(String serviceRespCode) {
		this.serviceRespCode = serviceRespCode;
	}

	@Column(name = "service_resp_message")

	public String getServiceRespMessage() {
		return this.serviceRespMessage;
	}

	public void setServiceRespMessage(String serviceRespMessage) {
		this.serviceRespMessage = serviceRespMessage;
	}


	@Column(name = "req_service_xml")

	public String getReqServiceXml() {
		return this.reqServiceXml;
	}

	public void setReqServiceXml(String reqServiceXml) {
		this.reqServiceXml = reqServiceXml;
	}

	@Column(name = "resp_xml")

	public String getRespXml() {
		return this.respXml;
	}

	public void setRespXml(String respXml) {
		this.respXml = respXml;
	}

	@Column(name = "resp_result")

	public String getRespResult() {
		return this.respResult;
	}

	public void setRespResult(String respResult) {
		this.respResult = respResult;
	}

	@Column(name = "request_time")

	public Timestamp getRequestTime() {
		return this.requestTime;
	}

	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}

	@Column(name = "resp_time")

	public Timestamp getRespTime() {
		return this.respTime;
	}

	public void setRespTime(Timestamp respTime) {
		this.respTime = respTime;
	}

	@Column(name = "error")

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Column(name = "service_resp_order_id")
	public String getServiceRespOrderId() {
		return serviceRespOrderId;
	}

	public void setServiceRespOrderId(String serviceRespOrderId) {
		this.serviceRespOrderId = serviceRespOrderId;
	}

	@Column(name = "service_resp_busi_serial_no")
	public String getServiceRespBusiSerialNo() {
		return serviceRespBusiSerialNo;
	}

	public void setServiceRespBusiSerialNo(String serviceRespBusiSerialNo) {
		this.serviceRespBusiSerialNo = serviceRespBusiSerialNo;
	}

	@Column(name = "offer_id")
	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	@Column(name = "source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}