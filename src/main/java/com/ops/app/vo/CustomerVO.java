package com.ops.app.vo;

public class CustomerVO {

	private Long customerId;

	private String email;

	private String message;

	private boolean isRegistered = false;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(final Long customerId) {
		this.customerId = customerId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public boolean isRegistered() {
		return isRegistered;
	}

	public void setRegistered(final boolean isRegistered) {
		this.isRegistered = isRegistered;
	}


}
