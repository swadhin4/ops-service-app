package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the pm_AppFeatures database table.
 * 
 */
@Entity
@Table(name="pm_appfeatures")
public class AppFeature implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="feature_id")
	private Long id;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_on")
	private Date createdOn=new Date();

	@Column(name="feature_desc")
	private String featureDesc;

	@Column(name="feature_name")
	private String featureName;

	@Column(name="feature_code")
	private String featureCode;


	public AppFeature() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	public Object getFeatureDesc() {
		return this.featureDesc;
	}

	public void setFeatureDesc(final String featureDesc) {
		this.featureDesc = featureDesc;
	}

	public String getFeatureName() {
		return this.featureName;
	}

	public String getFeatureCode() {
		return featureCode;
	}

	public void setFeatureCode(final String featureCode) {
		this.featureCode = featureCode;
	}

	public void setFeatureName(final String featureName) {
		this.featureName = featureName;
	}


}