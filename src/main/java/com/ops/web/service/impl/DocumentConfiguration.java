package com.ops.web.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("documentConfigService")
public class DocumentConfiguration {

	@Value(value="D:/TechM/SM351137/Sample-Applications/backup/challans")
	private String challanPDFUploadDirectory;

	@Value(value="D:/TechM/SM351137/Sample-Applications/backup/invoices")
	private String invoicePDFUploadDirectory;

	public String getChallanPDFUploadDirectory() {
		return challanPDFUploadDirectory;
	}

	public void setChallanPDFUploadDirectory(String challanPDFUploadDirectory) {
		this.challanPDFUploadDirectory = challanPDFUploadDirectory;
	}

	public String getInvoicePDFUploadDirectory() {
		return invoicePDFUploadDirectory;
	}

	public void setInvoicePDFUploadDirectory(String invoicePDFUploadDirectory) {
		this.invoicePDFUploadDirectory = invoicePDFUploadDirectory;
	}





}
