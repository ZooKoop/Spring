package com.whale.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "WORK_CONCENT")
public class WorkConcent implements Serializable{
	private static final long serialVersionUID = 6654545176170339428L;
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "ID")
	private String id;
	/*
	 * 是否有测试用例
	 */
	@Column(name = "EXAMPLE_URLS")
	private String exampleUrls;
	/*
	 * 脚本对外展示地址
	 */
	@Column(name = "SQL_URLS")
	private String sqlUrls;
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity = Work.class)
	@JoinColumn(name = "WORK_ID")
	@JsonIgnore
	private Work work;

	public String getSqlUrls() {
		return sqlUrls;
	}

	public void setSqlUrls(String sqlUrls) {
		this.sqlUrls = sqlUrls;
	}

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	
}
