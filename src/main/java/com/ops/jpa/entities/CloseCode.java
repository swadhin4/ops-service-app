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
 * The persistent class for the pm_CloseCode database table.
 * 
 */
@Entity
@Table(name="pm_closecode")
public class CloseCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="closed_code")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long closedCode;

	@Column(name="closed_code_desc")
	private String closedCodeDesc;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_on")
	private Date createdOn = new Date();

	public CloseCode() {
	}

	public Long getClosedCode() {
		return this.closedCode;
	}

	public void setClosedCode(final Long closedCode) {
		this.closedCode = closedCode;
	}

	public String getClosedCodeDesc() {
		return this.closedCodeDesc;
	}

	public void setClosedCodeDesc(final String closedCodeDesc) {
		this.closedCodeDesc = closedCodeDesc;
	}

	public String getCreatedBy() {
		return this.createdBy;
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