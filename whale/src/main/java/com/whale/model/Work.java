package com.whale.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	private String ticketNumber;
	/*
	 * 标题
	 */
	@org.hibernate.annotations.Type(type = "org.hibernate.type.TextType")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "TICKET_TITEL")
	private String ticketTitel;
	/*
	 * 描述
	 */
	@org.hibernate.annotations.Type(type = "org.hibernate.type.TextType")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "DESCRIPTION")
	private String description;

	/*
	 * 发包E-patch
	 */
	@Column(name = "PATCH")
	private String patch;

	/*
	 * 是否关闭0/1
	 */
	@Column(name = "ISCLOSE")
	private String isClose;
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
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@UpdateTimestamp
	@Column(name = "UPDATE_TIME")
	private Date updateTime;
	/*
	 * deadline时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")//这是进来string转date
	@Temporal(TemporalType.DATE)////这是返回前端view的格式
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")//这是返回前端json的格式
	@Column(name = "DEADLINE")
	private Date deadline;
	
	@ManyToOne(targetEntity = SecurityUser.class)
	@JoinColumn(name = "securityUserId")
	private SecurityUser securityUser;
	
	//@OneToMany (mappedBy = "Articles"),mappedBy指向的是要关联的属性，而不是要关联的类
	//cascade={CascadeType.REMOVE} 会把主表和从表的数据都删除
	@OneToMany(targetEntity=WorkConcent.class,cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="work")
//	@JsonIgnore
	private Set<WorkConcent> workConcentList = new HashSet<>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsClose() {
		return isClose;
	}

	public void setIsClose(String isClose) {
		this.isClose = isClose;
	}

	public String getPatch() {
		return patch;
	}

	public void setPatch(String patch) {
		this.patch = patch;
	}

	public String getTicketTitel() {
		return ticketTitel;
	}

	public void setTicketTitel(String ticketTitel) {
		this.ticketTitel = ticketTitel;
	}

	public Set<WorkConcent> getWorkConcentList() {
		return workConcentList;
	}

	public void setWorkConcentList(Set<WorkConcent> workConcentList) {
		this.workConcentList = workConcentList;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	
}
