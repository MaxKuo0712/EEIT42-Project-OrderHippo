package com.orderhippo.model.viewBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 6. 性別數量 - 百分比可用於圓餅圖
@Entity
@Table(name = "v_gender_chart")
public class VGenderChartBean {
	
	@Id
	@Column(name = "USER_GENDER", columnDefinition = "ENUM")
	private String usergender;
	
	@Column(name = "GENDER_COUNT", columnDefinition = "BIGINT")
	private Long gendercount;
	
	@Column(name = "PERCENTAGE", columnDefinition = "DECIMAL")
	private Double percentage;

	public String getUsergender() {
		return usergender;
	}

	public void setUsergender(String usergender) {
		this.usergender = usergender;
	}

	public Long getGendercount() {
		return gendercount;
	}

	public void setGendercount(Long gendercount) {
		this.gendercount = gendercount;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
}
