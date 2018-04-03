package com.ops.app.vo;


public class SiteLicenceVO  {

	private Long licenseId;
	private String licenseName;
	private String validfrom;
	private String validto;
	private String attachment;


	public Long getLicenseId() {
		return licenseId;
	}
	public void setLicenseId(final Long licenseId) {
		this.licenseId = licenseId;
	}
	public String getLicenseName() {
		return licenseName;
	}
	public void setLicenseName(final String licenseName) {
		this.licenseName = licenseName;
	}
	public String getValidfrom() {
		return validfrom;
	}
	public void setValidfrom(final String validfrom) {
		this.validfrom = validfrom;
	}
	public String getValidto() {
		return validto;
	}
	public void setValidto(final String validto) {
		this.validto = validto;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	@Override
	public String toString() {
		return "SiteLicenceVO [licenseId=" + licenseId + ", licenseName="
				+ licenseName + ", validfrom=" + validfrom + ", validto="
				+ validto + "]";
	}

}
