package com.ops.web.service;

import java.util.List;

import com.ops.app.vo.CustomerSPLinkedTicketVO;
import com.ops.app.vo.CustomerTicketVO;
import com.ops.app.vo.IncidentVO;
import com.ops.app.vo.LoginUser;
import com.ops.app.vo.SPLoginVO;
import com.ops.app.vo.TicketCommentVO;
import com.ops.app.vo.TicketEscalationVO;
import com.ops.app.vo.TicketHistoryVO;
import com.ops.app.vo.TicketMVO;
import com.ops.app.vo.TicketPrioritySLAVO;
import com.ops.app.vo.TicketVO;
import com.ops.jpa.entities.CustomerSPLinkedTicket;
import com.ops.jpa.entities.CustomerTicket;


public interface TicketService {

	public TicketVO saveOrUpdate(TicketVO customerTicket, LoginUser user, SPLoginVO savedLoginVO) throws Exception;

	public CustomerTicket saveOrUpdate(CustomerTicket customerTicket) throws Exception;

	/*public List<CustomerTicketVO> getOpenCustomerTickets() throws Exception;*/

	public List<CustomerTicket> getTicketsByStatus(Long statusId) throws Exception;

	public List<CustomerTicket> getOpenTicketsBySite(Long siteId) throws Exception;


	public CustomerTicketVO getCustomerTicket(Long ticktId) throws Exception;

	public List<TicketMVO> getAllCustomerTickets(LoginUser loginUser) throws Exception;

	public TicketVO getSelectedTicket(Long ticketId) throws Exception;
	
	public TicketPrioritySLAVO getTicketPriority(Long serviceProviderID, Long ticketCategoryId);

	public TicketEscalationVO saveTicketEscalations(TicketEscalationVO ticketEscalationLevel, LoginUser user) throws Exception;

	public CustomerSPLinkedTicketVO saveLinkedTicket(Long custTicket, String custTicketNumber, String linkedTicket, LoginUser loginUser) throws Exception;

	public List<CustomerSPLinkedTicketVO> getAllLinkedTickets(Long custTicket) throws Exception;

	public CustomerSPLinkedTicket deleteLinkedTicket(Long linkedTicketId, String email);

	public List<TicketEscalationVO> getAllEscalationLevels(Long ticketId);

	public TicketEscalationVO getEscalationStatus(Long ticketId, Long escId);
	
	public List<TicketHistoryVO> getTicketHistory(Long ticketId);

	public CustomerSPLinkedTicketVO changeLinkedTicketStatus(Long linkedTicket, String status, String updatedBy) throws Exception;
	
	public List<TicketCommentVO> getTicketComments(Long ticketId);
	
	public TicketCommentVO saveTicketComment(TicketCommentVO ticketCommentVO, LoginUser user) throws Exception;

	public List<TicketVO> getCustomerTicketsBySP(Long spId) throws Exception;

	public CustomerSPLinkedTicketVO saveSPLinkedTicket(Long custTicket, String custTicketNumber, String linkedTicket, String spEmail) throws Exception;

	public TicketCommentVO saveSPTicketComment(TicketCommentVO ticketCommentVO, SPLoginVO savedLoginVO) throws Exception;
	
	public List<IncidentVO> getUserTickets(LoginUser loginUser) throws Exception;

}
