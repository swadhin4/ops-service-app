package com.ops.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.jpa.entities.OpenTicketsView;
import com.ops.jpa.entities.TicketSiteView;
import com.ops.jpa.repository.OpenTicketsRepo;
import com.ops.jpa.repository.TicketViewRepo;
import com.ops.web.service.OpenTicketService;

@Service("openTicketViewService")
public class OpenTicketServiceImpl implements OpenTicketService {

	@Autowired
	private OpenTicketsRepo openTicketsRepo;

	@Autowired
	private TicketViewRepo ticketViewRepo;

	@Override
	public List<OpenTicketsView> findOpenTicketsViews() {
		List<OpenTicketsView> openTicketList = new ArrayList<OpenTicketsView>();
		openTicketList = openTicketsRepo.findAll();
		return openTicketList == null?Collections.EMPTY_LIST:openTicketList;
	}

	@Override
	public List<TicketSiteView> findAllTicketViews() {
		List<TicketSiteView> ticketViewList = new ArrayList<TicketSiteView>();
		ticketViewList = ticketViewRepo.findAll();
		return ticketViewList == null?Collections.EMPTY_LIST:ticketViewList;
	}

}
