package com.ops.app.vo;

public class EscalatedTicketVO {

	private Long escId;
	private String escalationLevel;
	private String escalationPerson;
	private String escalationEmail;
	private Long ticketId;
	private String ticketNumber;
	private String escalatedDate;
	private String escalationStatus;
	
	
	public EscalatedTicketVO() {
		super();
	}
	public EscalatedTicketVO(String escalationLevel, String escalationPerson, String escalationEmail) {
		super();
		this.escalationLevel = escalationLevel;
		this.escalationPerson = escalationPerson;
		this.escalationEmail = escalationEmail;
	}
	
	
	
	
	public EscalatedTicketVO(Long escId, Long ticketId, String ticketNumber, String escalatedDate,
			String escalationStatus) {
		super();
		this.escId = escId;
		this.ticketId = ticketId;
		this.ticketNumber = ticketNumber;
		this.escalatedDate = escalatedDate;
		this.escalationStatus = escalationStatus;
	}
	
	
	public Long getEscId() {
		return escId;
	}
	public void setEscId(Long escId) {
		this.escId = escId;
	}
	public String getEscalationLevel() {
		return escalationLevel;
	}
	public void setEscalationLevel(String escalationLevel) {
		this.escalationLevel = escalationLevel;
	}
	public String getEscalationPerson() {
		return escalationPerson;
	}
	public void setEscalationPerson(String escalationPerson) {
		this.escalationPerson = escalationPerson;
	}
	public String getEscalationEmail() {
		return escalationEmail;
	}
	public void setEscalationEmail(String escalationEmail) {
		this.escalationEmail = escalationEmail;
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
	public String getEscalatedDate() {
		return escalatedDate;
	}
	public void setEscalatedDate(String escalatedDate) {
		this.escalatedDate = escalatedDate;
	}
	public String getEscalationStatus() {
		return escalationStatus;
	}
	public void setEscalationStatus(String escalationStatus) {
		this.escalationStatus = escalationStatus;
	}
	
	

}
