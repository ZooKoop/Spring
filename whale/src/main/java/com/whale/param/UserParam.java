package com.whale.param;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserParam implements Serializable{
	private static final long serialVersionUID = 4658966840107394356L;

	private String id;

	@NotEmpty(message = "用户名不能为空！")
	private String userName;

	@NotEmpty(message="密码不能为空！")
	@Size(min=1,max=10,message="密码的长度应该在1和10之间！")
	private String userPassword;
	
	@NotEmpty(message="邮箱不能为空！")
	@Email(message="邮箱的格式不正确！") 
	private String email;

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

//	@Override
//	public String toString() {
//		return ToStringBuilder.reflectionToString(this);
//	}

}
