package com.ops.jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name="pm_role")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7429561972913695960L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="role_id", unique=true, nullable=false)
	private Long roleId;

	@Column(name="role_desc", length=100)
	private String description;

	@Column(name="role_name", length=100)
	private String roleName;



	public Role() {
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(final Long roleId) {
		this.roleId = roleId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(final String roleName) {
		this.roleName = roleName;
	}

}