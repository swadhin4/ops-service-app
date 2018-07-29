package com.ops.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pm_role_status_mapping")
public class RoleStatus {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="role_status_map_id",unique=true, nullable=false)
	private Long id;
	
	@Column(name="role_id",unique=true, nullable=false)
	private Long RoleId;
	
	@Column(name="status_id",unique=true, nullable=false)
	private Long StatusId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return RoleId;
	}

	public void setRoleId(Long roleId) {
		RoleId = roleId;
	}

	public Long getStatusId() {
		return StatusId;
	}

	public void setStatusId(Long statusId) {
		StatusId = statusId;
	}
	
	
	
}
