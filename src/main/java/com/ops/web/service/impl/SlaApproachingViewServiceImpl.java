package com.ops.web.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.jpa.entities.SlaApproachingView;
import com.ops.jpa.repository.SlaApproachingRepo;
import com.ops.web.service.SlaApproachingViewService;

@Service("slaApproachinService")
public class SlaApproachingViewServiceImpl implements SlaApproachingViewService {

	@Autowired
	private SlaApproachingRepo slaApproachingRepo;

	@Override
	public List<SlaApproachingView> findTicketsApproachingSLA(final int noOfDays) {
		return null;
	}

	@Override
	public List<SlaApproachingView> findTicketsApproachingSLA() {
		List<SlaApproachingView> slaDataViews = slaApproachingRepo.findAll();
		return slaDataViews == null?Collections.EMPTY_LIST:slaDataViews;
	}


}
