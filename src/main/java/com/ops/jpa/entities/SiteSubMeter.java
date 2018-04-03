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
@Table(name="pm_site_submeter")
public class SiteSubMeter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5021369675482394384L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="submeter_id")
	private Long subMeterId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="site_id")
	private Site site;

	@Column(name="submeter_number")
	private String subMeterNumber;

	@Column(name="submeter_user")
	private String subMeterUser;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_on")
	private Date createdOn=new Date();

	public SiteSubMeter() {
		super();
	}

	public Long getSubMeterId() {
		return subMeterId;
	}

	public void setSubMeterId(final Long subMeterId) {
		this.subMeterId = subMeterId;
	}

	@JsonIgnore
	public Site getSite() {
		return site;
	}

	public void setSite(final Site site) {
		this.site = site;
	}

	public String getSubMeterNumber() {
		return subMeterNumber;
	}

	public void setSubMeterNumber(final String subMeterNumber) {
		this.subMeterNumber = subMeterNumber;
	}

	public String getSubMeterUser() {
		return subMeterUser;
	}

	public void setSubMeterUser(final String subMeterUser) {
		this.subMeterUser = subMeterUser;
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


}
