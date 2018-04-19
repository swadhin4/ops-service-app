package com.ops.app.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AssetVO  {

	private Long assetId;

	private String assetCode;

	private Long siteId;

	private String siteName;

	private List<Long> sites = new ArrayList<Long>();
	
	private List<AssetVO> assetSites = new ArrayList<AssetVO>(); 
	
	private String assetName;

	private String content;

	private String assetDescription;

	private String modelNumber;

	private Long categoryId;

	private String assetCategoryName;

	private String assetType;

	private Long locationId;

	private String locationName;

	private String imagePath;

	private String documentPath;

	private Long serviceProviderId;

	private String serviceProviderName;

	private String commisionedDate;

	private String deCommissionedDate;

	private Date dateCommissioned;

	private Date dateDeComissioned;

	private String isAssetElectrical;

	private String isPWSensorAttached;

	private String pwSensorNumber;

	private String createdBy;

	private String modifiedBy;

	private String mode;

	private UploadFile assetImage = new UploadFile(); 
	
	private UploadFile assetDoc = new UploadFile();
	
	private int delFlag;
	

	public AssetVO() {
		super();
	}




	public Long getAssetId() {
		return assetId;
	}



	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}



	public String getAssetCode() {
		return assetCode;
	}



	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}



	public Long getSiteId() {
		return siteId;
	}



	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}



	public String getAssetName() {
		return assetName;
	}



	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}





	public String getModelNumber() {
		return modelNumber;
	}



	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}



	public Long getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}







	public Long getLocationId() {
		return locationId;
	}



	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}



	public String getImagePath() {
		return imagePath;
	}



	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}



	public String getDocumentPath() {
		return documentPath;
	}



	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}



	public Long getServiceProviderId() {
		return serviceProviderId;
	}



	public void setServiceProviderId(Long serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}






	public String getCommisionedDate() {
		return commisionedDate;
	}




	public void setCommisionedDate(String commisionedDate) {
		this.commisionedDate = commisionedDate;
	}




	public String getDeCommissionedDate() {
		return deCommissionedDate;
	}




	public void setDeCommissionedDate(String deCommissionedDate) {
		this.deCommissionedDate = deCommissionedDate;
	}




	public String getAssetDescription() {
		return assetDescription;
	}

	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}




	public String getIsAssetElectrical() {
		return isAssetElectrical;
	}




	public void setIsAssetElectrical(String isAssetElectrical) {
		this.isAssetElectrical = isAssetElectrical;
	}




	public String getIsPWSensorAttached() {
		return isPWSensorAttached;
	}




	public void setIsPWSensorAttached(String isPWSensorAttached) {
		this.isPWSensorAttached = isPWSensorAttached;
	}




	public String getPwSensorNumber() {
		return pwSensorNumber;
	}



	public void setPwSensorNumber(String pwSensorNumber) {
		this.pwSensorNumber = pwSensorNumber;
	}




	public String getSiteName() {
		return siteName;
	}




	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}




	public String getAssetCategoryName() {
		return assetCategoryName;
	}




	public void setAssetCategoryName(String assetCategoryName) {
		this.assetCategoryName = assetCategoryName;
	}




	public String getAssetType() {
		return assetType;
	}




	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}




	public String getLocationName() {
		return locationName;
	}




	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}




	public String getServiceProviderName() {
		return serviceProviderName;
	}




	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}




	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Date getDateCommissioned() {
		return dateCommissioned;
	}

	public void setDateCommissioned(Date dateCommissioned) {
		this.dateCommissioned = dateCommissioned;
	}

	public Date getDateDeComissioned() {
		return dateDeComissioned;
	}

	public void setDateDeComissioned(Date dateDeComissioned) {
		this.dateDeComissioned = dateDeComissioned;
	}
	public String getContent() {
		return content;
	}




	public void setContent(String content) {
		this.content = content;
	}




	public List<Long> getSites() {
		return sites;
	}




	public void setSites(List<Long> sites) {
		this.sites = sites;
	}




	public List<AssetVO> getAssetSites() {
		return assetSites;
	}




	public void setAssetSites(List<AssetVO> assetSites) {
		this.assetSites = assetSites;
	}




	public String getMode() {
		return mode;
	}




	public void setMode(String mode) {
		this.mode = mode;
	}




	public UploadFile getAssetImage() {
		return assetImage;
	}




	public void setAssetImage(UploadFile assetImage) {
		this.assetImage = assetImage;
	}




	public UploadFile getAssetDoc() {
		return assetDoc;
	}




	public void setAssetDoc(UploadFile assetDoc) {
		this.assetDoc = assetDoc;
	}




	public int getDelFlag() {
		return delFlag;
	}




	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}




	@Override
	public String toString() {
		return "AssetVO [assetId=" + assetId + ", assetCode=" + assetCode
				+ ", siteId=" + siteId + ", siteName=" + siteName
				+ ", assetName=" + assetName + ", assetDescription="
				+ assetDescription + ", modelNumber=" + modelNumber
				+ ", categoryId=" + categoryId + ", assetCategoryName="
				+ assetCategoryName + ", assetType=" + assetType 
				+ " locationId=" + locationId + ", locationName="
				+ locationName + ", imagePath=" + imagePath + ", documentPath="
				+ documentPath + ", serviceProviderId=" + serviceProviderId
				+ ", serviceProviderName=" + serviceProviderName
				+ ", commisionedDate=" + commisionedDate
				+ ", deCommissionedDate=" + deCommissionedDate
				+ ", dateCommissioned=" + dateCommissioned
				+ ", dateDeComissioned=" + dateDeComissioned
				+ ", isAssetElectrical=" + isAssetElectrical
				+ ", isPWSensorAttached=" + isPWSensorAttached
				+ ", pwSensorNumber=" + pwSensorNumber + ", createdBy="
				+ createdBy + ", modifiedBy=" + modifiedBy + "]";
	}


}
