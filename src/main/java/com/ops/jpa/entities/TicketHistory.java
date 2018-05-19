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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="pm_ct_historic_activities")
@NamedQuery(name="TicketHistory.findAll", query="SELECT p FROM TicketHistory p")
public class TicketHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6319711962659753812L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="history_id", unique=true, nullable=false)
	private Long historyId;
	
	@Column(name="ticket_number")
	private String ticketNumber;
	
	@Column(name="action")
	private char action;
	
	@Column(name="column_name")
	private String columnName;
	
	@Column(name="value_before")
	private String valueBefore;
	
	@Column(name="value_after")
	private String valueAfter;
	
	@Column(name="message")
	private String message;
	
	@Column(name="who")
	private String who;
	
	@Column(name="ts")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp = new Date();
	
	
	
	public TicketHistory() {
		super();
	}
	public Long getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public char getAction() {
		return action;
	}
	public void setAction(char action) {
		this.action = action;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getValueBefore() {
		return valueBefore;
	}
	public void setValueBefore(String valueBefore) {
		this.valueBefore = valueBefore;
	}
	public String getValueAfter() {
		return valueAfter;
	}
	public void setValueAfter(String valueAfter) {
		this.valueAfter = valueAfter;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
