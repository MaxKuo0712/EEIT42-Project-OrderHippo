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
	private String mealprice;
	
	@Column(name = "MEAL_NAME", columnDefinition = "mediumtext")
	private String mealname;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getMealprice() {
		return mealprice;
	}

	public void setMealprice(String mealprice) {
		this.mealprice = mealprice;
	}

	public String getMealname() {
		return mealname;
	}

	public void setMealname(String mealname) {
		this.mealname = mealname;
	}
}
