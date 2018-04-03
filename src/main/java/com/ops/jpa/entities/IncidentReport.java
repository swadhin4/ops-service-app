package com.ops.jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="vw_incident_report")
@NamedQuery(name="IncidentReport.findAll", query="SELECT p FROM IncidentReport p")
public class IncidentReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4018302663482648424L;
	@Id
	@Column(name="id", unique=true, nullable=false)
	private String id;
	
	@Column(name="sp_ticket_id", unique=true, nullable=false)
	private Long spId;
	
	@Column(name="ticket_id", unique=true, nullable=false)
	private Long ticketId;
	
	@Column(name="customer_ticket_number")
	private String customerTicketNumber;
	
	@Column(name="sp_ticket_number")
	private String spTicketNumber;
	
	@Column(name="customer_ticket_raised_by")
	private String ticketRaiseBy;
	
	@Column(name="sp_name")
	private String spName;
	
	@Column(name="site_id")
	private Long siteId;
	
	@Column(name="site_number1")
	private Long siteNumber1;
	
	@Column(name="site_name")
	private String siteName;
	
	@Column(name="asset_name")
	private String assetName;
	
	@Column(name="asset_code")
	private String assetCode;
	
	@Column(name="asset_category")
	private String assetCategory;
	
	@Column(name="priority")
	private String priority;
	
	@Column(name="status")
	private String status;
	
	@Column(name="issue_start_time")
	private String issueStartTime;
	
	@Column(name="customer_ticket_raised_on" )
	private String ticketRaisedOn;
	
	@Column(name="sp_ticket_created_on")
	private String spTicketCreatedOn;
	
	@Column(name="sp_ticket_closed_time")
	private String spTicketClosedTime;
	
	@Column(name="service_restoration_time")
	private String serviceRestorationTime;
	
	@Column(name="service_restoration_duration")
	private String serviceRestorationDuration;
	
	@Column(name="rootcause_fixed_time")
	private String rootCauseFixTime;
	
	@Column(name="rootcause_fix_duration")
	private String rootCauseFixDuration;
	
	@Column(name="time_to_raise_ticket")
	private String timeToRaiseTicket;
	
	@Column(name="time_to_log_ticket")
	private String timeToLogTicket;
	
	@Column(name="sla_duration")
	private String slaDuration;
	
	@Column(name="time_of_sp_ticket_close_ticket")
	private String timeOfSPTicketClose;
	
	@Column(name="actual_sla_sp_sla_diff")
	private String actualSlaSPSlaDiff;
		
	@Column(name="rootcause_fix_time_after_service_restoration")
	private String rootCauseFixTimeAfterServiceRestore;
	

	public IncidentReport() {
		super();
	}

	
public String getId() {
		return id;
	}




	public void setId(String Id) {
		this.id = Id;
	}

	public Long getSpId() {
		return spId;
	}




	public void setSpId(Long spId) {
		this.spId = spId;
	}




	public Long getTicketId() {
		return ticketId;
	}


	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}


	public String getCustomerTicketNumber() {
		return customerTicketNumber;
	}

	public void setCustomerTicketNumber(String customerTicketNumber) {
		this.customerTicketNumber = customerTicketNumber;
	}

	public String getSpTicketNumber() {
		return spTicketNumber;
	}

	public void setSpTicketNumber(String spTicketNumber) {
		this.spTicketNumber = spTicketNumber;
	}

	public String getTicketRaiseBy() {
		return ticketRaiseBy;
	}

	public void setTicketRaiseBy(String ticketRaiseBy) {
		this.ticketRaiseBy = ticketRaiseBy;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public Long getSiteNumber1() {
		return siteNumber1;
	}

	public void setSiteNumber1(Long siteNumber1) {
		this.siteNumber1 = siteNumber1;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetCode() {
		return assetCode;
	}


	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}


	public String getAssetCategory() {
		return assetCategory;
	}

	public void setAssetCategory(String assetCategory) {
		this.assetCategory = assetCategory;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getIssueStartTime() {
		return issueStartTime;
	}




	public void setIssueStartTime(String issueStartTime) {
		this.issueStartTime = issueStartTime;
	}




	public String getTicketRaisedOn() {
		return ticketRaisedOn;
	}

	public void setTicketRaisedOn(String ticketRaisedOn) {
		this.ticketRaisedOn = ticketRaisedOn;
	}

	public String getSpTicketCreatedOn() {
		return spTicketCreatedOn;
	}

	public void setSpTicketCreatedOn(String spTicketCreatedOn) {
		this.spTicketCreatedOn = spTicketCreatedOn;
	}




	public String getSpTicketClosedTime() {
		return spTicketClosedTime;
	}




	public void setSpTicketClosedTime(String spTicketClosedTime) {
		this.spTicketClosedTime = spTicketClosedTime;
	}




	public String getServiceRestorationTime() {
		return serviceRestorationTime;
	}




	public void setServiceRestorationTime(String serviceRestorationTime) {
		this.serviceRestorationTime = serviceRestorationTime;
	}




	public String getServiceRestorationDuration() {
		return serviceRestorationDuration;
	}




	public void setServiceRestorationDuration(String serviceRestorationDuration) {
		this.serviceRestorationDuration = serviceRestorationDuration;
	}




	public String getRootCauseFixTime() {
		return rootCauseFixTime;
	}




	public void setRootCauseFixTime(String rootCauseFixTime) {
		this.rootCauseFixTime = rootCauseFixTime;
	}




	public String getRootCauseFixDuration() {
		return rootCauseFixDuration;
	}




	public void setRootCauseFixDuration(String rootCauseFixDuration) {
		this.rootCauseFixDuration = rootCauseFixDuration;
	}




	public String getTimeToRaiseTicket() {
		return timeToRaiseTicket;
	}




	public void setTimeToRaiseTicket(String timeToRaiseTicket) {
		this.timeToRaiseTicket = timeToRaiseTicket;
	}




	public String getTimeToLogTicket() {
		return timeToLogTicket;
	}




	public void setTimeToLogTicket(String timeToLogTicket) {
		this.timeToLogTicket = timeToLogTicket;
	}




	public String getSlaDuration() {
		return slaDuration;
	}




	public void setSlaDuration(String slaDuration) {
		this.slaDuration = slaDuration;
	}




	public String getTimeOfSPTicketClose() {
		return timeOfSPTicketClose;
	}




	public void setTimeOfSPTicketClose(String timeOfSPTicketClose) {
		this.timeOfSPTicketClose = timeOfSPTicketClose;
	}




	public String getActualSlaSPSlaDiff() {
		return actualSlaSPSlaDiff;
	}




	public void setActualSlaSPSlaDiff(String actualSlaSPSlaDiff) {
		this.actualSlaSPSlaDiff = actualSlaSPSlaDiff;
	}




	public String getRootCauseFixTimeAfterServiceRestore() {
		return rootCauseFixTimeAfterServiceRestore;
	}




	public void setRootCauseFixTimeAfterServiceRestore(String rootCauseFixTimeAfterServiceRestore) {
		this.rootCauseFixTimeAfterServiceRestore = rootCauseFixTimeAfterServiceRestore;
	}


	public Long getSiteId() {
		return siteId;
	}


	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

}
