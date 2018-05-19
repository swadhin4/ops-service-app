package com.ops.app.vo;

public class TicketEscalationVO {
	
	private Long custEscId;
	private Long escId;
	private Long ticketId;
	private String ticketNumber;
	private String escLevelDesc;
	private String escalatedBy;
	private String escalatedDate;
	private String escalationStatus;
	private TicketVO ticketData;
	
	public TicketEscalationVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getCustEscId() {
		return custEscId;
	}
	public void setCustEscId(Long custEscId) {
		this.custEscId = custEscId;
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
	public String getEscalatedDate() {
		return escalatedDate;
	}
	public void setEscalatedDate(String escalatedDate) {
		this.escalatedDate = escalatedDate;
	}
	public String getEscLevelDesc() {
		return escLevelDesc;
	}
	public void setEscLevelDesc(String escLevelDesc) {
		this.escLevelDesc = escLevelDesc;
	}
	public String getEscalationStatus() {
		return escalationStatus;
	}
	public void setEscalationStatus(String escalationStatus) {
		this.escalationStatus = escalationStatus;
	}
	public TicketVO getTicketData() {
		return ticketData;
	}
	public void setTicketData(TicketVO ticketData) {
		this.ticketData = ticketData;
	}
	@Override
	public String toString() {
		return "TicketEscalationVO [escId=" + escId + ", ticketId=" + ticketId + ", ticketNumber=" + ticketNumber
				+ ", escLevelDesc=" + escLevelDesc + ", escalatedBy=" + escalatedBy + ", escalatedDate=" + escalatedDate
				+ "]";
	}
	

}
