package com.ops.jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sla_approaching_view")
public class SlaApproachingView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9083752763025322951L;
	@Id
	@Column(name = "company_id")
	private Long companyId;

	@Column(name = "ticket_number")
	private String ticketNumber;
	@Column(name = "company_code")
	private String companyCode;
	@Column(name = "company_name")
	private String companyName;
	@Column(name = "status")
	private String status;

	public SlaApproachingView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(final String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(final Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(final String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

}
