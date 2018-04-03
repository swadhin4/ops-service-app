package com.ops.app.vo;


public class RegionVO {

	private Long regionId;

	private String regionCode;

	private String regionDescription;

	private String regionName;

	public RegionVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(final Long regionId) {
		this.regionId = regionId;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(final String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionDescription() {
		return regionDescription;
	}

	public void setRegionDescription(final String regionDescription) {
		this.regionDescription = regionDescription;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(final String regionName) {
		this.regionName = regionName;
	}

	@Override
	public String toString() {
		return "RegionVO [regionId=" + regionId + ", regionCode=" + regionCode
				+ ", regionDescription=" + regionDescription + ", regionName="
				+ regionName + "]";
	}



}
