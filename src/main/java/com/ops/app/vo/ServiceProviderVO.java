package com.ops.app.vo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.ops.jpa.entities.Country;
import com.ops.jpa.entities.Region;

public class ServiceProviderVO {

	private Long serviceProviderId;
	private String code;
	private String name;
	private String spUserName;
	private String email;
	private String helpDeskNumber;
	private String helpDeskEmail;
	private Region region = new Region();
	private Country country = new Country();
	private String additionalDetails;
	private List<SLADetailsVO> slaListVOList= new ArrayList<SLADetailsVO>();
	private List<EscalationLevelVO> escalationLevelList = new ArrayList<EscalationLevelVO>();
	private String slaDescription;
	private String accessKey;
	private int status;
	private String message;
	private String option;
	private int version;

	public Long getServiceProviderId() {
		return serviceProviderId;
	}
	public void setServiceProviderId(Long serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpUserName() {
		return spUserName;
	}
	public void setSpUserName(String spUserName) {
		this.spUserName = spUserName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHelpDeskNumber() {
		return helpDeskNumber;
	}
	public void setHelpDeskNumber(String helpDeskNumber) {
		this.helpDeskNumber = helpDeskNumber;
	}
	public String getHelpDeskEmail() {
		return helpDeskEmail;
	}
	public void setHelpDeskEmail(String helpDeskEmail) {
		this.helpDeskEmail = helpDeskEmail;
	}
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public String getAdditionalDetails() {
		return additionalDetails;
	}
	public void setAdditionalDetails(String additionalDetails) {
		this.additionalDetails = additionalDetails;
	}
	public List<SLADetailsVO> getSlaListVOList() {
		return slaListVOList;
	}
	public void setSlaListVOList(List<SLADetailsVO> slaListVOList) {
		this.slaListVOList = slaListVOList;
	}
	public List<EscalationLevelVO> getEscalationLevelList() {
		return escalationLevelList;
	}
	public void setEscalationLevelList(List<EscalationLevelVO> escalationLevelList) {
		this.escalationLevelList = escalationLevelList;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSlaDescription() {
		return slaDescription;
	}
	public void setSlaDescription(String slaDescription) {
		this.slaDescription = slaDescription;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	public static Comparator<ServiceProviderVO> COMPARE_BY_SPNAME = new Comparator<ServiceProviderVO>() {
        public int compare(ServiceProviderVO one, ServiceProviderVO other) {
            return one.name.compareTo(other.name);
        }
    };
	
}
