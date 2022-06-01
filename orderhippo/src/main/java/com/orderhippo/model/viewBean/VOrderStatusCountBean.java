package com.orderhippo.model.viewBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 2. 訂單筆數 1未確認訂單, 2已確認訂單, 3已完成訂單 4取消的訂單

@Entity
@Table(name="v_order_status_count")
public class VOrderStatusCountBean {
	
	@Id
	@Column(name = "ORDER_STATUS", columnDefinition = "ENUM")
	private String orderstatus;
	
	@Column(name = "ORDER_STATUS_DESC")
	private String orderstatusdesc;
	
	@Column(name = "ORDER_COUNT", columnDefinition = "BIGINT")
	private Long ordercount;

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getOrderstatusdesc() {
		return orderstatusdesc;
	}

	public void setOrderstatusdesc(String orderstatusdesc) {
		this.orderstatusdesc = orderstatusdesc;
	}

	public Long getOrdercount() {
		return ordercount;
	}

	public void setOrdercount(Long ordercount) {
		this.ordercount = ordercount;
	}
}
