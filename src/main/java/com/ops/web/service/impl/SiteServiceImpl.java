package com.ops.web.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ops.app.constants.QueryConstants;
import com.ops.app.exception.RequiredFieldException;
import com.ops.app.exception.Validator;
import com.ops.app.util.RestResponse;
import com.ops.app.vo.CreateSiteVO;
import com.ops.app.vo.LoginUser;
import com.ops.app.vo.SiteContactVO;
import com.ops.app.vo.SiteDeliveryVO;
import com.ops.app.vo.SiteInfoVO;
import com.ops.app.vo.SiteLicenceVO;
import com.ops.app.vo.SiteOperationVO;
import com.ops.app.vo.SiteSubmeterVO;
import com.ops.app.vo.SiteVO;
import com.ops.app.vo.UploadFile;
import com.ops.jpa.entities.Area;
import com.ops.jpa.entities.Cluster;
import com.ops.jpa.entities.Company;
import com.ops.jpa.entities.District;
import com.ops.jpa.entities.Site;
import com.ops.jpa.entities.SiteDeliveryOperation;
import com.ops.jpa.entities.SiteLicence;
import com.ops.jpa.entities.SiteSalesOperation;
import com.ops.jpa.entities.SiteSubMeter;
import com.ops.jpa.entities.User;
import com.ops.jpa.entities.UserSiteAccess;
import com.ops.jpa.repository.AreaRepo;
import com.ops.jpa.repository.ClusterRepo;
import com.ops.jpa.repository.CompanyRepo;
import com.ops.jpa.repository.CountryRepo;
import com.ops.jpa.repository.DistrictRepo;
import com.ops.jpa.repository.LicenseRepo;
import com.ops.jpa.repository.SiteDeliveryRepo;
import com.ops.jpa.repository.SiteRepo;
import com.ops.jpa.repository.SiteSalesOperationRepo;
import com.ops.jpa.repository.SiteSubmeterRepo;
import com.ops.jpa.repository.UserDAO;
import com.ops.jpa.repository.UserSiteAccessRepo;
import com.ops.web.service.AwsIntegrationService;
import com.ops.web.service.FileIntegrationService;
import com.ops.web.service.SiteService;



@Service("siteService")
public class SiteServiceImpl implements SiteService{

	private final static Logger LOGGER = LoggerFactory.getLogger(SiteServiceImpl.class);
	private static final String SUFFIX = "/";
	@Autowired
	private SiteRepo siteRepo;

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private CountryRepo countryRepo;

	@Autowired
	private LicenseRepo licenseRepo;

	@Autowired
	private ClusterRepo clusterRepo;

	@Autowired
	private DistrictRepo districtRepo;

	@Autowired
	private AreaRepo areaRepo;

	@Autowired
	private SiteDeliveryRepo siteDeliveryRepo;

	@Autowired
	private SiteSalesOperationRepo siteSalesOperationRepo;

	@Autowired
	private SiteSubmeterRepo siteSubMeterRepo;

	@Autowired
	private UserSiteAccessRepo userSiteAccessRepo;

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private AwsIntegrationService awsIntegrationService;
	
	@Autowired
	private FileIntegrationService fileIntegrationService;

	@Autowired
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public List<CreateSiteVO> getSiteList(LoginUser user) throws Exception {
		Set<Site> siteList= null ;
		List<Cluster> clusterList = clusterRepo.findAll();
		List<District> districtList = districtRepo.findAll();
		List<Area> areaList = areaRepo.findAll();
		List<CreateSiteVO> siteVOList=new ArrayList<CreateSiteVO>();
		List<UserSiteAccess> userSiteAccessList = userSiteAccessRepo.findSiteAssignedFor(user.getUserId());

		LOGGER.info("Site List for User : " + userSiteAccessList.size());
		if(!userSiteAccessList.isEmpty()){
			siteList = new HashSet<Site>( userSiteAccessList.size());

			for(UserSiteAccess userSite : userSiteAccessList){
				Site site = siteRepo.findOne(userSite.getSite().getSiteId());
				site.getSiteLicences().clear();
				List<SiteLicence> licenseList = licenseRepo.findBySiteSiteId(site.getSiteId());
				site.setSiteLicences(licenseList);


				List<SiteSalesOperation> salesOpsList = siteSalesOperationRepo.findBySiteSiteId(site.getSiteId());
				site.setSiteSalesOpetaionTimes(salesOpsList);

				List<SiteDeliveryOperation> deliveryOpsList = siteDeliveryRepo.findBySiteSiteId(site.getSiteId());
				site.setSiteDeliveryOpetaionTimes(deliveryOpsList);


				List<SiteSubMeter> subMeterList = siteSubMeterRepo.findBySiteSiteId(site.getSiteId());
				site.setSiteSubmeterList(subMeterList);
				siteList.add(site);

			}
		}


		if(siteList!=null &&  !siteList.isEmpty()){
			List<String> fullAddress = new ArrayList<String>();
			for(Site site:siteList){
				CreateSiteVO siteVO=new CreateSiteVO();
				List<SiteLicenceVO> siteLicensesVO =  siteVO.getSiteLicense();
				siteVO.setSiteId(site.getSiteId());
				siteVO.setSiteName(site.getSiteName());
				siteVO.setSiteAddress1(site.getSiteAddress1());
				siteVO.setSiteAddress2(site.getSiteAddress2());
				siteVO.setSiteAddress3(site.getSiteAddress3());
				siteVO.setSiteAddress4(site.getSiteAddress4());
				siteVO.setZipCode(site.getPostCode());
				
				if(!StringUtils.isEmpty(siteVO.getSiteAddress1())){
					fullAddress.add(siteVO.getSiteAddress1());
				}
				if(!StringUtils.isEmpty(siteVO.getSiteAddress2())){
					fullAddress.add(siteVO.getSiteAddress2());
				}
				if(!StringUtils.isEmpty(siteVO.getSiteAddress3())){
					fullAddress.add(siteVO.getSiteAddress3());
				}
				if(!StringUtils.isEmpty(siteVO.getSiteAddress4())){
					fullAddress.add(siteVO.getSiteAddress4());
				}
				if(!StringUtils.isEmpty(siteVO.getZipCode())){
					fullAddress.add(siteVO.getZipCode());
				}
				if(site.getSalesAreaSize()!=null){
					siteVO.setSalesAreaSize(site.getSalesAreaSize().toString());
				}
				
				String finalAddress = org.apache.commons.lang3.StringUtils.join(fullAddress,",");
				siteVO.setFullAddress(finalAddress);
				
				siteVO.getOperator().setCompanyId(site.getOperator().getCompanyId());
				siteVO.getOperator().setCompanyName(site.getOperator().getCompanyName());
				
				siteVO.setOwner(site.getSiteOwner());


				if(site.getClusterId() !=null){
					for(Cluster cluster : clusterList){
						if(site.getClusterId().equals(cluster.getClusterID())){
							siteVO.setCluster(cluster);
							break;
						}
					}
				}
				if(site.getDistrictId() !=null){
					for(District district : districtList){
						if(site.getDistrictId().equals(district.getDistrictId())){
							siteVO.setDistrict(district);
							break;
						}
					}
				}
				if(site.getAreaId() !=null){
					for(Area area : areaList){
						if(site.getAreaId().equals(area.getAreaId())){
							siteVO.setArea(area);
							break;
						}
					}
				}

				siteVO.setContactName(site.getAreaManagerName());
				siteVO.setEmail(site.getEmail());
				siteVO.setElectricityId(site.getElectricIdNo());
				if(site.getLatitude()!=null) {
					siteVO.setLatitude(site.getLatitude().toString());
				}
				if(site.getLongitude() != null){
					siteVO.setLongitude(site.getLongitude().toString());
				}

				if(site.getSiteNumberOne() ==  null ) {

				}else{
					siteVO.setSiteNumber1(site.getSiteNumberOne().toString());
				}

				if(site.getSiteNumberTwo()==null ) {

				}else{
					siteVO.setSiteNumber2(site.getSiteNumberTwo().toString());
				}


				if(StringUtils.isEmpty(site.getPrimaryContact())){

				}
				else{
					siteVO.setPrimaryContact(site.getPrimaryContact().toString());
				}
				if(StringUtils.isEmpty(site.getSecondaryContact())){

				}else{
					siteVO.setSecondaryContact(site.getSecondaryContact().toString());
				}


				if(!site.getSiteLicences().isEmpty()){
					for(SiteLicence siteLicence : site.getSiteLicences()){
						SiteLicenceVO siteLicenceVO = new SiteLicenceVO();
						siteLicenceVO.setLicenseId(siteLicence.getLicenseId());
						siteLicenceVO.setLicenseName(siteLicence.getLicenseName());

						SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						if(siteLicence.getStartDate()!=null && siteLicence.getEndDate()!=null){
							Date licenseValidFrom =  siteLicence.getStartDate();
							Date licenseValidTo  = siteLicence.getEndDate();
							String startDate = formatter.format(licenseValidFrom);
							String endDate = formatter.format(licenseValidTo);
							siteLicenceVO.setValidfrom(startDate);
							siteLicenceVO.setValidto(endDate);
							siteLicensesVO.add(siteLicenceVO);
						}
						siteVO.setSiteLicense(siteLicensesVO);

					}
				}
				//siteVO.getSiteOperation().clear();
				for(SiteSalesOperation siteSalesOperation : site.getSiteSalesOpetaionTimes()){
					SiteOperationVO siteOperationVO = new SiteOperationVO();
					siteOperationVO.setOpId(siteSalesOperation.getSaledsOpId());
					siteOperationVO.setDays(siteSalesOperation.getDayOfWeek());
					siteOperationVO.setFrom(siteSalesOperation.getOpStartTime());
					siteOperationVO.setTo(siteSalesOperation.getOpCloseTime());
					siteVO.getSiteOperation().add(siteOperationVO);
				}

				//siteVO.getSiteDelivery().clear();
				for(SiteDeliveryOperation siteDeliveryOperation : site.getSiteDeliveryOpetaionTimes()){
					SiteDeliveryVO siteDeliveryVO = new SiteDeliveryVO();
					siteDeliveryVO.setOpId(siteDeliveryOperation.getDeliveryOpId());
					siteDeliveryVO.setDays(siteDeliveryOperation.getDayOfWeek());
					siteDeliveryVO.setFrom(siteDeliveryOperation.getOpStartTime());
					siteDeliveryVO.setTo(siteDeliveryOperation.getOpCloseTime());
					siteVO.getSiteDelivery().add(siteDeliveryVO);
				}
				if(!site.getSiteSubmeterList().isEmpty()){
					for(SiteSubMeter siteSubmeter: site.getSiteSubmeterList()){
						SiteSubmeterVO siteSubmeterVO = new SiteSubmeterVO();
						siteSubmeterVO.setSubMeterId(siteSubmeter.getSubMeterId());
						siteSubmeterVO.setSubMeterNumber(siteSubmeter.getSubMeterNumber());
						siteSubmeterVO.setSubMeterUser(siteSubmeter.getSubMeterUser());
						siteVO.getSiteSubmeter().add(siteSubmeterVO);
					}
				}
				siteVOList.add(siteVO);
				fullAddress.clear();
			}
		}
		return siteVOList == null?Collections.EMPTY_LIST:siteVOList;
	}

	@Deprecated
	public SiteVO saveOrUpdateFromRest(final SiteVO siteVO) throws Exception {
		LOGGER.info("Inside SiteServiceImpl - saveOrUpdate");
		Site savedSite = null;
		if(!StringUtils.isEmpty(siteVO.getSiteName()) && siteVO.getSiteId()==null){
			Site site = new Site();
			BeanUtils.copyProperties(siteVO, site);
			LOGGER.info("site : " + site);
			site.setCreatedDate(new Date());
			savedSite= siteRepo.save(site);
			BeanUtils.copyProperties(savedSite, siteVO);
		}
		LOGGER.info("Exit SiteServiceImpl - saveOrUpdate");
		return siteVO;
	}
	@Override
	public CreateSiteVO updateSite(CreateSiteVO siteVO, LoginUser loginUser) throws Exception {
		LOGGER.info("Inside SiteServiceImpl - updateSite");
		CreateSiteVO savedSiteVO = new CreateSiteVO();
		if(siteVO.getSiteId() != null){
			Site savedSite = siteRepo.findOne(siteVO.getSiteId());
			savedSite.setModifiedBy(loginUser.getUsername());
			Company company = loginUser.getCompany();
			savedSite.setOperator(company);
			try{
				savedSite=populateSitePrimaryData(savedSite,siteVO, company);
				savedSite.setOperator(company);
				savedSite = siteRepo.save(savedSite);
				if(savedSite.getVersion()>0){
					LOGGER.info("Site information updated successfully");
					savedSiteVO.setSiteId(savedSite.getSiteId());
					savedSiteVO.setSiteName(savedSite.getSiteName());
				}
			}catch(Exception e){
				LOGGER.info("Exception while updating site information :");
				e.printStackTrace();
			}
		}
		LOGGER.info("Exit SiteServiceImpl - updateSite");
		return savedSiteVO;
	}
	
	@Override
	public CreateSiteVO updateSiteContact(CreateSiteVO siteVO, LoginUser loginUser) throws Exception {
		LOGGER.info("Inside SiteServiceImpl - updateSite");
		CreateSiteVO savedSiteVO = new CreateSiteVO();
		if(siteVO.getSiteId() != null){
			Site savedSite = siteRepo.findOne(siteVO.getSiteId());
			savedSite.setModifiedBy(loginUser.getUsername());
			Company company = loginUser.getCompany();
			savedSite.setOperator(company);
			try{
				savedSite=populateSiteContact(savedSite,siteVO);
				savedSite = siteRepo.save(savedSite);
				if(savedSite.getVersion()>0){
					LOGGER.info("Site Contact information updated successfully");
					savedSiteVO.setSiteId(savedSite.getSiteId());
					savedSiteVO.setSiteName(savedSite.getSiteName());
				}
			}catch(Exception e){
				LOGGER.info("Exception while updating site information :");
				e.printStackTrace();
			}
		}
		LOGGER.info("Exit SiteServiceImpl - updateSite");
		return savedSiteVO;
	}

	@Override
	public SiteLicenceVO updateSiteLicense(Long siteId, SiteLicenceVO siteLicenseVO) throws Exception {
		LOGGER.info("Inside SiteServiceImpl - updateSiteLicense");
		Site savedSite = siteRepo.findOne(siteId);
		SiteLicence siteLicence = null;
		if(siteLicenseVO.getLicenseId() != null){
			siteLicence = licenseRepo.findOne(siteLicenseVO.getLicenseId());
		}else {
			 siteLicence = new SiteLicence();
		}
			siteLicence.setLicenceName(siteLicenseVO.getLicenseName());
			siteLicence.setSite(savedSite);
			String licenceFromData = siteLicenseVO.getValidfrom();
			String licenceToData = siteLicenseVO.getValidto();

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(!StringUtils.isEmpty(licenceFromData) && !StringUtils.isEmpty(licenceToData)){
				Date licenseValidFrom;
				Date licenseValidTo;
				try {
					licenseValidFrom = formatter.parse(licenceFromData);
					licenseValidTo = formatter.parse(licenceToData);
					siteLicence.setStartDate(licenseValidFrom);
					siteLicence.setEndDate(licenseValidTo);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				siteLicence =  licenseRepo.save(siteLicence);
				if(siteLicence.getLicenseId()!=null){
					LOGGER.info("Site license updated successfully.");
					siteLicenseVO.setLicenseId(siteLicence.getLicenseId());
				}
				
			
		}
		LOGGER.info("Exit SiteServiceImpl - updateSiteLicense");
		return siteLicenseVO;
	}

	
	
	@Override
	@Transactional
	public CreateSiteVO saveOrUpdate(final CreateSiteVO siteVO, LoginUser user) throws Exception {
		LOGGER.info("Inside SiteServiceImpl - saveOrUpdate");
		Site savedSite = null;

		CreateSiteVO savedSiteVO = new CreateSiteVO();
		savedSiteVO = validateObject(savedSite, siteVO);

		if(savedSiteVO.getStatus() == 200){
			if(siteVO.getSiteId() == null){
				savedSite = new Site();
				siteVO.setCreatedBy(user.getUsername());
			}else{
				savedSite = siteRepo.findOne(siteVO.getSiteId());
				savedSite.setModifiedBy(user.getUsername());
			}
			
			try{
				savedSite=populateSitePrimaryData(savedSite,siteVO, user.getCompany());
				savedSite=populateSiteContact(savedSite,siteVO);
				savedSite=populateLicenseDetails(savedSite,siteVO, user.getCompany());
				savedSite=populateOperationDetails(savedSite,siteVO);
				Company company = user.getCompany();
				savedSite.setOperator(company);
				LOGGER.info("Getting Sales and Operations timings");
				if(savedSite.getSiteSalesOpetaionTimes().size()==7 && savedSite.getSiteDeliveryOpetaionTimes().size()==7) {
					savedSite=populateSubmeterDetails(savedSite,siteVO);
					savedSite.setCreatedBy(siteVO.getCreatedBy());
					savedSite = siteRepo.save(savedSite);
					if(savedSite.getSiteId() != null ){
						LOGGER.info("Site information saved successfully");
						savedSiteVO.setSiteId(savedSite.getSiteId());
						savedSiteVO.setSiteName(savedSite.getSiteName());
						LOGGER.info("Saving User and Site data to User site Access table");
						UserSiteAccess userSiteAccess = userSiteAccessRepo.findAccessDetails(user.getUserId(), savedSite.getSiteId());
						if(userSiteAccess==null){
							userSiteAccess = new UserSiteAccess();
							User siteUser = userDAO.findOne(user.getUserId());
							userSiteAccess.setSite(savedSite);
							userSiteAccess.setUser(siteUser);
							userSiteAccess = userSiteAccessRepo.save(userSiteAccess);
							if(userSiteAccess.getAccessId()!=null){
								LOGGER.info("Site mapped to user successfully");
								savedSiteVO.setStatus(201);
								/*if(org.apache.commons.lang.StringUtils.isNotBlank(siteVO.getFileInput()) && org.apache.commons.lang.StringUtils.isNotBlank(siteVO.getFileExtension())){
									uploadSiteImage(savedSiteVO, siteVO.getFileLocation(), siteVO.getFileExtension());
									//uploadSiteImageToAwsS3(savedSiteVO, siteVO.getFileInput(), siteVO.getFileExtension());
								}*/
								
							}
						}else{
							LOGGER.info("Site ID already mapped to user in  User site Access table");
							savedSiteVO.setStatus(202);
							/*if(org.apache.commons.lang.StringUtils.isNotBlank(siteVO.getFileInput()) && org.apache.commons.lang.StringUtils.isNotBlank(siteVO.getFileExtension())){
								uploadSiteImage(savedSiteVO, siteVO.getFileInput(), siteVO.getFileExtension());
								//uploadSiteImageToAwsS3(savedSiteVO, siteVO.getFileInput(), siteVO.getFileExtension());
							}*/
							
							
					}

				}else{
					LOGGER.info("Site Operation details not entered");
				}
					List<SiteLicence> licenseList = savedSite.getSiteLicences();
					if(!licenseList.isEmpty()){
						for(SiteLicence license:licenseList){
							SiteLicence savedLicense = license;
							String licenseLocation=null;
							for(UploadFile attachment : siteVO.getLicenseAttachments()){
								if(attachment.getFileName().equalsIgnoreCase(license.getLicenseName())){
									licenseLocation = uploadSiteLicenseImage(attachment, company );
									savedLicense.setAttachmentPath(licenseLocation);
									licenseRepo.save(savedLicense);
									break;
								}
							  }
							}
					}else{
						
					}
				}
				
			}
			catch(Exception e){
				LOGGER.info("Exception while populating site information :", e);
				savedSiteVO.setValidationMessage("Exception occured. Please verify all the tab information.");
			}
			
			

		}

		LOGGER.info("Exit SiteServiceImpl - saveOrUpdate");
		return savedSiteVO;
	}


	private String uploadSiteLicenseImage( UploadFile attachment, Company userCompany) {
		LOGGER.info("Inside SiteServiceImpl .. uploadSiteLicenseImage");
		String destinationFile=null;
		try {
			destinationFile = fileIntegrationService.siteLicenseFileUpload(attachment, userCompany);
		} catch (IOException e) {
			LOGGER.info("Exception while uploading license file ", e );
		}
		
		LOGGER.info("Exit SiteServiceImpl .. uploadSiteLicenseImage");
		return destinationFile.toString();
	}

	private void uploadSiteImageToAwsS3(CreateSiteVO savedSiteVO, String fileInput, String fileExtension) {
		LOGGER.info("Inside SiteServiceImpl .. uploadSiteImageToAwsS3");
		AWSCredentials credentials = new BasicAWSCredentials("AKIAICD42CCYTOXBJDOA","fwKFXHtteCVnKt3bxaj6muPNs55ZlI3BvKw70Zp/");
		String uploadLocation = "D:\\TechM\\SM351137\\GIT\\chris-app\\pms-product\\uploads\\";
		@SuppressWarnings("deprecation")
		AmazonS3 s3client = new AmazonS3Client(credentials);
		s3client.setRegion(com.amazonaws.regions.Region.getRegion(Regions.US_WEST_2));
		String bucketName="malay-first-s3-bucket-pms-test";
		//s3client.createBucket("malay-first-s3-bucket-pms-test");
		for (Bucket bucket : s3client.listBuckets()) {
		System.out.println(" - " + bucket.getName());
		
		}

		// create folder into bucket
		String folderName = "cadentive";
		awsIntegrationService.createFolder(bucketName, folderName, s3client);
		// upload file to folder and set it to public
		String fileName = folderName + SUFFIX + savedSiteVO.getSiteName()+"."+fileExtension.toLowerCase();
		awsIntegrationService.uploadObject(new PutObjectRequest(bucketName, fileName, new File(uploadLocation+"\\"+savedSiteVO.getSiteName()+"."+fileExtension.toLowerCase())).withCannedAcl(CannedAccessControlList.Private), s3client);

		//awsIntegrationService.deleteFolder(bucketName, folderName, s3client);

		// deletes bucket
		//s3client.deleteBucket(bucketName);
		
		LOGGER.info("Exit SiteServiceImpl .. uploadSiteImageToAwsS3");
	}

	private CreateSiteVO uploadSiteImage(Company company,CreateSiteVO savedSiteVO, String fileInput, String fileExtension) {
		LOGGER.info("Inside SiteServiceImpl .. uploadSiteImage");
		UploadFile uploadFile = new UploadFile();
		uploadFile.setBase64ImageString(fileInput);
		uploadFile.setFileExtension(fileExtension);
		uploadFile.setFileName(savedSiteVO.getSiteName());
		String fileLocation=null;
		try {
			fileLocation = fileIntegrationService.siteFileUpload(savedSiteVO, uploadFile, company);
			savedSiteVO.setFileLocation(fileLocation);
		} catch (IOException e) {
			LOGGER.info("Error while uploading site image file", e);
		}
		LOGGER.info("Exit SiteServiceImpl .. uploadSiteImage");
		return savedSiteVO;
		
	}

	

	private CreateSiteVO validateObject(Site savedSite, CreateSiteVO siteVO) {
		try {
			if (Validator.validateForNulls(siteVO)) {
				// Do something that you want to
				LOGGER.info("Validations Successful");
				siteVO.setStatus(200);
			}
		} catch (RequiredFieldException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
			LOGGER.info("Validation error for site object", e);
			siteVO.setStatus(500);
			siteVO.setValidationMessage("Please enter the required field.");
		}
		return siteVO;
	}

	// Site Primary Information - UI TAB 1
	private Site populateSitePrimaryData(Site site, CreateSiteVO siteVO, Company company) {
		LOGGER.info("Inside SiteServiceImpl - populateSitePrimaryData");
		List<String> siteAttachments = new ArrayList<String>();
		if(org.apache.commons.lang.StringUtils.isNotBlank(siteVO.getSiteName())){
			site.setSiteName(siteVO.getSiteName());
		}

		if(org.apache.commons.lang.StringUtils.isNotBlank(siteVO.getOwner())){
			site.setSiteOwner(siteVO.getOwner());
		}

		if(siteVO.getDistrict()!=null){
			LOGGER.info("District Selected" + siteVO.getDistrict().getDistrictName());
			site.setDistrictId(siteVO.getDistrict().getDistrictId());
		}
		if(siteVO.getArea()!=null){
			LOGGER.info("Area Selected" + siteVO.getArea().getAreaName());
			site.setAreaId(siteVO.getArea().getAreaId());
		}
		if(siteVO.getCluster()!=null){
			LOGGER.info("Cluster Selected" + siteVO.getArea().getAreaName());
			site.setClusterId(siteVO.getCluster().getClusterID());
		}

		site.setElectricIdNo(siteVO.getElectricityId());

		if(org.apache.commons.lang.StringUtils.isNotBlank(siteVO.getSiteNumber1())){
			site.setSiteNumberOne(Long.parseLong(siteVO.getSiteNumber1()));
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(siteVO.getSiteNumber2())){
			site.setSiteNumberTwo(Long.parseLong(siteVO.getSiteNumber2()));
		}

		if(org.apache.commons.lang3.StringUtils.isNotBlank(siteVO.getSalesAreaSize())){
			BigDecimal siteSalesArea = new BigDecimal(siteVO.getSalesAreaSize());
			site.setSalesAreaSize(siteSalesArea);
		}
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(siteVO.getFileInput()) && org.apache.commons.lang.StringUtils.isNotBlank(siteVO.getFileExtension())){
				try {
					RestResponse response = fileIntegrationService.deleteFile(site.getSiteId(), null, null, null, null);
					if(response.getStatusCode() == 0){
						siteVO = uploadSiteImage(company,siteVO, siteVO.getFileInput(), siteVO.getFileExtension());
						//uploadSiteImageToAwsS3(savedSiteVO, siteVO.getFileInput(), siteVO.getFileExtension());
						if(!StringUtils.isEmpty(siteVO.getFileLocation())){
							site.setAttachmentPath(siteVO.getFileLocation());
						}
					}else if( response.getStatusCode()==200){
						siteVO = uploadSiteImage(company,siteVO, siteVO.getFileInput(), siteVO.getFileExtension());
						//uploadSiteImageToAwsS3(savedSiteVO, siteVO.getFileInput(), siteVO.getFileExtension());
						if(!StringUtils.isEmpty(siteVO.getFileLocation())){
							site.setAttachmentPath(siteVO.getFileLocation());
						}
					}
					else if( response.getStatusCode()==500){
						siteVO = uploadSiteImage(company,siteVO, siteVO.getFileInput(), siteVO.getFileExtension());
						//uploadSiteImageToAwsS3(savedSiteVO, siteVO.getFileInput(), siteVO.getFileExtension());
						if(!StringUtils.isEmpty(siteVO.getFileLocation())){
							site.setAttachmentPath(siteVO.getFileLocation());
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					LOGGER.info("Site Image populateSitePrimaryData .. Exception while deleting attachment");
				}
			
		/*	if(StringUtils.isEmpty(site.getAttachmentPath())){
				attachment = siteVO.getFileLocation();
				siteAttachments.add(attachment);
			}else{
				String[] siteFiles = site.getAttachmentPath().split(",");
				for(String file:siteFiles){
					siteAttachments.add(file);
				}
				siteAttachments.add(siteVO.getFileLocation());
			}
			String finalSiteFileList = org.apache.commons.lang3.StringUtils.join(siteAttachments, ',');*/
			
		}
		LOGGER.info("Exit SiteServiceImpl - populateSitePrimaryData");
		return site;
	}


	// Site Contact Information - UI TAB 2
	private Site populateSiteContact(Site site, final CreateSiteVO siteVO) {
		LOGGER.info("Inside SiteServiceImpl - populateSiteContact");

		if(org.apache.commons.lang.StringUtils.isNotBlank(siteVO.getContactName())){
			site.setAreaManagerName(siteVO.getContactName());
		}

		if(org.apache.commons.lang.StringUtils.isNotBlank(siteVO.getEmail())){
			site.setEmail(siteVO.getEmail());
		}

		site.setLatitude(siteVO.getLatitude());
		site.setLongitude(siteVO.getLongitude());

		if(org.apache.commons.lang.StringUtils.isNotBlank(siteVO.getPrimaryContact())){
			site.setPrimaryContact(Long.parseLong(siteVO.getPrimaryContact()));
		}

		if(org.apache.commons.lang.StringUtils.isNotBlank(siteVO.getSecondaryContact())){
			site.setSecondaryContact(Long.parseLong(siteVO.getSecondaryContact()));
		}
		site.setSiteAddress1(siteVO.getSiteAddress1());
		site.setSiteAddress2(siteVO.getSiteAddress2());
		site.setSiteAddress3(siteVO.getSiteAddress3());
		site.setSiteAddress4(siteVO.getSiteAddress4());
		site.setPostCode(siteVO.getZipCode());
		
		LOGGER.info("Exit SiteServiceImpl - populateSiteContact");
		return site;
	}

	// Site License Information - UI TAB 3
	private Site populateLicenseDetails(Site site, CreateSiteVO siteVO, Company company) {
		LOGGER.info("Inside SiteServiceImpl - populateLicenseDetails");
		List<SiteLicenceVO> licenseVOList= siteVO.getSiteLicense();
		List<SiteLicence> licenseList= null; 
		if(siteVO.getSiteId()== null){
			licenseList = new ArrayList<SiteLicence>();
		}else if(siteVO.getSiteId()!=null){
			//licenseList = new ArrayList<SiteLicence>();
			
			
			/*for(SiteLicenceVO siteLicenceVO : licenseVOList){
				if(siteLicenceVO.getLicenseId()!=null){
					licenseRepo.delete(siteLicenceVO.getLicenseId());
				}
			}*/
			licenseList = licenseRepo.findBySiteSiteId(siteVO.getSiteId());
			
		}
		
		for(SiteLicenceVO siteLicenceVO : licenseVOList){
			SiteLicence siteLicense = null;
			if(siteVO.getSiteId()== null){
					siteLicense = new SiteLicence();
			}else if(siteVO.getSiteId()!= null){
				if(licenseList.isEmpty()){
					siteLicense = new SiteLicence();
				}else{
					if(siteLicenceVO.getLicenseId()!=null){
						for(SiteLicence licence:licenseList){
							if(licence.getLicenseId().equals(siteLicenceVO.getLicenseId())){
								siteLicense=licence;
								break;
							}
						}
					}else{
						siteLicense = new SiteLicence();
					}
				}
			}
			String licenseLocation=null;
			siteLicense.setLicenceName(siteLicenceVO.getLicenseName());
			String licenceFromData = siteLicenceVO.getValidfrom();
			String licenceToData = siteLicenceVO.getValidto();

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(!StringUtils.isEmpty(licenceFromData) && !StringUtils.isEmpty(licenceToData)){
				Date licenseValidFrom;
				Date licenseValidTo;
				try {
					licenseValidFrom = formatter.parse(licenceFromData);
					licenseValidTo = formatter.parse(licenceToData);
					siteLicense.setStartDate(licenseValidFrom);
					siteLicense.setEndDate(licenseValidTo);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				/*if(!siteVO.getLicenseAttachments().isEmpty()){
					uploadSiteLicenseImage(siteVO.getLicenseAttachments(), company);
				}*/
				
			}
			
		/*	if(!siteVO.getLicenseAttachments().isEmpty()){
				for(UploadFile attachment : siteVO.getLicenseAttachments()){
					if(attachment.getLicenseId()!=null){
						if(attachment.getFileName().equalsIgnoreCase(siteLicenceVO.getLicenseName())){
							licenseLocation=uploadSiteLicenseImage(attachment, company );
							if(!StringUtils.isEmpty(licenseLocation)){
								siteLicense.setAttachmentPath(licenseLocation);
							}
							break;
						}
					}else{
						if(attachment.getFileName().equalsIgnoreCase(siteLicenceVO.getLicenseName())){
							licenseLocation=uploadSiteLicenseImage(attachment, company );
							if(!StringUtils.isEmpty(licenseLocation)){
								siteLicense.setAttachmentPath(licenseLocation);
							}
						}
						break;
					}
				}
				
			}*/
			siteLicense.setCreatedBy(siteVO.getCreatedBy());
			siteLicense.setSite(site);
			licenseList.add(siteLicense);

		}

		site.setSiteLicences(licenseList);
		LOGGER.info("Exit SiteServiceImpl - populateLicenseDetails");
		return site;
	}

	// Site Operation Information - UI TAB 4
	private Site populateOperationDetails(Site site, CreateSiteVO siteVO) {
		LOGGER.info("Inside SiteServiceImpl - populateOperationDetails");

		List<SiteOperationVO> siteOperationVOList =  siteVO.getSiteOperation();
		List<SiteDeliveryVO> siteDeliveryVOList =  siteVO.getSiteDelivery();

		List<SiteSalesOperation> salesOperationList = new ArrayList<SiteSalesOperation>();
		List<SiteDeliveryOperation> deliveryOperationList = new ArrayList<SiteDeliveryOperation>();

		if(siteVO.getSiteId() == null){
			LOGGER.info("Adding new Operation Details");
			for(SiteOperationVO siteSalesOpsVO : siteOperationVOList){
				SiteSalesOperation siteSalesOperation = new SiteSalesOperation();
				siteSalesOperation.setDayOfWeek(siteSalesOpsVO.getDays());
				if(org.apache.commons.lang.StringUtils.isNotBlank(siteSalesOpsVO.getFrom()) && org.apache.commons.lang.StringUtils.isNotBlank(siteSalesOpsVO.getTo())){
					siteSalesOperation.setOpStartTime(siteSalesOpsVO.getFrom());
					siteSalesOperation.setOpCloseTime(siteSalesOpsVO.getTo());
					siteSalesOperation.setSite(site);
				}else{
					siteSalesOperation.setOpStartTime("00:00");
					siteSalesOperation.setOpCloseTime("00:00");
				}
				salesOperationList.add(siteSalesOperation);
			}

			for(SiteDeliveryVO siteDeliveryVO : siteDeliveryVOList){
				SiteDeliveryOperation siteDeliveryOperation =  new SiteDeliveryOperation();;
				siteDeliveryOperation.setDayOfWeek(siteDeliveryVO.getDays());
				if(org.apache.commons.lang.StringUtils.isNotBlank(siteDeliveryVO.getFrom()) && org.apache.commons.lang.StringUtils.isNotBlank(siteDeliveryVO.getTo())){
					siteDeliveryOperation.setOpStartTime(siteDeliveryVO.getFrom());
					siteDeliveryOperation.setOpCloseTime(siteDeliveryVO.getTo());
					siteDeliveryOperation.setSite(site);

				}else{
					siteDeliveryOperation.setOpStartTime("00:00");
					siteDeliveryOperation.setOpCloseTime("00:00");
				}
				deliveryOperationList.add(siteDeliveryOperation);
			}

		}else if(siteVO.getSiteId()!=null){
			LOGGER.info("Updating existing Operation Details");

			if(!siteOperationVOList.isEmpty()){
				salesOperationList = siteSalesOperationRepo.findBySiteSiteId(siteVO.getSiteId());
				deliveryOperationList = siteDeliveryRepo.findBySiteSiteId(siteVO.getSiteId());
				/*for(SiteOperationVO siteSalesOperationVO :  siteOperationVOList){
					siteSalesOperationRepo.delete(siteSalesOperationVO.getOpId());
				}
				for(SiteDeliveryVO siteDeliveryVO : siteDeliveryVOList){
					siteDeliveryRepo.delete(siteDeliveryVO.getOpId());
				}*/
				List<SiteSalesOperation> temSalesOperations = new ArrayList<SiteSalesOperation>();
				List<SiteDeliveryOperation> temDeliveryOperations = new ArrayList<SiteDeliveryOperation>();

				for(SiteSalesOperation selectedSalesOps:salesOperationList){
					temSalesOperations.add(selectedSalesOps);
				}
				for(SiteDeliveryOperation selectedDeliveryOps:deliveryOperationList){
					temDeliveryOperations.add(selectedDeliveryOps);
				}
				salesOperationList.clear();
				deliveryOperationList.clear();

				for(SiteSalesOperation salesOperation: temSalesOperations){
					SiteSalesOperation tempSalesOperation = new SiteSalesOperation();
					for(SiteOperationVO siteSalesOpsVO : siteOperationVOList){
						if(salesOperation.getSaledsOpId().equals(siteSalesOpsVO.getOpId())){
							tempSalesOperation.setSaledsOpId(salesOperation.getSaledsOpId());
							tempSalesOperation.setDayOfWeek(siteSalesOpsVO.getDays());
							if(org.apache.commons.lang.StringUtils.isNotBlank(siteSalesOpsVO.getFrom()) && 
									org.apache.commons.lang.StringUtils.isNotBlank(siteSalesOpsVO.getTo())){
								tempSalesOperation.setOpStartTime(siteSalesOpsVO.getFrom());
								tempSalesOperation.setOpCloseTime(siteSalesOpsVO.getTo());
								tempSalesOperation.setSite(site);
							}else{
								tempSalesOperation.setOpStartTime("00:00");
								tempSalesOperation.setOpCloseTime("00:00");
							}
							salesOperationList.add(tempSalesOperation);
						}
					}
				}


				for(SiteDeliveryOperation deliveryOperation: temDeliveryOperations){
					SiteDeliveryOperation tempDelOperation = new SiteDeliveryOperation();
					for(SiteDeliveryVO siteDeliveryVO : siteDeliveryVOList){
						if(deliveryOperation.getDeliveryOpId().equals(siteDeliveryVO.getOpId())){
							tempDelOperation.setDeliveryOpId(deliveryOperation.getDeliveryOpId());
							tempDelOperation.setDayOfWeek(siteDeliveryVO.getDays());
							if(org.apache.commons.lang.StringUtils.isNotBlank(siteDeliveryVO.getFrom()) && 
									org.apache.commons.lang.StringUtils.isNotBlank(siteDeliveryVO.getTo())){
								tempDelOperation.setOpStartTime(siteDeliveryVO.getFrom());
								tempDelOperation.setOpCloseTime(siteDeliveryVO.getTo());
								tempDelOperation.setSite(site);
							}else{
								tempDelOperation.setOpStartTime("00:00");
								tempDelOperation.setOpCloseTime("00:00");
							}
							deliveryOperationList.add(tempDelOperation);
						}
					}
				}
			}
		}

		/*	if(!siteOperationVOList.isEmpty()){
			// TO be decided for optimization later

			for(SiteOperationVO siteSalesOpsVO : siteOperationVOList){
				SiteSalesOperation siteSalesOperation = new SiteSalesOperation();
				siteSalesOperation.setDayOfWeek(siteSalesOpsVO.getDays());
				if(org.apache.commons.lang.StringUtils.isNotBlank(siteSalesOpsVO.getFrom()) && org.apache.commons.lang.StringUtils.isNotBlank(siteSalesOpsVO.getTo())){
					siteSalesOperation.setOpStartTime(siteSalesOpsVO.getFrom());
					siteSalesOperation.setOpCloseTime(siteSalesOpsVO.getTo());
					siteSalesOperation.setSite(site);
				}else{
					siteSalesOperation.setOpStartTime("NO TIME");
					siteSalesOperation.setOpCloseTime("NO TIME");
				}
				salesOperationList.add(siteSalesOperation);
			}
		}*/

		/*for(SiteDeliveryVO siteDeliveryVO : siteDeliveryVOList){
			SiteDeliveryOperation siteDeliveryOperation =  new SiteDeliveryOperation();;
			siteDeliveryOperation.setDayOfWeek(siteDeliveryVO.getDays());
			if(org.apache.commons.lang.StringUtils.isNotBlank(siteDeliveryVO.getFrom()) && org.apache.commons.lang.StringUtils.isNotBlank(siteDeliveryVO.getTo())){
				siteDeliveryOperation.setOpStartTime(siteDeliveryVO.getFrom());
				siteDeliveryOperation.setOpCloseTime(siteDeliveryVO.getTo());
				siteDeliveryOperation.setSite(site);

			}else{
				siteDeliveryOperation.setOpStartTime("NO TIME");
				siteDeliveryOperation.setOpCloseTime("NO TIME");
			}
			deliveryOperationList.add(siteDeliveryOperation);
		}*/
		if(salesOperationList.size()==7 && deliveryOperationList.size()==7){
			site.setSiteDeliveryOpetaionTimes(deliveryOperationList);
			site.setSiteSalesOpetaionTimes(salesOperationList);
		}

		LOGGER.info("Exit SiteServiceImpl - populateOperationDetails");
		return site;
	}


	// Site Submeter Information - UI TAB 5
	private Site populateSubmeterDetails(Site site, CreateSiteVO siteVO) {
		LOGGER.info("Inside SiteServiceImpl - populateSubmeterDetails");
		List<SiteSubmeterVO> siteSubmeterVOList =  siteVO.getSiteSubmeter();
		List<SiteSubMeter> subMeterList = new ArrayList<SiteSubMeter>();
		if(siteVO.getSiteId()== null){
			subMeterList = new ArrayList<SiteSubMeter>();

		}else if(siteVO.getSiteId()!=null){
			subMeterList = new ArrayList<SiteSubMeter>();
		}
		for(SiteSubmeterVO siteSubmeterVO :  siteSubmeterVOList){
			if(siteSubmeterVO.getSubMeterId()!=null){
				SiteSubMeter siteSubmeter = siteSubMeterRepo.findOne(siteSubmeterVO.getSubMeterId());
				siteSubmeter.setSubMeterNumber(siteSubmeterVO.getSubMeterNumber());
				siteSubmeter.setSubMeterUser(siteSubmeterVO.getSubMeterUser());
				siteSubmeter.setCreatedBy(siteVO.getCreatedBy());
				siteSubmeter.setSite(site);
				subMeterList.add(siteSubmeter);
			}else{
				SiteSubMeter siteSubmeter = new SiteSubMeter();
				siteSubmeter.setSubMeterNumber(siteSubmeterVO.getSubMeterNumber());
				siteSubmeter.setSubMeterUser(siteSubmeterVO.getSubMeterUser());
				siteSubmeter.setCreatedBy(siteVO.getCreatedBy());
				siteSubmeter.setSite(site);
				subMeterList.add(siteSubmeter);
			}
		}


		/*	for(SiteSubmeterVO siteSubmeterVO : siteSubmeterVOList){
			SiteSubMeter siteSubmeter = new SiteSubMeter();
			siteSubmeter.setSubMeterNumber(siteSubmeterVO.getSubMeterNumber());
			siteSubmeter.setSubMeterUser(siteSubmeterVO.getSubMeterUser());
			siteSubmeter.setCreatedBy(siteVO.getCreatedBy());
			siteSubmeter.setSite(site);
			subMeterList.add(siteSubmeter);
		}*/
		site.setSiteSubmeterList(subMeterList);
		LOGGER.info("Exit SiteServiceImpl - populateSubmeterDetails");
		return site;
	}



	@Override
	public CreateSiteVO getSiteDetails(final Long siteId) {
		LOGGER.info("Inside SiteServiceImpl - getSiteDetails");
		Site site = siteRepo.findOne(siteId);
		CreateSiteVO siteVO = new CreateSiteVO();
		if(site!=null){
			List<String> attachments = new ArrayList<String>();
			List<String> fullAddress = new ArrayList<String>();
			List<SiteLicenceVO> siteLicensesVO =  siteVO.getSiteLicense();
			List<SiteLicence> licenseList = licenseRepo.findBySiteSiteId(site.getSiteId());
			site.setSiteLicences(licenseList);
			List<SiteSalesOperation> salesOpsList = siteSalesOperationRepo.findBySiteSiteId(site.getSiteId());
			site.setSiteSalesOpetaionTimes(salesOpsList);
			List<SiteDeliveryOperation> deliveryOpsList = siteDeliveryRepo.findBySiteSiteId(site.getSiteId());
			site.setSiteDeliveryOpetaionTimes(deliveryOpsList);
			List<SiteSubMeter> subMeterList = siteSubMeterRepo.findBySiteSiteId(site.getSiteId());
			site.setSiteSubmeterList(subMeterList);
			siteVO.setSiteId(site.getSiteId());
			siteVO.setSiteName(site.getSiteName());
			
			siteVO.setSiteAddress1(site.getSiteAddress1());
			siteVO.setSiteAddress2(site.getSiteAddress2());
			siteVO.setSiteAddress3(site.getSiteAddress3());
			siteVO.setSiteAddress4(site.getSiteAddress4());
			siteVO.setZipCode(site.getPostCode());
			
			if(!StringUtils.isEmpty(siteVO.getSiteAddress1())){
				fullAddress.add(siteVO.getSiteAddress1());
			}
			if(!StringUtils.isEmpty(siteVO.getSiteAddress2())){
				fullAddress.add(siteVO.getSiteAddress2());
			}
			if(!StringUtils.isEmpty(siteVO.getSiteAddress3())){
				fullAddress.add(siteVO.getSiteAddress3());
			}
			if(!StringUtils.isEmpty(siteVO.getSiteAddress4())){
				fullAddress.add(siteVO.getSiteAddress4());
			}
			if(!StringUtils.isEmpty(siteVO.getZipCode())){
				fullAddress.add(siteVO.getZipCode());
			}
			if(site.getSalesAreaSize()!=null){
				siteVO.setSalesAreaSize(site.getSalesAreaSize().toString());
			}
			String finalAddress = org.apache.commons.lang3.StringUtils.join(fullAddress,",");
			siteVO.setFullAddress(finalAddress);
			
		
			siteVO.setOperator(site.getOperator()); 
			siteVO.setOwner(site.getSiteOwner());


			if(site.getClusterId() !=null){
				Cluster cluster = clusterRepo.findOne(site.getClusterId());
				siteVO.setCluster(cluster);
			}
			if(site.getDistrictId() !=null){
				District district = districtRepo.findOne(site.getDistrictId());
				siteVO.setDistrict(district);
			}
			if(site.getAreaId() !=null){
				Area area = areaRepo.findOne(site.getAreaId());
				siteVO.setArea(area);
			}

			siteVO.setContactName(site.getAreaManagerName());
			siteVO.setEmail(site.getEmail());
			siteVO.setElectricityId(site.getElectricIdNo());
			if(site.getLatitude()==null && site.getLatitude() == null){

			}else{
				siteVO.setLatitude(site.getLatitude().toString());
				siteVO.setLongitude(site.getLongitude().toString());
			}

			if(site.getSiteNumberOne() ==  null ) {

			}else{
				siteVO.setSiteNumber1(site.getSiteNumberOne().toString());
			}

			if(site.getSiteNumberTwo()==null ) {

			}else{
				siteVO.setSiteNumber2(site.getSiteNumberTwo().toString());
			}


			if(StringUtils.isEmpty(site.getPrimaryContact())){

			}
			else{
				siteVO.setPrimaryContact(site.getPrimaryContact().toString());
			}
			if(StringUtils.isEmpty(site.getSecondaryContact())){

			}else{
				siteVO.setSecondaryContact(site.getSecondaryContact().toString());
			}


			if(!site.getSiteLicences().isEmpty()){
				for(SiteLicence siteLicence : site.getSiteLicences()){
					SiteLicenceVO siteLicenceVO = new SiteLicenceVO();
					siteLicenceVO.setLicenseId(siteLicence.getLicenseId());
					siteLicenceVO.setLicenseName(siteLicence.getLicenseName());
					siteLicenceVO.setAttachment(siteLicence.getAttachmentPath());
					
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					if(siteLicence.getStartDate()!=null && siteLicence.getEndDate()!=null){
						Date licenseValidFrom =  siteLicence.getStartDate();
						Date licenseValidTo  = siteLicence.getEndDate();
						String startDate = formatter.format(licenseValidFrom);
						String endDate = formatter.format(licenseValidTo);
						siteLicenceVO.setValidfrom(startDate);
						siteLicenceVO.setValidto(endDate);
						siteLicensesVO.add(siteLicenceVO);
					}
					siteVO.setSiteLicense(siteLicensesVO);

				}
			}
			//siteVO.getSiteOperation().clear();
			for(SiteSalesOperation siteSalesOperation : site.getSiteSalesOpetaionTimes()){
				SiteOperationVO siteOperationVO = new SiteOperationVO();
				siteOperationVO.setOpId(siteSalesOperation.getSaledsOpId());
				siteOperationVO.setDays(siteSalesOperation.getDayOfWeek());
				siteOperationVO.setFrom(siteSalesOperation.getOpStartTime());
				siteOperationVO.setTo(siteSalesOperation.getOpCloseTime());
				siteVO.getSiteOperation().add(siteOperationVO);
			}

			//siteVO.getSiteDelivery().clear();
			for(SiteDeliveryOperation siteDeliveryOperation : site.getSiteDeliveryOpetaionTimes()){
				SiteDeliveryVO siteDeliveryVO = new SiteDeliveryVO();
				siteDeliveryVO.setOpId(siteDeliveryOperation.getDeliveryOpId());
				siteDeliveryVO.setDays(siteDeliveryOperation.getDayOfWeek());
				siteDeliveryVO.setFrom(siteDeliveryOperation.getOpStartTime());
				siteDeliveryVO.setTo(siteDeliveryOperation.getOpCloseTime());
				siteVO.getSiteDelivery().add(siteDeliveryVO);
			}
			if(!site.getSiteSubmeterList().isEmpty()){
				for(SiteSubMeter siteSubmeter: site.getSiteSubmeterList()){
					SiteSubmeterVO siteSubmeterVO = new SiteSubmeterVO();
					siteSubmeterVO.setSubMeterId(siteSubmeter.getSubMeterId());
					siteSubmeterVO.setSubMeterNumber(siteSubmeter.getSubMeterNumber());
					siteSubmeterVO.setSubMeterUser(siteSubmeter.getSubMeterUser());
					siteVO.getSiteSubmeter().add(siteSubmeterVO);
				}
			}
			
			if(org.apache.commons.lang3.StringUtils.isNotBlank(site.getAttachmentPath())){
				/*String[] siteFiles = site.getAttachmentPath().split(",");
				for(String file:siteFiles){
					attachments.add(file);
				}*/
				siteVO.setFileInput(site.getAttachmentPath());
			}
		}
		LOGGER.info("Exit SiteServiceImpl - getSiteDetails");
		return siteVO;
	}

	@Override
	public List<SiteInfoVO> getSites(LoginUser user) throws Exception {
		String ejbQl = QueryConstants.SITE_LIST_QUERY;
		Query q= entityManager.createNativeQuery(ejbQl);
		q.setParameter("userId", user.getUserId());
		List<Object[]> siteList =  q.getResultList();
		List<SiteInfoVO> siteVOList = new ArrayList<SiteInfoVO>();
		if(!siteList.isEmpty()){
			for (Object[] result : siteList) {
				SiteInfoVO siteInfoVO = new SiteInfoVO();
				List<String> fullAddress = new ArrayList<String>();
				
				siteInfoVO.setSiteId(Long.parseLong(result[0].toString()));
				siteInfoVO.setSiteName(result[1].toString());
				siteInfoVO.setSiteOwner(result[2].toString());
				siteInfoVO.setPrimaryContactNumber(result[3].toString());
				siteInfoVO.setEmail(result[4].toString());
				siteInfoVO.setSiteNumber1(result[5].toString());
				
				siteInfoVO.setSiteAddress1(result[6]==null?"":result[6].toString());
				siteInfoVO.setSiteAddress2(result[7]==null?"":result[7].toString());
				siteInfoVO.setSiteAddress3(result[8]==null?"":result[8].toString());
				siteInfoVO.setSiteAddress4(result[9]==null?"":result[9].toString());
				siteInfoVO.setZipCode(result[10]==null?"":result[10].toString());
				
				if(!StringUtils.isEmpty(siteInfoVO.getSiteAddress1())){
					fullAddress.add(siteInfoVO.getSiteAddress1());
				}
				if(!StringUtils.isEmpty(siteInfoVO.getSiteAddress2())){
					fullAddress.add(siteInfoVO.getSiteAddress2());
				}
				if(!StringUtils.isEmpty(siteInfoVO.getSiteAddress3())){
					fullAddress.add(siteInfoVO.getSiteAddress3());
				}
				if(!StringUtils.isEmpty(siteInfoVO.getSiteAddress4())){
					fullAddress.add(siteInfoVO.getSiteAddress4());
				}
				if(!StringUtils.isEmpty(siteInfoVO.getZipCode())){
					fullAddress.add(siteInfoVO.getZipCode());
				}
				String finalAddress = org.apache.commons.lang3.StringUtils.join(fullAddress,",");
				siteInfoVO.setFullAddress(finalAddress);
				siteVOList.add(siteInfoVO);
			}		
		 }
		return siteVOList==null?Collections.emptyList():siteVOList;
	}

	@Override
	public SiteContactVO getSiteContacts(Long siteId) throws Exception {
		LOGGER.info("Inside SiteServiceImpl - getSiteContacts");
		Site site = siteRepo.findOne(siteId);
		List<String> fullAddress = new ArrayList<String>();
		SiteContactVO siteContactVO = new SiteContactVO();
		siteContactVO.setSiteId(site.getSiteId());
		siteContactVO.setSiteName(site.getSiteName());
		
		siteContactVO.setContactName(site.getAreaManagerName());
		
		siteContactVO.setSiteAddress1(site.getSiteAddress1()==null?"":site.getSiteAddress1());
		siteContactVO.setSiteAddress2(site.getSiteAddress2()==null?"":site.getSiteAddress2());
		siteContactVO.setSiteAddress3(site.getSiteAddress3()==null?"":site.getSiteAddress3());
		siteContactVO.setSiteAddress4(site.getSiteAddress4()==null?"":site.getSiteAddress4());
		siteContactVO.setZipCode(site.getPostCode()==null?"":site.getPostCode());
		
		if(!StringUtils.isEmpty(site.getSiteAddress1())){
			fullAddress.add(site.getSiteAddress1());
		}
		if(!StringUtils.isEmpty(site.getSiteAddress2())){
			fullAddress.add(site.getSiteAddress2());
		}
		if(!StringUtils.isEmpty(site.getSiteAddress3())){
			fullAddress.add(site.getSiteAddress3());
		}
		if(!StringUtils.isEmpty(site.getSiteAddress4())){
			fullAddress.add(site.getSiteAddress4());
		}
		if(!StringUtils.isEmpty(site.getPostCode())){
			fullAddress.add(site.getPostCode());
		}
		if(site.getSalesAreaSize()!=null){
			siteContactVO.setSalesAreaSize(site.getSalesAreaSize().toString());
		}
		String finalAddress = org.apache.commons.lang3.StringUtils.join(fullAddress,",");
		siteContactVO.setFullAddress(finalAddress);
		siteContactVO.setEmail(site.getEmail());
		if(site.getLatitude()==null && site.getLatitude() == null){

		}else{
			siteContactVO.setLatitude(site.getLatitude().toString());
			siteContactVO.setLongitude(site.getLongitude().toString());
		}


		if(StringUtils.isEmpty(site.getPrimaryContact())){

		}
		else{
			siteContactVO.setPrimaryContact(site.getPrimaryContact().toString());
		}
		if(StringUtils.isEmpty(site.getSecondaryContact())){

		}else{
			siteContactVO.setSecondaryContact(site.getSecondaryContact().toString());
		}

		if(site.getOperator()!=null){
		siteContactVO.setOperator(site.getOperator().getCompanyName()); 
		siteContactVO.setOwner(site.getSiteOwner());
		}
		LOGGER.info("Exit SiteServiceImpl - getSiteContacts");
		return siteContactVO;
		
	}

	@Override
	public List<SiteLicenceVO> getSiteLicences(Long siteId) throws Exception {
		LOGGER.info("Inside SiteServiceImpl - getSiteLicences");
		List<SiteLicence> licenseList = licenseRepo.findBySiteSiteId(siteId);
		List<SiteLicenceVO> siteLicensesVO=new ArrayList<SiteLicenceVO>();
		if(!licenseList.isEmpty()){
			for(SiteLicence siteLicence : licenseList){
				SiteLicenceVO siteLicenceVO = new SiteLicenceVO();
				siteLicenceVO.setLicenseId(siteLicence.getLicenseId());
				siteLicenceVO.setLicenseName(siteLicence.getLicenseName());
				siteLicenceVO.setAttachment(siteLicence.getAttachmentPath());
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				if(siteLicence.getStartDate()!=null && siteLicence.getEndDate()!=null){
					Date licenseValidFrom =  siteLicence.getStartDate();
					Date licenseValidTo  = siteLicence.getEndDate();
					String startDate = formatter.format(licenseValidFrom);
		
					String endDate = formatter.format(licenseValidTo);
					siteLicenceVO.setValidfrom(startDate);
					siteLicenceVO.setValidto(endDate);
					siteLicensesVO.add(siteLicenceVO);
				}

			}
		}else{
			LOGGER.info("No license available for site id : " + siteId);
		}
		LOGGER.info("Inside SiteServiceImpl - getSiteLicences");
		return siteLicensesVO == null ? Collections.EMPTY_LIST:siteLicensesVO;
		
	}

	@Override
	public List<SiteOperationVO> getSiteSalesOperations(Long siteId) throws Exception {
		LOGGER.info("Inside SiteServiceImpl - getSiteSalesOperations");
		List<SiteSalesOperation> salesOpsList = siteSalesOperationRepo.findBySiteSiteId(siteId);
		List<SiteOperationVO> salesOperations = new ArrayList<SiteOperationVO>();
		if(!salesOpsList.isEmpty()){
			LOGGER.info("Sales Oprations Size : " + salesOpsList.size());	
		for(SiteSalesOperation siteSalesOperation : salesOpsList){
			SiteOperationVO siteOperationVO = new SiteOperationVO();
			siteOperationVO.setOpId(siteSalesOperation.getSaledsOpId());
			siteOperationVO.setDays(siteSalesOperation.getDayOfWeek());
			siteOperationVO.setFrom(siteSalesOperation.getOpStartTime());
			siteOperationVO.setTo(siteSalesOperation.getOpCloseTime());
			salesOperations.add(siteOperationVO);
		}
		}else{
			LOGGER.info("No sales operation available for site id : " + siteId);	
		}
		LOGGER.info("Exit SiteServiceImpl - getSiteSalesOperations");
		return salesOperations==null?Collections.EMPTY_LIST:salesOperations;
		
	}

	@Override
	public List<SiteDeliveryVO> getSiteDeliveryVO(Long siteId) throws Exception {
		LOGGER.info("Inside SiteServiceImpl - getSiteDeliveryVO");
		List<SiteDeliveryOperation> deliveryOpsList = siteDeliveryRepo.findBySiteSiteId(siteId);
		List<SiteDeliveryVO> deliveryOpsVO = new ArrayList<SiteDeliveryVO>();
		if(!deliveryOpsList.isEmpty()){
		LOGGER.info("Delivery Oprations Size : " + deliveryOpsList.size());	
		for(SiteDeliveryOperation siteDeliveryOperation : deliveryOpsList){
			SiteDeliveryVO siteDeliveryVO = new SiteDeliveryVO();
			siteDeliveryVO.setOpId(siteDeliveryOperation.getDeliveryOpId());
			siteDeliveryVO.setDays(siteDeliveryOperation.getDayOfWeek());
			siteDeliveryVO.setFrom(siteDeliveryOperation.getOpStartTime());
			siteDeliveryVO.setTo(siteDeliveryOperation.getOpCloseTime());
			deliveryOpsVO.add(siteDeliveryVO);
		}
		}else{
			LOGGER.info("No delivery operation available for site id : " + siteId);	
		}
		LOGGER.info("Exit SiteServiceImpl - getSiteDeliveryVO");
		return deliveryOpsVO==null?Collections.EMPTY_LIST:deliveryOpsVO;
	}

	@Override
	public List<SiteSubmeterVO> getSiteSubmeterVO(Long siteId) throws Exception {
		LOGGER.info("Inside SiteServiceImpl - getSiteSubmeterVO");
		List<SiteSubMeter> subMeterList = siteSubMeterRepo.findBySiteSiteId(siteId);
		List<SiteSubmeterVO> subMeterVo=new ArrayList<SiteSubmeterVO>();
		if(!subMeterList.isEmpty()){
			LOGGER.info("Submeter List Size : " + subMeterVo.size());	
			for(SiteSubMeter siteSubmeter: subMeterList){
				SiteSubmeterVO siteSubmeterVO = new SiteSubmeterVO();
				siteSubmeterVO.setSubMeterId(siteSubmeter.getSubMeterId());
				siteSubmeterVO.setSubMeterNumber(siteSubmeter.getSubMeterNumber());
				siteSubmeterVO.setSubMeterUser(siteSubmeter.getSubMeterUser());
				subMeterVo.add(siteSubmeterVO);
			}
		}else{
			LOGGER.info("No submeter list available for site id : " + siteId);	
		}
		LOGGER.info("Exit SiteServiceImpl - getSiteSubmeterVO");
		return subMeterVo==null?Collections.EMPTY_LIST:subMeterVo;
	}

	@Override
	public int deleteLicense(Long licenseId) throws Exception {
		LOGGER.info("Inside SiteServiceImpl - deleteLicense");
		SiteLicence siteLicence = licenseRepo.findOne(licenseId);
		int isDeleted=0;
		if(siteLicence!=null){
			isDeleted=1;
			licenseRepo.delete(licenseId);
		}
		
		LOGGER.info("Exit SiteServiceImpl - deleteLicense");
		return isDeleted;
	}



}
