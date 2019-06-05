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

	@Column(name = "OPCTION_CODE")
	private String opctionCode;
	@Column(name = "OPCTION")
	private String opction;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpctionCode() {
		return opctionCode;
	}
	public void setOpctionCode(String opctionCode) {
		this.opctionCode = opctionCode;
	}
	public String getOpction() {
		return opction;
	}
	public void setOpction(String opction) {
		this.opction = opction;
	}
	
//	@ManyToOne(targetEntity=Work.class)
//	@JoinColumn(name="workId")
//	private Work work;

}
