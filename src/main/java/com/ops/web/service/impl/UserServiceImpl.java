package com.ops.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ops.app.security.AuthorizedUserDetails;
import com.ops.app.util.QuickPasswordEncodingGenerator;
import com.ops.app.util.RandomUtils;
import com.ops.app.util.RestResponse;
import com.ops.app.util.ServiceUtil;
import com.ops.app.vo.AppUserVO;
import com.ops.app.vo.LoginUser;
import com.ops.app.vo.PasswordVO;
import com.ops.app.vo.UserVO;
import com.ops.jpa.entities.Company;
import com.ops.jpa.entities.Role;
import com.ops.jpa.entities.User;
import com.ops.jpa.entities.UserRole;
import com.ops.jpa.repository.CompanyRepo;
import com.ops.jpa.repository.JDBCQueryDAO;
import com.ops.jpa.repository.RoleDAO;
import com.ops.jpa.repository.UserDAO;
import com.ops.jpa.repository.UserRoleDAO;
import com.ops.web.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {


	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private JDBCQueryDAO jdbcQueryDAO;
	
	@Autowired
	private CompanyRepo companyRepo;

	@Override
	public User save(final User user) {
		return null;
	}

	@Override
	public List<User> findALL() {
		return userDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> listRoles() {
		return roleDAO.findAll();
	}

	@Override
	public User retrieve(final Long userId) {
		return userDAO.findOne(userId);
	}

	@Override
	public User update(final User user) {
		return null;
	}

	@Override
	public User findByUserName(final String userName) {
		return userDAO.findByEmailId(userName);
	}

	@Override
	public User findByEmail(final String email) {
		return findByUserName(email);
	}

	@Transactional(readOnly = true)
	@Override
	public User getCurrentLoggedinUser() {
		String username = null;
		try {
			username = ServiceUtil.getCurrentLoggedinUserName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isBlank(username)) {
			return null;
		}
		return findByUserName(username);
	}

	@Override
	@Transactional
	public List<UserVO> findALLUsers(Long companyId) {
		LOGGER.info("Inside UserServiceImpl - findALLUsers :");
		List<User> enabledUserList=userDAO.findUserListByCompany(companyId);
		Company company = companyRepo.findOne(companyId);
		List<UserVO> userVOList=new ArrayList<UserVO>();
		if(!enabledUserList.isEmpty()){
			for(User user: enabledUserList){
				UserVO userVO=new UserVO();
				userVO.setUserName(user.getEmailId());
				userVO.setUserId(user.getUserId());
				userVO.setFirstName(user.getFirstName());
				userVO.setLastName(user.getLastName());
				userVO.setEmailId(user.getEmailId());
				userVO.setCompany(company);
				userVO.setEnabled(user.getEnabled());
				userVO.setPhoneNo(String.valueOf(user.getPhone()));
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
				userVO.setCreatedAt(simpleDateFormat.format(user.getCreatedAt()));
				List<UserRole> userRoles = user.getUserRoles();
				if(!userRoles.isEmpty()){
					for(UserRole userRole:userRoles){
						String roleName=userRole.getRole().getDescription();
						Long roleId=userRole.getRole().getRoleId();
						userVO.getRoles().put(roleId, roleName);
						userVO.getRoleNames().add(roleId+","+roleName);
					}
				}else{
					LOGGER.info("User roles is empty");
				}
				userVOList.add(userVO);
			}
		}else{
			LOGGER.info("No Users available");
		}
		LOGGER.info("Exit all Users :");
		return userVOList;
	}



	@Override
	@Transactional
	public UserVO updateRoles(final UserVO userVO, final LoginUser user) {
		LOGGER.info("Inside UserServiceImpl - updateRoles ");
		User applicationUser=userDAO.findOne(userVO.getUserId());
		if(applicationUser!=null){
			List<UserRole> userRoles = applicationUser.getUserRoles();
			if(userRoles.isEmpty()){
				LOGGER.info("No roles found");
			}else{
				for(UserRole userRole:userRoles){
					LOGGER.info("Removing existing role as "+ userRole.getRole().getRoleName() );
					userRoleDAO.delete(userRole.getId());
				}
				List<Long> roles=userVO.getRoleIds();
				userRoles.clear();
				LOGGER.info("Updating new roles for the user" );
				if(roles.size()>0){
					for(Long role:roles){
						UserRole userRole=new UserRole();
						userRole.setUser(applicationUser);
						Role role2=new Role();
						role2.setRoleId(role);
						userRole.setRole(role2);
						userRole = userRoleDAO.save(userRole);
						userRoles.add(userRole);
					}

				}else{
					LOGGER.info("No roles found");
				}

			}
		}else{
			LOGGER.info("Unable to get user details for userId: "+ userVO.getUserId() + " and employee: "+ userVO.getFirstName() +""+ userVO.getLastName());
		}
		return userVO;
	}

	@Override
	public UserVO saveUser(final AppUserVO appUserVO) {
		LOGGER.info("Inside UserServiceImpl -  saveUser");
		User user = findByUserName(appUserVO.getEmail());
		UserVO savedUserVO = new UserVO();
		if(user==null){
			LOGGER.info("Creating new user for email :" + appUserVO.getEmail());
			user = new User();
			user.setFirstName(appUserVO.getFirstName());
			user.setLastName(appUserVO.getLastName());
			user.setEmailId(appUserVO.getEmail());
			if(appUserVO.getIsEnabled().equalsIgnoreCase("true")){
				user.setEnabled(1);
			}else{
				user.setEnabled(0);
			}
			user.setPhone(Long.parseLong(appUserVO.getPhoneNo()));
			user.setLoginName(appUserVO.getEmail());
			String generatedRawPassword = RandomUtils.randomAlphanumeric(8);
			String encryptedPassword = QuickPasswordEncodingGenerator.encodePassword(generatedRawPassword);
			user.setPassword(encryptedPassword);
			user.setSysPassword("YES");
			Company company = appUserVO.getCompany();
			user.setCompany(company);

			Role role = appUserVO.getRole();
			UserRole userRole=new UserRole();
			userRole.setRole(role);
			userRole.setUser(user);
			user.getUserRoles().add(userRole);
			user = userDAO.save(user);
			if(user.getUserId()!=null){
				savedUserVO.setUserId(user.getUserId());
				savedUserVO.setEmailId(user.getEmailId());
				savedUserVO.setPasswordGenerated(generatedRawPassword);
				savedUserVO.setExists(false);

			}
		}else{
			LOGGER.info("User already exists with email :" + appUserVO.getEmail());
			savedUserVO.setExists(true);
		}
		return savedUserVO;
	}

	@Override
	public AuthorizedUserDetails getAuthorizedUser(Authentication springAuthentication) throws Exception {
		AuthorizedUserDetails authorizedUser = (AuthorizedUserDetails) springAuthentication.getPrincipal();
		authorizedUser.getUser().setPassword("");
		return authorizedUser;
	}

	@Override
	public RestResponse changePassword(PasswordVO passwordVO, LoginUser user) {
		LOGGER.info("Inside UserServiceImpl ... changePassword");
		boolean passwordMatched = false;
		RestResponse response = new RestResponse();
		if(user.getUserId()!=null){
			User loggedInUser = userDAO.findOne(user.getUserId());
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String existingPassword = passwordVO.getOldPassword();
			String dbPassword       = loggedInUser.getPassword();

			if (passwordEncoder.matches(existingPassword, dbPassword)) {
				LOGGER.info("Old password match existing password.");
				String newEncodedPassword = QuickPasswordEncodingGenerator.encodePassword(passwordVO.getConfirmPassword());
				loggedInUser.setPassword(newEncodedPassword);
				loggedInUser.setSysPassword("NO");
				loggedInUser = userDAO.save(loggedInUser);
				if(loggedInUser.getPassword().equalsIgnoreCase(newEncodedPassword)){
					LOGGER.info("New password saved successfully.");
					passwordMatched =true;
					response.setStatusCode(200);
				}else{
					passwordMatched = false;
					response.setStatusCode(204);
					response.setMessage("Your new password could not be validated");
				}
			} else {
				passwordMatched =false;
				response.setStatusCode(205);
				LOGGER.info("Old password did not match.");
				response.setMessage("Old password is not valid");

			}

		}
		LOGGER.info("Exit UserServiceImpl ... changePassword");
		return response;
	}
	

	@Override
	public RestResponse resetNewPassword(String email, String newPassword) throws Exception{
		LOGGER.info("Inside UserServiceImpl ... resetNewPassword");
		RestResponse response = new RestResponse();
		try{
		User user = userDAO.findByEmailId(email);
		if(user.getUserId()!=null){
				LOGGER.info("Reseting new password for user :"+ user.getEmailId());
				String newEncodedPassword = QuickPasswordEncodingGenerator.encodePassword(newPassword);
				user.setPassword(newEncodedPassword);
				user.setSysPassword("NO");
				user = userDAO.save(user);
				if(user.getPassword().equalsIgnoreCase(newEncodedPassword))
					LOGGER.info("New Password reset successfully.");
					response.setStatusCode(200);
				}else{
					response.setStatusCode(204);
					response.setMessage("Your new password could not be validated");
				}
		}catch(Exception e){
			response.setStatusCode(500);
			LOGGER.error("Exception while resetting new password", e);
		}

		LOGGER.info("Exit UserServiceImpl ... resetNewPassword");
		return response;
	}
	
	@Override
	public RestResponse resetForgotPassword(String email, String newPassword) throws Exception{
		LOGGER.info("Inside UserServiceImpl ... resetForgotPassword");
		RestResponse response = new RestResponse();
		try{
			User user = userDAO.findByEmailId(email);
			if(user.getUserId()!=null){
				LOGGER.info("Reseting new password for user :"+ user.getEmailId());
				String newEncodedPassword = QuickPasswordEncodingGenerator.encodePassword(newPassword);
				user.setPassword(newEncodedPassword);
				user.setSysPassword("YES");
				user = userDAO.save(user);
				if(user.getPassword().equalsIgnoreCase(newEncodedPassword))
					LOGGER.info("New Password reset successfully.");
					response.setStatusCode(200);
					response.setMessage(newPassword);
				}else{
					response.setStatusCode(204);
					response.setMessage("Your new password could not be validated");
				}
		}catch(Exception e){
			response.setStatusCode(500);
			LOGGER.error("Exception while resetting forgot password", e);
		}

		LOGGER.info("Exit UserServiceImpl ... resetForgotPassword");
		return response;
	}

	@Override
	public RestResponse updateRole(AppUserVO appUserVO, LoginUser user) throws Exception{
		LOGGER.info("Inside UserServiceImpl ... updateRole");
		User siteUser= userDAO.findOne(appUserVO.getUserId());
		RestResponse response = new RestResponse();
		if(siteUser!=null){
			List<UserRole> userRoles = siteUser.getUserRoles();
			UserRole userRole = userRoles.get(0);
			LOGGER.info("Existing role for the user is: "+ userRole.getRole().getDescription());
			Role roleAssigned = roleDAO.findOne(appUserVO.getRole().getRoleId());
			LOGGER.info("Changing role for the user to: "+ roleAssigned.getDescription());
			userRole.setRole(roleAssigned);
			userRole = userRoleDAO.save(userRole);
			if(userRole.getRole().getRoleId().equals(appUserVO.getRole().getRoleId())){
				response.setStatusCode(200);
				response.setMessage("User Role updated succcessfully");
				LOGGER.info("User Role updated succcessfully");
			}else{
				response.setStatusCode(204);
				response.setMessage("User Role could not be updated.");
				LOGGER.info("User Role could not be updated.");
			}

		}

		LOGGER.info("Exit UserServiceImpl ... updateRole");
		return response;
	}

	@Override
	public RestResponse updateStatus(AppUserVO appUserVO, String isEnabled)	throws Exception {
		LOGGER.info("Inside UserServiceImpl ... updateStatus");
		User siteUser= userDAO.findOne(appUserVO.getUserId());
		RestResponse response = new RestResponse();

		if(siteUser!=null){
			if(StringUtils.isNotBlank(isEnabled)){
				Integer isEnable =  Integer.parseInt(isEnabled);
				if(isEnable == 1){
					LOGGER.info("Enabling User Account.");
					siteUser.setEnabled(isEnable);
				}else if(isEnable == 0){
					LOGGER.info("Disabling User Account.");
					siteUser.setEnabled(isEnable);
				}
				siteUser = userDAO.save(siteUser);
				if(siteUser.getEnabled() == 1){
					response.setStatusCode(200);
					response.setMessage("User Account enabled succcessfully.");
				}else if(siteUser.getEnabled() == 0){
					response.setStatusCode(200);
					response.setMessage("User Account disabled succcessfully.");
					LOGGER.info("User Account disabled succcessfully.");
				}else{
					response.setStatusCode(204);
					response.setMessage("Unable to update user account status.");
					LOGGER.info("Unable to update user account status.");
				}
			}

		}

		LOGGER.info("Exit UserServiceImpl ... updateStatus");
		return response;
	}

	@Override
	public int checkUserAvailibility(String email) throws Exception {
		return userDAO.checkUserAvailibity(email);
	}

	@Override
	@Transactional
	public RestResponse updateProfile(AppUserVO appUserVO) throws Exception {
		LOGGER.info("Inside UserServiceImpl ... updateProfile");
		User siteUser= userDAO.findOne(appUserVO.getUserId());
		RestResponse response = new RestResponse();
		User user = userDAO.findByPhone(Long.parseLong(appUserVO.getPhoneNo()));
		LOGGER.info("Checking existing user with same phone number");
		if(null==user ){
			LOGGER.info("No other user available with same number");
			siteUser.setFirstName(appUserVO.getFirstName());
			siteUser.setLastName(appUserVO.getLastName());
			siteUser.setPhone(Long.parseLong(appUserVO.getPhoneNo()));
			siteUser = userDAO.save(siteUser);
			response.setStatusCode(200);
			response.setObject(siteUser);
			response.setMessage("Profile updated successfully");
		}
		else if(user.getUserId().equals(siteUser.getUserId())){
			user.setFirstName(appUserVO.getFirstName());
			user.setLastName(appUserVO.getLastName());
			user.setPhone(Long.parseLong(appUserVO.getPhoneNo()));
			user = userDAO.save(user);
			response.setStatusCode(200);
			response.setObject(user);
			response.setMessage("Profile updated successfully");
		}else{
			LOGGER.info("User already exists with same phone number");
			response.setMessage("User with phone number "+ appUserVO.getPhoneNo() + " already exists.");
			response.setStatusCode(204);
		}
		LOGGER.info("Exit UserServiceImpl ... updateProfile");
		return response;
	}

	@Override
	public UserVO findUserByUsername(String email) throws Exception {
		UserVO userVO=new UserVO();
		User user = userDAO.findUserByEmail(email);
		if(user!=null){
			userVO.setUserName(user.getEmailId());
			userVO.setUserId(user.getUserId());
			userVO.setFirstName(user.getFirstName());
			userVO.setLastName(user.getLastName());
			userVO.setEmailId(user.getEmailId());
			userVO.setCompany(user.getCompany());
			userVO.setEnabled(user.getEnabled());
			userVO.setPhoneNo(String.valueOf(user.getPhone()));
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
			userVO.setCreatedAt(simpleDateFormat.format(user.getCreatedAt()));
			List<UserRole> userRoles = user.getUserRoles();
			if(!userRoles.isEmpty()){
				for(UserRole userRole:userRoles){
					String roleName=userRole.getRole().getDescription();
					Long roleId=userRole.getRole().getRoleId();
					userVO.getRoles().put(roleId, roleName);
					userVO.getRoleNames().add(roleId+","+roleName);
				}
			}else{
				LOGGER.info("User roles is empty");
			}
		}else{
			LOGGER.info("No User available");
		}
		return userVO;
	}

}
