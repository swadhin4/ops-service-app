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
@Table(name="pm_ticket_priority")
public class TicketPriority implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8224425449100541685L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="priority_id", unique=true, nullable=false)
	private Long priorityId;

	@Column(name="priority")
	private String priority;

	@Column(name="description")
	private String description;

	@Column(name="created_on")
	private Date createdOn = new Date();

	@Column(name="created_by")
	private String createdBy;




	public TicketPriority() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(Long priorityId) {
		this.priorityId = priorityId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



}
