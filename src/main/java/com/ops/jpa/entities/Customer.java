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
@Table(name="pm_customer")
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8815645816248106770L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="customer_id")
	private Long customerId;

	@Column(name="email")
	private String email;

	@Column(name="message")
	private String message;

	@Column(name="created_on")
	private Date createdAt=new Date();

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


}
