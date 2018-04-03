package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="pm_area")
public class Area implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1241308826965453415L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="area_id")
	private Long areaId;

	@Column(name="area_desc")
	private String areaDesc;

	@Column(name="area_name")
	private String areaName;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Date createdDate =new Date();

	@Column(name="modified_by")
	private String modifiedBy;

	@Column(name="modified_date")
	private String modifiedDate;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="dist_id", referencedColumnName="district_id")
	@JsonIgnore
	private District district;

	public Area() {
	}

	public Long getAreaId() {
		return this.areaId;
	}

	public void setAreaId(final Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaDesc() {
		return this.areaDesc;
	}

	public void setAreaDesc(final String areaDesc) {
		this.areaDesc = areaDesc;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(final String areaName) {
		this.areaName = areaName;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(final String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(final String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}




	public District getDistrict() {
		return district;
	}

	public void setDistrict(final District district) {
		this.district = district;
	}

	@Override
	public String toString() {
		return "Area [areaId=" + areaId + ", areaDesc=" + areaDesc
				+ ", areaName=" + areaName + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}



}
