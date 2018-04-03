package com.ops.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.app.vo.LoginUser;
import com.ops.jpa.entities.IncidentReport;
import com.ops.jpa.entities.Site;
import com.ops.jpa.entities.UserSiteAccess;
import com.ops.jpa.repository.IncidentRepo;
import com.ops.jpa.repository.UserSiteAccessRepo;
import com.ops.web.service.IncidentReportService;

@Service("incidentReportService")
public class IncidentReportServiceImpl implements IncidentReportService {

	private static Logger logger = LoggerFactory.getLogger(IncidentReportServiceImpl.class);
	
	@Autowired
	private IncidentRepo incidentReportRepo;
	
	
	@Autowired
	private UserSiteAccessRepo userSiteAccessRepo;
	
	@Override
	public List<IncidentReport> findAllIncident() {
		List<IncidentReport> incidentReportList = incidentReportRepo.findAll();
		return incidentReportList==null?Collections.EMPTY_LIST:incidentReportList;
	}

	@Override
	public List<IncidentReport> findReportsByUser(LoginUser loggedUser) {
		List<IncidentReport> incidentReportList = new ArrayList<IncidentReport>();
		List<UserSiteAccess> userSiteAccessList = userSiteAccessRepo.findSiteAssignedFor(loggedUser.getUserId());
		Set<Site> siteList=null;
		if(!userSiteAccessList.isEmpty()){
			siteList = new HashSet<Site>( userSiteAccessList.size());
			for(UserSiteAccess userSiteAccess : userSiteAccessList){
				siteList.add(userSiteAccess.getSite());
			}
			List<Long> siteIds = new ArrayList<Long>();
			for(Site site : siteList){
				siteIds.add(site.getSiteId());
			}
			incidentReportList=incidentReportRepo.findBySiteIdIn(siteIds);
		}
			
		return incidentReportList==null?Collections.EMPTY_LIST:incidentReportList;
	}

}
