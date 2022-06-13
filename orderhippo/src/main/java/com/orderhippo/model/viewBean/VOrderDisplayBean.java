package com.orderhippo.model.viewBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 7. 訂單頁面顯示
@Entity
@Table(name = "v_order_display")
public class VOrderDisplayBean {
	
	@Id
	@Column(name = "ORDER_ID")
	private String orderid;
	
	@Column(name = "ORDER_STATUS", columnDefinition = "ENUM")
	private String orderstatus;
	
	@Column(name = "ORDER_STATUS_NAME")
	private String orderstatusname;
	
	@Column(name = "USER_NAME")
	private String username;
	
	@Column(name = "MEAL_ORDER_QTY", columnDefinition = "mediumtext")
//	@Column(name = "MEAL_ORDER_QTY", columnDefinition = "text")
	private String mealorderqty;
	
	@Column(name = "ORDERS_PRICE", columnDefinition = "VARBINARY")
	private String ordersprice;
	
	@Column(name = "ORDER_DESC", columnDefinition = "longtext")
	private String orderdesc;
	
	@Column(name = "CREATE_TIME")
	private java.util.Date createtime;
	
	@Column(name = "USER_PHONE")
	private String userphone;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getOrderstatusname() {
		return orderstatusname;
	}

	public void setOrderstatusname(String orderstatusname) {
		this.orderstatusname = orderstatusname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMealorderqty() {
		return mealorderqty;
	}

	public void setMealorderqty(String mealorderqty) {
		this.mealorderqty = mealorderqty;
	}

	public String getOrdersprice() {
		return ordersprice;
	}

	public void setOrdersprice(String ordersprice) {
		this.ordersprice = ordersprice;
	}

	public String getOrderdesc() {
		return orderdesc;
	}

	public void setOrderdesc(String orderdesc) {
		this.orderdesc = orderdesc;
	}

	public java.util.Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}

	public String getUserphone() {
		return userphone;
	}

	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}
}
