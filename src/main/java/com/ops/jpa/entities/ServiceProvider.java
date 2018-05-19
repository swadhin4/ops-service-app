package com.ops.jpa.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="pm_service_provider")
public class ServiceProvider implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -317266482621641521L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sp_id", unique=true, nullable=false)
	private Long serviceProviderId;

	@Column(name="sp_code")
	private String code;

	@Column(name="sp_name")
	private String name;
	
	@Column(name="sp_username")
	private String spUsername;


	@Column(name="sp_email")
	private String email;
	
	@Column(name="help_desk_email")
	private String helpDeskEmail;
	
	@Column(name="help_desk_number")
	private Long helpDeskNumber;

	@ManyToOne
	@JoinColumn(name="country_id")
	private Country country;

	@Column(name="sp_desc")
	private String additionalDetails;

	@ManyToOne
	@JoinColumn(name="customer_id")
	private Company company;

	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate = new Date();

	@Column(name="created_by")
	private String createdBy;

	@Column(name="modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name="modified_by")
	private String modifiedBy;
	
	@Column(name="access_key")
	private String accessKey;

	@OneToMany(mappedBy="serviceProvider", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<SPEscalationLevels> spEscalationLevelList = new ArrayList<SPEscalationLevels>(0);

	@OneToMany(mappedBy="serviceProvider", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<ServiceProviderSLADetails> spSLAList = new ArrayList<ServiceProviderSLADetails>(0);
	
	@Column(name="sla_description")
	private String slaDescription;

	@Version
	private int version;

	public ServiceProvider() {
		super();
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getHelpDeskNumber() {
		return helpDeskNumber;
	}

	public void setHelpDeskNumber(Long helpDeskNumber) {
		this.helpDeskNumber = helpDeskNumber;
	}

	public String getHelpDeskEmail() {
		return helpDeskEmail;
	}

	public void setHelpDeskEmail(String helpDeskEmail) {
		this.helpDeskEmail = helpDeskEmail;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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


	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public List<SPEscalationLevels> getSpEscalationLevelList() {
		return spEscalationLevelList;
	}

	public void setSpEscalationLevelList(
			List<SPEscalationLevels> spEscalationLevelList) {
		this.spEscalationLevelList = spEscalationLevelList;
	}

	public List<ServiceProviderSLADetails> getSpSLAList() {
		return spSLAList;
	}

	public void setSpSLAList(List<ServiceProviderSLADetails> spSLAList) {
		this.spSLAList = spSLAList;
	}

	public String getSlaDescription() {
		return slaDescription;
	}

	public void setSlaDescription(String slaDescription) {
		this.slaDescription = slaDescription;
	}

	public String getSpUsername() {
		return spUsername;
	}

	public void setSpUsername(String spUsername) {
		this.spUsername = spUsername;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
