package com.ops.app.vo;

public class SPLoginVO {
	
	private Long spId;
	
	private String email;
	
	private String accessCode;
	
	private boolean isValidated;
	
	private String role;
	
	private String spName;
	

	public SPLoginVO() {
		super();
	}

	public Long getSpId() {
		return spId;
	}

	public void setSpId(Long spId) {
		this.spId = spId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}
	
	
}
