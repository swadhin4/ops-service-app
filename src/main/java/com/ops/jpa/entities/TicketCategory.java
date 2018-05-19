package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the pm_TicketCategory database table.
 * 
 */
@Entity
@Table(name="pm_ticket_category")
@NamedQuery(name="TicketCategory.findAll", query="SELECT p FROM TicketCategory p")
public class TicketCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4352906237898200584L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_on")
	private Date createdOn = new Date();

	private String description;

	@Column(name="ticket_category")
	private String ticketCategory;


	public TicketCategory() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getTicketCategory() {
		return this.ticketCategory;
	}

	public void setTicketCategory(final String ticketCategory) {
		this.ticketCategory = ticketCategory;
	}

	@Override
	public String toString() {
		return "TicketCategory [id=" + id + ", createdBy=" + createdBy
				+ ", createdOn=" + createdOn + ", description=" + description
				+ ", ticketCategory=" + ticketCategory + "]";
	}



}