package com.ops.app.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ops.jpa.entities.Company;

public class UserVO {

	private Long userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String emailId;
	private Map<Long,String> roles=new HashMap<Long,String>();
	private List<String> roleNames = new ArrayList<String>();
	private List<Long> roleIds=new ArrayList<Long>();
	private String createdAt;
	private boolean isExists;
	private Company company = new Company();
	private String passwordGenerated;
	private int enabled;
	private String phoneNo;

	public Long getUserId() {
		return userId;
	}
	public void setUserId(final Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(final String userName) {
		this.userName = userName;
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

	public void setRoles(final Map<Long, String> roles) {
		this.roles = roles;
	}
	public List<Long> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(final List<Long> roleIds) {
		this.roleIds = roleIds;
	}
	public Map<Long, String> getRoles() {
		return roles;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(final String emailId) {
		this.emailId = emailId;
	}
	public List<String> getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(final List<String> roleNames) {
		this.roleNames = roleNames;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(final String createdAt) {
		this.createdAt = createdAt;
	}
	public boolean isExists() {
		return isExists;
	}
	public void setExists(final boolean isExists) {
		this.isExists = isExists;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(final Company company) {
		this.company = company;
	}
	public String getPasswordGenerated() {
		return passwordGenerated;
	}
	public void setPasswordGenerated(String passwordGenerated) {
		this.passwordGenerated = passwordGenerated;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}



}
