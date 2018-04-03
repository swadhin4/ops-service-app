package com.ops.app.vo;


public class SiteOperationVO {

	private Long opId;
	private String days;
	private String from;
	private String to;



	public Long getOpId() {
		return opId;
	}

	public void setOpId(final Long opId) {
		this.opId = opId;
	}

	public String getDays() {
		return days;
	}

	public void setDays(final String days) {
		this.days = days;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(final String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(final String to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "SiteOperationVO [opId=" + opId + ", days=" + days + ", from="
				+ from + ", to=" + to + "]";
	}

}
