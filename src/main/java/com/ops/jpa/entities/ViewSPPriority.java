package com.ops.jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="vw_customer_view_ticket_by_sp_priority")
@NamedQuery(name="ViewSPPriority.findAll", query="SELECT p FROM ViewSPPriority p")
public class ViewSPPriority implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3338890154914486931L;

	@Id
	@Column(name="id", unique=true, nullable=false)
	private String id;
	
	@Column(name="ticket_count", unique=true, nullable=false)
	private Long ticketCount;
	
	@Column(name="priority_id")
	private String priorityId;
	
	@Column(name="priority")
	private String priority;
	
	@Column(name="sp_id")
	private Long spId;
	
	@Column(name="service_provider_name")
	private String serviceProviderName;
	

	public ViewSPPriority() {
		super();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Long getTicketCount() {
		return ticketCount;
	}


	public void setTicketCount(Long ticketCount) {
		this.ticketCount = ticketCount;
	}


	public String getPriorityId() {
		return priorityId;
	}


	public void setPriorityId(String priorityId) {
		this.priorityId = priorityId;
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public Long getSpId() {
		return spId;
	}


	public void setSpId(Long spId) {
		this.spId = spId;
	}


	public String getServiceProviderName() {
		return serviceProviderName;
	}


	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}

	
}
