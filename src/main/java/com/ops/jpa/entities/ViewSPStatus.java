package com.ops.jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="vw_customer_view_ticket_by_sp_status")
@NamedQuery(name="ViewSPStatus.findAll", query="SELECT p FROM ViewSPStatus p")
public class ViewSPStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3338890154914486931L;

	@Id
	@Column(name="id", unique=true, nullable=false)
	private String id;
	
	@Column(name="ticket_count", unique=true, nullable=false)
	private Long ticketCount;
	
	@Column(name="status_id", unique=true, nullable=false)
	private Long statusId;
	
	@Column(name="status")
	private String status;
	
	@Column(name="sp_id")
	private Long spId;
	
	@Column(name="service_provider_name")
	private String serviceProviderName;
	

	public ViewSPStatus() {
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

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
