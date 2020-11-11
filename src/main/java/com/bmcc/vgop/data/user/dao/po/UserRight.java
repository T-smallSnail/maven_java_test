package com.bmcc.vgop.data.user.dao.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_right_white_list")
public class UserRight {

	public UserRight() {
		super();
	}

	@Id
	@Column(name = "phone")
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
