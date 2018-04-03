package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the pm_ServiceType database table.
 * 
 */
@Entity
@Table(name="pm_service_type")
@NamedQuery(name="ServiceType.findAll", query="SELECT p FROM ServiceType p")
public class ServiceType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="service_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int serviceId;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_on")
	private Date createdOn = new Date();

	@Column(name="service_desc")
	private String serviceDesc;

	@Column(name="service_type")
	private String serviceType;

	public ServiceType() {
	}

	public int getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(final int serviceId) {
		this.serviceId = serviceId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getServiceDesc() {
		return this.serviceDesc;
	}

	public void setServiceDesc(final String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(final String serviceType) {
		this.serviceType = serviceType;
	}

}