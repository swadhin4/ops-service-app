package com.ops.app.vo;

import java.util.Date;

import com.ops.jpa.entities.Asset;
import com.ops.jpa.entities.ServiceProvider;
import com.ops.jpa.entities.Site;
import com.ops.jpa.entities.Status;
import com.ops.jpa.entities.TicketCategory;

public class CustomerTicketVO {

	private Long id;
	private String ticketNumber;
	private TicketCategory ticketCategory=new TicketCategory();
	private String ticketTitle;
	private Site site = new Site();
	private String siteName;
	private Asset asset = new Asset();
	private String assetName;
	private String priority;
	private Date slaDueDate;
	private Status status = new Status();
	private String ticketDesc;
	private Long closeCode;
	private String closeNote;
	private ServiceProvider assignedTo = new ServiceProvider();
	private String closedBy;
	private Date closeOn;
	private String createdBy;
	private Date createdOn;
	private String modifiedBy;
	private Date modifiedOn;
	private int isRootCasueResolved;
	private int isEscalated;
	private Date ticketStartTime;
	private String ticketStartDateAndTime;
	private String createdDate;
	private String dueSlaDate;

	public CustomerTicketVO() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(final Long id) {
		this.id = id;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(final String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}




	public TicketCategory getTicketCategory() {
		return ticketCategory;
	}
	public void setTicketCategory(final TicketCategory ticketCategory) {
		this.ticketCategory = ticketCategory;
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
	public Date getSlaDueDate() {
		return slaDueDate;
	}
	public void setSlaDueDate(final Date slaDueDate) {
		this.slaDueDate = slaDueDate;
	}
	public String getTicketDesc() {
		return ticketDesc;
	}
	public void setTicketDesc(final String ticketDesc) {
		this.ticketDesc = ticketDesc;
	}
	public Long getCloseCode() {
		return closeCode;
	}
	public void setCloseCode(final Long closeCode) {
		this.closeCode = closeCode;
	}
	public String getCloseNote() {
		return closeNote;
	}
	public void setCloseNote(final String closeNote) {
		this.closeNote = closeNote;
	}
	public String getClosedBy() {
		return closedBy;
	}
	public void setClosedBy(final String closedBy) {
		this.closedBy = closedBy;
	}
	public Date getCloseOn() {
		return closeOn;
	}
	public void setCloseOn(final Date closeOn) {
		this.closeOn = closeOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(final String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(final Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public int getIsRootCasueResolved() {
		return isRootCasueResolved;
	}
	public void setIsRootCasueResolved(final int isRootCasueResolved) {
		this.isRootCasueResolved = isRootCasueResolved;
	}
	public int getIsEscalated() {
		return isEscalated;
	}
	public void setIsEscalated(final int isEscalated) {
		this.isEscalated = isEscalated;
	}
	public Date getTicketStartTime() {
		return ticketStartTime;
	}
	public void setTicketStartTime(final Date ticketStartTime) {
		this.ticketStartTime = ticketStartTime;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(final String createdDate) {
		this.createdDate = createdDate;
	}
	public String getDueSlaDate() {
		return dueSlaDate;
	}
	public void setDueSlaDate(final String dueSlaDate) {
		this.dueSlaDate = dueSlaDate;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(final String siteName) {
		this.siteName = siteName;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(final String assetName) {
		this.assetName = assetName;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(final Site site) {
		this.site = site;
	}
	public Asset getAsset() {
		return asset;
	}
	public void setAsset(final Asset asset) {
		this.asset = asset;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(final Status status) {
		this.status = status;
	}
	public ServiceProvider getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(final ServiceProvider assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getTicketStartDateAndTime() {
		return ticketStartDateAndTime;
	}
	public void setTicketStartDateAndTime(final String ticketStartDateAndTime) {
		this.ticketStartDateAndTime = ticketStartDateAndTime;
	}
	@Override
	public String toString() {
		return "CustomerTicketVO [id=" + id + ", ticketNumber=" + ticketNumber
				+ ", ticketCategory=" + ticketCategory + ", ticketTitle="
				+ ticketTitle + ", site=" + site + ", siteName=" + siteName
				+ ", asset=" + asset + ", assetName=" + assetName
				+ ", priority=" + priority + ", slaDueDate=" + slaDueDate
				+ ", status=" + status + ", ticketDesc=" + ticketDesc
				+ ", closeCode=" + closeCode + ", closeNote=" + closeNote
				+ ", assignedTo=" + assignedTo + ", closedBy=" + closedBy
				+ ", closeOn=" + closeOn + ", createdBy=" + createdBy
				+ ", createdOn=" + createdOn + ", modifiedBy=" + modifiedBy
				+ ", modifiedOn=" + modifiedOn + ", isRootCasueResolved="
				+ isRootCasueResolved + ", isEscalated=" + isEscalated
				+ ", ticketStartTime=" + ticketStartTime + ", createdDate="
				+ createdDate + ", dueSlaDate=" + dueSlaDate + "]";
	}



}
