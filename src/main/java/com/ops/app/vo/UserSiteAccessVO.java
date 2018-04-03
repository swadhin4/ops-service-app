package com.ops.app.vo;


public class UserSiteAccessVO {

	private Long accessId;
	private LoginUser assignedUser;
	private LoginUser unAssignedUser;

	public UserSiteAccessVO() {
		super();
		// TODO Auto-generated constructor stub
	}



	public UserSiteAccessVO(LoginUser unAssignedUser) {
		super();
		this.unAssignedUser = unAssignedUser;
	}



	public UserSiteAccessVO(Long accessId, LoginUser assignedUser) {
		super();
		this.accessId = accessId;
		this.assignedUser = assignedUser;
	}



	public Long getAccessId() {
		return accessId;
	}

	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}

	public LoginUser getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(LoginUser assignedUser) {
		this.assignedUser = assignedUser;
	}

	public LoginUser getUnAssignedUser() {
		return unAssignedUser;
	}

	public void setUnAssignedUser(LoginUser unAssignedUser) {
		this.unAssignedUser = unAssignedUser;
	}




}
