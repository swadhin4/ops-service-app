package com.ops.app.vo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.ops.app.exception.Required;
import com.ops.jpa.entities.Area;
import com.ops.jpa.entities.Cluster;
import com.ops.jpa.entities.Company;
import com.ops.jpa.entities.District;
import com.ops.jpa.entities.Site;

public class CreateSiteVO {

	private Long siteId;

	private String siteName;
	private String owner;
	private District district = new District();
	private Area area = new Area();
	private Cluster cluster = new Cluster();
	private Company operator = new Company();

	private String electricityId;
	private String siteNumber1;
	private String siteNumber2;
	private String contactName;
	private String email;
	private String longitude;
	private String latitude;
	private String primaryContact;
	private String secondaryContact;
	private String siteAddress1;
	private String siteAddress2;
	private String siteAddress3;
	private String siteAddress4;
	private String fullAddress;
	private List<SiteLicenceVO> siteLicense = new ArrayList<SiteLicenceVO>();
	private List<UploadFile> licenseAttachments = new ArrayList<UploadFile>();
	private List<SiteDeliveryVO> siteDelivery = new ArrayList<SiteDeliveryVO>();
	private List<SiteOperationVO> siteOperation = new ArrayList<SiteOperationVO>();

	private List<SiteSubmeterVO> siteSubmeter =  new ArrayList<SiteSubmeterVO>();
	private List<String> siteAttachments = new ArrayList<String>(0);

	private String createdBy;

	private Site site;

	private String validationMessage;

	private int status;
	
	private String fileInput;
	private String fileExtension;
	private String fileLocation;
	
	private String zipCode;
	
	private String salesAreaSize;


	public CreateSiteVO() {
		super();
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(final Long siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(final String siteName) {
		this.siteName = siteName;
	}

	public Company getOperator() {
		return operator;
	}

	public void setOperator(Company operator) {
		this.operator = operator;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(final String owner) {
		this.owner = owner;
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

	public String getElectricityId() {
		return electricityId;
	}

	public void setElectricityId(final String electricityId) {
		this.electricityId = electricityId;
	}



	public String getSiteNumber1() {
		return siteNumber1;
	}

	public void setSiteNumber1(final String siteNumber1) {
		this.siteNumber1 = siteNumber1;
	}

	public String getSiteNumber2() {
		return siteNumber2;
	}

	public void setSiteNumber2(final String siteNumber2) {
		this.siteNumber2 = siteNumber2;
	}

	public String getFileInput() {
		return fileInput;
	}

	public void setFileInput(final String fileInput) {
		this.fileInput = fileInput;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(final String contactName) {
		this.contactName = contactName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(final String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(final String latitude) {
		this.latitude = latitude;
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

	
	public String getSiteAddress1() {
		return siteAddress1;
	}

	public void setSiteAddress1(String siteAddress1) {
		this.siteAddress1 = siteAddress1;
	}

	public String getSiteAddress2() {
		return siteAddress2;
	}

	public void setSiteAddress2(String siteAddress2) {
		this.siteAddress2 = siteAddress2;
	}

	public String getSiteAddress3() {
		return siteAddress3;
	}

	public void setSiteAddress3(String siteAddress3) {
		this.siteAddress3 = siteAddress3;
	}

	public String getSiteAddress4() {
		return siteAddress4;
	}

	public void setSiteAddress4(String siteAddress4) {
		this.siteAddress4 = siteAddress4;
	}

	public List<SiteLicenceVO> getSiteLicense() {
		return siteLicense;
	}

	public void setSiteLicense(final List<SiteLicenceVO> siteLicense) {
		this.siteLicense = siteLicense;
	}

	public List<SiteDeliveryVO> getSiteDelivery() {
		return siteDelivery;
	}

	public void setSiteDelivery(final List<SiteDeliveryVO> siteDelivery) {
		this.siteDelivery = siteDelivery;
	}

	public List<SiteOperationVO> getSiteOperation() {
		return siteOperation;
	}

	public void setSiteOperation(final List<SiteOperationVO> siteOperation) {
		this.siteOperation = siteOperation;
	}

	public List<SiteSubmeterVO> getSiteSubmeter() {
		return siteSubmeter;
	}

	public void setSiteSubmeter(final List<SiteSubmeterVO> siteSubmeter) {
		this.siteSubmeter = siteSubmeter;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getSalesAreaSize() {
		return salesAreaSize;
	}

	public void setSalesAreaSize(String salesAreaSize) {
		this.salesAreaSize = salesAreaSize;
	}

	
	public List<UploadFile> getLicenseAttachments() {
		return licenseAttachments;
	}

	public void setLicenseAttachments(List<UploadFile> licenseAttachments) {
		this.licenseAttachments = licenseAttachments;
	}

	public List<String> getSiteAttachments() {
		return siteAttachments;
	}

	public void setSiteAttachments(List<String> siteAttachments) {
		this.siteAttachments = siteAttachments;
	}

	@Override
	public String toString() {
		return "CreateSiteVO [siteId=" + siteId + ", siteName=" + siteName + ", owner=" + owner + ", district="
				+ district + ", area=" + area + ", cluster=" + cluster + ", operator=" + operator + ", electricityId="
				+ electricityId + ", siteNumber1=" + siteNumber1 + ", siteNumber2=" + siteNumber2 + ", contactName="
				+ contactName + ", email=" + email + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", primaryContact=" + primaryContact + ", secondaryContact=" + secondaryContact + ", siteAddress1="
				+ siteAddress1 + ", siteAddress2=" + siteAddress2 + ", siteAddress3=" + siteAddress3 + ", siteAddress4="
				+ siteAddress4 + ", siteLicense=" + siteLicense + ", siteDelivery=" + siteDelivery + ", siteOperation="
				+ siteOperation + ", siteSubmeter=" + siteSubmeter + ", createdBy=" + createdBy + ", site=" + site
				+ ", validationMessage=" + validationMessage + ", status=" + status + ", fileInput=" + fileInput
				+ ", fileExtension=" + fileExtension + ", fileLocation=" + fileLocation + "]";
	}
	

	public static Comparator<CreateSiteVO> COMPARE_BY_SITENAME = new Comparator<CreateSiteVO>() {
        public int compare(CreateSiteVO one, CreateSiteVO other) {
            return one.siteName.compareTo(other.siteName);
        }
    };

}
