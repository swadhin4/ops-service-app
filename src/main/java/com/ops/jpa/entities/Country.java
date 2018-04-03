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
@Table(name="pm_country")
public class Country implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1241308826965453415L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="country_id")
	private Long countryId;

	@Column(name="country_code")
	private String countryCode;

	@Column(name="country_name")
	private String countryName;

	@Column(name="country_desc")
	private String countryDesc;

	@Column(name="created_date")
	private Date createdDate = new Date();

	@Column(name="created_by")
	private String createdBy;

	@Column(name="modified_date")
	private String modifiedDate;

	@Column(name="modified_by")
	private String modifiedBy;

	@Column(name="region_id")
	private Long regionId;


	public Country() {
	}


	public Long getCountryId() {
		return countryId;
	}


	public void setCountryId(final Long countryId) {
		this.countryId = countryId;
	}


	public String getCountryCode() {
		return countryCode;
	}


	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}


	public String getCountryName() {
		return countryName;
	}


	public void setCountryName(final String countryName) {
		this.countryName = countryName;
	}


	public String getCountryDesc() {
		return countryDesc;
	}


	public void setCountryDesc(final String countryDesc) {
		this.countryDesc = countryDesc;
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


	public String getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(final String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(final String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Long getRegionId() {
		return regionId;
	}


	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}


	@Override
	public String toString() {
		return "Country [countryId=" + countryId + ", countryCode="
				+ countryCode + ", countryName=" + countryName
				+ ", countryDesc=" + countryDesc + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy + ", modifiedDate="
				+ modifiedDate + ", modifiedBy=" + modifiedBy + "]";
	}




}
