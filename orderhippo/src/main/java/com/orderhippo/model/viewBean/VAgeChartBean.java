package com.orderhippo.model.viewBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 5. 年齡數量 - 百分比可用於圓餅圖
@Entity
@Table(name = "v_age_chart")
public class VAgeChartBean {
	
	@Id
	@Column(name = "AGE_RANGE")
	private String agerange;
	
	@Column(name = "QTY", columnDefinition = "BIGINT")
	private Long qty;
	
	@Column(name = "PERCENTAGE", columnDefinition = "DECIMAL")
	private Double percentage;

	public String getAgerange() {
		return agerange;
	}

	public void setAgerange(String agerange) {
		this.agerange = agerange;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
}
