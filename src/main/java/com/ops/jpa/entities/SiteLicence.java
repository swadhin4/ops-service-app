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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="pm_sitelicense")
public class SiteLicence implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6449095352529300553L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="license_id")
	private Long licenseId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="site_id")
	private Site site;

	@Column(name="license_name")
	private String licenseName;

	@Column(name="start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name="end_date")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(name="attachment_path")
	private String attachmentPath;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_on")
	private Date createdOn = new Date();

	@Column(name="modified_by")
	private String modifiedBy;

	@Column(name="modified_on")
	private Date modifiedOn;


	public SiteLicence() {
		super();
		// TODO Auto-generated constructor stub
	}



	public SiteLicence(Long licenseId, Site site) {
		super();
		this.licenseId = licenseId;
		this.site = site;
	}



	public Long getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(final Long licenseId) {
		this.licenseId = licenseId;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(final Site site) {
		this.site = site;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenceName(final String licenseName) {
		this.licenseName = licenseName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(final String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(final String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(final Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Override
	public String toString() {
		return "SiteLicence [licenseId=" + licenseId + ", licenseName="
				+ licenseName + ", startDate=" + startDate + ", endDate="
				+ endDate + ", attachmentPath=" + attachmentPath
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn
				+ ", modifiedBy=" + modifiedBy + ", modifiedOn=" + modifiedOn
				+ "]";
	}





}
