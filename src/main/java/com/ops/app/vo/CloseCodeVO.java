package com.ops.app.vo;


public class CloseCodeVO {


	private Long closedCode;

	private String closedCodeDesc;

	public CloseCodeVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getClosedCode() {
		return closedCode;
	}

	public void setClosedCode(final Long closedCode) {
		this.closedCode = closedCode;
	}

	public String getClosedCodeDesc() {
		return closedCodeDesc;
	}

	public void setClosedCodeDesc(final String closedCodeDesc) {
		this.closedCodeDesc = closedCodeDesc;
	}

	@Override
	public String toString() {
		return "CloseCodeVO [closedCode=" + closedCode + ", closedCodeDesc="
				+ closedCodeDesc + "]";
	}




}
