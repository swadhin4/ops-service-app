package com.ops.app.vo;

import java.util.ArrayList;
import java.util.List;


public class CountryVO {

	private Long countryId;

	private String countryCode;

	private String countryName;

	private String countryDesc;

	private String createdBy;

	private List<DistrictVO> districtList = new ArrayList<DistrictVO>();

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(final Long countryId) {
		this.countryId = countryId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(final String countryName) {
		this.countryName = countryName;
	}

	public String getCountryDesc() {
		return countryDesc;
	}

	public void setCountryDesc(final String countryDesc) {
		this.countryDesc = countryDesc;
	}

	public List<DistrictVO> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(final List<DistrictVO> districtList) {
		this.districtList = districtList;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}


}
