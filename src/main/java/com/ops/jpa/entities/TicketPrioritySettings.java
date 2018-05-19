package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="pm_ct_priority_settings")
public class TicketPrioritySettings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -343473541843249144L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="settings_id", unique=true, nullable=false)
	private Long settingsId;
	
	@Column(name="category_id")
	private Long ticketCategoryId;
	
	@Column(name="priority_id")
	private Long priorityId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdAt = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="modified_by")
	private String modifiedBy;

	public Long getSettingsId() {
		return settingsId;
	}

	public void setSettingsId(Long settingsId) {
		this.settingsId = settingsId;
	}

	public Long getTicketCategoryId() {
		return ticketCategoryId;
	}

	public void setTicketCategoryId(Long ticketCategoryId) {
		this.ticketCategoryId = ticketCategoryId;
	}

	public Long getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(Long priorityId) {
		this.priorityId = priorityId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		return "TicketPrioritySettings [settingsId=" + settingsId + ", ticketCategoryId=" + ticketCategoryId
				+ ", priorityId=" + priorityId + ", createdAt=" + createdAt + ", modifiedDate=" + modifiedDate
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + "]";
	}
	
	
	

}
