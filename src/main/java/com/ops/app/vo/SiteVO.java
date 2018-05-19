package com.ops.app.vo;

import java.util.ArrayList;
import java.util.List;

import com.ops.jpa.entities.Area;
import com.ops.jpa.entities.Cluster;
import com.ops.jpa.entities.Company;
import com.ops.jpa.entities.District;
import com.ops.jpa.entities.Site;
import com.ops.jpa.entities.SiteDeliveryOperation;
import com.ops.jpa.entities.SiteLicence;
import com.ops.jpa.entities.SiteSalesOperation;
import com.ops.jpa.entities.SiteSubMeter;

public class SiteVO {

	private Long siteId;

	private String siteCode;

	private String siteName;

	private District district;

	private Area area;

	private Cluster cluster = new Cluster();

	private String siteOwner;

	private Company operator = new Company();

	private String attachmentPath;

	private String siteAddress;

	private String postCode;

	private String latitude;

	private String longitude;

	private String primaryContact;

	private String secondaryContact;

	private String email;

	private String electricityId;

	private String siteNumberOne;

	private String siteNumberTwo;

	private List<SiteDeliveryOperation> siteDeliveryOpetaionTimes = new ArrayList<SiteDeliveryOperation>();

	private List<SiteSalesOperation> siteSalesOpetaionTimes = new ArrayList<SiteSalesOperation>();

	private List<SiteLicence> siteLicenseList = new ArrayList<SiteLicence>();

	private List<SiteSubMeter> siteSubmeterList = new ArrayList<SiteSubMeter>();

	private String createdBy;

	private String createdDate;

	private String areaManagerName;

	private String country;

	private Site site;


	public SiteVO() {
	}


	public Long getSiteId() {
		return siteId;
	}


	public void setSiteId(final Long siteId) {
		this.siteId = siteId;
	}


	public String getSiteCode() {
		return siteCode;
	}


	public void setSiteCode(final String siteCode) {
		this.siteCode = siteCode;
	}


	public String getSiteName() {
		return siteName;
	}


	public void setSiteName(final String siteName) {
		this.siteName = siteName;
	}


	public District getDistrict() {
		return district;
	}


	public void setDistrict(final District district) {
		this.district = district;
	}


	public Area getArea() {
		return area;
	}


	public void setArea(final Area area) {
		this.area = area;
	}


	public Cluster getCluster() {
		return cluster;
	}


	public void setCluster(final Cluster cluster) {
		this.cluster = cluster;
	}


	public String getSiteOwner() {
		return siteOwner;
	}


	public void setSiteOwner(final String siteOwner) {
		this.siteOwner = siteOwner;
	}


	public Company getOperator() {
		return operator;
	}


	public void setOperator(final Company operator) {
		this.operator = operator;
	}


	public String getAttachmentPath() {
		return attachmentPath;
	}


	public void setAttachmentPath(final String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}




	public String getSiteAddress() {
		return siteAddress;
	}


	public void setSiteAddress(final String siteAddress) {
		this.siteAddress = siteAddress;
	}


	public String getPostCode() {
		return postCode;
	}


	public void setPostCode(final String postCode) {
		this.postCode = postCode;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(final String latitude) {
		this.latitude = latitude;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(final String longitude) {
		this.longitude = longitude;
	}




	public String getEmail() {
		return email;
	}


	public void setEmail(final String email) {
		this.email = email;
	}


	public String getPrimaryContact() {
		return primaryContact;
	}


	public void setPrimaryContact(final String primaryContact) {
		this.primaryContact = primaryContact;
	}


	public String getSecondaryContact() {
		return secondaryContact;
	}


	public void setSecondaryContact(final String secondaryContact) {
		this.secondaryContact = secondaryContact;
	}


	public List<SiteLicence> getSiteLicenseList() {
		return siteLicenseList;
	}


	public void setSiteLicenseList(final List<SiteLicence> siteLicenseList) {
		this.siteLicenseList = siteLicenseList;
	}


	public List<SiteSubMeter> getSiteSubmeterList() {
		return siteSubmeterList;
	}


	public void setSiteSubmeterList(final List<SiteSubMeter> siteSubmeterList) {
		this.siteSubmeterList = siteSubmeterList;
	}


	public String getElectricityId() {
		return electricityId;
	}


	public void setElectricityId(final String electricityId) {
		this.electricityId = electricityId;
	}


	public String getSiteNumberOne() {
		return siteNumberOne;
	}


	public void setSiteNumberOne(final String siteNumberOne) {
		this.siteNumberOne = siteNumberOne;
	}


	public String getSiteNumberTwo() {
		return siteNumberTwo;
	}


	public void setSiteNumberTwo(final String siteNumberTwo) {
		this.siteNumberTwo = siteNumberTwo;
	}


	public List<SiteDeliveryOperation> getSiteDeliveryOpetaionTimes() {
		return siteDeliveryOpetaionTimes;
	}


	public void setSiteDeliveryOpetaionTimes(
			final List<SiteDeliveryOperation> siteDeliveryOpetaionTimes) {
		this.siteDeliveryOpetaionTimes = siteDeliveryOpetaionTimes;
	}


	public List<SiteSalesOperation> getSiteSalesOpetaionTimes() {
		return siteSalesOpetaionTimes;
	}


	public void setSiteSalesOpetaionTimes(
			final List<SiteSalesOperation> siteSalesOpetaionTimes) {
		this.siteSalesOpetaionTimes = siteSalesOpetaionTimes;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}


	public String getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(final String createdDate) {
		this.createdDate = createdDate;
	}


	public String getAreaManagerName() {
		return areaManagerName;
	}


	public void setAreaManagerName(final String areaManagerName) {
		this.areaManagerName = areaManagerName;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(final String country) {
		this.country = country;
	}


	public Site getSite() {
		return site;
	}


	public void setSite(Site site) {
		this.site = site;
	}



}
