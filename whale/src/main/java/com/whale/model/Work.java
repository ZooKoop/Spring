package com.whale.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.whale.security.model.SecurityUser;
@Entity
@Table(name = "WORK")
public class Work implements Serializable{
	private static final long serialVersionUID = -1021903537751565805L;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "ID")
	private String id;
	
	@Column(name = "TICKET_NUMBER")
	private Integer ticketNumber;
	/*
	 * 描述
	 */
	@Column(name = "DESCRIPTION")
	private String description;
	
	/*
	 * 是否发包 0/1
	 */
	@Column(name = "ISCREATE")
	private Integer isCreate;
	
	/*
	 * 是否有测试用例0/1
	 */
	@Column(name = "ISEXAMPLE")
	private Integer isExample;
	
	/*
	 * 包含哪些版本
	 */
	@Column(name = "VERSION")
	private String version;
	/*
	 * 时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DATE_TIME")
	private Date dateTime;
	
	
	@ManyToOne(targetEntity=SecurityUser.class)
	@JoinColumn(name="securityUserId")
	private SecurityUser securityUser;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Integer getTicketNumber() {
		return ticketNumber;
	}


	public void setTicketNumber(Integer ticketNumber) {
		this.ticketNumber = ticketNumber;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public SecurityUser getSecurityUser() {
		return securityUser;
	}


	public void setSecurityUser(SecurityUser securityUser) {
		this.securityUser = securityUser;
	}


	public Date getDateTime() {
		return dateTime;
	}


	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}


	public Integer getIsCreate() {
		return isCreate;
	}


	public void setIsCreate(Integer isCreate) {
		this.isCreate = isCreate;
	}


	public Integer getIsExample() {
		return isExample;
	}


	public void setIsExample(Integer isExample) {
		this.isExample = isExample;
	}
	
}
