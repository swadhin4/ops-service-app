package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="pm_asset_category")
public class AssetCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2741135886581506883L;


	@Id	
	@Column(name="category_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long assetCategoryId;

	@Column(name="category_name")
	private String assetCategoryName;
	@Column(name="asset_type")
	private String assetType;

	@Column(name="created_date")
	private Date createdDate = new Date();

	@Column(name="created_by")
	private String createdBy;

	public AssetCategory() {
		super();
	}

	public Long getAssetCategoryId() {
		return assetCategoryId;
	}

	public void setAssetCategoryId(Long assetCategoryId) {
		this.assetCategoryId = assetCategoryId;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
