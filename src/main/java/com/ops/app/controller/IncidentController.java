/*
 * Copyright (C) 2013 , Inc. All rights reserved
 */
package com.ops.app.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ops.app.util.RestResponse;
import com.ops.app.vo.CustomerSPLinkedTicketVO;
import com.ops.app.vo.EscalationLevelVO;
import com.ops.app.vo.IncidentVO;
import com.ops.app.vo.LoginUser;
import com.ops.app.vo.ServiceProviderVO;
import com.ops.app.vo.TicketCommentVO;
import com.ops.app.vo.TicketEscalationVO;
import com.ops.app.vo.TicketHistoryVO;
import com.ops.app.vo.TicketMVO;
import com.ops.app.vo.TicketPrioritySLAVO;
import com.ops.app.vo.TicketVO;
import com.ops.app.vo.UserVO;
import com.ops.jpa.entities.CustomerSPLinkedTicket;
import com.ops.jpa.entities.SPEscalationLevels;
import com.ops.jpa.entities.Status;
import com.ops.jpa.entities.TicketAttachment;
import com.ops.jpa.entities.TicketCategory;
import com.ops.jpa.entities.User;
import com.ops.jpa.repository.SPEscalationLevelRepo;
import com.ops.jpa.repository.TicketAttachmentRepo;
import com.ops.web.service.EmailService;
import com.ops.web.service.ServiceProviderService;
import com.ops.web.service.StatusService;
import com.ops.web.service.TicketCategoryService;
import com.ops.web.service.TicketService;
import com.ops.web.service.UserService;

/**
 * The Class UserController.
 *
 */
@Controller
@RequestMapping("/incident")
public class IncidentController  {

	private static final Logger logger = LoggerFactory.getLogger(IncidentController.class);

	/** The user service. */
	@Autowired
	private UserService userService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private TicketCategoryService ticketCategoryService;
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private TicketAttachmentRepo ticketAttachmentRepo;
	
	@Autowired
	private SPEscalationLevelRepo spEscalationRepo;
	
	@Autowired
	private ServiceProviderService serviceProviderService;
	
	@Autowired
	private EmailService emailService;
	

	@RequestMapping(value = "/v1/list", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<RestResponse> listAllTickets(@RequestParam("email") String email) {
		List<IncidentVO> tickets = null;
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			UserVO user = userService.findUserByUsername(email);
			if(user.getUserId()!=null){
				LoginUser authorizedUser = new LoginUser();
				authorizedUser.setEmail(user.getEmailId());
				authorizedUser.setFirstName(user.getFirstName());
				authorizedUser.setLastName(user.getLastName());
				authorizedUser.setUserId(user.getUserId());
				tickets = ticketService.getUserTickets(authorizedUser);
				if (tickets.isEmpty()) {
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NO_CONTENT);
					return responseEntity;
				}else{
					response.setStatusCode(200);
					response.setObject(tickets);
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			response.setStatusCode(500);
			logger.info("Exception in getting ticket list response", e);
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.EXPECTATION_FAILED);
		}

		return responseEntity;
	}

	

	@RequestMapping(value = "/v1/ticket/{ticketId}", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<RestResponse> getSelectedTicket(@PathVariable(value="ticketId") Long ticketId) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			TicketVO  ticketVO = ticketService.getSelectedTicket(ticketId);
				if (StringUtils.isNotBlank(ticketVO.getTicketNumber())) {
					response.setStatusCode(200);
					response.setObject(ticketVO);
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
				}
		} catch (Exception e) {
			response.setStatusCode(500);
			logger.info("Exception in getting ticket response", e);
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.EXPECTATION_FAILED);
		}

		return responseEntity;
	}

	@RequestMapping(value = "/v1/ticket/categories", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<RestResponse> getTicketPriorities() {
		RestResponse response = new RestResponse();
		
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			List<TicketCategory> ticketCategories = ticketCategoryService.getAllTicketCategories();
				if (!ticketCategories.isEmpty()) {
					response.setStatusCode(200);
					response.setObject(ticketCategories);
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
				}
		} catch (Exception e) {
			response.setStatusCode(500);
			logger.info("Exception in getting ticket categoriesresponse", e);
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.EXPECTATION_FAILED);
		}

		return responseEntity;
	}
	

	
	@RequestMapping(value = "/status/{category}", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<List<Status>> listAllOpenTickets(@PathVariable (value="category") final String category) {
		List<Status> statusList = null;
		try {
			statusList = statusService.getStatusByCategory(category);
			if (statusList.isEmpty()) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
				// You many decide to return HttpStatus.NOT_FOUND
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<List<Status>>(statusList, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/v1/ticket/escalations/CT/{ticketId}", method = RequestMethod.GET)
	public ResponseEntity<RestResponse> getSelectedTicketEscalations(@PathVariable(value="ticketId") Long ticketId) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
					TicketVO selectedTicketVO = ticketService.getSelectedTicket(ticketId);
					ServiceProviderVO serviceProviderVO = serviceProviderService.findServiceProvider(selectedTicketVO.getAssignedTo());
					if(StringUtils.isNotBlank(serviceProviderVO.getHelpDeskEmail())){
						selectedTicketVO.setAssignedSPEmail(serviceProviderVO.getHelpDeskEmail());
					}
					List<EscalationLevelVO> escalationLevelVOs = serviceProviderVO.getEscalationLevelList();
					if(escalationLevelVOs.isEmpty()){
						
					}else{
						List<EscalationLevelVO> finalEscalationList = new ArrayList<EscalationLevelVO>();
						for(EscalationLevelVO escalationVO:escalationLevelVOs){
							TicketEscalationVO ticketEscalationVO = ticketService.getEscalationStatus(selectedTicketVO.getTicketId(), escalationVO.getEscId());
							EscalationLevelVO tempEscalationVO = new EscalationLevelVO();
							if(ticketEscalationVO.getCustEscId()!=null){
								tempEscalationVO.setStatus("Escalated");
							}
							tempEscalationVO.setEscId(escalationVO.getEscId());
							tempEscalationVO.setEscalationEmail(escalationVO.getEscalationEmail());
							tempEscalationVO.setEscalationLevel(escalationVO.getEscalationLevel());
							tempEscalationVO.setLevelId(escalationVO.getLevelId());
							tempEscalationVO.setServiceProdviderId(escalationVO.getServiceProdviderId());
							tempEscalationVO.setEscalationPerson(escalationVO.getEscalationPerson());
							finalEscalationList.add(tempEscalationVO);
						}
						
						selectedTicketVO.setEscalationLevelList(finalEscalationList);
					}
					
					//selectedTicketVO.getEscalationLevelList().clear();
					selectedTicketVO.setEscalationLevelList(selectedTicketVO.getEscalationLevelList());
					response.setStatusCode(200);
					response.setObject(selectedTicketVO);
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
		} catch (Exception e) {
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
			logger.info("Exception in getting ticket response", e);
		}

		return responseEntity;
	}
	
	
	@RequestMapping(value = "/v1/ticket/attachements/{ticketNumber}", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<RestResponse> getTicketAttachments(@PathVariable(value="ticketNumber") String ticketNumber) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			List<TicketAttachment> fileAttachments = ticketAttachmentRepo.findByTicketNumber(ticketNumber);
			if(fileAttachments==null){
				logger.info("Not Ticket Attachment for "+ ticketNumber);
			}else{
				if(fileAttachments.isEmpty()){
					logger.info("Not Ticket Attachment for "+ ticketNumber);
				}else{
					TicketMVO ticketMVO = new TicketMVO();
					List<TicketAttachment> fileAttachmentList = new ArrayList<TicketAttachment>();
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
						for(TicketAttachment ticketAttachment : fileAttachments){
							ticketAttachment.setCreatedDate(formatter.format(ticketAttachment.getCreatedOn()));
							fileAttachmentList.add(ticketAttachment);
						}
						ticketMVO.setAttachments(fileAttachmentList);
						response.setObject(ticketMVO);
				}
			}
		} catch (Exception e) {
			response.setStatusCode(500);
			logger.info("Exception in getting ticket attachements", e);
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}
	
	
	
	@RequestMapping(value = "/v1/ticket/comments/{ticketId}", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<RestResponse> getTicketComments(@PathVariable(value="ticketId") Long ticketId) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			TicketMVO ticketMVO = new TicketMVO();
			List<TicketCommentVO> selectedTicketComments=ticketService.getTicketComments(ticketId);
			if(!selectedTicketComments.isEmpty()){
				ticketMVO.setTicketComments(selectedTicketComments);
				response.setStatusCode(200);
				response.setObject(ticketMVO);
				responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setStatusCode(500);
			logger.info("Exception in getting comments for tickect "+ ticketId, e);
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}
	

	@RequestMapping(value = "/v1/ticket/history/{ticketId}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<RestResponse> incidentSessionTicket(@PathVariable (value="ticketId") Long ticketId) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		List<TicketHistoryVO> selectedTicketHistory=ticketService.getTicketHistory(ticketId);
		try {
		if(!selectedTicketHistory.isEmpty()){
			response.setStatusCode(200);
			response.setObject(selectedTicketHistory);
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
		}
		}
		 catch (Exception e) {
				response.setStatusCode(500);
				logger.info("Exception in getting history for tickect "+ ticketId, e);
				responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.EXPECTATION_FAILED);
			}
			return responseEntity;
	}
	
	@RequestMapping(value = "/priority/sla/{spId}/{categoryId}", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<RestResponse> getPriorityAndSLA(@PathVariable(value="spId") Long spId, @PathVariable(value="categoryId") Long categoryId,
			final HttpSession session) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
			try {
				TicketPrioritySLAVO ticketPrioritySLAVO = ticketService.getTicketPriority(spId, categoryId);
				if(ticketPrioritySLAVO.getPriorityId()!= null){
					response.setStatusCode(200);
					response.setObject(ticketPrioritySLAVO);
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
				}else{
					response.setStatusCode(204);
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
				}

			} catch (Exception e) {
				logger.info("Exception in getting response", e);
				response.setMessage("Exception while getting Priority");
				response.setStatusCode(500);
				responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);

			}
		return responseEntity;
	}
	
	
	
	@RequestMapping(value = "/v1/ticket/comment/save", method = RequestMethod.POST, produces="application/json")
	public ResponseEntity<RestResponse> saveComment(@RequestBody TicketCommentVO ticketCommentVO) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
				User user = userService.findByEmail(ticketCommentVO.getCreatedBy());
				if(user!=null){
					LoginUser loginUser = new LoginUser();
					loginUser.setUserId(user.getUserId());
					loginUser.setUsername(user.getEmailId());
					TicketCommentVO savedComment = ticketService.saveTicketComment(ticketCommentVO, loginUser);
						if(savedComment.getCommentId()!=null){
							response.setStatusCode(200);
							response.setObject(savedComment);
							responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
						}
				}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
			logger.info("Exception while saving comment", e);
		}

		return responseEntity;
	}
	
	
	@RequestMapping(value = "/v1/ticket/linkedtickets/{ticketId}", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<RestResponse> getSPLinkedTickets(@PathVariable(value="ticketId") Long ticketId) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			TicketMVO ticketMVO = new TicketMVO();
			List<CustomerSPLinkedTicketVO> customerLinkedTickets = ticketService.getAllLinkedTickets(ticketId);
			if(!customerLinkedTickets.isEmpty()){
				ticketMVO.setLinkedTickets(customerLinkedTickets);
				response.setStatusCode(200);
				response.setObject(ticketMVO);
				responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setStatusCode(500);
			logger.info("Exception in getting linked tickects for "+ ticketId, e);
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}
	
	
	
	@RequestMapping(value = "/linkedticket/{custticket}/{custticketnumber}/{linkedticket}", method = RequestMethod.GET)
	public ResponseEntity<RestResponse> linked(@RequestParam(value="email") String email, @PathVariable (value="custticket") Long custTicket, @PathVariable (value="custticketnumber") String custTicketNumber,
			@PathVariable (value="linkedticket") String linkedTicket) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			User user = userService.findByEmail(email);
			if(user!=null){
				LoginUser loginUser = new LoginUser();
				loginUser.setUserId(user.getUserId());
				loginUser.setUsername(user.getEmailId());
				CustomerSPLinkedTicketVO savedTicketLinked = ticketService.saveLinkedTicket(custTicket,custTicketNumber, linkedTicket, loginUser);
					if(savedTicketLinked.getId()!=null){
						response.setStatusCode(200);
						response.setObject(savedTicketLinked);
						responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
					}
			}
		} catch (Exception e) {
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
			logger.info("Exception while saving linked ticket", e);
		}

		return responseEntity;
	}
	
	@RequestMapping(value = "/linkedticket/status/{linkedTicket}/{status}", method = RequestMethod.GET)
	public ResponseEntity<RestResponse> changeStatusLinkedTicket(@RequestParam(value="email") String email, 
			@PathVariable (value="linkedTicket") Long linkedTicket) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			User user = userService.findByEmail(email);
			if(user!=null){
				LoginUser loginUser = new LoginUser();
				loginUser.setUserId(user.getUserId());
				loginUser.setUsername(user.getEmailId());
						CustomerSPLinkedTicketVO linkedTicketStatus = ticketService.changeLinkedTicketStatus(linkedTicket,"CLOSE", loginUser.getUsername());
						if(linkedTicketStatus.getClosedFlag().equalsIgnoreCase("CLOSED")){
							response.setStatusCode(200);
							responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
							/*List<CustomerSPLinkedTicketVO> customerLinkedTickets = ticketService.getAllLinkedTickets(Long.parseLong(linkedTicketStatus.getCustTicketId()));
							if(!customerLinkedTickets.isEmpty()){
								response.setStatusCode(200);
								response.setObject(customerLinkedTickets);
								responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
							}*/
						}
					}
		} catch (Exception e) {
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
			logger.info("Exception while getting listLinkedTickets", e);
		}

		return responseEntity;
	}
	
	@RequestMapping(value = "/linkedticket/delete/{linkedTicketId}", method = RequestMethod.GET)
	public ResponseEntity<RestResponse> deleteLinkedTicket(@RequestParam(value="email") String email,
			@PathVariable (value="linkedTicketId") Long linkedTicketId) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			User user = userService.findByEmail(email);
			if(user!=null){
				LoginUser loginUser = new LoginUser();
				loginUser.setUserId(user.getUserId());
				loginUser.setUsername(user.getEmailId());
				CustomerSPLinkedTicket customerSPLinkedTicket = ticketService.deleteLinkedTicket(linkedTicketId, loginUser.getUsername());
				if(customerSPLinkedTicket.getDelFlag()==1){
				response.setStatusCode(200);
				logger.info("Customer Linked ticket :"+ customerSPLinkedTicket.getSpTicketNo() +"deleted successfully.");
					response.setObject(customerSPLinkedTicket);
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			logger.info("Exception while deleting", e);
		}

		return responseEntity;
	}
	
	@RequestMapping(value = "/v1/ticket/comment/list/{ticketId}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<RestResponse> commentLint(@PathVariable (value="ticketId") Long ticketId) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try{
			List<TicketCommentVO> selectedTicketComments=ticketService.getTicketComments(ticketId);
			if(!selectedTicketComments.isEmpty()){
				response.setStatusCode(200);
				response.setObject(selectedTicketComments);
				responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
			}
			else{
				response.setStatusCode(404);
				responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
			e.printStackTrace();
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
			logger.info("Exception while getting comments", e);
		}
		return responseEntity;
	}
	
	@RequestMapping(value = "/v1/ticket/escalate", method = RequestMethod.POST)
	public ResponseEntity<RestResponse> escalate(@RequestParam(value="email") String email,
			@RequestBody TicketEscalationVO ticketEscalationLevels) {
		logger.info("Inside IncidentController .. escalate");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		TicketEscalationVO savedTicketEscalation =null;
		try {
			User user = userService.findByEmail(email);
			if(user!=null){
				LoginUser loginUser = new LoginUser();
				loginUser.setUserId(user.getUserId());
				loginUser.setUsername(user.getEmailId());
				
				savedTicketEscalation = ticketService.saveTicketEscalations(ticketEscalationLevels, loginUser);
					if(savedTicketEscalation.getEscId()!=null){
						response.setStatusCode(200);
						response.setObject(savedTicketEscalation);
						response.setMessage("Incident "+savedTicketEscalation.getTicketNumber()+ "escalated to "+savedTicketEscalation.getEscLevelDesc());
						responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
					}
			}
		} catch (Exception e) {
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
			logger.info("Exception while escalations", e);
		}
		if(response.getStatusCode() == 200){
			List<String> escCCMailList = new ArrayList<String>(0);
			SPEscalationLevels spEscalationLevel = null;
			try{
			TicketVO selectedTicketVO = ticketService.getSelectedTicket(savedTicketEscalation.getTicketId());
			ServiceProviderVO serviceProviderVO = serviceProviderService.findServiceProvider(selectedTicketVO.getAssignedTo());
			if(StringUtils.isNotBlank(serviceProviderVO.getHelpDeskEmail())){
				selectedTicketVO.setAssignedSPEmail(serviceProviderVO.getHelpDeskEmail());
			}
			List<EscalationLevelVO> escalationLevelVOs = serviceProviderVO.getEscalationLevelList();
			/*if(escalationLevelVOs.isEmpty()){
				
			}else{
				List<EscalationLevelVO> finalEscalationList = new ArrayList<EscalationLevelVO>();
				for(EscalationLevelVO escalationVO:escalationLevelVOs){
					TicketEscalationVO ticketEscalationVO = ticketService.getEscalationStatus(selectedTicketVO.getTicketId(), escalationVO.getEscId());
					EscalationLevelVO tempEscalationVO = new EscalationLevelVO();
					if(ticketEscalationVO.getCustEscId()!=null){
						
					}
				}
				
			}*/
			
			//List<EscalationLevelVO> escalationLevelVOs = ticketEscalationLevels.getTicketData().getEscalationLevelList();
			
			if(escalationLevelVOs.size()==0){
				logger.info("No escalation list available");
			}else{
				logger.info("Escalation Level list : "+  escalationLevelVOs.size());
				spEscalationLevel = spEscalationRepo.findOne(savedTicketEscalation.getEscId());
				for(EscalationLevelVO escLevelVO: escalationLevelVOs){
					TicketEscalationVO ticketEscalationVO = ticketService.getEscalationStatus(selectedTicketVO.getTicketId(), escLevelVO.getEscId());
					if(StringUtils.isNotBlank(ticketEscalationVO.getEscalationStatus())){
						escCCMailList.add(escLevelVO.getEscalationEmail());
					}
				}
				logger.info("escCCMailList :" + escCCMailList);
			}
			
				if(spEscalationLevel!=null){
					String ccEscList= "";
					if(!escCCMailList.isEmpty()){	
						ccEscList= StringUtils.join(escCCMailList, ',');
						logger.info("Escalation To List : "+  spEscalationLevel.getEscalationEmail());
						logger.info("Escalation CC List : "+  ccEscList);
						emailService.successEscalationLevel(selectedTicketVO, spEscalationLevel, ccEscList, savedTicketEscalation.getEscLevelDesc());
					}else{
						emailService.successEscalationLevel(selectedTicketVO, spEscalationLevel, ccEscList, savedTicketEscalation.getEscLevelDesc());
					}
				}	
				} catch (Exception e) {
					logger.info("Exception while sending email", e);
				}
				
			}else{
				logger.info("No ticket escalated for SP");
			}
		
		logger.info("Exit IncidentController .. escalate");
		return responseEntity;
	}
	
	
	@RequestMapping(value = "/v1/create", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> createNewIncident(@RequestBody final TicketVO ticketVO) {
		logger.info("Inside IncidentController .. createNewIncident");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		TicketVO savedTicketVO = null;
		User user = userService.findByEmail(ticketVO.getCreatedBy());
		if(user!=null){
			LoginUser loginUser = new LoginUser();
			loginUser.setUserId(user.getUserId());
			loginUser.setUsername(user.getEmailId());
			loginUser.setCompany(user.getCompany());
			try {
				logger.info("TicektVO : "+ ticketVO);
				savedTicketVO = ticketService.saveOrUpdate(ticketVO, loginUser, null);
				if(savedTicketVO.getTicketId()!= null && savedTicketVO.getMessage().equalsIgnoreCase("CREATED")){
					response.setStatusCode(200);
					response.setObject(savedTicketVO);
					response.setMessage("New Incident created successfully");
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
					/*List<TicketAttachment> fileAttachmentList = getIncidentAttachments(ticketVO.getTicketNumber());
					if(!fileAttachmentList.isEmpty()){
					savedTicketVO.setAttachments(fileAttachmentList);
					}*/
			
				}else if(savedTicketVO.getTicketId()!= null && savedTicketVO.getMessage().equalsIgnoreCase("UPDATED")){
					response.setStatusCode(200);
					response.setObject(savedTicketVO);
					response.setMessage("New Incident updated successfully");
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
					/*List<TicketAttachment> fileAttachmentList = getIncidentAttachments(ticketVO.getTicketNumber());
					if(!fileAttachmentList.isEmpty()){
						savedTicketVO.setAttachments(fileAttachmentList);
						}
					response.setObject(savedTicketVO);
					response.setMessage("Incident updated successfully");
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);*/
				}

			} catch (Exception e) {
				logger.info("Exception in getting response", e);
				response.setMessage("Exception while creating an incident");
				response.setStatusCode(500);
				responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			if(response.getStatusCode()==200 && savedTicketVO.getMessage().equalsIgnoreCase("CREATED")){
				try {
					  savedTicketVO.setCreatedUser(user.getFirstName() +" "+ user.getLastName());
					  emailService.successTicketCreationSPEmail(savedTicketVO, "CREATED", loginUser.getCompany().getCompanyName());
				 } catch (Exception e) {
					 logger.info("Exception in sending incident creation mail", e);
				}


			}/*else if(response.getStatusCode()==200 && savedTicketVO.getMessage().equalsIgnoreCase("UPDATED")){

			}
			/*else if(response.getStatusCode()==200 && savedTicketVO.getMessage().equalsIgnoreCase("UPDATED")){


			}
			/*else if(response.getStatusCode()==200 && savedTicketVO.getMessage().equalsIgnoreCase("UPDATED")){

				try {
					 emailResponse = emailService.successTicketCreationSPEmail(savedTicketVO, "UPDATED");
				 } catch (Exception e) {
					 logger.info("Exception in sending incident update mail", e);
				}
			}*/
		}

		logger.info("Exit IncidentController .. createNewIncident");
		return responseEntity;
	}
	
}
