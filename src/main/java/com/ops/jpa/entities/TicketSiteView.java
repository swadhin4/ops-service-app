package com.ops.jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ticket_view")
public class TicketSiteView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 530275700740213905L;

	@Id
	@Column(name="ticket_number")
	private String ticketNumber;
	@Column(name="site_name")
	private String siteName;

	@Column(name="ticket_title")
	private String ticketTitle;
	@Column(name="priority")
	private String priority;
	@Column(name="is_escalated")
	private String 	isEscalated;


	public TicketSiteView() {
		super();
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(final String siteName) {
		this.siteName = siteName;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(final String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public String getTicketTitle() {
		return ticketTitle;
	}
	public void setTicketTitle(final String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(final String priority) {
		this.priority = priority;
	}
	public String getIsEscalated() {
		return isEscalated;
	}
	public void setIsEscalated(final String isEscalated) {
		this.isEscalated = isEscalated;
	}



}
