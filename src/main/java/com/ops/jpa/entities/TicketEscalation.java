package com.ops.jpa.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="pm_ct_escalations")
@NamedQuery(name="TicketEscalation.findAll", query="SELECT p FROM TicketEscalation p")
public class TicketEscalation {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ct_esc_id", unique=true, nullable=false)
	private Long escId;
	
	@Column(name="ticket_id")
	private Long ticketId;
	
	@Column(name="ticket_number")
	private String ticketNumber;
	
	@Column(name="esc_level")
	private String escLevelDesc;
	
	@Column(name="escalated_by")
	private String escalatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="escalated_date")
	private Date escalatedDate = new Date();
	
	@Column(name="esc_id")
	private Long escLevelId;
	
	
	public TicketEscalation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getEscId() {
		return escId;
	}
	public void setEscId(Long escId) {
		this.escId = escId;
	}
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	
	public String getEscalatedBy() {
		return escalatedBy;
	}
	public void setEscalatedBy(String escalatedBy) {
		this.escalatedBy = escalatedBy;
	}
	
	
	public Date getEscalatedDate() {
		return escalatedDate;
	}
	public void setEscalatedDate(Date escalatedDate) {
		this.escalatedDate = escalatedDate;
	}
	public String getEscLevelDesc() {
		return escLevelDesc;
	}
	public void setEscLevelDesc(String escLevelDesc) {
		this.escLevelDesc = escLevelDesc;
	}
	public Long getEscLevelId() {
		return escLevelId;
	}
	public void setEscLevelId(Long escLevelId) {
		this.escLevelId = escLevelId;
	}
	@Override
	public String toString() {
		return "TicketEscalation [escId=" + escId + ", ticketId=" + ticketId + ", ticketNumber=" + ticketNumber
				+ ", escLevelDesc=" + escLevelDesc + ", escalatedBy=" + escalatedBy + ", escalatedDate=" + escalatedDate
				+ "]";
	}
	
	

}
