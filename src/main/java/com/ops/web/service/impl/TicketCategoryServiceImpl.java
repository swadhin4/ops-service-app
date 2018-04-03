package com.ops.web.service.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.jpa.entities.TicketCategory;
import com.ops.jpa.repository.TicketCategoryRepo;
import com.ops.web.service.TicketCategoryService;

@Service("ticketCategoryService")
public class TicketCategoryServiceImpl implements TicketCategoryService {

	private final static Logger LOGGER = LoggerFactory.getLogger(TicketCategoryServiceImpl.class);

	@Autowired
	private TicketCategoryRepo ticketCategoryRepo;

	@Override
	public List<TicketCategory> getAllTicketCategories() throws Exception {
		List<TicketCategory> ticketCatgoryList =null;
		ticketCatgoryList = ticketCategoryRepo.findAll();
		return ticketCatgoryList == null?Collections.EMPTY_LIST:ticketCatgoryList;
	}


}
