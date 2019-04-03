package com.whale.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "WORK_OPCTION")
public class WorkOpction implements Serializable {
	private static final long serialVersionUID = -4159006514563159070L;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "ID")
	private String id;

	@Column(name = "WORK_OPCTION")
	private Integer workOpction;
	
	@ManyToOne(targetEntity=Work.class)
	@JoinColumn(name="workId")
	private Work work;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getWorkOpction() {
		return workOpction;
	}

	public void setWorkOpction(Integer workOpction) {
		this.workOpction = workOpction;
	}
	
}
