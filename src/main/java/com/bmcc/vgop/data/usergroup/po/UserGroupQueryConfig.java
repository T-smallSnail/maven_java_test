package com.bmcc.vgop.data.usergroup.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_USER_GROUP_QUERY_CONFIG")
public class UserGroupQueryConfig {

	@Id
	@Column(name = "code")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public String code;

	@Column(name = "sql")
	public String sql;

	@Column(name = "is_valid")
	public Short isValid;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Short getIsValid() {
		return isValid;
	}

	public void setIsValid(Short isValid) {
		this.isValid = isValid;
	}
}
