package com.ops.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;
import com.ops.app.security.AuthorizedUserDetails;
import com.ops.app.util.QuickPasswordEncodingGenerator;
import com.ops.app.vo.EscalationLevelVO;
import com.ops.app.vo.LoginUser;
import com.ops.app.vo.SLADetailsVO;
import com.ops.app.vo.SPLoginVO;
import com.ops.app.vo.ServiceProviderVO;
import com.ops.jpa.entities.Role;
import com.ops.jpa.entities.SPEscalationLevels;
import com.ops.jpa.entities.ServiceProvider;
import com.ops.jpa.entities.ServiceProviderSLADetails;
import com.ops.jpa.entities.TicketPriority;
import com.ops.jpa.entities.TicketPriorityEnum;
import com.ops.jpa.entities.UserSiteAccess;
import com.ops.jpa.repository.JDBCQueryDAO;
import com.ops.jpa.repository.RoleDAO;
import com.ops.jpa.repository.SPEscalationLevelRepo;
import com.ops.jpa.repository.ServiceProviderRepo;
import com.ops.jpa.repository.ServiceProviderSLARepo;
import com.ops.jpa.repository.SiteRepo;
import com.ops.jpa.repository.TicketEscalationRepo;
import com.ops.jpa.repository.UserSiteAccessRepo;
import com.ops.web.service.ServiceProviderService;

@Service("serviceProviderService")
public class ServiceProviderServiceImpl implements ServiceProviderService {

	private static final Logger logger = LoggerFactory.getLogger(ServiceProviderServiceImpl.class);

	@Autowired
	private ServiceProviderRepo serviceProvderRepo;

	@Autowired
	private ServiceProviderSLARepo spSLARepo;

	@Autowired
	private SPEscalationLevelRepo spEscLevelRepo;

	@Autowired
	private UserSiteAccessRepo userSiteAccessRepo;
	
	@Autowired
	private SiteRepo siteRepo;
	
	@Autowired
	private TicketEscalationRepo ticketEscalationRepo;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private JDBCQueryDAO jdbcQueryDAO;

	@Override
	@Transactional
	public ServiceProviderVO saveServiceProvider(ServiceProviderVO serviceProviderVO, LoginUser loginUser) throws Exception {
		logger.info("Inside ServiceProviderServiceImpl -- saveServiceProvider");
		ServiceProvider savedServiceProvider =null;
		if(serviceProviderVO.getServiceProviderId() == null){
			if(!StringUtils.isEmpty(serviceProviderVO.getEmail())){
				savedServiceProvider= serviceProvderRepo.findByEmailAndCompanyCompanyId(serviceProviderVO.getEmail(),loginUser.getCompany().getCompanyId());
				if(savedServiceProvider!=null){
					logger.info("Service Provider with email "+serviceProviderVO.getEmail()+" already exists.");
					serviceProviderVO.setStatus(204);
					serviceProviderVO.setMessage("Service Provider with email "+serviceProviderVO.getEmail()+" already exists.");
				}else{
					savedServiceProvider = new ServiceProvider();
					savedServiceProvider.setCreatedBy(loginUser.getUsername());
					Integer spUniqueUser = jdbcQueryDAO.getUniqueSPUserId();
					savedServiceProvider.setSpUsername("SP-00"+loginUser.getCompany().getCompanyId()+spUniqueUser);
					
					serviceProviderVO.setStatus(100);
					StringBuilder spPassword = new StringBuilder();
					spPassword.append(savedServiceProvider.getName());
					spPassword.append(",");
					spPassword.append(savedServiceProvider.getEmail());
					spPassword.append(",");
					spPassword.append(savedServiceProvider.getHelpDeskEmail());
					UUIDGenerator uiGen = new UUIDGenerator();
					UUID unquieSPID = uiGen.generateId(savedServiceProvider);
					System.out.println("Unique ID for SP : " + unquieSPID.toString());
					spPassword.append(unquieSPID.toString());
					String spAccessKey = QuickPasswordEncodingGenerator.encodePassword(spPassword.toString());
					savedServiceProvider.setAccessKey(spAccessKey);
				}
			}else{
				logger.info("Email id should not be blank");
				serviceProviderVO.setStatus(204);
				serviceProviderVO.setMessage("Email id is required to created a service provider.");
			}
		}else{
			if(!StringUtils.isEmpty(serviceProviderVO.getEmail())){
				savedServiceProvider= serviceProvderRepo.findByEmailAndCompanyCompanyId(serviceProviderVO.getEmail(),loginUser.getCompany().getCompanyId());
				if(savedServiceProvider!=null){
					if(savedServiceProvider.getServiceProviderId().equals(serviceProviderVO.getServiceProviderId())){
						serviceProviderVO.setStatus(100);
					}else{
						logger.info("Service Provider with email "+serviceProviderVO.getEmail()+" already exists.");
						serviceProviderVO.setStatus(204);
						serviceProviderVO.setMessage("Service Provider with email "+serviceProviderVO.getEmail()+" already exists.");
					}
				}
			}else{
				logger.info("Email not found");
				serviceProviderVO.setStatus(204);
				serviceProviderVO.setMessage("Email id is required for the service provider.");
			}
			
			
			if(!StringUtils.isEmpty(serviceProviderVO.getHelpDeskEmail())){
				serviceProviderVO.setStatus(100);
			}else{
				serviceProviderVO.setStatus(204);
				serviceProviderVO.setMessage("Help desk email is required for service provider");
			}
		}
		if(serviceProviderVO.getStatus() == 100 ){
			logger.info("Saving Service Provider Object to Database");
			savedServiceProvider.setName(serviceProviderVO.getName());
			savedServiceProvider.setCode(serviceProviderVO.getCode());
			savedServiceProvider.setEmail(serviceProviderVO.getEmail());
			savedServiceProvider.setCountry(serviceProviderVO.getCountry());
			savedServiceProvider.setAdditionalDetails(serviceProviderVO.getAdditionalDetails());
			savedServiceProvider.setHelpDeskEmail(serviceProviderVO.getHelpDeskEmail());
			if(!StringUtils.isEmpty(serviceProviderVO.getHelpDeskNumber())){
				savedServiceProvider.setHelpDeskNumber(Long.parseLong(serviceProviderVO.getHelpDeskNumber()));
			}
			
			if(!StringUtils.isEmpty(serviceProviderVO.getSlaDescription())){
				savedServiceProvider.setSlaDescription(serviceProviderVO.getSlaDescription());
			}

			/*if(serviceProviderVO.getServiceProviderId()!=null){
				List<SLADetailsVO> slaList = serviceProviderVO.getSlaListVOList();
				for(SLADetailsVO slaDetail: slaList){
					spSLARepo.delete(slaDetail.getSlaId());
				}
			}
			 */
			List<SLADetailsVO> slaList = serviceProviderVO.getSlaListVOList();
			for(SLADetailsVO slaDetail: slaList){
				ServiceProviderSLADetails slaValue =null;
				if(slaDetail.getSlaId()==null){
					slaValue = new ServiceProviderSLADetails();
					slaValue.setPriorityId(slaDetail.getTicketPriority().getPriorityId());
					slaValue.setServiceProvider(savedServiceProvider);
				}else{
					slaValue = spSLARepo.findOne(slaDetail.getSlaId());
					//slaValue.setServiceProviderId(serviceProviderVO.getServiceProviderId());
					slaValue.setServiceProvider(savedServiceProvider);
				}
				//slaValue.setTicketPriority(slaDetail.getTicketPriority());

				slaValue.setDuration(Integer.parseInt(slaDetail.getDuration()));
				slaValue.setUnit(slaDetail.getUnit());
				slaValue.setCreatedBy(loginUser.getUsername());
				savedServiceProvider.getSpSLAList().add(slaValue);
			}

			List<EscalationLevelVO> escalationLevelVOs = serviceProviderVO.getEscalationLevelList();
			for(EscalationLevelVO  escalationLevel: escalationLevelVOs){
				SPEscalationLevels spLevels = null;
				if(escalationLevel.getEscId() == null){
					spLevels = new SPEscalationLevels();
				}else{
					spLevels = spEscLevelRepo.findOne(escalationLevel.getEscId());

				}

				//spLevels.setEscalationLevel(escalationLevel.getEscalationLevel());
				if(!StringUtils.isEmpty(escalationLevel.getEscalationPerson()) && !StringUtils.isEmpty(escalationLevel.getEscalationEmail())){
					spLevels.setEscalationLevel(Integer.parseInt(escalationLevel.getEscalationLevel()));
					spLevels.setEscalationPerson(escalationLevel.getEscalationPerson());
					spLevels.setEscalationEmail(escalationLevel.getEscalationEmail());
					spLevels.setServiceProvider(savedServiceProvider);
					spLevels.setCreatedBy(loginUser.getUsername());
					savedServiceProvider.getSpEscalationLevelList().add(spLevels);
				}else{

				}

			}
			savedServiceProvider.setCompany(loginUser.getCompany());
			savedServiceProvider = serviceProvderRepo.save(savedServiceProvider);
				if(savedServiceProvider.getServiceProviderId()!=null && savedServiceProvider.getVersion() == 0){
					serviceProviderVO.setServiceProviderId(savedServiceProvider.getServiceProviderId());
					serviceProviderVO.setEmail(savedServiceProvider.getHelpDeskEmail());
					serviceProviderVO.setSpUserName(savedServiceProvider.getSpUsername());
					serviceProviderVO.setAccessKey(savedServiceProvider.getAccessKey());
					serviceProviderVO.setStatus(200);
					serviceProviderVO.setOption("CREATED");
					serviceProviderVO.setMessage("New Service Provider \""+ savedServiceProvider.getName() +"\" created successfully");
				}else{
					serviceProviderVO.setServiceProviderId(savedServiceProvider.getServiceProviderId());
					serviceProviderVO.setStatus(200);
					serviceProviderVO.setOption("UPDATED");
					serviceProviderVO.setMessage("Service Provider \""+ savedServiceProvider.getName() +"\"updated successfully");
					serviceProviderVO.setSpUserName(savedServiceProvider.getSpUsername());
					serviceProviderVO.setAccessKey(savedServiceProvider.getAccessKey());
			     }
		   }
		logger.info("Exit ServiceProviderServiceImpl -- saveServiceProvider");
		return serviceProviderVO;
	}

	@Override
	public ServiceProviderVO findServiceProvider(Long serviceProviderId)
			throws Exception {
		ServiceProvider serviceProvider = serviceProvderRepo.findOne(serviceProviderId);
		ServiceProviderVO serviceProviderVO =new ServiceProviderVO();
		if(serviceProvider!=null){
			serviceProviderVO.setServiceProviderId(serviceProvider.getServiceProviderId());
			serviceProviderVO.setEmail(serviceProvider.getHelpDeskEmail());
			serviceProviderVO.setName(serviceProvider.getName());
			List<SPEscalationLevels> escalationLevels  = spEscLevelRepo.findByServiceProviderServiceProviderId(serviceProvider.getServiceProviderId());
			 for(SPEscalationLevels escLevel :  escalationLevels){
				 EscalationLevelVO escalationLevelVO = new EscalationLevelVO();
				 escalationLevelVO.setEscId(escLevel.getEscId());
				 escalationLevelVO.setServiceProdviderId(serviceProvider.getServiceProviderId());
				 if(escLevel.getEscalationLevel()==1){
					 escalationLevelVO.setLevelId(1);
					 escalationLevelVO.setEscalationLevel("Level 1");
				 }
				 else if(escLevel.getEscalationLevel()==2){
					 escalationLevelVO.setLevelId(2);
					 escalationLevelVO.setEscalationLevel("Level 2");
				 }
				 else if(escLevel.getEscalationLevel()==3){
					 escalationLevelVO.setLevelId(3);
					 escalationLevelVO.setEscalationLevel("Level 3");
				 }
				 else if(escLevel.getEscalationLevel()==4){
					 escalationLevelVO.setLevelId(4);
					 escalationLevelVO.setEscalationLevel("Level 4");
				 }
				 escalationLevelVO.setEscalationPerson(escLevel.getEscalationPerson());
				 escalationLevelVO.setEscalationEmail(escLevel.getEscalationEmail());
				 serviceProviderVO.getEscalationLevelList().add(escalationLevelVO);
			 }
		}
		return serviceProviderVO;
	}

	@Override
	@Transactional
	public List<ServiceProviderVO> findAllServiceProvider(  LoginUser user ) throws Exception {
		logger.info("Inside ServiceProviderServiceImpl -- findAllServiceProvider");
		logger.info("Getting Service Provider List for logged in user : "+  user.getFirstName() + " " + user.getLastName());
		List<UserSiteAccess> userSiteAccessList = userSiteAccessRepo.findSiteAssignedFor(user.getUserId());
		List<ServiceProviderVO> serviceProviderVOList = new ArrayList<ServiceProviderVO>();

		if(userSiteAccessList.isEmpty()){
			logger.info("User donot have any access to sites");
		}else{
			Set<Long> serviceProviderIdSet = new HashSet<Long>();
			boolean isUserCompanyId = false;
			for(UserSiteAccess userSiteAccess: userSiteAccessList){
				if(userSiteAccess.getSite().getOperator()!=null){
					if(userSiteAccess.getSite().getOperator().getCompanyId().equals(user.getCompany().getCompanyId())){
						serviceProviderIdSet.add(userSiteAccess.getSite().getOperator().getCompanyId());
						isUserCompanyId = true;
					}
				}else{
					logger.info("Site do not have an Operator for site Id : "+  userSiteAccess.getSite().getSiteId());	
				}

			}
			if(!isUserCompanyId && serviceProviderIdSet.size() == 0){
				logger.info("No Service provder added for user");	
			}else{
				List<Long> spList = new ArrayList(serviceProviderIdSet);
				logger.info("Getting list of Service Provider by IDs  : "+spList);
				List<ServiceProvider> serviceProviderList =  serviceProvderRepo.findByCompanyCompanyIdIn(spList);
				logger.info("Total Service Provider for user : "+  serviceProviderList.size());	
				serviceProviderVOList = getServiceProviderList(serviceProviderList, serviceProviderVOList);
			}
		}
		logger.info("Exit ServiceProviderServiceImpl -- findAllServiceProvider");
		return serviceProviderVOList ==  null ? Collections.EMPTY_LIST:serviceProviderVOList;
	}

	@Override
	public boolean deleteServiceProvider() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public List<ServiceProviderVO> findServiceProviderByCustomer(Long customerId)
			throws Exception {
		logger.info("Inside ServiceProviderServiceImpl -- findAllServiceProvider");
		List<ServiceProviderVO> serviceProviderVOList = new ArrayList<ServiceProviderVO>();
		List<ServiceProvider> serviceProviderList = serviceProvderRepo.findByCompany(customerId);
		serviceProviderVOList = getServiceProviderList(serviceProviderList, serviceProviderVOList);
		logger.info("Exit ServiceProviderServiceImpl -- findAllServiceProvider");
		return serviceProviderVOList ==  null ? Collections.EMPTY_LIST:serviceProviderVOList;
	}

	/**
	 * @param serviceProviderList
	 * @param serviceProviderVOList
	 */
	private List<ServiceProviderVO> getServiceProviderList(List<ServiceProvider> serviceProviderList,
			List<ServiceProviderVO> serviceProviderVOList) {
		logger.info("Inside ServiceProviderServiceImpl -- getServiceProviderList");
		if(!serviceProviderList.isEmpty()){
			for(ServiceProvider serviceProvider:serviceProviderList){
				ServiceProviderVO serviceProviderVO = new ServiceProviderVO();
				serviceProviderVO.setServiceProviderId(serviceProvider.getServiceProviderId());
				serviceProviderVO.setName(serviceProvider.getName());
				serviceProviderVO.setCode(serviceProvider.getCode());
				serviceProviderVO.setCountry(serviceProvider.getCountry());
				serviceProviderVO.setEmail(serviceProvider.getEmail());
				serviceProviderVO.setAdditionalDetails(serviceProvider.getAdditionalDetails());
				serviceProviderVO.setHelpDeskEmail(serviceProvider.getHelpDeskEmail());
				if(!StringUtils.isEmpty(serviceProvider.getSlaDescription())){
					serviceProviderVO.setSlaDescription(serviceProvider.getSlaDescription());
				}
				if(!StringUtils.isEmpty(serviceProvider.getHelpDeskNumber())){
					serviceProviderVO.setHelpDeskNumber(serviceProvider.getHelpDeskNumber().toString());
				}
				List<ServiceProviderSLADetails> spSLAList = spSLARepo.findByServiceProviderServiceProviderId(serviceProvider.getServiceProviderId());

				for(ServiceProviderSLADetails spSLADetails: spSLAList){
					SLADetailsVO slaDetailsVO = new SLADetailsVO();
					slaDetailsVO.setSlaId(spSLADetails.getSlaId());
					//slaDetailsVO.setTicketPriority(spSLADetails.getTicketPriority());
					TicketPriority ticketPriority = new TicketPriority();
					ticketPriority.setPriorityId(spSLADetails.getPriorityId());
					if(spSLADetails.getPriorityId()!=null){
						if(spSLADetails.getPriorityId().intValue() == 1){
							ticketPriority.setDescription(TicketPriorityEnum.CRITICAL.name());
						}
						else if(spSLADetails.getPriorityId().intValue() == 2){
							ticketPriority.setDescription(TicketPriorityEnum.HIGH.name());
						}
						else if(spSLADetails.getPriorityId().intValue() == 3){
							ticketPriority.setDescription(TicketPriorityEnum.MEDIUM.name());
						}
						else if(spSLADetails.getPriorityId().intValue() == 4){
							ticketPriority.setDescription(TicketPriorityEnum.LOW.name());
						}
						slaDetailsVO.setTicketPriority(ticketPriority);
						slaDetailsVO.setDuration(String.valueOf(spSLADetails.getDuration()));
						slaDetailsVO.setUnit(spSLADetails.getUnit());
						serviceProviderVO.getSlaListVOList().add(slaDetailsVO);
					}
				}

				for( SPEscalationLevels escalationLevel: serviceProvider.getSpEscalationLevelList()){
					EscalationLevelVO escalationLevelVO = new EscalationLevelVO();
					escalationLevelVO.setEscId(escalationLevel.getEscId());
					escalationLevelVO.setEscalationLevel(String.valueOf(escalationLevel.getEscalationLevel()));
					escalationLevelVO.setEscalationPerson(escalationLevel.getEscalationPerson());
					escalationLevelVO.setEscalationEmail(escalationLevel.getEscalationEmail());
					serviceProviderVO.getEscalationLevelList().add(escalationLevelVO);
				}
				serviceProviderVOList.add(serviceProviderVO);
			}
		}
		logger.info("Exit ServiceProviderServiceImpl -- getServiceProviderList");
		return serviceProviderVOList;
	}

	@Override
	public SPLoginVO validateServiceProvider(String username, String accessCode) throws Exception {
		logger.info("Inside ServiceProviderServiceImpl -- validateServiceProvider");
		ServiceProvider serviceProvider =null;
		SPLoginVO spLoginVO = new SPLoginVO();
		if(org.apache.commons.lang.StringUtils.isNotBlank(username)){
			serviceProvider = serviceProvderRepo.findBySpUsername(username);
			if(serviceProvider!=null){
				if(accessCode.equals(serviceProvider.getAccessKey())){
					Role role = roleDAO.findRoleByName();
					spLoginVO.setRole(role.getDescription());
					spLoginVO.setSpId(serviceProvider.getServiceProviderId());
					spLoginVO.setSpName(serviceProvider.getName());
					spLoginVO.setEmail(serviceProvider.getHelpDeskEmail());
					spLoginVO.setValidated(true);
					
					List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
					authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
					AuthorizedUserDetails authorizedUserDetails =
							new AuthorizedUserDetails(serviceProvider.getEmail(), serviceProvider.getAccessKey(), true, true,
									true, true, authorities);
					authorizedUserDetails.setServiceProvider(serviceProvider);
				}
			}
		}
		
		logger.info("Exit ServiceProviderServiceImpl -- validateServiceProvider");
		return spLoginVO;
	}

}
