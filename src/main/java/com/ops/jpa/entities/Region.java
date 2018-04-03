package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the pm_Region database table.
 * 
 */
@Entity
@Table(name="pm_region")
public class Region implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="region_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long regionId;

	@Column(name="region_code")
	private String regionCode;

	@Column(name="region_description")
	private String regionDescription;

	@Column(name="region_name")
	private String regionName;


	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Date createdDate = new Date();

	@Column(name="modified_by")
	private String modifiedBy;

	@Column(name="modified_date")
	private Date modifiedDate;


	public Region() {
	}

	public Long getRegionId() {
		return this.regionId;
	}

	public void setRegionId(final Long regionId) {
		this.regionId = regionId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(final String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(final Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(final String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionDescription() {
		return regionDescription;
	}

	public void setRegionDescription(final String regionDescription) {
		this.regionDescription = regionDescription;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(final String regionName) {
		this.regionName = regionName;
	}



}