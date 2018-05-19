package com.ops.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.app.vo.LoginUser;
import com.ops.app.vo.UserSiteAccessVO;
import com.ops.jpa.entities.Site;
import com.ops.jpa.entities.User;
import com.ops.jpa.entities.UserSiteAccess;
import com.ops.jpa.repository.CompanyRepo;
import com.ops.jpa.repository.SiteRepo;
import com.ops.jpa.repository.UserDAO;
import com.ops.jpa.repository.UserSiteAccessRepo;
import com.ops.web.service.UserSiteAccessService;

@Service("userSiteAccessService")
public class UserSiteAccessServiceImpl implements UserSiteAccessService {

	private static final Logger logger = LoggerFactory.getLogger(UserSiteAccessServiceImpl.class);

	@Autowired
	private UserSiteAccessRepo userSiteAccessRepo;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private SiteRepo siteRepo;
	
	@Autowired
	private CompanyRepo companyRepo;

	@Override
	public List<UserSiteAccess> getUserSiteAccess(Long userId) throws Exception {
		logger.info("Inside UserSiteAccessServiceImpl .. getUserSiteAccess");
		//User user = userDAO.findOne(userId);
		List<UserSiteAccess> userSiteAccessList = userSiteAccessRepo.findSiteAssignedFor(userId);
		logger.info("Exit UserSiteAccessServiceImpl .. getUserSiteAccess");
		return userSiteAccessList == null?Collections.EMPTY_LIST:userSiteAccessList;
	}

	@Override
	public List<UserSiteAccessVO> getUserSiteAccessBySite(Long siteId, LoginUser loggedInUser)
			throws Exception {
		logger.info("Inside UserSiteAccessServiceImpl .. getUserSiteAccessBySite");
		Site site = siteRepo.findOne(siteId);
		List<Object[]> userIdList = userSiteAccessRepo.findUserAccessList(site.getSiteId());
		List<UserSiteAccessVO> userSiteAccessList = new ArrayList<UserSiteAccessVO>();
		List<User> assignedUserList = new ArrayList<User>();
		for(Object[] object:userIdList){
			Integer accessId = (Integer) object[0];
			Integer userId= (Integer) object[1];
			User user = userDAO.findOne(userId.longValue());
			logger.info("Getting User list having access to site similar to logged in user and belongs to logged in user Company ");
			if(user.getCompany().getCompanyId().equals(loggedInUser.getCompany().getCompanyId())){
				LoginUser assignedUser = new LoginUser();

				assignedUser.setUserId(user.getUserId());
				assignedUser.setFirstName(user.getFirstName());
				assignedUser.setLastName(user.getLastName());
				assignedUser.setEmail(user.getEmailId());
				assignedUser.setCompany(loggedInUser.getCompany());
				assignedUser.setSite(site);
				assignedUser.setUserRoles(user.getUserRoles());
				assignedUserList.add(user);
				UserSiteAccessVO userSiteAccessVO = new UserSiteAccessVO(accessId.longValue(), assignedUser);
				userSiteAccessList.add(userSiteAccessVO);
			}else{
				logger.info("No user found from same company having access to site ");

			}
		}

		logger.info("Getting list of user not assigned to any sites. ");
		List<User> allUserList = userDAO.findUserListByCompany(loggedInUser.getCompany().getCompanyId());

		logger.info("No: of Users  :" +  allUserList.size());

		if(allUserList!=null && !allUserList.isEmpty()){
			boolean isUserAssigned = false;
			//Sorting the User list based on ID
			/*	Collections.sort(assignedUserList, new Comparator<User>() {
				@Override
				public int compare(User op1, User op2) {
					return op1.getUserId().compareTo(op2.getUserId());
				}
			});

			Collections.sort(userSiteAccessList, new Comparator<UserSiteAccessVO>() {
				@Override
				public int compare(UserSiteAccessVO op1, UserSiteAccessVO op2) {
					return op1.getAssignedUser().getUserId().compareTo(op2.getAssignedUser().getUserId());
				}
			});

			Collections.sort(allUserList, new Comparator<User>() {
				@Override
				public int compare(User op1, User op2) {
					return op1.getUserId().compareTo(op2.getUserId());
				}
			});
			 */
			for(User searchedUser: allUserList){
				for(User assignedUser : assignedUserList){
					if(assignedUser.getUserId().equals(searchedUser.getUserId())){
						isUserAssigned=true;
						break;
					}else{
						isUserAssigned=false;
					}
				}
				if(!isUserAssigned){
					LoginUser unassignedSiteUser = new LoginUser();
					unassignedSiteUser.setUserId(searchedUser.getUserId());
					unassignedSiteUser.setUserId(searchedUser.getUserId());
					unassignedSiteUser.setFirstName(searchedUser.getFirstName());
					unassignedSiteUser.setLastName(searchedUser.getLastName());
					unassignedSiteUser.setEmail(searchedUser.getEmailId());
					unassignedSiteUser.setCompany(loggedInUser.getCompany());
					unassignedSiteUser.setUserRoles(searchedUser.getUserRoles());
					UserSiteAccessVO userSiteAccessVO = new UserSiteAccessVO(unassignedSiteUser);
					userSiteAccessList.add(userSiteAccessVO);
				}
			}
		}
		logger.info("Exit UserSiteAccessServiceImpl .. getUserSiteAccessBySite");	
		return userSiteAccessList == null?Collections.EMPTY_LIST:userSiteAccessList;
	}

	@Override
	public UserSiteAccessVO assignUserToSite(Long userId, Long siteId)
			throws Exception {
		logger.info("Inside UserSiteAccessServiceImpl .. assignUserToSite");
		logger.info("Saving User and Site data to User site Access table");
		UserSiteAccess userSiteAccess = userSiteAccessRepo.findAccessDetails(userId, siteId);
		UserSiteAccessVO userSiteAccessVO = new UserSiteAccessVO();
		if(userSiteAccess==null){
			userSiteAccess = new UserSiteAccess();
			User siteUser = userDAO.findOne(userId);
			Site site = siteRepo.findOne(siteId);
			userSiteAccess.setSite(site);
			userSiteAccess.setUser(siteUser);
			userSiteAccess = userSiteAccessRepo.save(userSiteAccess);
			if(userSiteAccess.getAccessId()!=null){
				logger.info("Site mapped to user successfully");
				userSiteAccessVO.setAccessId(userSiteAccess.getAccessId());
				LoginUser loginUser = new LoginUser();
				loginUser.setUserId(userSiteAccess.getUser().getUserId());
				loginUser.setSite(userSiteAccess.getSite());
				userSiteAccessVO.setAssignedUser(loginUser);
			}
		}else{
			logger.info("Site ID already mapped to user in  User site Access table");

		}
		logger.info("Exit UserSiteAccessServiceImpl .. assignUserToSite");
		return userSiteAccessVO;
	}

	@Override
	public boolean removeUserAccessFromSite(Long accessId)	throws Exception {
		logger.info("Inside UserSiteAccessServiceImpl .. removeUserAccessFromSite");
		boolean isUserAccessRevoked= false;
		UserSiteAccess userAccessSite = userSiteAccessRepo.findOne(accessId);
		if(userAccessSite!=null){
			userSiteAccessRepo.delete(accessId);
			isUserAccessRevoked= true;
		}

		logger.info("Exit UserSiteAccessServiceImpl .. removeUserAccessFromSite");
		return isUserAccessRevoked;
	}

}
