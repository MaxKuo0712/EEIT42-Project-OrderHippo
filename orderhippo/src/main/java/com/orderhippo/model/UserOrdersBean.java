package com.orderhippo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "USER_ORDERS")
@DynamicInsert(true)
@DynamicUpdate(true)
public class UserOrdersBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ID")
	@Column(name = "ID")
	private Integer id;
	
	@JsonProperty("USER_ID")
	@Column(name = "USER_ID")
	private String userid;
	
	@JsonProperty("ORDER_ID")
	@Column(name = "ORDER_ID")
	private String orderid;
	
	@JsonProperty("MEAL_ID")
	@Column(name = "MEAL_ID")
	private String mealid;
	
	@JsonProperty("MEAL_NAME")
	@Column(name = "MEAL_NAME")
	private String mealname;
	
	@JsonProperty("ORDER_MEAL_QTY")
	@Column(name = "ORDER_MEAL_QTY")
	private Integer ordermealqty;
	
	@JsonProperty("MEAL_PRICE")
	@Column(name = "MEAL_PRICE")
	private Integer mealprice;
	
	@JsonProperty("STORE_ID")
	@Column(name = "STORE_ID")
	private String storeid;
	
	@JsonProperty("USER_MAIL")
	@Column(name = "USER_MAIL")
	private String usermail;
	
	@JsonProperty("ORDER_STATUS")
	@Column(name = "ORDER_STATUS")
	private Integer orderstatus;
	
	@JsonProperty("CREATE_ID")
	@Column(name = "CREATE_ID", updatable = false, insertable = false)
	private String createtid;
	
	@JsonProperty("CREATE_TIME")
	@Column(name = "CREATE_TIME", updatable = false, insertable = false)
	private Date createtime;
	
	@JsonProperty("REVISE_ID")
	@Column(name = "REVISE_ID", insertable = false)
	private String reviseid;
	
	@JsonProperty("REVISE_TIME")
	@Column(name = "REVISE_TIME", insertable = false)
	private Date revisetime;

	@Override
	public String toString() {
		return "UserOrdersBean [id=" + id + ", userid=" + userid + ", orderid=" + orderid + ", mealid=" + mealid
				+ ", mealname=" + mealname + ", ordermealqty=" + ordermealqty + ", mealprice=" + mealprice
				+ ", storeid=" + storeid + ", usermail=" + usermail + ", orderstatus=" + orderstatus + ", createtid="
				+ createtid + ", createtime=" + createtime + ", reviseid=" + reviseid + ", revisetime=" + revisetime
				+ "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getMealid() {
		return mealid;
	}

	public void setMealid(String mealid) {
		this.mealid = mealid;
	}

	public String getMealname() {
		return mealname;
	}

	public void setMealname(String mealname) {
		this.mealname = mealname;
	}

	public Integer getOrdermealqty() {
		return ordermealqty;
	}

	public void setOrdermealqty(Integer ordermealqty) {
		this.ordermealqty = ordermealqty;
	}

	public Integer getMealprice() {
		return mealprice;
	}

	public void setMealprice(Integer mealprice) {
		this.mealprice = mealprice;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}

	public Integer getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Integer orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getCreatetid() {
		return createtid;
	}

	public void setCreatetid(String createtid) {
		this.createtid = createtid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getReviseid() {
		return reviseid;
	}

	public void setReviseid(String reviseid) {
		this.reviseid = reviseid;
	}

	public Date getRevisetime() {
		return revisetime;
	}

	public void setRevisetime(Date revisetime) {
		this.revisetime = revisetime;
	}
}