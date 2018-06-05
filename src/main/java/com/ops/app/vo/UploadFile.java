package com.ops.app.vo;

public class UploadFile {

	private String base64ImageString;
	private String fileExtension;
	private String fileName;
	private Long licenseId;
	private Long siteId;
	private String siteName;
	private Long assetId;
	private String assetName;
	private Long ticketId;
	private String file;
	private Long imgPos;
	private Long fileSize;
	
	
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getBase64ImageString() {
		return base64ImageString;
	}
	public void setBase64ImageString(String base64ImageString) {
		this.base64ImageString = base64ImageString;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getLicenseId() {
		return licenseId;
	}
	public void setLicenseId(Long licenseId) {
		this.licenseId = licenseId;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public Long getAssetId() {
		return assetId;
	}
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public Long getImgPos() {
		return imgPos;
	}
	public void setImgPos(Long imgPos) {
		this.imgPos = imgPos;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	@Override
	public String toString() {
		return "UploadFile [base64ImageString=" + base64ImageString + ", fileExtension=" + fileExtension + ", fileName="
				+ fileName + ", licenseId=" + licenseId + ", siteId=" + siteId + ", assetId=" + assetId + ", ticketId="
				+ ticketId + ", file=" + file + ", imgPos=" + imgPos + ", fileSize=" + fileSize + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imgPos == null) ? 0 : imgPos.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UploadFile other = (UploadFile) obj;
		if (imgPos == null) {
			if (other.imgPos != null)
				return false;
		} else if (!imgPos.equals(other.imgPos))
			return false;
		return true;
	}
	
}
