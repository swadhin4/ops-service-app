package com.ops.web.service.impl;

import java.io.IOException;
import java.lang.reflect.Array;
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

import com.ops.app.constants.QueryConstants;
import com.ops.app.util.RestResponse;
import com.ops.app.vo.AssetVO;
import com.ops.app.vo.LoginUser;
import com.ops.app.vo.UploadFile;
import com.ops.jpa.entities.Asset;
import com.ops.jpa.entities.AssetCategory;
import com.ops.jpa.entities.AssetLocation;
import com.ops.jpa.entities.Company;
import com.ops.jpa.entities.ServiceProvider;
import com.ops.jpa.entities.Site;
import com.ops.jpa.repository.AssetCategoryRepo;
import com.ops.jpa.repository.AssetLocationRepo;
import com.ops.jpa.repository.AssetRepo;
import com.ops.jpa.repository.ServiceProviderRepo;
import com.ops.jpa.repository.SiteRepo;
import com.ops.jpa.repository.UserSiteAccessRepo;
import com.ops.web.service.AssetService;
import com.ops.web.service.FileIntegrationService;



@Service("assetService")
public class AssetServiceImpl implements AssetService{
	private final static Logger LOGGER = LoggerFactory.getLogger(AssetServiceImpl.class);

	@Autowired
	private AssetRepo assetRepo;

	@Autowired
	private SiteRepo siteRepo;

	@Autowired
	private AssetCategoryRepo assetCategoryRepo;

	@Autowired
	private AssetLocationRepo assetLocationRepo;

	@Autowired
	private ServiceProviderRepo serviceProviderRepo;

	@Autowired
	private UserSiteAccessRepo userSiteAccessRepo;
	
	@Autowired
	private FileIntegrationService fileIntegrationService;
	
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public List<AssetVO> findAllAsset(LoginUser user) throws Exception {
		LOGGER.info("Inside AssetServiceImpl .. findAllAsset");
		LOGGER.info("Getting Asset List for logged in user : "+  user.getFirstName() + "" + user.getLastName());
		//List<UserSiteAccess> userSiteAccessList = userSiteAccessRepo.findSiteAssignedFor(user.getUserId());
		String ejbQl1 = QueryConstants.SITE_ACCESS_QUERY;
		Query q= entityManager.createNativeQuery(ejbQl1);
		q.setParameter("userId", user.getUserId());
		List<Object[]> siteList =  q.getResultList();
		List<String> siteIds=new ArrayList<String>();
		List<AssetVO> siteAssetVOList = new ArrayList<AssetVO>();
		if(!siteList.isEmpty()){
			for (Object[] result : siteList) {
				siteIds.add(result[0].toString());
			}
			String sites=String.join(",", siteIds);
		String ejbQl2 = QueryConstants.USER_SITE_ASSET_QUERY + sites + " ) order by a.asset_name";
		Query q2= entityManager.createNativeQuery(ejbQl2);
		
		List<Object[]> assetList =  q2.getResultList();
		LOGGER.info("Total Assets for user : "+  assetList.size());
		
		siteAssetVOList = populateAssetList1(siteAssetVOList, assetList);
		}
		
		/*if(userSiteAccessList.isEmpty()){
			LOGGER.info("User donot have any access to sites");
		}else{
			LOGGER.info("User is having access to "+userSiteAccessList.size()+" sites");
			List<Long> siteIdList = new ArrayList<Long>();
			for(UserSiteAccess userSiteAccess: userSiteAccessList){
				siteIdList.add(userSiteAccess.getSite().getSiteId());
			}
			LOGGER.info("Getting list of Asset for siteIds : "+siteIdList);

			List<Asset> siteAssetList =  assetRepo.findBySiteIdIn(siteIdList);

			LOGGER.info("Total Assets for user : "+  siteAssetList.size());


			if(!siteAssetList.isEmpty()){
				siteAssetVOList = populateAssetList(siteAssetVOList, siteAssetList);
			}
		}*/

		LOGGER.info("Exit AssetServiceImpl .. findAllAsset");
		return siteAssetVOList == null?Collections.EMPTY_LIST:siteAssetVOList;
	}

	private List<AssetVO> populateAssetList1(List<AssetVO> siteAssetVOList, List<Object[]> assetList) {
		
		for (Object[] result : assetList) {
			AssetVO assetVO = new AssetVO();
			assetVO.setAssetId(Long.parseLong(result[0].toString()));
			assetVO.setAssetName(result[1]==null?"":result[1].toString());
			assetVO.setSiteName(result[2]==null?"":result[2].toString());
			assetVO.setCategoryId(result[3]==null?0l:Long.parseLong(result[3].toString()));
			assetVO.setAssetCategoryName(result[4]==null?"":result[4].toString());
			assetVO.setAssetType(result[5]==null?"":result[5].toString());
			siteAssetVOList.add(assetVO);
		}
		return siteAssetVOList;
	}

	private List<AssetVO> populateAssetList(List<AssetVO> siteAssetVOList, List<Asset> siteAssetList) {
		for(Asset asset : siteAssetList){
			AssetVO assetVO = new AssetVO();
			BeanUtils.copyProperties(asset, assetVO);
			assetVO = getAssetDetails(asset, assetVO);
			
			siteAssetVOList.add(assetVO);
			
		}
		LOGGER.info("Total asset size : " + siteAssetVOList.size());
		return siteAssetVOList;
	}

	private AssetVO getAssetDetails(Asset asset, AssetVO assetVO) {
		if(asset.getCategoryId()!=null){
			AssetCategory assetCategory = assetCategoryRepo.findOne(asset.getCategoryId());
			assetVO.setCategoryId(asset.getCategoryId());
			assetVO.setAssetCategoryName(assetCategory.getAssetCategoryName());
			assetVO.setAssetType(assetCategory.getAssetType());
		}

		if(asset.getLocationId()!=null){
			AssetLocation assetLocation = assetLocationRepo.findOne(asset.getLocationId());
			assetVO.setLocationId(asset.getLocationId());
			assetVO.setLocationName(assetLocation.getLocationName());
		}

		if(asset.getServiceProviderId()!=null){
			ServiceProvider serviceProvider = serviceProviderRepo.findOne(asset.getServiceProviderId());
			assetVO.setServiceProviderId(asset.getServiceProviderId());
			assetVO.setServiceProviderName(serviceProvider.getName());
		}

		if(asset.getSiteId()!=null){
			Site site = siteRepo.findOne(asset.getSiteId());
			assetVO.setSiteId(site.getSiteId());
			assetVO.setSiteName(site.getSiteName());
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		if(asset.getDateCommissioned()!=null){
			Date commDate =  asset.getDateCommissioned();
			String comDateStr = formatter.format(commDate);
			assetVO.setCommisionedDate(comDateStr);
		}

		if(asset.getDateDeComissioned()!=null){
			Date deCommDate  = asset.getDateDeComissioned();
			String deComDateStr = formatter.format(deCommDate);
			assetVO.setDeCommissionedDate(deComDateStr);
		}

		if(!StringUtils.isEmpty(asset.getIsAssetElectrical())){
			assetVO.setIsAssetElectrical(asset.getIsAssetElectrical());
		}

		if(!StringUtils.isEmpty(asset.getIsPWSensorAttached())){
			assetVO.setIsPWSensorAttached(asset.getIsPWSensorAttached());
		}

		if(!StringUtils.isEmpty(asset.getPwSensorNumber())){
			assetVO.setPwSensorNumber(asset.getPwSensorNumber());
		}
		
		if(!StringUtils.isEmpty(asset.getImagePath())){
			assetVO.setImagePath(asset.getImagePath());
		}
		
		if(!StringUtils.isEmpty(asset.getDocumentPath())){
			assetVO.setDocumentPath(asset.getDocumentPath());
		}
		return assetVO;
	}

	@Override
	@Transactional	
	public List<AssetVO> findAssetsBySite(Long siteId) throws Exception {
		LOGGER.info("Inside AssetServiceImpl .. findAssetsBySite");
		List<Asset> siteAssetList =  assetRepo.findBySiteId(siteId);
		List<AssetVO> siteAssetVOList = new ArrayList<AssetVO>();
		if(!siteAssetList.isEmpty()){
			siteAssetVOList = populateAssetList(siteAssetVOList, siteAssetList);
		}

		LOGGER.info("Exit AssetServiceImpl .. findAssetsBySite");
		return siteAssetVOList == null?Collections.EMPTY_LIST:siteAssetVOList;
	}

	@Override
	@Transactional	
	public AssetVO findAssetById(Long assetid) {
		LOGGER.info("Inside AssetServiceImpl .. findAssetById");
		Asset savedAsset =  assetRepo.findOne(assetid);
		AssetVO assetVO = new AssetVO();
		BeanUtils.copyProperties(savedAsset, assetVO);
		/*AssetCategory assetCategory = assetCategoryRepo.findOne(savedAsset.getCategoryId());
		assetVO.setAssetCategoryName(assetCategory.getAssetCategoryName());

		AssetLocation assetLocation = assetLocationRepo.findOne(savedAsset.getLocationId());
		assetVO.setLocationName(assetLocation.getLocationName());

		ServiceProvider serviceProvider = serviceProviderRepo.findOne(savedAsset.getServiceProviderId());
		assetVO.setServiceProviderName(serviceProvider.getName());*/
		assetVO =  getAssetDetails(savedAsset, assetVO);
		LOGGER.info("Exit AssetServiceImpl .. findAssetById");
		return assetVO;
	}

	@Override
	public AssetVO findAssetByModelNumber(String modelNumber) throws Exception {

		LOGGER.info("Inside AssetServiceImpl .. findAssetByModelNumber");

		LOGGER.info("Exit AssetServiceImpl .. findAssetByModelNumber");
		return null;
	}

	@Override
	@Transactional
	public RestResponse saveOrUpdateAsset(AssetVO assetVO, LoginUser user) throws Exception {
		LOGGER.info("Inside AssetServiceImpl .. saveOrUpdateAsset");
		List<AssetVO> assetVOList = new ArrayList<AssetVO>();
		RestResponse response = new RestResponse();
		List<Asset> assetList=new ArrayList<Asset>();
		boolean isAssetAvailable=false;
	//	Set<Long> uniqueSites = new HashSet<>(assetVO.getSites());
		//LOGGER.info("No sites selected for asset : "+ uniqueSites);
		//if(uniqueSites.size() == 0){
			//LOGGER.info("No sites selected for asset : "+ assetVO.getAssetName());
		//}else{
			if(assetVO.getAssetId() == null){
				assetList= assetRepo.findByAssetCodeAndSiteIdInAndDelFlag(assetVO.getAssetCode(), assetVO.getSites(), 0);
				
				if(assetList.isEmpty()){
					LOGGER.info("No asset found for asset code : "+ assetVO.getAssetCode() +" and for sites :"+ assetVO.getSites());
					isAssetAvailable=false;
				}else{
					isAssetAvailable=true;
				}
				 if(!isAssetAvailable){
					assetVOList = saveAssetsforMultipleSites(assetVO, user, assetVOList);
					if(!assetVOList.isEmpty()){
						response.setStatusCode(200);
						response.setMode("SAVING");
					}
				 
				}else{
					LOGGER.info("Asset already exists for any of these sites : "+ assetVO.getSites());
					response.setStatusCode(204);
				}
			}else{
				assetVO.getSites().add(assetVO.getSiteId());
				assetList = assetRepo.findByAssetCodeAndSiteIdInAndDelFlag(assetVO.getAssetCode(), assetVO.getSites(),0);
				if(assetList.isEmpty()){
					LOGGER.info("No asset found for asset code : "+ assetVO.getAssetCode() +" and for sites :"+ assetVO.getSites());
					isAssetAvailable=false;
				}else{
					isAssetAvailable=true;
				}
				if(!isAssetAvailable){
					assetVOList = updateAssetsforMultipleSites(null,assetVO, user, assetVOList);
						if(assetVOList.isEmpty()){
							LOGGER.info("Asset already exists for site "+ assetList.get(0).getSiteId());
							response.setStatusCode(204);
						}else{
							response.setStatusCode(200);
							response.setMode("UPDATING");
						}
				}else{
					assetVOList = updateAssetsforMultipleSites(assetList.get(0),assetVO, user, assetVOList);
					if(assetVOList.isEmpty()){
						LOGGER.info("Asset already exists for site "+ assetList.get(0).getSiteId());
						response.setStatusCode(204);
					}else{
						response.setStatusCode(200);
						response.setMode("UPDATING");
					}
				}
			}
		//}
		
		LOGGER.info("Exit AssetServiceImpl .. saveOrUpdateAsset");
		return response;
		
	}
		private List<AssetVO> updateAssetsforMultipleSites(Asset savedAsset, AssetVO assetVO, LoginUser user, List<AssetVO> assetVOList) {
				Asset asset  = assetRepo.findOne(assetVO.getAssetId());
				if(savedAsset==null){
					LOGGER.info("New Asset code is updating for asset : "+  savedAsset.getAssetName() +" and site :"+ savedAsset.getSiteId());
					assetVOList = updateAssetForSite(assetVO, user, assetVOList, asset);
				}
				else if(savedAsset.getAssetCode().equals(asset.getAssetCode()) && savedAsset.getSiteId().equals(asset.getSiteId())){
					LOGGER.info("Found same asset Code and Site ID, so updating the same");
					assetVOList = updateAssetForSite(assetVO, user, assetVOList, asset);
					
				}else{
					LOGGER.info("Asset code already exists.");
				}
				return assetVOList==null?Collections.EMPTY_LIST:assetVOList;
		}

		private List<AssetVO> updateAssetForSite(AssetVO assetVO, LoginUser user, List<AssetVO> assetVOList, Asset asset) {
			
			BeanUtils.copyProperties(assetVO, asset);
			asset.setModifiedBy(user.getUsername());
			asset.setModifiedDate(new Date());
			//assetVO = saveOrUpdateAssetSites(assetVO, asset, assetVOList);
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			if(!StringUtils.isEmpty(assetVO.getCommisionedDate())){
				Date commDate;
				try {
					commDate = formatter.parse(assetVO.getCommisionedDate());
					asset.setDateCommissioned(commDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			if(!StringUtils.isEmpty(assetVO.getDeCommissionedDate())){
				Date deCommDate;
				try {
					deCommDate = formatter.parse(assetVO.getDeCommissionedDate());
					asset.setDateDeComissioned(deCommDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(assetVO.getAssetType().equalsIgnoreCase("E")){
				LOGGER.info("Validating  Asset Electrical and Power sensor attached");
				if(!StringUtils.isEmpty(assetVO.getIsAssetElectrical())){
					if(assetVO.getIsAssetElectrical().equalsIgnoreCase("YES")){
						LOGGER.info("Asset is electrical");
						asset.setIsAssetElectrical(assetVO.getIsAssetElectrical());
						if(assetVO.getIsPWSensorAttached().equalsIgnoreCase("YES")){
							LOGGER.info("Asset has power sensor attached");
							asset.setIsPWSensorAttached(assetVO.getIsPWSensorAttached());

							if(!StringUtils.isEmpty(assetVO.getPwSensorNumber())){
								LOGGER.info("Asset has power sensor number");
								asset.setPwSensorNumber(assetVO.getPwSensorNumber());
							}else{
								LOGGER.info("Asset power sensor must not be empty");
								throw new RuntimeException("Asset power sensor should not be empty.");
							}
						}else{
							LOGGER.info("Asset has no power sensor attached");
							asset.setIsPWSensorAttached("NO");
							asset.setPwSensorNumber("");
						}
					}else{
						LOGGER.info("Asset is not electical");
						asset.setIsAssetElectrical("NO");
						asset.setIsPWSensorAttached("NO");
						asset.setPwSensorNumber("");
					}

				}
			}
			assetVO.getSites().add(assetVO.getSiteId());
			
		/*	if(assetVO.getAssetType().equalsIgnoreCase("E")){
				if(!org.apache.commons.lang3.StringUtils.isBlank(assetVO.getAssetImage().getBase64ImageString())){
					assetVO = uploadAssetFiles(assetVO, user, "IMAGE", user.getCompany(), asset);
					
				}
			}
				
			if(!org.apache.commons.lang3.StringUtils.isBlank(assetVO.getAssetDoc().getBase64ImageString())){
				assetVO = uploadAssetFiles(assetVO, user, "DOC", user.getCompany(), asset);
			}
			if(org.apache.commons.lang3.StringUtils.isNotBlank(assetVO.getImagePath())){
				asset.setImagePath(assetVO.getImagePath());
			}
			if(org.apache.commons.lang3.StringUtils.isNotBlank(assetVO.getDocumentPath())){
				asset.setDocumentPath(assetVO.getDocumentPath());
			}*/
				Asset savedAsset = assetRepo.save(asset);
			assetVOList.add(assetVO);
			return assetVOList;
		}

		private List<AssetVO> saveAssetsforMultipleSites(AssetVO assetVO, LoginUser user, List<AssetVO> assetVOList) {
			List<Asset> savedAssetList=new ArrayList<Asset>();
			for(Long site:assetVO.getSites()){
				AssetVO savedAssetVO = new AssetVO();
				Asset asset = new Asset();
				BeanUtils.copyProperties(assetVO, asset);
				asset.setSiteId(site);
				asset.setCreatedBy(user.getUsername());
				//savedAssetVO = saveOrUpdateAssetSites(assetVO, asset, assetVOList);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				if(!StringUtils.isEmpty(assetVO.getCommisionedDate())){
					Date commDate;
					try {
						commDate = formatter.parse(assetVO.getCommisionedDate());
						asset.setDateCommissioned(commDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				if(!StringUtils.isEmpty(assetVO.getDeCommissionedDate())){
					Date deCommDate;
					try {
						deCommDate = formatter.parse(assetVO.getDeCommissionedDate());
						asset.setDateDeComissioned(deCommDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if(assetVO.getAssetType().equalsIgnoreCase("E")){
					LOGGER.info("Validating  Asset Electrical and Power sensor attached");
					if(!StringUtils.isEmpty(assetVO.getIsAssetElectrical())){
						if(assetVO.getIsAssetElectrical().equalsIgnoreCase("YES")){
							LOGGER.info("Asset is electrical");
							asset.setIsAssetElectrical(assetVO.getIsAssetElectrical());
							if(assetVO.getIsPWSensorAttached().equalsIgnoreCase("YES")){
								LOGGER.info("Asset has power sensor attached");
								asset.setIsPWSensorAttached(assetVO.getIsPWSensorAttached());

								if(!StringUtils.isEmpty(assetVO.getPwSensorNumber())){
									LOGGER.info("Asset has power sensor number");
									asset.setPwSensorNumber(assetVO.getPwSensorNumber());
								}else{
									LOGGER.info("Asset power sensor must not be empty");
									throw new RuntimeException("Asset power sensor should not be empty.");
								}
							}else{
								LOGGER.info("Asset has no power sensor attached");
								asset.setIsPWSensorAttached("NO");
								asset.setPwSensorNumber("");
							}
						}else{
							LOGGER.info("Asset is not electical");
							asset.setIsAssetElectrical("NO");
							asset.setIsPWSensorAttached("NO");
							asset.setPwSensorNumber("");
						}

					}
				}

				Asset savedAsset = assetRepo.save(asset);
				if(savedAsset.getAssetId()!=null){
					BeanUtils.copyProperties(savedAsset, savedAssetVO);
					assetVOList.add(savedAssetVO);
					LOGGER.info("Asset created successfully for site "+ savedAsset.getAssetId() +" / "+ asset.getSiteId());
					savedAssetList.add(savedAsset);
				}
				
				if(assetVO.getSites().size()==1){
					/*if(assetVO.getAssetType().equalsIgnoreCase("E")){
						if(!StringUtils.isEmpty(assetVO.getAssetImage().getBase64ImageString())){
							LOGGER.info("Asset Image uploading..");
							assetVO = uploadAssetFiles(assetVO, user, "IMAGE", user.getCompany(), savedAssetList.get(0));
						}
					}*/
					
					/*if(!StringUtils.isEmpty(assetVO.getAssetDoc().getBase64ImageString())){
						LOGGER.info("Asset Document uploading..");
						assetVO = uploadAssetFiles(assetVO, user, "DOC", user.getCompany(), savedAssetList.get(0));
					}*/
				}
				
			}
			return assetVOList;
		}

		private AssetVO uploadAssetFiles(AssetVO assetVO, LoginUser user, String type, Company company, Asset savedAsset) {
			if(assetVO.getAssetType().equalsIgnoreCase("E")){
				if(type.equalsIgnoreCase("IMAGE")){
					UploadFile assetFile = assetVO.getAssetImage();
					try {
						assetVO.setAssetId(savedAsset.getAssetId());
						if(StringUtils.isEmpty(savedAsset.getImagePath())){
							assetVO= fileIntegrationService.siteAssetFileUpload(assetVO, assetFile, company, type);
							//savedAsset.setImagePath(assetVO.getImagePath());
						}else{
							RestResponse response = fileIntegrationService.deleteFile(null, null, savedAsset.getAssetId(), null, "IMG");
							if(response.getStatusCode() == 0){
								assetVO= fileIntegrationService.siteAssetFileUpload(assetVO, assetFile, company, type);
							}else if( response.getStatusCode()==200){
								assetVO= fileIntegrationService.siteAssetFileUpload(assetVO, assetFile, company, type);
							}
							else if( response.getStatusCode()==500){
								assetVO= fileIntegrationService.siteAssetFileUpload(assetVO, assetFile, company, type);
							}		
						}
						
					} catch (Exception e) {
						LOGGER.info("Exception in upload asset Image file..uploadAssetFiles");
					}
				}
			}
			
			if(type.equalsIgnoreCase("DOC")){
				UploadFile assetFile = assetVO.getAssetDoc();
				try {
					assetVO.setAssetId(savedAsset.getAssetId());
					assetVO = fileIntegrationService.siteAssetFileUpload(assetVO, assetFile, company, type);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/*if(assetVO.getAssetType().equalsIgnoreCase("E")){
				if(StringUtils.isEmpty(savedAsset.getImagePath())){
					savedAsset.setImagePath(assetVO.getImagePath());
				}
			}
			
			if(StringUtils.isEmpty(savedAsset.getDocumentPath())){
				savedAsset.setDocumentPath(assetVO.getDocumentPath());
			}*/
			
			return assetVO;
			
		}

	@Override
	public List<AssetCategory> getAllAssetCategories() throws Exception {
		List<AssetCategory> assetCategories = assetCategoryRepo.findAssetCategories();
		List<AssetCategory> tempCategories = assetCategories;
		AssetCategory removedAssetCategory = null;
		for(AssetCategory assetCategory : tempCategories){
			if(assetCategory.getAssetCategoryName().equalsIgnoreCase("Other")){
				removedAssetCategory = assetCategory;
				assetCategories.remove(assetCategory);
				break;
			}
		}
		if(removedAssetCategory!=null){
			assetCategories.add(removedAssetCategory);
		}
		return assetCategories ==  null?Collections.EMPTY_LIST:assetCategories;
	}

	@Override
	public List<AssetLocation> getAllAssetLocations() throws Exception {
		List<AssetLocation> assetLocations = assetLocationRepo.findAssetLocations();
		List<AssetLocation> tempLocations = assetLocations;
		AssetLocation removedAssetLocation = null;
		for(AssetLocation assetLocation : assetLocations){
			if(assetLocation.getLocationName().equalsIgnoreCase("Other")){
				removedAssetLocation = assetLocation;
				assetLocations.remove(assetLocation);
				break;
			}
		}
		if(removedAssetLocation!=null){
			assetLocations.add(removedAssetLocation);
		}
		return assetLocations ==  null?Collections.EMPTY_LIST:assetLocations;
	}

	@Override
	public AssetVO deleteAsset(Long assetId) throws Exception {
		LOGGER.info("Inside AssetServiceImpl .. deleteAsset");
		Asset savedAsset =  assetRepo.findOne(assetId);
		AssetVO assetVO = new AssetVO();
		if(savedAsset!=null){
			savedAsset.setDelFlag(1);
			savedAsset = assetRepo.save(savedAsset);
			BeanUtils.copyProperties(savedAsset, assetVO);
		}
		return assetVO;
	}





}
