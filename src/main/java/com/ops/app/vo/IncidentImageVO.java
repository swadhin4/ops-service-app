package com.ops.app.vo;

public class IncidentImageVO {

	private String fileName;
	private String file;
	private String incidentImgId;
	private int imgPos;
	private String base64ImageString;
	private String fileExtension;
	private int fileSize;
	private int totalSize;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getIncidentImgId() {
		return incidentImgId;
	}
	public void setIncidentImgId(String incidentImgId) {
		this.incidentImgId = incidentImgId;
	}
	public int getImgPos() {
		return imgPos;
	}
	public void setImgPos(int imgPos) {
		this.imgPos = imgPos;
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
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	@Override
	public String toString() {
		return "IncidentImageVO [fileName=" + fileName + ", file=" + file + ", incidentImgId=" + incidentImgId
				+ ", imgPos=" + imgPos + ", base64ImageString=" + base64ImageString + ", fileExtension=" + fileExtension
				+ ", fileSize=" + fileSize + "]";
	}
	
	
}
