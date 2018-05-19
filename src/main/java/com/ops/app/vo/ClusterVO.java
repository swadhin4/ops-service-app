package com.ops.app.vo;


public class ClusterVO {

	private Long clusterID;

	private String clusterName;

	public ClusterVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getClusterID() {
		return clusterID;
	}

	public void setClusterID(final Long clusterID) {
		this.clusterID = clusterID;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(final String clusterName) {
		this.clusterName = clusterName;
	}

	@Override
	public String toString() {
		return "ClusterVO [clusterID=" + clusterID + ", clusterName="
				+ clusterName + "]";
	}


}
