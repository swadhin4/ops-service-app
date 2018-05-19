package com.ops.app.vo;

public class SiteOpsTimingVO {
	
	private Long opId;

	private String days;

	private String from;

	private String to;
	
	private String type;


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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SiteOpsTimingVO [opId=" + opId + ", days=" + days + ", from=" + from + ", to=" + to + ", type=" + type
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((opId == null) ? 0 : opId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SiteOpsTimingVO other = (SiteOpsTimingVO) obj;
		if (opId == null) {
			if (other.opId != null)
				return false;
		} else if (!opId.equals(other.opId))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
}
