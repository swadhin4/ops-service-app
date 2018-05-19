package com.ops.app.vo;

public class CustomerSPLinkedTicketVO {

	private Long id;
	private String spLinkedTicket;
	private String custTicketNumber;
	private String custTicketId;
	private String closedFlag;
	private String closeTime;
	private String createdBy;
	private String createdOn;
	private String modifiedOn;
	private String modifiedBy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSpLinkedTicket() {
		return spLinkedTicket;
	}
	public void setSpLinkedTicket(String spLinkedTicket) {
		this.spLinkedTicket = spLinkedTicket;
	}
	public String getCustTicketNumber() {
		return custTicketNumber;
	}
	public void setCustTicketNumber(String custTicketNumber) {
		this.custTicketNumber = custTicketNumber;
	}
	public String getCustTicketId() {
		return custTicketId;
	}
	public void setCustTicketId(String custTicketId) {
		this.custTicketId = custTicketId;
	}
	public String getClosedFlag() {
		return closedFlag;
	}
	public void setClosedFlag(String closedFlag) {
		this.closedFlag = closedFlag;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Override
	public String toString() {
		return "CustomerSPLinkedTicketVO [id=" + id + ", spLinkedTicket=" + spLinkedTicket + ", custTicketNumber="
				+ custTicketNumber + ", custTicketId=" + custTicketId + ", closedFlag=" + closedFlag + ", closeTime="
				+ closeTime + ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}
	
	
	
}
