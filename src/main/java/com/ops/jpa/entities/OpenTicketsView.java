package com.ops.jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="open_tickets_view")
public class OpenTicketsView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1008976452300027939L;

	@Id
	@Column(name="site_id")
	private Long siteId;
	@Column(name="site_name")
	private String siteName;
	@Column(name="no_open_tickets")
	private Long noOfOpenTickets;
	@Column(name="status")
	private Long status;


	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(final Long siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(final String siteName) {
		this.siteName = siteName;
	}
	public Long getNoOfOpenTickets() {
		return noOfOpenTickets;
	}
	public void setNoOfOpenTickets(final Long noOfOpenTickets) {
		this.noOfOpenTickets = noOfOpenTickets;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(final Long status) {
		this.status = status;
	}



}
