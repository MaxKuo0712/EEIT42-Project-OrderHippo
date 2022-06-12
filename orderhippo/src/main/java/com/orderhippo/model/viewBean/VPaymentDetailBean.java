package com.orderhippo.model.viewBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="v_payment_detail")
public class VPaymentDetailBean {
	
	@Id
	@Column(name = "ORDER_ID")
	private String orderid;
	
	@Column(name = "MEAL_PRICE", columnDefinition = "decimal")
	private Integer mealprice;
	
	@Column(name = "MEALS", columnDefinition = "mediumtext")
//	@Column(name = "MEALS", columnDefinition = "text")
	private String meals;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Integer getMealprice() {
		return mealprice;
	}

	public void setMealprice(Integer mealprice) {
		this.mealprice = mealprice;
	}

	public String getMeals() {
		return meals;
	}

	public void setMeals(String meals) {
		this.meals = meals;
	}
}
