package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="pm_user_access")
public class UserSiteAccess implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1194571700953313603L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="access_id" , unique=true, nullable=false)
	private Long accessId;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name="site_id")
	private Site site;

	@Column(name="created_date")
	private Date createdDate=new Date();

	@Column(name="created_by")
	private String createdBy;

	@Column(name="modified_date")
	private Date modifiedDate;

	@Column(name="modified_by")
	private String modifiedBy;


	public UserSiteAccess() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getAccessId() {
		return accessId;
	}

	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}


	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public static Comparator<UserSiteAccess> COMPARE_BY_SITENAME = new Comparator<UserSiteAccess>() {
        public int compare(UserSiteAccess one, UserSiteAccess other) {
            return one.getSite().getSiteName().compareTo(other.getSite().getSiteName());
        }
    };


}
