package com.ops.app.vo;

public class SPTicketPriorityVO {

	private int companyId;
	private String companyName;
	private String priority;
	private int ticketCount;

	public SPTicketPriorityVO() {
		super();
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(final int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(final String priority) {
		this.priority = priority;
	}
	public int getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(final int ticketCount) {
		this.ticketCount = ticketCount;
	}


}
