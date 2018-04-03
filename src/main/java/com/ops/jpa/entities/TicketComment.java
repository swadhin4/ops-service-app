package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pm_cust_ticket_comment")
public class TicketComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7413795831112656355L;
	
	@Id
	@Column(name="comment_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long commentId;
	
	@Column(name="ticket_id")
	private Long ticketId;
	
	@Column(name="custticket_number")
	private String custTicketNumber;
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_date")
	private Date createdDate = new Date();
	
	

	public TicketComment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getCustTicketNumber() {
		return custTicketNumber;
	}

	public void setCustTicketNumber(String custTicketNumber) {
		this.custTicketNumber = custTicketNumber;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	

}
