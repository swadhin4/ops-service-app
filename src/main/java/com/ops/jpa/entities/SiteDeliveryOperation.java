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
@Table(name="pm_site_delivery_op_time")
public class SiteDeliveryOperation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2281069998582556473L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="op_id")
	private Long deliveryOpId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="site_id")
	private Site site;

	@Column(name="day_of_week", nullable=false)
	private String dayOfWeek;

	@Column(name="op_start_time", nullable=false)
	private String opStartTime;

	@Column(name="op_close_time", nullable=false)
	private String opCloseTime;


	public SiteDeliveryOperation() {
		super();
	}

	public Long getDeliveryOpId() {
		return deliveryOpId;
	}

	public void setDeliveryOpId(final Long deliveryOpId) {
		this.deliveryOpId = deliveryOpId;
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
