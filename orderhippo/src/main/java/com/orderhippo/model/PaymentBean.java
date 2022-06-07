package com.orderhippo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "PAYMENT")
@DynamicInsert(true)
@DynamicUpdate(true)
public class PaymentBean {
	
	@Id
	@ApiModelProperty(hidden = true)
	@JsonProperty("ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@JsonProperty("PAYMENT_ID")
	@Column(name = "PAYMENT_ID", updatable = false, unique = true)
	private String paymentid;
	
	@JsonProperty("ORDER_ID")
	@Column(name = "ORDER_ID")
	private String orderid;
	
	@JsonProperty("USER_ID")
	@Column(name = "USER_ID")
	private String userid;
	
	@JsonProperty("STORE_ID")
	@Column(name = "STORE_ID")
	private String storeid;
	
	@JsonProperty("PAYMENT_PRICE")
	@Column(name = "PAYMENT_PRICE")
	private Integer paymentprice;
	
	@JsonProperty("PAYMENT_CATEGORY")
	@Column(name = "PAYMENT_CATEGORY", columnDefinition="ENUM")
	private String paymentcategory;
	
	@JsonProperty("PAYMENT_STATUS")
	@Column(name = "PAYMENT_STATUS")
	private Boolean paymentstatus;
	
	@ApiModelProperty(hidden = true)
	@JsonProperty("CREATE_ID")
	@Column(name = "CREATE_ID", updatable = false)
	private String createid;
	
	@ApiModelProperty(hidden = true)
	@JsonProperty("CREATE_TIME")
	@Column(name = "CREATE_TIME", updatable = false, insertable = false)
	private Date createtime;
	
	@ApiModelProperty(hidden = true)
	@JsonProperty("REVISE_TIME")
	@Column(name = "REVISE_TIME", insertable = false)
	private Date revisetime;
	
	@ApiModelProperty(hidden = true)
	@JsonProperty("REVISE_ID")
	@Column(name = "REVISE_ID", insertable = false)
	private String reviseid;

	@Override
	public String toString() {
		return "PaymentBean [id=" + id + ", paymentid=" + paymentid + ", orderid=" + orderid + ", userid=" + userid
				+ ", storeid=" + storeid + ", paymentprice=" + paymentprice + ", paymentcategory=" + paymentcategory
				+ ", paymentstatus=" + paymentstatus + ", createid=" + createid + ", createtime=" + createtime
				+ ", revisetime=" + revisetime + ", reviseid=" + reviseid + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(String paymentid) {
		this.paymentid = paymentid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
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

	public Boolean getPaymentstatus() {
		return paymentstatus;
	}

	public void setPaymentstatus(Boolean paymentstatus) {
		this.paymentstatus = paymentstatus;
	}

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getRevisetime() {
		return revisetime;
	}

	public void setRevisetime(Date revisetime) {
		this.revisetime = revisetime;
	}

	public String getReviseid() {
		return reviseid;
	}

	public void setReviseid(String reviseid) {
		this.reviseid = reviseid;
	}
}
