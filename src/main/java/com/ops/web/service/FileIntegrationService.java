package com.ops.web.service;

import java.io.IOException;
import java.util.List;

import com.ops.app.util.RestResponse;
import com.ops.app.vo.AssetVO;
import com.ops.app.vo.CreateSiteVO;
import com.ops.app.vo.TicketVO;
import com.ops.app.vo.UploadFile;
import com.ops.jpa.entities.Company;

public interface FileIntegrationService {

	public String siteFileUpload(CreateSiteVO siteVO, UploadFile siteFile, Company company)  throws IOException;
	
	public String siteLicenseFileUpload(UploadFile siteFile, Company company)  throws IOException;
	
	public String siteIncidentFileUpload(List<UploadFile> fileList,TicketVO customerTicketVO, Company company, String folderLocation, String uploadedBy)  throws IOException;
	
	public AssetVO siteAssetFileUpload(AssetVO assetVO,UploadFile assetFile, Company company, String type)  throws IOException;
	
	public String createIncidentFolder(String incidentNumber, Company company) throws IOException;
	
	public RestResponse getFileLocation(Company company, String keyName) throws Exception;
	
	public RestResponse deleteFile(Long siteId, List<Long> licenseIdList, Long assetId, List<Long> incidentList, String keyName) throws Exception;
	
}
