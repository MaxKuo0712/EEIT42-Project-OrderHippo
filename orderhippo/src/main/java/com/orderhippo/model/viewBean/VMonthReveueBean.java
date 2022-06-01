package com.orderhippo.model.viewBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 4. 每月營收 - 3已完成訂單

@Entity
@Table(name="v_month_revenue")
public class VMonthReveueBean {
	
	@Id
	@Column(name = "YEAR")
	private Integer year;
	
	@Column(name = "MONTH")
	private Integer month;
	
	@Column(name = "REVENUE_OF_MONTH", columnDefinition = "DECIMAL")
	private Double revenueofmonth;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Double getRevenueofmonth() {
		return revenueofmonth;
	}

	public void setRevenueofmonth(Double revenueofmonth) {
		this.revenueofmonth = revenueofmonth;
	}
}
