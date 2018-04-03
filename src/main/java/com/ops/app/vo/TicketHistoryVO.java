package com.ops.app.vo;

public class TicketHistoryVO {

	private Long historyId;
	private String ticketNumber;
	private char action;
	private String columnName;
	private String valueBefore;
	private String valueAfter;
	private String message;
	private String who;
	private String timeStamp;
	private String ticketCreatedOn;
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
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getTicketCreatedOn() {
		return ticketCreatedOn;
	}
	public void setTicketCreatedOn(String ticketCreatedOn) {
		this.ticketCreatedOn = ticketCreatedOn;
	}
	@Override
	public String toString() {
		return "TicketHistory [historyId=" + historyId + ", ticketNumber=" + ticketNumber + ", action=" + action
				+ ", columnName=" + columnName + ", valueBefore=" + valueBefore + ", valueAfter=" + valueAfter
				+ ", message=" + message + ", who=" + who + ", timeStamp=" + timeStamp + ", ticketCreatedOn="
				+ ticketCreatedOn + "]";
	}
	
	
	
	
}
