package com.bmcc.vgop.data.bizs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_right_bizcode_offerid")
public class BizOfferid {
	
	@Id
	@Column(name="bizcode")
	private String bizCode;
	
	@Column(name="offerid_no_free")
	private String offeridNoFree;
	
	@Column(name="offerid_for_free")
	private String offeridForFree;

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getOfferidNoFree() {
		return offeridNoFree;
	}

	public void setOfferidNoFree(String offeridNoFree) {
		this.offeridNoFree = offeridNoFree;
	}

	public String getOfferidForFree() {
		return offeridForFree;
	}

	public void setOfferidForFree(String offeridForFree) {
		this.offeridForFree = offeridForFree;
	}
	

}
