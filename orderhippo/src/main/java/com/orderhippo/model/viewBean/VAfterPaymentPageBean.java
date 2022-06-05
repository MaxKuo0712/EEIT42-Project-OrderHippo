package com.orderhippo.model.viewBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 10. 訂單結帳後的確認頁
@Entity
@Table(name="v_after_payment_page")
public class VAfterPaymentPageBean {
	@Id
	@Column(name="ORDER_ID")
	private String orderid;
	
	@Column(name="STORE_NAME")
	private String storename;
	
	@Column(name="PAYMENT_PRICE")
	private Integer paymentprice;
	
	@Column(name="PAYMENT_CATEGORY")
	private String paymentcategory;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public Integer getPaymentprice() {
		return paymentprice;
	}

	public void setPaymentprice(Integer paymentprice) {
		this.paymentprice = paymentprice;
	}

	public String getPaymentcategory() {
		return paymentcategory;
	}

	public void setPaymentcategory(String paymentcategory) {
		this.paymentcategory = paymentcategory;
	}
	
	
}
