package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="pm_role_permission")
public class RolePermission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3151877773486627776L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="permission_id", unique=true, nullable=false)
	private Long permissionId;

	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="feature_id")
	private AppFeature appFeature;

	@Column(name="access_level", insertable=false, updatable=false)
	private String accessLevel;

	@Column(name="access_code")
	private String accessCode;

	@Column(name="created_date")
	private Date createdDate=new Date();

	@Column(name="created_by")
	private String createdBy;

	@Column(name="modified_date")
	private Date modifiedDate;

	@Column(name="modified_by")
	private String modifiedBy;


	public RolePermission() {
		super();
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(final Long permissionId) {
		this.permissionId = permissionId;
	}


	public Role getRole() {
		return role;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	public AppFeature getAppFeature() {
		return appFeature;
	}

	public void setAppFeature(final AppFeature appFeature) {
		this.appFeature = appFeature;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(final String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(final Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(final String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		return "RolePermission [permissionId=" + permissionId
				+ ", accessLevel=" + accessLevel + "]";
	}


}
