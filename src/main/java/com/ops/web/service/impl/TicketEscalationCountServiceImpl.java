package com.ops.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.app.vo.SPTicketEscalatedVO;
import com.ops.app.vo.SPTicketPriorityVO;
import com.ops.jpa.entities.TicketEscalationCountView;
import com.ops.jpa.repository.CompanyRepo;
import com.ops.jpa.repository.TicketEscalationCountViewRepo;
import com.ops.web.service.TicketEscalationCountService;

@Service("ticketEscalationCountService")
public class TicketEscalationCountServiceImpl implements TicketEscalationCountService {

	@Autowired
	private TicketEscalationCountViewRepo ticketEscaltionViewRepo;


	@Autowired
	private CompanyRepo companyRepo;

	@Override
	public List<TicketEscalationCountView> getAllTicketCount() throws Exception {
		List<TicketEscalationCountView> ticketEscalationCountList = ticketEscaltionViewRepo.findAll();
		return ticketEscalationCountList == null ? Collections.EMPTY_LIST : ticketEscalationCountList;
	}

	@Override
	public List<TicketEscalationCountView> getEscalatedTicketCount() throws Exception {
		List<Object[]> ticketEscalationCountList = ticketEscaltionViewRepo.findTicketEscalatedCount();
		List<TicketEscalationCountView> ticketEscaltedCountList = new ArrayList<TicketEscalationCountView>();

		for(Object[] element:ticketEscalationCountList){
			TicketEscalationCountView ticketEscalationCountView =  new TicketEscalationCountView();
			ticketEscalationCountView.setSiteId(Long.parseLong(element[0].toString()));
			ticketEscalationCountView.setSiteCode(element[1].toString());
			ticketEscalationCountView.setSiteName(element[2].toString());
			ticketEscalationCountView.setTicketCount(Long.parseLong(element[3].toString()));
			ticketEscaltedCountList.add(ticketEscalationCountView);
		}
		return ticketEscaltedCountList;
	}

	@Override
	public List<SPTicketEscalatedVO> getSPEscalatedTicketCount()
			throws Exception {
		List<Object[]> ticketEscalationCountList = companyRepo.findTicketEscalatedCount();
		List<SPTicketEscalatedVO> ticketEscaltedCountList = new ArrayList<SPTicketEscalatedVO>();

		for(Object[] element:ticketEscalationCountList){
			SPTicketEscalatedVO ticketEscalationCountView =  new SPTicketEscalatedVO();
			ticketEscalationCountView.setCompanyId(Integer.parseInt(element[0].toString()));
			ticketEscalationCountView.setCompanyName(element[1].toString());
			ticketEscalationCountView.setTicketCount(Integer.parseInt(element[2].toString()));
			ticketEscaltedCountList.add(ticketEscalationCountView);
		}
		return ticketEscaltedCountList;
	}

	@Override
	public List<SPTicketPriorityVO> getSPPriorityTicketCount()
			throws Exception {
		List<Object[]> ticketPriorityCountList = companyRepo.findTicketPriorityCount();
		List<SPTicketPriorityVO> ticketPrioritySPList = new ArrayList<SPTicketPriorityVO>();

		for(Object[] element:ticketPriorityCountList){
			SPTicketPriorityVO ticketPriorityCountView =  new SPTicketPriorityVO();
			ticketPriorityCountView.setCompanyId(Integer.parseInt(element[0].toString()));
			ticketPriorityCountView.setCompanyName(element[1].toString());
			ticketPriorityCountView.setPriority(element[2].toString());
			ticketPriorityCountView.setTicketCount(Integer.parseInt(element[3].toString()));
			ticketPrioritySPList.add(ticketPriorityCountView);
		}
		return ticketPrioritySPList;
	}



}
