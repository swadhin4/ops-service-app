package com.ops.app.vo;

import java.util.List;

public class SiteTicketPriority {

	private String siteName;
	private List<String> priorityList;
	private Long escalatedTicketCount;
	private String serviceProviderName;
	private int totalTicketCount;


	public SiteTicketPriority() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getSiteName() {
		return siteName;
	}


	public void setSiteName(final String siteName) {
		this.siteName = siteName;
	}


	public List<String> getPriorityList() {
		return priorityList;
	}


	public void setPriorityList(final List<String> priorityList) {
		this.priorityList = priorityList;
	}


	public Long getEscalatedTicketCount() {
		return escalatedTicketCount;
	}


	public void setEscalatedTicketCount(final Long escalatedTicketCount) {
		this.escalatedTicketCount = escalatedTicketCount;
	}


	public String getServiceProviderName() {
		return serviceProviderName;
	}


	public void setServiceProviderName(final String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}


	public int getTotalTicketCount() {
		return totalTicketCount;
	}


	public void setTotalTicketCount(final int totalTicketCount) {
		this.totalTicketCount = totalTicketCount;
	}


}
