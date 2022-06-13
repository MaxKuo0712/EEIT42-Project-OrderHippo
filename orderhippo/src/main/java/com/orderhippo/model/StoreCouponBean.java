package com.orderhippo.model;


import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "STORE_COUPON")
@DynamicInsert(true)
@DynamicUpdate(true)
public class StoreCouponBean {
	
	@Id
	@ApiModelProperty(hidden = true)
	@JsonProperty("ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@JsonProperty("COUPON_ID")
	@Column(name = "COUPON_ID", updatable = false, unique = true)
	private String couponid;
	
	@JsonProperty("COUPON_NAME")
	@Column(name = "COUPON_NAME", unique = true)
	private String couponname;
	
	@JsonProperty("STORE_ID")
	@Column(name = "STORE_ID", updatable = false)
	private String storeid;
	
	@JsonProperty("COUPON_DESC")
	@Column(name = "COUPON_DESC")
	private Double coupondesc;
	
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
		return "StoreCouponBean [id=" + id + ", couponid=" + couponid + ", couponname=" + couponname + ", storeid="
				+ storeid + ", coupondesc=" + coupondesc + ", createid=" + createid + ", createtime=" + createtime
				+ ", revisetime=" + revisetime + ", reviseid=" + reviseid + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCouponid() {
		return couponid;
	}

	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}

	public String getCouponname() {
		return couponname;
	}

	public void setCouponname(String couponname) {
		this.couponname = couponname;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public Double getCoupondesc() {
		return coupondesc;
	}

	public void setCoupondesc(Double coupondesc) {
		this.coupondesc = coupondesc;
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
