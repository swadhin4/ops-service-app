package com.ops.jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="vw_customer_view_ticket_by_site_status")
@NamedQuery(name="ViewSiteStatus.findAll", query="SELECT p FROM ViewSiteStatus p")
public class ViewSiteStatus implements Serializable {

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
	
	@Column(name="site_id")
	private Long siteId;
	
	@Column(name="site_name")
	private String siteName;
	

	public ViewSiteStatus() {
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

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	
}
