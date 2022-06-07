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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "STORE_INFO")
@DynamicInsert(true)
@DynamicUpdate(true)
public class StoreInfoBean {
	
	@Id
	@ApiModelProperty(hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ID")
	@Column(name = "ID")
	private Integer id;
	
	@ApiModelProperty(hidden = true)
	@JsonProperty("STORE_ID")
	@Column(name = "STORE_ID", updatable = false, unique = true)
	private String storeid;
	
	@JsonProperty("STORE_NAME")
	@Column(name = "STORE_NAME")
	private String storename;
	
	@JsonProperty("STORE_ADDRESS")
	@Column(name = "STORE_ADDRESS")
	private String storeaddress;
	
	@JsonProperty("STORE_PHONE")
	@Column(name = "STORE_PHONE")
	private String storephone;
	
	@JsonProperty("STORE_MAIL")
	@Column(name = "STORE_MAIL", unique = true)
	private String storemail;
	
	@JsonProperty("STORE_LOCATION")
	@Column(name = "STORE_LOCATION")
	private String storelocation;
	
	@JsonProperty("STORE_OPEN_STATUS")
	@Column(name = "STORE_OPEN_STATUS", insertable = false)
	private Boolean storeopenstatus;
	
	@JsonProperty("STORE_TOKEN")
	@Column(name = "STORE_TOKEN", insertable = false)
	private String storetoken;
	
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
		return "StoreInfoBean [id=" + id + ", storeid=" + storeid + ", storename=" + storename + ", storeaddress="
				+ storeaddress + ", storephone=" + storephone + ", storemail=" + storemail + ", storelocation="
				+ storelocation + ", storeopenstatus=" + storeopenstatus + ", storetoken=" + storetoken + ", createid="
				+ createid + ", createtime=" + createtime + ", revisetime=" + revisetime + ", reviseid=" + reviseid
				+ "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getStoreaddress() {
		return storeaddress;
	}

	public void setStoreaddress(String storeaddress) {
		this.storeaddress = storeaddress;
	}

	public String getStorephone() {
		return storephone;
	}

	public void setStorephone(String storephone) {
		this.storephone = storephone;
	}

	public String getStoremail() {
		return storemail;
	}

	public void setStoremail(String storemail) {
		this.storemail = storemail;
	}

	public String getStorelocation() {
		return storelocation;
	}

	public void setStorelocation(String storelocation) {
		this.storelocation = storelocation;
	}

	public Boolean getStoreopenstatus() {
		return storeopenstatus;
	}

	public void setStoreopenstatus(Boolean storeopenstatus) {
		this.storeopenstatus = storeopenstatus;
	}

	public String getStoretoken() {
		return storetoken;
	}

	public void setStoretoken(String storetoken) {
		this.storetoken = storetoken;
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
