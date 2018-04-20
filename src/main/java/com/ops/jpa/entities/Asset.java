package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="pm_asset")
public class Asset implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -399069178662765102L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="asset_id" , unique=true, nullable=false)
	private Long assetId;

	@Column(name="asset_code")
	private String assetCode;

	@Column(name="site_id")
	private Long siteId;

	@Column(name="asset_name")
	private String assetName;


	@Column(name="model_number")
	private String modelNumber;

	@Column(name="category_id")
	private Long categoryId;

	@Column(name="content")
	private String content;

	@Column(name="location_id")
	private Long locationId;

	@Column(name="image_path")
	private String imagePath;

	@Column(name="document_path")
	private String documentPath;

	@Column(name="sp_id")
	private Long serviceProviderId;

	@Column(name="date_commissioned")
	private Date dateCommissioned;

	@Column(name="date_decomissioned")
	private Date dateDeComissioned;

	@Column(name="asset_desc")
	private String assetDescription;

	@Column(name="is_asset_electrical")
	private String isAssetElectrical;

	@Column(name="is_pw_sensor_attached")
	private String isPWSensorAttached;

	@Column(name="pw_sensor_number")
	private String pwSensorNumber;

	@Column(name="created_date")
	private Date createdDate=new Date();

	@Column(name="modified_date")
	private Date modifiedDate;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="modified_by")
	private String modifiedBy;
	
	@Column(name="del_flag")
	private int delFlag = 0;
	

	private int version;



	public Asset() {
		super();
	}



	public Asset(Long assetId, String assetCode, Long siteId, String assetName,
			String modelNumber, Long categoryId, Long locationId) {
		super();
		this.assetId = assetId;
		this.assetCode = assetCode;
		this.siteId = siteId;
		this.assetName = assetName;
		this.modelNumber = modelNumber;
		this.categoryId = categoryId;
		this.locationId = locationId;
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



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
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



	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}



	public Date getModifiedDate() {
		return modifiedDate;
	}



	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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



	public int getDelFlag() {
		return delFlag;
	}



	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}



	public int getVersion() {
		return version;
	}



	public void setVersion(int version) {
		this.version = version;
	}




}
