package com.ops.app.vo;

public class EscalationLevelVO {

	private Long escId;

	private Long serviceProdviderId;
	
	private int levelId;

	private String escalationLevel;

	private String escalationPerson;

	private String escalationEmail;
	
	private String status;

	private int createdBy;

	private String createdOn;



	public Long getEscId() {
		return escId;
	}

	public void setEscId(Long escId) {
		this.escId = escId;
	}

	public Long getServiceProdviderId() {
		return serviceProdviderId;
	}

	public void setServiceProdviderId(Long serviceProdviderId) {
		this.serviceProdviderId = serviceProdviderId;
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

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int i) {
		this.levelId = i;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



}
