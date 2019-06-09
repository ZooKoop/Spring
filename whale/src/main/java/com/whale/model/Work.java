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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.whale.security.model.SecurityUser;

@Entity
@Table(name = "WORK")
public class Work implements Serializable {
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
	@Column(name = "ISCREATE317")
	private Integer isCreate317;
	@Column(name = "ISCREATE316")
	private Integer isCreate316;
	/*
	 * 是否有测试用例0/1
	 */
	@Column(name = "ISEXAMPLE")
	private Integer isExample;
	/*
	 * 是否关闭0/1
	 */
	@Column(name = "ISCLOSE")
	private Integer isClose;

	/*
	 * 包含哪些版本
	 */
	@Column(name = "VERSION")
	private String version;
	/*
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@CreationTimestamp
	@Column(name = "DATE_TIME")
	private Date dateTime;
	/*
	 * 修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@UpdateTimestamp
	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	@ManyToOne(targetEntity = SecurityUser.class)
	@JoinColumn(name = "securityUserId")
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

	public Integer getIsCreate317() {
		return isCreate317;
	}

	public void setIsCreate317(Integer isCreate317) {
		this.isCreate317 = isCreate317;
	}

	public Integer getIsCreate316() {
		return isCreate316;
	}

	public void setIsCreate316(Integer isCreate316) {
		this.isCreate316 = isCreate316;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsClose() {
		return isClose;
	}

	public void setIsClose(Integer isClose) {
		this.isClose = isClose;
	}

	@Override
	public String toString() {
		return "Work [id=" + id + ", ticketNumber=" + ticketNumber + ", description=" + description + ", isCreate="
				+ isCreate + ", isCreate317=" + isCreate317 + ", isCreate316=" + isCreate316 + ", isExample="
				+ isExample + ", isClose=" + isClose + ", version=" + version + ", dateTime=" + dateTime
				+ ", updateTime=" + updateTime + ", securityUser=" + securityUser + "]";
	}

}
