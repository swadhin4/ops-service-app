package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the pm_ProspectServiceProvider database table.
 * 
 */
@Entity
@Table(name="pm_prospect_service_provider")
public class ProspectServiceProvider implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 294256202079544969L;

	@Id
	@Column(name="prospect_spid")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long prospectSpid;

	private String code;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Date createdDate=new Date();

	@Column(name="modified_by")
	private String modifiedBy;

	@Column(name="modified_date")
	private String modifiedDate;

	private String name;

	@Column(name="phone_number")
	private long phoneNumber;

	//bi-directional many-to-one association to pm_Company
	/*	@OneToMany
	@JoinColumn(name="real_spid")
	private List<Company> company;

	//bi-directional many-to-one association to pm_Country
	@OneToMany
	@JoinColumn(name="country_id")
	private List<Country> country;*/

	//bi-directional many-to-one association to pm_Region
	@OneToMany
	@JoinColumn(name="region_id")
	private List<Region> region;

	public ProspectServiceProvider() {
	}

	public Long getProspectSpid() {
		return this.prospectSpid;
	}

	public void setProspectSpid(final Long prospectSpid) {
		this.prospectSpid = prospectSpid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
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

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(final String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/*	public List<Company> getCompany() {
		return company;
	}

	public void setCompany(final List<Company> company) {
		this.company = company;
	}

	public List<Country> getCountry() {
		return country;
	}

	public void setCountry(final List<Country> country) {
		this.country = country;
	}*/

	public List<Region> getRegion() {
		return region;
	}

	public void setRegion(final List<Region> region) {
		this.region = region;
	}



}