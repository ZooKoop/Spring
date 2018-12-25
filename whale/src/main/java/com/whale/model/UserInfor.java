package com.whale.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="USER_INFOR")
public class UserInfor implements Serializable {
	private static final long serialVersionUID = -6019822183661689633L;
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "ID")
	private String id;
	
	@Basic(optional = false) //不为空
	@Column(name="USER_NAME")
	private String userName;
	
	@Basic(optional = false) //不为空
	@Column(name="USER_PASSWORD")
	private String userPassword;
	
	@Basic(optional = false) //不为空
	@Column(name="EMAIL")
	@Email
	private String email;
	
	@Column(name="REG_TIME")
	private Date regTime=new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	@Override
	public String toString() {
		return "UserInfor [id=" + id + ", userName=" + userName + ", userPassword=" + userPassword + ", email=" + email
				+ "]";
	}

}
