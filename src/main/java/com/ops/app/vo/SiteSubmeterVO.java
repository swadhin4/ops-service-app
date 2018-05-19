package com.ops.app.vo;


public class SiteSubmeterVO {

	private Long subMeterId;

	private String subMeterNumber;

	private String subMeterUser;

	public Long getSubMeterId() {
		return subMeterId;
	}

	public void setSubMeterId(final Long subMeterId) {
		this.subMeterId = subMeterId;
	}

	public String getSubMeterNumber() {
		return subMeterNumber;
	}

	public void setSubMeterNumber(final String subMeterNumber) {
		this.subMeterNumber = subMeterNumber;
	}

	public String getSubMeterUser() {
		return subMeterUser;
	}

	public void setSubMeterUser(final String subMeterUser) {
		this.subMeterUser = subMeterUser;
	}

	@Override
	public String toString() {
		return "SiteSubmeterVO [subMeterId=" + subMeterId + ", subMeterNumber="
				+ subMeterNumber + ", subMeterUser=" + subMeterUser + "]";
	}


}
