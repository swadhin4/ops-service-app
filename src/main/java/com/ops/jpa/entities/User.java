package com.ops.jpa.entities;

import java.io.Serializable;
import java.sql.Blob;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


/**
 * The persistent class for the application_users database table.
 *
 */
@Entity
@Table(name="pm_users")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2777690658363311196L;

	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userId;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt=new Date();

	@Column(name="email_id")
	private String emailId;

	@Column(name="first_name")
	private String firstName;

	@Column(name="enabled")
	private int enabled ;

	@Column(name="last_name")
	private String lastName;

	@Column(name="login_name")
	private String loginName;
	
	@Column(name="phone")
	private Long phone;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="company_id")
	private Company company;

	@Column(name="modified_by")
	private String modifiedBy;

	@Column(name="modified_date")
	private Date modifiedDate;

	private String password;

	@Column(name="sys_password")
	private String sysPassword;

	@Column(name="image")
	private Blob image;

	@Version
	private int version;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserRole> userRoles=new ArrayList<UserRole>();


	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<UserSiteAccess> userAccessList = new ArrayList<UserSiteAccess>(0);

	public User() {
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(final Long userId) {
		this.userId = userId;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(final Date createdAt) {
		this.createdAt = createdAt;
	}


	public String getEmailId() {
		return emailId;
	}


	public void setEmailId(final String emailId) {
		this.emailId = emailId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}


	public int getEnabled() {
		return enabled;
	}


	public void setEnabled(final int enabled) {
		this.enabled = enabled;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(final String loginName) {
		this.loginName = loginName;
	}


	public Company getCompany() {
		return company;
	}


	public void setCompany(final Company company) {
		this.company = company;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(final String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Date getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(final Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(final String password) {
		this.password = password;
	}


	public String getSysPassword() {
		return sysPassword;
	}


	public void setSysPassword(final String sysPassword) {
		this.sysPassword = sysPassword;
	}


	public Blob getImage() {
		return image;
	}


	public void setImage(final Blob image) {
		this.image = image;
	}


	public int getVersion() {
		return version;
	}


	public void setVersion(final int version) {
		this.version = version;
	}


	public List<UserRole> getUserRoles() {
		return userRoles;
	}


	public void setUserRoles(final List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}


	public List<UserSiteAccess> getUserAccessList() {
		return userAccessList;
	}


	public void setUserAccessList(List<UserSiteAccess> userAccessList) {
		this.userAccessList = userAccessList;
	}


	public Long getPhone() {
		return phone;
	}


	public void setPhone(Long phone) {
		this.phone = phone;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}


	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (emailId == null) {
			if (other.emailId != null) {
				return false;
			}
		} else if (!emailId.equals(other.emailId)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (loginName == null) {
			if (other.loginName != null) {
				return false;
			}
		} else if (!loginName.equals(other.loginName)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}


}