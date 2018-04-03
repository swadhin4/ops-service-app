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
@Table(name="pm_cluster")
public class Cluster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6781479516784577668L;
	@Id
	@Column(name="cluster_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long clusterID;

	@Column(name="cluster_name")
	private String clusterName;

	@Column(name="region_id")
	private Long regionId;

	@Column(name="country_id")
	private Long countryId;

	@Column(name="district_id")
	private Long districtId;

	@Column(name="area_id")
	private Long area;

	@Column(name="cluster_desc")
	private String clusterDesc;

	@Column(name="created_date")
	private Date createdDate = new Date();

	@Column(name="modified_date")
	private Date modifiedDate;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="modified_by")
	private String modifiedBy;



	public Cluster() {
		super();
	}



	public Long getClusterID() {
		return clusterID;
	}



	public void setClusterID(final Long clusterID) {
		this.clusterID = clusterID;
	}



	public String getClusterName() {
		return clusterName;
	}



	public void setClusterName(final String clusterName) {
		this.clusterName = clusterName;
	}





	public String getClusterDesc() {
		return clusterDesc;
	}



	public void setClusterDesc(final String clusterDesc) {
		this.clusterDesc = clusterDesc;
	}



	public Long getCountryId() {
		return countryId;
	}



	public void setCountryId(final Long countryId) {
		this.countryId = countryId;
	}



	public Long getDistrictId() {
		return districtId;
	}



	public void setDistrictId(final Long districtId) {
		this.districtId = districtId;
	}



	public void setRegionId(final Long regionId) {
		this.regionId = regionId;
	}



	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}



	public Date getModifiedDate() {
		return modifiedDate;
	}



	public void setModifiedDate(final Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}



	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}



	public String getModifiedBy() {
		return modifiedBy;
	}



	public void setModifiedBy(final String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}



	public Long getArea() {
		return area;
	}



	public void setArea(final Long area) {
		this.area = area;
	}



	public Long getRegionId() {
		return regionId;
	}



	@Override
	public String toString() {
		return "Cluster [clusterID=" + clusterID + ", clusterName="
				+ clusterName + ", regionId=" + regionId + ", countryId="
				+ countryId + ", districtId=" + districtId + ", area=" + area
				+ ", clusterDesc=" + clusterDesc + ", createdDate="
				+ createdDate + ", modifiedDate=" + modifiedDate
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ "]";
	}


}
