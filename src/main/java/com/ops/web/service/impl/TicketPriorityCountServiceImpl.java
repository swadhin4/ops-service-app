package com.ops.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.jpa.entities.TicketPriorityCountView;
import com.ops.jpa.repository.TicketPriorityCountViewRepo;
import com.ops.web.service.TicketPriorityCountService;

@Service("ticketPriorityCountService")
public class TicketPriorityCountServiceImpl implements TicketPriorityCountService {

	@Autowired
	private TicketPriorityCountViewRepo ticketCountPriorityViewRepo;

	@Override
	public List<TicketPriorityCountView> getAllTicketCount() throws Exception {

		List<TicketPriorityCountView> ticketPrioritCountList = ticketCountPriorityViewRepo.findAll();
		return ticketPrioritCountList == null ? Collections.EMPTY_LIST : ticketPrioritCountList;
	}

	@Override
	public List<TicketPriorityCountView> getPriorityTicketCount()
			throws Exception {
		List<TicketPriorityCountView> priorityTicketCountList = new ArrayList<TicketPriorityCountView>();
		List<Object[]> priorityCountList = ticketCountPriorityViewRepo.listPriorityTIckets();
		for (Object[] element : priorityCountList) {
			TicketPriorityCountView ticketPriorityCountView =new TicketPriorityCountView();
			ticketPriorityCountView.setSiteId(Long.parseLong(element[0].toString()));
			ticketPriorityCountView.setSiteCode(element[1].toString());
			ticketPriorityCountView.setSiteName(element[2].toString());
			ticketPriorityCountView.setPriority(element[3].toString());
			ticketPriorityCountView.setTicketCountFrom(Long.parseLong(element[4].toString()));

			priorityTicketCountList.add(ticketPriorityCountView);
		}
		return priorityTicketCountList == null?Collections.EMPTY_LIST:priorityTicketCountList;
	}



}
