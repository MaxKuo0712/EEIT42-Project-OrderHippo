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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "ORDERS")
@DynamicInsert(true)
@DynamicUpdate(true)
public class OrdersBean {
	
	@Id
	@ApiModelProperty(hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ID")
	@Column(name = "ID")
	private Integer id;
	
	@JsonProperty("ORDER_ID")
	@Column(name = "ORDER_ID", updatable = false, unique = true)
	private String orderid;
	
	@JsonProperty("STORE_ID")
	@Column(name = "STORE_ID")
	private String storeid;
	
	@JsonProperty("USER_ID")
	@Column(name = "USER_ID")
	private String userid;
	
	@JsonProperty("ORDERS_PRICE")
	@Column(name = "ORDERS_PRICE")
	private Integer ordersprice;
	
	@JsonProperty("ORDER_STATUS")
	@Column(name = "ORDER_STATUS", columnDefinition="ENUM")
	private String orderstatus;
	
	@JsonProperty("ORDER_DESC")
	@Column(name = "ORDER_DESC", columnDefinition="longtext")
	private String orderdesc;
	
	@ApiModelProperty(hidden = true)
	@JsonProperty("CREATE_ID")
	@Column(name = "CREATE_ID", updatable = false)
	private String createtid;
	
	@ApiModelProperty(hidden = true)
	@JsonProperty("CREATE_TIME")
	@Column(name = "CREATE_TIME", updatable = false, insertable = false)
	private Date createtime;
	
	@ApiModelProperty(hidden = true)
	@JsonProperty("REVISE_ID")
	@Column(name = "REVISE_ID", insertable = false)
	private String reviseid;
	
	@ApiModelProperty(hidden = true)
	@JsonProperty("REVISE_TIME")
	@Column(name = "REVISE_TIME", insertable = false)
	private Date revisetime;

	@Override
	public String toString() {
		return "OrdersBean [id=" + id + ", orderid=" + orderid + ", storeid=" + storeid + ", userid=" + userid
				+ ", ordersprice=" + ordersprice + ", orderstatus=" + orderstatus + ", orderdesc=" + orderdesc
				+ ", createtid=" + createtid + ", createtime=" + createtime + ", reviseid=" + reviseid + ", revisetime="
				+ revisetime + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getOrdersprice() {
		return ordersprice;
	}

	public void setOrdersprice(Integer ordersprice) {
		this.ordersprice = ordersprice;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getOrderdesc() {
		return orderdesc;
	}

	public void setOrderdesc(String orderdesc) {
		this.orderdesc = orderdesc;
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
