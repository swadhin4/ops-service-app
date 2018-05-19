package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="pm_sp_escalation_levels")
public class SPEscalationLevels implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -350858306889079535L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="esc_id", unique=true, nullable=false)
	private Long escId;

	@ManyToOne
	@JoinColumn(name="sp_id")
	private ServiceProvider serviceProvider;

	@Column(name="esc_level")
	private int escalationLevel;

	@Column(name="esc_person")
	private String escalationPerson;

	@Column(name="esc_email")
	private String escalationEmail;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn = new Date();

	public SPEscalationLevels() {
		super();
	}

	public Long getEscId() {
		return escId;
	}

	public void setEscId(Long escId) {
		this.escId = escId;
	}

	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public int getEscalationLevel() {
		return escalationLevel;
	}

	public void setEscalationLevel(int escalationLevel) {
		this.escalationLevel = escalationLevel;
	}

	public String getEscalationPerson() {
		return escalationPerson;
	}

	public void setEscalationPerson(String escalationPerson) {
		this.escalationPerson = escalationPerson;
	}

	public String getEscalationEmail() {
		return escalationEmail;
	}

	public void setEscalationEmail(String escalationEmail) {
		this.escalationEmail = escalationEmail;
	}



	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


}
