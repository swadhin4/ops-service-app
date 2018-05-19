package com.ops.web.service;

import java.util.List;

import com.ops.jpa.entities.OpenTicketsView;
import com.ops.jpa.entities.TicketSiteView;

public interface OpenTicketService {

	public List<OpenTicketsView> findOpenTicketsViews();

	public List<TicketSiteView> findAllTicketViews();

}
