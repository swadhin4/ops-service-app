package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="pm_sp_sla")
public class ServiceProviderSLADetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6568473314645130752L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sla_id", unique=true, nullable=false)
	private Long slaId;

	//@ManyToOne
	/*@Column(name="sp_id")
	private Long serviceProviderId;*/

	@ManyToOne
	@JoinColumn(name="sp_id")
	private ServiceProvider serviceProvider;

	@Transient
	private TicketPriority ticketPriority;

	@Column(name="priority_id")
	private Long priorityId;

	@Column(name="duration")
	private int duration;

	@Column(name="unit")
	private String unit;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn = new Date();



	public ServiceProviderSLADetails() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Long getSlaId() {
		return slaId;
	}



	public void setSlaId(Long slaId) {
		this.slaId = slaId;
	}





	/*public Long getServiceProviderId() {
		return serviceProviderId;
	}



	public void setServiceProviderId(Long serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}*/



	public TicketPriority getTicketPriority() {
		return ticketPriority;
	}



	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}



	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}



	public void setTicketPriority(TicketPriority ticketPriority) {
		this.ticketPriority = ticketPriority;
	}



	public Long getPriorityId() {
		return priorityId;
	}



	public void setPriorityId(Long priorityId) {
		this.priorityId = priorityId;
	}



	public int getDuration() {
		return duration;
	}



	public void setDuration(int duration) {
		this.duration = duration;
	}



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public Date getCreatedOn() {
		return createdOn;
	}



	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}



}
