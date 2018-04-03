package com.ops.app.vo;

import java.util.ArrayList;
import java.util.List;

import com.ops.jpa.entities.TicketAttachment;

public class TicketMVO {

	private Long ticketId;
	private String ticketTitle;
	private String ticketNumber;
	private Long statusId;
	private String status;
	private String statusDescription;
	private String raisedOn;
	private String raisedBy;
	private String createdUser;
	private String createdOn;
	private String sla;
	private List<TicketAttachment> attachments = new ArrayList<TicketAttachment>();
	private List<CustomerSPLinkedTicketVO> linkedTickets = new ArrayList<CustomerSPLinkedTicketVO>();
	private List<TicketEscalationVO> escalatedTicketList = new ArrayList<TicketEscalationVO>();
	private List<TicketCommentVO> ticketComments = new ArrayList<TicketCommentVO>();
	
	public TicketMVO() {
		super();
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketTitle() {
		return ticketTitle;
	}

	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getRaisedOn() {
		return raisedOn;
	}

	public void setRaisedOn(String raisedOn) {
		this.raisedOn = raisedOn;
	}

	public String getRaisedBy() {
		return raisedBy;
	}

	public void setRaisedBy(String raisedBy) {
		this.raisedBy = raisedBy;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getSla() {
		return sla;
	}

	public void setSla(String sla) {
		this.sla = sla;
	}

	public List<TicketAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<TicketAttachment> attachments) {
		this.attachments = attachments;
	}

	public List<CustomerSPLinkedTicketVO> getLinkedTickets() {
		return linkedTickets;
	}

	public void setLinkedTickets(List<CustomerSPLinkedTicketVO> linkedTickets) {
		this.linkedTickets = linkedTickets;
	}

	public List<TicketEscalationVO> getEscalatedTicketList() {
		return escalatedTicketList;
	}

	public void setEscalatedTicketList(List<TicketEscalationVO> escalatedTicketList) {
		this.escalatedTicketList = escalatedTicketList;
	}

	public List<TicketCommentVO> getTicketComments() {
		return ticketComments;
	}

	public void setTicketComments(List<TicketCommentVO> ticketComments) {
		this.ticketComments = ticketComments;
	}


	
	
}
