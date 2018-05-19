package com.ops.app.vo;


public class DistrictVO {

	private Long districtId;

	private String districtCode;

	private String districtDesc;

	private String districtName;

	public DistrictVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(final Long districtId) {
		this.districtId = districtId;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(final String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictDesc() {
		return districtDesc;
	}

	public void setDistrictDesc(final String districtDesc) {
		this.districtDesc = districtDesc;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(final String districtName) {
		this.districtName = districtName;
	}

	@Override
	public String toString() {
		return "DistrictVO [districtId=" + districtId + ", districtCode="
				+ districtCode + ", districtDesc=" + districtDesc
				+ ", districtName=" + districtName + "]";
	}


}
