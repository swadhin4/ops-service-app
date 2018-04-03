package com.ops.app.vo;

public class TicketPrioritySLAVO {

	private Long priorityId;
	
	private String priorityName;
	
	private Long ticketCategoryId;
	
	private String ticetCategoryName;
	
	private Long serviceProviderId;
	
	private String serviceProviderName;
	
	private String ticketSlaDueDate;
	
	private String units;
	
	private int duration;

	public Long getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(Long priorityId) {
		this.priorityId = priorityId;
	}

	public String getPriorityName() {
		return priorityName;
	}

	public void setPriorityName(String priorityName) {
		this.priorityName = priorityName;
	}

	public Long getTicketCategoryId() {
		return ticketCategoryId;
	}

	public void setTicketCategoryId(Long ticketCategoryId) {
		this.ticketCategoryId = ticketCategoryId;
	}

	public String getTicetCategoryName() {
		return ticetCategoryName;
	}

	public void setTicetCategoryName(String ticetCategoryName) {
		this.ticetCategoryName = ticetCategoryName;
	}

	public Long getServiceProviderId() {
		return serviceProviderId;
	}

	public void setServiceProviderId(Long serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}

	public String getServiceProviderName() {
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}

	public String getTicketSlaDueDate() {
		return ticketSlaDueDate;
	}

	public void setTicketSlaDueDate(String ticketSlaDueDate) {
		this.ticketSlaDueDate = ticketSlaDueDate;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
