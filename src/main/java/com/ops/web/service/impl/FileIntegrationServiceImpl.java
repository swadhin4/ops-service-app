package com.ops.web.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ops.app.util.RestResponse;
import com.ops.app.vo.AssetVO;
import com.ops.app.vo.CreateSiteVO;
import com.ops.app.vo.TicketVO;
import com.ops.app.vo.UploadFile;
import com.ops.jpa.entities.Asset;
import com.ops.jpa.entities.Company;
import com.ops.jpa.entities.Site;
import com.ops.jpa.entities.SiteLicence;
import com.ops.jpa.entities.TicketAttachment;
import com.ops.jpa.repository.AssetRepo;
import com.ops.jpa.repository.LicenseRepo;
import com.ops.jpa.repository.SiteRepo;
import com.ops.jpa.repository.TicketAttachmentRepo;
import com.ops.web.service.AwsIntegrationService;
import com.ops.web.service.FileIntegrationService;


@Service(value="fileIntegrationService")
public class FileIntegrationServiceImpl implements FileIntegrationService {

	private final static Logger LOGGER = LoggerFactory.getLogger(FileIntegrationServiceImpl.class);
	
	 @Autowired
	 private Environment environment;
	
	@Autowired
	private SiteRepo siteRepo;
	
	@Autowired
	private AwsIntegrationService awsIntegrationService;
	
	@Autowired
	private LicenseRepo licenseRepo;
	
	@Autowired
	private AssetRepo assetRepo;
	
	@Autowired
	private TicketAttachmentRepo ticketAttachmentRepo;
	
	
	
	@Override
	public String siteFileUpload(CreateSiteVO siteVO, UploadFile siteFile, Company company) throws IOException{
		LOGGER.info("Insdie FileIntegrationServiceImpl .. siteFileUpload");
		String base64Image = siteFile.getBase64ImageString().split(",")[1];
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
		String fileUploadLocation = environment.getProperty("file.upload.location");
		Site site=null;
		String generatedFileName="";
		Path destinationFile =null;
		String fileKey="";
		String siteName=siteVO.getSiteName();
		if(siteVO.getSiteId()!=null){
			site=siteRepo.findOne(siteVO.getSiteId());
		/*	if(StringUtils.isNotBlank(site.getAttachmentPath())){
				generatedFileName=site.getAttachmentPath();
				fileKey=generatedFileName;
				destinationFile = Paths.get(fileUploadLocation+"\\"+generatedFileName);
			}else{*/
				siteName = siteName.replaceAll(" ", "_").toLowerCase();
				generatedFileName = siteName+"_"+Calendar.getInstance().getTimeInMillis()+"."+siteFile.getFileExtension().toLowerCase();
				destinationFile = Paths.get(fileUploadLocation+"\\"+company.getCompanyCode()+"\\site\\"+generatedFileName);
				fileKey=company.getCompanyCode()+"/site/"+generatedFileName;
			//}
		}else{
			generatedFileName = siteVO.getSiteName()+"_"+Calendar.getInstance().getTimeInMillis()+"."+siteFile.getFileExtension().toLowerCase();
			destinationFile = Paths.get(fileUploadLocation+"\\"+company.getCompanyCode()+"\\site\\"+generatedFileName);
			fileKey=company.getCompanyCode()+"/site/"+generatedFileName;
		}
		
		try {
			Files.write(destinationFile, imageBytes);
			LOGGER.info("Saving image to location : "+ destinationFile.toString() );
			siteVO.setFileLocation(destinationFile.toString());
			pushToAwsS3(destinationFile, fileKey);
		
		} catch (IOException e) {
			LOGGER.info("Unable to upload site image ", e );
		}
		
		LOGGER.info("Exit FileIntegrationServiceImpl .. siteFileUpload");
		return fileKey;
	}



	private void pushToAwsS3(Path destinationFile, String fileKey) {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJZTA6BYNTESWQWBQ", "YWzhoGSfC1ADDT+xHzvAsvf/wyMlSl71TexLLg8t");
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
		                        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
		                        .build();
		String bucketName="malay-first-s3-bucket-pms-test";
		try{
			awsIntegrationService.uploadObject(new PutObjectRequest(bucketName, fileKey, destinationFile.toFile()).withCannedAcl(CannedAccessControlList.Private), s3client);
		}catch(Exception e){
			LOGGER.info("Exception occured while pushing file to Cloud", e);
		}
	}

	

	@Override
	public String siteLicenseFileUpload(UploadFile licenseFile, Company company)  throws IOException {
		LOGGER.info("Inside FileIntegrationServiceImpl .. siteLicenseFileUpload");
			String base64Image = licenseFile.getBase64ImageString().split(",")[1];
			byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
			String fileUploadLocation = environment.getProperty("file.upload.location");
			SiteLicence license = null;
			if(licenseFile.getLicenseId()!=null){
				license = licenseRepo.findOne(licenseFile.getLicenseId());
			}
			String generatedFileName="";
			String fileKey="";
			Path destinationFile = null;
			String licenseName=licenseFile.getFileName();
			if(license!=null){
				/*if(StringUtils.isNotBlank(license.getAttachmentPath())){
					generatedFileName=license.getAttachmentPath();
					destinationFile = Paths.get(fileUploadLocation+"\\"+generatedFileName);
					fileKey=generatedFileName;
				}else{*/
					licenseName=licenseName.replaceAll(" ", "_").toLowerCase();
					generatedFileName = licenseName+"_"+Calendar.getInstance().getTimeInMillis()+"."+licenseFile.getFileExtension().toLowerCase();
					destinationFile = Paths.get(fileUploadLocation+"\\"+company.getCompanyCode()+"\\site\\license\\"+generatedFileName);
					fileKey=company.getCompanyCode()+"/site/license/"+generatedFileName;
				//}\
			}else{
				licenseName=licenseName.replaceAll(" ", "_").toLowerCase();
				generatedFileName = licenseName+"_"+Calendar.getInstance().getTimeInMillis()+"."+licenseFile.getFileExtension().toLowerCase();
				destinationFile = Paths.get(fileUploadLocation+"\\"+company.getCompanyCode()+"\\site\\license\\"+generatedFileName);
				fileKey=company.getCompanyCode()+"/site/license/"+generatedFileName;
			}
			try {
				Files.write(destinationFile, imageBytes);
				LOGGER.info("Saving image to location : "+  destinationFile.toString() );
				//pushToAwsS3(destinationFile,  fileKey);
			} catch (IOException e) {
				LOGGER.info("Unable to upload license image ", e );
			}
		
		LOGGER.info("Exit SiteServiceImpl .. uploadSiteLicenseImage");
		return fileKey;
	}

	@Override
	public String siteIncidentFileUpload(List<UploadFile> incidentFileList, TicketVO customerTicketVO, Company company, String folderLocation, String uploadedBy)  throws IOException {
		LOGGER.info("Inside FileIntegrationServiceImpl .. siteIncidentFileUpload");
		String dataSent="STARTED";
		Map<Path,String> incidentKeyMap = new HashMap<Path, String>();
		for(UploadFile attachment : incidentFileList){
			String base64Image = "";
			String fileKey="";
			Path destinationFile = null;
			String generatedFileName="";
				//String fileUploadLocation = ApplicationUtil.getServerUploadLocation();
				base64Image = attachment.getBase64ImageString().split(",")[1];
				generatedFileName=customerTicketVO.getTicketNumber()+"_"+Calendar.getInstance().getTimeInMillis()+".jpg";
				destinationFile = Paths.get(folderLocation+"\\"+generatedFileName);
				fileKey=company.getCompanyCode()+"/incident/"+customerTicketVO.getTicketNumber()+"/"+generatedFileName;
			if(StringUtils.isEmpty(base64Image)){
				LOGGER.info("No Image or Document selected" );
			}else{
				byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
				try {
					Files.write(destinationFile, imageBytes);
					LOGGER.info("Saving image to location : "+ destinationFile.toString() );
					incidentKeyMap.put(destinationFile, fileKey);
					TicketAttachment ticketAttachment = new TicketAttachment();
					ticketAttachment.setAttachmentPath(fileKey);
					ticketAttachment.setTicketId(customerTicketVO.getTicketId());
					ticketAttachment.setTicketNumber(customerTicketVO.getTicketNumber());
					ticketAttachment.setCreatedBy(uploadedBy);
					ticketAttachmentRepo.save(ticketAttachment);
				} catch (IOException e) {
					LOGGER.info("Unable to upload incident files ", e );
				}
			}
		}
		LOGGER.info("Pushing files to AWS s3 Bucket");
		int start=0;
		for (Map.Entry<Path, String> pathEntry : incidentKeyMap.entrySet()) {
			LOGGER.info("Uploading file to S3 : "+ pathEntry.getValue());
			++start;
			pushToAwsS3(pathEntry.getKey(),  pathEntry.getValue());
			++start;
		}
		if(start!=0 && start % 2==0){
			dataSent="FINISHED";
		}
		LOGGER.info("Exit FileIntegrationServiceImpl .. siteIncidentFileUpload");
		return dataSent;
	}

	@Override
	public AssetVO siteAssetFileUpload(AssetVO assetVO,UploadFile assetFile, Company company, String type)  throws IOException{
		LOGGER.info("Inside FileIntegrationServiceImpl .. siteAssetFileUpload");
		String base64Image = assetFile.getBase64ImageString().split(",")[1];
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
		String fileUploadLocation = environment.getProperty("file.upload.location");
		Asset asset=null;
		String generatedFileName="";
		Path destinationFile =null;
		String fileKey="";
		String assetName=assetVO.getAssetName();
		if(assetVO.getAssetId()!=null){
			asset=assetRepo.findOne(assetVO.getAssetId());
			if(type.equalsIgnoreCase("IMAGE")){
				/*if(StringUtils.isNotBlank(asset.getImagePath())){
					generatedFileName=asset.getImagePath();
					destinationFile = Paths.get(fileUploadLocation+"\\"+generatedFileName);
					fileKey=generatedFileName;
				}else{*/
					assetName = assetName.replaceAll(" ", "_").toLowerCase();
					generatedFileName = assetName+"_"+Calendar.getInstance().getTimeInMillis()+"."+assetFile.getFileExtension().toLowerCase();
					destinationFile = Paths.get(fileUploadLocation+"\\"+company.getCompanyCode()+"\\asset\\"+generatedFileName);
					fileKey=company.getCompanyCode()+"/asset/"+generatedFileName;
				//}
				try {
					Files.write(destinationFile, imageBytes);
					LOGGER.info("Saving image to location : "+ destinationFile.toString() );
					assetVO.setImagePath(fileKey);
					pushToAwsS3(destinationFile, fileKey);
					asset.setImagePath(assetVO.getImagePath());
					
				
				} catch (IOException e) {
					LOGGER.info("Unable to upload asset image ", e );
				}
				
			}
			
			if(type.equalsIgnoreCase("DOC")){
			/*	if(StringUtils.isNotBlank(asset.getDocumentPath())){
					generatedFileName=asset.getDocumentPath();
					destinationFile = Paths.get(fileUploadLocation+"\\"+generatedFileName);
					fileKey=generatedFileName;
				}else{*/
					assetName = assetName.replaceAll(" ", "_").toLowerCase();
					generatedFileName = assetName+"_"+Calendar.getInstance().getTimeInMillis()+"."+assetFile.getFileExtension().toLowerCase();
					destinationFile = Paths.get(fileUploadLocation+"\\"+company.getCompanyCode()+"\\asset\\"+generatedFileName);
					fileKey=company.getCompanyCode()+"/asset/"+generatedFileName;
				//}
				
				try {
					Files.write(destinationFile, imageBytes);
					LOGGER.info("Saving Doc to location : "+ destinationFile.toString() );
					assetVO.setDocumentPath(fileKey);
					pushToAwsS3(destinationFile, fileKey);
				
				} catch (IOException e) {
					LOGGER.info("Unable to upload asset doc ", e );
				}
			}
		}
		LOGGER.info("Exit FileIntegrationServiceImpl .. siteAssetFileUpload");
		return assetVO;
	}



	@Override
	public RestResponse getFileLocation(Company company, String keyName) throws Exception {
		LOGGER.info("Inside FileIntegrationServiceImpl .. getFileLocation");
		RestResponse response = new RestResponse();
		File file = null;
		if (org.apache.commons.lang3.StringUtils.isNotBlank(keyName)) {
			try{
		    String fileUploadLocation = environment.getProperty("file.upload.location");
			String fileDownloadLocation = environment.getProperty("file.download.location");
			file = new File(fileUploadLocation+"/"+keyName);
			String contentType="";
			if(file.exists()){
				 File downloadDirectory = new File(fileDownloadLocation+"\\"+keyName);
				 copyFileUsingApacheCommonsIO(file, downloadDirectory);
				 LOGGER.info("File copied to download location : "+ downloadDirectory.getPath());
				 String windowsFilePath = downloadDirectory.getPath();
				 String javaFilePath = windowsFilePath.replace("\\", "/"); 
				 contentType= new MimetypesFileTypeMap().getContentType(downloadDirectory.getPath());
					response.setStatusCode(200);
					response.setMessage(javaFilePath);
					response.setFileType(contentType);
				 
			}else{
				 LOGGER.info("File not found in server, so connecting to AWS S3");
			file = awsIntegrationService.downloadFile("malay-first-s3-bucket-pms-test", keyName);
			if (file!=null){
				if(file.exists()) { 
					 String windowsFilePath = file.getPath();
					 String javaFilePath = windowsFilePath.replace("\\", "/"); 
					contentType= new MimetypesFileTypeMap().getContentType(file.getPath());
					response.setStatusCode(200);
					response.setMessage(javaFilePath);
					response.setFileType(contentType);
				}
			}
			else{
				response.setStatusCode(404);
				response.setMessage("File not found");
			}
			 LOGGER.info("File Content Type : "+ contentType);
			}
			}catch(AmazonS3Exception e){
				LOGGER.info("Key name"+ keyName +" does not exits.", e);
				response.setStatusCode(500);
				response.setMessage("Invalid File name");
			}
			catch(Exception e){
				LOGGER.info("File does not exits", e);
				response.setStatusCode(500);
				response.setMessage("File does not exits");
			}
		}else{
			response.setStatusCode(404);
			response.setMessage("File Key name is empty");
			
		}
		LOGGER.info("Exit FileIntegrationServiceImpl .. getFileLocation");
		return response;
	}

	private static void copyFileUsingApacheCommonsIO(File source, File dest) throws IOException {
	    FileUtils.copyFile(source, dest);
	}

	@Override
	public String createIncidentFolder(String incidentNumber, Company company) throws IOException {
		LOGGER.info("Inside FileIntegrationServiceImpl .. createIncidentFolder");
		boolean result=false;
		String uploadDirectoryName = environment.getProperty("file.upload.location")+File.separatorChar+company.getCompanyCode()+File.separatorChar+"incident"+File.separatorChar+incidentNumber;
		String downloadDirectoryName = environment.getProperty("file.download.location")+File.separatorChar+company.getCompanyCode()+File.separatorChar+"incident"+File.separatorChar+incidentNumber;
		 File uploadFile=new File(uploadDirectoryName);
		 File downloadFile=new File(downloadDirectoryName);
		 try{
		    if (! uploadFile.exists()){
		    	 boolean isUploadDirCreated = uploadFile.mkdirs();
				 boolean isDownloadDirCreated = downloadFile.mkdirs();
	        	 LOGGER.info("Incident Upload Folder created : "+  uploadFile.getPath());
	        	 LOGGER.info("Incident Download Folder created : "+  downloadFile.getPath());
	        	  result=true;
		        }else{
		        	LOGGER.info("Directory alread exists");
		    }
		 }catch(SecurityException  se){
		    	LOGGER.info("Security Exception occured while creating exception");
		    	se.printStackTrace();
		    	
		 }
		 if(result){
			 LOGGER.info("Directory created successfully");
		 }
		LOGGER.info("Exit FileIntegrationServiceImpl .. createIncidentFolder");
		return uploadFile.getPath();
	}



	@Override
	@Transactional
	public RestResponse deleteFile(Long siteId, List<Long> licenseIdList, Long assetId, List<Long> incidentList, String assetType) throws Exception {
		LOGGER.info("Inside FileIntegrationServiceImpl .. deleteFile");
		RestResponse response = new RestResponse();
		if(siteId!=null){
			Site site = siteRepo.findOne(siteId);
			LOGGER.info("Deleting attachment for Site : "+site.getSiteName());
			String fileUploadLocation = environment.getProperty("file.upload.location");
			String fileDownloadLocation = environment.getProperty("file.download.location");
			boolean isFileDeleted=false;
			if(StringUtils.isNotBlank(site.getAttachmentPath())){
			File uploadDirectory = new File(fileUploadLocation +"\\"+site.getAttachmentPath());
			File downloadDirectory = new File(fileDownloadLocation +"\\"+site.getAttachmentPath());
				if(uploadDirectory.exists()){
					if(uploadDirectory.delete()){
						isFileDeleted =true;
						response.setStatusCode(200);
						LOGGER.info("Deleted successfully from "+uploadDirectory.getPath());
					
						response = deleteFileFromS3(site.getAttachmentPath(), response);
						LOGGER.info("Deleted successfully from S3");
						isFileDeleted=true;
					}
				}else{
					response = deleteFileFromS3(site.getAttachmentPath(), response);
					LOGGER.info("Deleted successfully from S3");
					isFileDeleted=true;
				}
				
				if(downloadDirectory.exists()){
					if(downloadDirectory.delete()){
						LOGGER.info("Deleted successfully from "+downloadDirectory.getPath());
						isFileDeleted=true;
					}
				}
				if(isFileDeleted){
					site.setAttachmentPath(null);
					site = siteRepo.save(site);
					//response.setObject(site);
					response.setStatusCode(200);
				}
			}
		}
		if(licenseIdList!=null && !licenseIdList.isEmpty()){
			boolean isFileDeleted=false;
			List<SiteLicence> siteLicenseList = licenseRepo.findByLicenseIdIn(licenseIdList);
			if(!siteLicenseList.isEmpty()){
				List<KeyVersion> keys = new ArrayList<KeyVersion>();
				for(SiteLicence license:siteLicenseList){
					LOGGER.info("Deleting attachment for License  : "+license.getLicenseName());
					SiteLicence tempLicense = license;
					keys.add(new KeyVersion(tempLicense.getAttachmentPath()));
				}
				String fileUploadLocation = environment.getProperty("file.upload.location");
				File uploadDirectory = new File(fileUploadLocation +"\\"+siteLicenseList.get(0).getAttachmentPath());
				if(uploadDirectory.exists()){
					if(uploadDirectory.delete()){
						isFileDeleted =true;
						response.setStatusCode(200);
						LOGGER.info("Deleted successfully from "+uploadDirectory.getPath());
					}else{
						response =  awsIntegrationService.deleteMultipleFile(keys);
						LOGGER.info("Deleted successfully from S3");
						isFileDeleted=true;
						response.setStatusCode(200);
					}
				}else{
					response =  awsIntegrationService.deleteMultipleFile(keys);
					LOGGER.info("Deleted successfully from S3");
					isFileDeleted=true;
				}
				if(response.getStatusCode()==200 && isFileDeleted){
					LOGGER.info("Updating site license path ");
					for(SiteLicence license:siteLicenseList){
						SiteLicence tempLicense = license;
						tempLicense.setAttachmentPath(null);
						tempLicense = licenseRepo.save(tempLicense);
						//response.setObject(tempLicense);
					}
					response.setStatusCode(200);
				}
			}
			
		}
		
		if(assetId!=null){
			Asset asset  = assetRepo.findOne(assetId);
			if(StringUtils.isNotBlank(assetType) && assetType.equalsIgnoreCase("IMG")){
				boolean isImgFileDeleted=false;
				if(asset.getImagePath()!=null){
					String fileUploadLocation = environment.getProperty("file.upload.location");
					String fileDownloadLocation = environment.getProperty("file.download.location");
					File uploadDirectory = new File(fileUploadLocation +"\\"+asset.getImagePath());
					File downloadDirectory = new File(fileDownloadLocation +"\\"+asset.getImagePath());
					if(uploadDirectory.exists()){
						if(uploadDirectory.delete()){
							isImgFileDeleted =true;
							response.setStatusCode(200);
							LOGGER.info("Deleted successfully from "+uploadDirectory.getPath());
							response = deleteFileFromS3(asset.getImagePath(), response);
							LOGGER.info("Deleted successfully from S3");
							isImgFileDeleted=true;
						}
					}else{
						response = deleteFileFromS3(asset.getImagePath(), response);
						LOGGER.info("Deleted successfully from S3");
						isImgFileDeleted=true;
						response.setStatusCode(200);
					}
					if(downloadDirectory.exists()){
						if(downloadDirectory.delete()){
							LOGGER.info("Deleted successfully from "+downloadDirectory.getPath());
							isImgFileDeleted=true;
							response.setStatusCode(200);
						}
					}
					if(isImgFileDeleted){
						LOGGER.info("Updating asset Image path to null.");
						if(response.getStatusCode() == 200){
							asset.setImagePath(null);
							LOGGER.info("Asset image path updated successfully");
						}
					}
				}
			}
			if(StringUtils.isNotBlank(assetType) && assetType.equalsIgnoreCase("DOC")){
				if(asset.getDocumentPath()!=null){
					boolean isDocFileDeleted=false;
					LOGGER.info("Deleting attachment document for asset  : "+asset.getAssetName());
					String fileUploadLocation = environment.getProperty("file.upload.location");
					File uploadDirectory = new File(fileUploadLocation +"\\"+asset.getDocumentPath());
					if(uploadDirectory.exists()){
						if(uploadDirectory.delete()){
							isDocFileDeleted =true;
							response.setStatusCode(200);
							LOGGER.info("Deleted successfully from "+uploadDirectory.getPath());
						}else{
							response = deleteFileFromS3(asset.getDocumentPath(), response);
							LOGGER.info("Deleted successfully from S3");
							isDocFileDeleted=true;
						}
					}else{
						response = deleteFileFromS3(asset.getDocumentPath(), response);
						LOGGER.info("Deleted successfully from S3");
						isDocFileDeleted=true;
					}
					if(response.getStatusCode()==200 && isDocFileDeleted){
						LOGGER.info("Updating asset Doc path to null.");
						if(response.getStatusCode() == 200){
							asset.setDocumentPath(null);
							LOGGER.info("Asset document path updated successfully");
						}
					}
				}
			}
			if(response.getStatusCode() == 200){
				asset = assetRepo.save(asset);
				//response.setObject(asset);
				response.setStatusCode(200);
			}
		}
		
		if(incidentList!=null && !incidentList.isEmpty()){
			List<TicketAttachment> ticketAttachmentList = ticketAttachmentRepo.findByAttachmentIdIn(incidentList);
			boolean isFileDeleted=false;
			if(!ticketAttachmentList.isEmpty()){
				List<KeyVersion> keys = new ArrayList<KeyVersion>();
				for(TicketAttachment attachment :ticketAttachmentList){
					LOGGER.info("Deleting incident attachment file for incident  : "+attachment.getTicketNumber());
					TicketAttachment tempAttachment = attachment;
					keys.add(new KeyVersion(tempAttachment.getAttachmentPath()));
					//ticketAttachmentRepo.delete(tempAttachment.getAttachmentId());
				}
				String fileUploadLocation = environment.getProperty("file.upload.location");
				File uploadDirectory = new File(fileUploadLocation +"\\"+ticketAttachmentList.get(0).getAttachmentPath());
				if(uploadDirectory.exists()){
					if(uploadDirectory.delete()){
						isFileDeleted =true;
						response.setStatusCode(200);
						LOGGER.info("Deleted successfully from "+uploadDirectory.getPath());
					}else{
						response = deleteFileFromS3(ticketAttachmentList.get(0).getAttachmentPath(), response);
						LOGGER.info("Deleted successfully from S3");
						isFileDeleted=true;
					}
				}else{
					response = deleteFileFromS3(ticketAttachmentList.get(0).getAttachmentPath(), response);
					LOGGER.info("Deleted successfully from S3");
					isFileDeleted=true;
				}
				if(response.getStatusCode()==200 && isFileDeleted){
					response= awsIntegrationService.deleteMultipleFile(keys);
					if(response.getStatusCode()==200){
						LOGGER.info("Updating incident image path");
						for(TicketAttachment attachment :ticketAttachmentList){
							TicketAttachment tempAttachment = attachment;
							ticketAttachmentRepo.delete(tempAttachment.getAttachmentId());
						}
						response.setStatusCode(200);
					}else{
						response.setStatusCode(404);
					}
				}
			}
		}
		
		
		LOGGER.info("Exit FileIntegrationServiceImpl .. deleteFile");
		return response;
	}



	private RestResponse deleteFileFromS3(String keyName, RestResponse response) throws Exception {
		try{
			response = awsIntegrationService.deleteFile("malay-first-s3-bucket-pms-test", keyName);
			if(response.getStatusCode()==200){
				response.setStatusCode(200);
			}
		}catch(IOException e){
			response.setStatusCode(500);
			//LOGGER.info("Exception while deleting attachment", e);
		}
		return response;
	}
	

}
