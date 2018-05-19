package com.ops.jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="pm_site_sales_op_time")
public class SiteSalesOperation implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6899719699092410722L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="op_id")
	private Long saledsOpId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="site_id")
	private Site site;

	@Column(name="day_of_week", nullable=false)
	private String dayOfWeek;

	@Column(name="op_start_time",nullable=false)
	private String opStartTime;

	@Column(name="op_close_time", nullable=false)
	private String opCloseTime;


	public SiteSalesOperation() {
		super();
	}


	public Long getSaledsOpId() {
		return saledsOpId;
	}


	public void setSaledsOpId(final Long saledsOpId) {
		this.saledsOpId = saledsOpId;
	}


	public Site getSite() {
		return site;
	}

	public void setSite(final Site site) {
		this.site = site;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(final String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getOpStartTime() {
		return opStartTime;
	}

	public void setOpStartTime(final String opStartTime) {
		this.opStartTime = opStartTime;
	}

	public String getOpCloseTime() {
		return opCloseTime;
	}

	public void setOpCloseTime(final String opCloseTime) {
		this.opCloseTime = opCloseTime;
	}


}
