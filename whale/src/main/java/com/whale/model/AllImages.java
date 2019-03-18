/**
 * 
 */
package com.whale.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whale.security.model.SecurityUser;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "ALL_IMAGES")
public class AllImages implements Serializable{
	private static final long serialVersionUID = 7523721117871457102L;
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "ID")
	private String id;
	@Column(name = "IMG_NAME")
	private String imgName;
	@Column(name = "IMG_PATH")
	private String imgPath;
	@Column(name = "DESCRIPTION")
	private String description;
	
	//@CollectionTable(name = "SECURITY_USER", joinColumns = @JoinColumn(name = "securityUserId"))
	@JsonBackReference
	@ManyToOne(targetEntity=SecurityUser.class)
	@JoinColumn(name="securityUserId")
	private SecurityUser securityUser;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public SecurityUser getSecurityUser() {
		return securityUser;
	}
	public void setSecurityUser(SecurityUser securityUser) {
		this.securityUser = securityUser;
	}
	
}
