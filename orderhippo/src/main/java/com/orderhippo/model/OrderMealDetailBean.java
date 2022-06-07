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

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "ORDER_MEALDETAIL")
@DynamicInsert(true)
@DynamicUpdate(true)
public class OrderMealDetailBean {
	
	@Id
	@ApiModelProperty(hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ID")
	@Column(name = "ID")
	private Integer id;
	
	@JsonProperty("ORDER_ID")
	@Column(name = "ORDER_ID")
	private String orderid;
	
	@JsonProperty("MEAL_ID")
	@Column(name = "MEAL_ID")
	private String mealid;
	
	@JsonProperty("ORDER_MEAL_QTY")
	@Column(name = "ORDER_MEAL_QTY")
	private Integer ordermealqty;
	
	@JsonProperty("MEAL_PRICE")
	@Column(name = "MEAL_PRICE")
	private Integer mealprice;
	
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
		return "OrderMealDetailBean [id=" + id + ", orderid=" + orderid + ", mealid=" + mealid + ", ordermealqty="
				+ ordermealqty + ", mealprice=" + mealprice + ", createtid=" + createtid + ", createtime=" + createtime
				+ ", reviseid=" + reviseid + ", revisetime=" + revisetime + "]";
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

	public String getMealid() {
		return mealid;
	}

	public void setMealid(String mealid) {
		this.mealid = mealid;
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
