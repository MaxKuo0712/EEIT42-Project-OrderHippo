package com.orderhippo.model.viewBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 3. 銷售前10名
@Entity
@Table(name = "v_sale_rank")
public class VSaleRankBean {
	
	@Id
	@Column(name = "MEAL_NAME")
	private String mealname;
	
	@Column(name = "MEAL_PRICE")
	private Integer mealprice;
	
	@Column(name = "COUNT", columnDefinition = "decimal")
//	@Column(name = "COUNT", columnDefinition = "bigint")
	private Integer count;

	public String getMealname() {
		return mealname;
	}

	public void setMealname(String mealname) {
		this.mealname = mealname;
	}

	public Integer getMealprice() {
		return mealprice;
	}

	public void setMealprice(Integer mealprice) {
		this.mealprice = mealprice;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
