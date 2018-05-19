package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="pm_asset_location")
public class AssetLocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3370686035238869870L;


	@Id	
	@Column(name="location_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long locationId;

	@Column(name="location_name")
	private String locationName;

	@Column(name="created_date")
	private Date createdDate = new Date();

	@Column(name="created_by")
	private String createdBy;

	public AssetLocation() {
		super();
	}



	public Long getLocationId() {
		return locationId;
	}



	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}



	public String getLocationName() {
		return locationName;
	}



	public void setLocationName(String locationName) {
		this.locationName = locationName;
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

}
