package com.ops.web.service;

import java.util.List;

import com.ops.app.vo.SPTicketEscalatedVO;
import com.ops.app.vo.SPTicketPriorityVO;
import com.ops.jpa.entities.TicketEscalationCountView;


public interface TicketEscalationCountService {


	public List<TicketEscalationCountView> getAllTicketCount() throws Exception;

	public List<TicketEscalationCountView> getEscalatedTicketCount() throws Exception;


	public List<SPTicketEscalatedVO> getSPEscalatedTicketCount() throws Exception;

	public List<SPTicketPriorityVO> getSPPriorityTicketCount() throws Exception;


}
