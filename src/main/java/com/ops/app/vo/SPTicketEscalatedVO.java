package com.ops.app.vo;

public class SPTicketEscalatedVO {

	private int companyId;
	private String companyName;
	private int ticketCount;


	public SPTicketEscalatedVO() {
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
	public int getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(final int ticketCount) {
		this.ticketCount = ticketCount;
	}



}
