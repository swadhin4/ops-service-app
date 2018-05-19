package com.ops.jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ticket_escalation_count_view")
public class TicketEscalationCountView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1008976452300027939L;

	@Id
	@Column(name="site_id")
	private Long siteId;
	@Column(name="site_code")
	private String siteCode;
	@Column(name="site_name")
	private String siteName;
	@Column(name="tkt_count")
	private Long ticketCount;



	public TicketEscalationCountView() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(final Long siteId) {
		this.siteId = siteId;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(final String siteCode) {
		this.siteCode = siteCode;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(final String siteName) {
		this.siteName = siteName;
	}
	public Long getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(final Long ticketCount) {
		this.ticketCount = ticketCount;
	}


}
