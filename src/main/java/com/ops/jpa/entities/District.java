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
@Table(name="pm_district")
public class District implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -7971549941815610884L;

	@Id
	@Column(name="district_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long districtId;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Date createdDate=new Date();

	@Column(name="district_code")
	private String districtCode;

	@Column(name="district_desc")
	private String districtDesc;

	@Column(name="district_name")
	private String districtName;

	@Column(name="modified_by")
	private String modifiedBy;

	@Column(name="modified_date")
	private Date modifiedDate;

	@Column(name="country_id")
	private Long country;



	public District() {
	}


	public Long getDistrictId() {
		return districtId;
	}


	public void setDistrictId(final Long districtId) {
		this.districtId = districtId;
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

	public String getDistrictCode() {
		return this.districtCode;
	}

	public void setDistrictCode(final String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictDesc() {
		return this.districtDesc;
	}

	public void setDistrictDesc(final String districtDesc) {
		this.districtDesc = districtDesc;
	}

	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(final String districtName) {
		this.districtName = districtName;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
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




	public Long getCountry() {
		return country;
	}


	public void setCountry(final Long country) {
		this.country = country;
	}




	@Override
	public String toString() {
		return "District [districtId=" + districtId + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate
				+ ", districtCode=" + districtCode + ", districtDesc="
				+ districtDesc + ", districtName=" + districtName
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

}
