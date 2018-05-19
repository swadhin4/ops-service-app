package com.ops.app.vo;

import com.ops.jpa.entities.Company;
import com.ops.jpa.entities.Role;

public class AppUserVO {

	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String isEnabled;
	private Role role;
	private Company company;
	private String generatedPassword;
	private String phoneNo;

	public AppUserVO() {
		super();
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	public String getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(final String isEnabled) {
		this.isEnabled = isEnabled;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(final Role role) {
		this.role = role;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(final Company company) {
		this.company = company;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getGeneratedPassword() {
		return generatedPassword;
	}
	public void setGeneratedPassword(String generatedPassword) {
		this.generatedPassword = generatedPassword;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


}
