package com.ops.jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the pm_Status database table.
 * 
 */
@Entity
@Table(name="pm_status")
public class Status implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5836416518353897636L;

	@Id
	@Column(name="status_id")
	private Long statusId;

	@Column(name="category")
	private String category;

	@Column(name="status")
	private String status;
	
	@Column(name="description")
	private String description;

	public Status() {
	}

	public Long getStatusId() {
		return this.statusId;
	}

	public void setStatusId(final Long statusId) {
		this.statusId = statusId;
	}

	public Object getCategory() {
		return this.category;
	}

	public String getStatus() {
		return status;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



}