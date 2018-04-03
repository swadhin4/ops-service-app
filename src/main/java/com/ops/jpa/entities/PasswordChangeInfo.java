package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="pm_pwd_change_info")
public class PasswordChangeInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2613849258329668351L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="link_id")
	private Long linkId;
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="email")
	private String email;
	
	@Column(name="link")
	private String link;
	
	@Column(name="token")
	private String token;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expiry_date")
	private Date expiryDate;
	
	@Column(name="del_flag")
	private int delFlag;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt = new Date();
	

	public PasswordChangeInfo() {
		super();
	}

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiry() {
		return expiryDate;
	}

	public void setExpiry(Date expiry) {
		this.expiryDate = expiry;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
