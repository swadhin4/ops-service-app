package com.ops.web.service;

import java.util.List;

import com.ops.app.vo.CreateSiteVO;
import com.ops.app.vo.LoginUser;
import com.ops.app.vo.SiteContactVO;
import com.ops.app.vo.SiteDeliveryVO;
import com.ops.app.vo.SiteInfoVO;
import com.ops.app.vo.SiteLicenceVO;
import com.ops.app.vo.SiteOperationVO;
import com.ops.app.vo.SiteSubmeterVO;


public interface SiteService {

	public List<CreateSiteVO> getSiteList(LoginUser user) throws Exception;

	public CreateSiteVO saveOrUpdate(CreateSiteVO siteVO, LoginUser loginUser) throws Exception;
	
	public CreateSiteVO updateSite(CreateSiteVO siteVO, LoginUser loginUser) throws Exception;

	public CreateSiteVO getSiteDetails(Long siteId) throws Exception;
	
	public List<SiteInfoVO> getSites(LoginUser user) throws Exception;

	public SiteContactVO getSiteContacts(Long siteId) throws Exception;
	
	public List<SiteLicenceVO> getSiteLicences(Long siteId) throws Exception;
	
	public List<SiteOperationVO> getSiteSalesOperations(Long siteId) throws Exception;
	
	public List<SiteDeliveryVO> getSiteDeliveryVO(Long siteId) throws Exception;
	
	public List<SiteSubmeterVO> getSiteSubmeterVO(Long siteId) throws Exception;

	public CreateSiteVO updateSiteContact(CreateSiteVO createSiteVO, LoginUser authorizedUser) throws Exception;

	public SiteLicenceVO updateSiteLicense(Long siteId, SiteLicenceVO siteLicenseVO) throws Exception;

	public int deleteLicense(Long licenseId)throws Exception;

	
	
}
