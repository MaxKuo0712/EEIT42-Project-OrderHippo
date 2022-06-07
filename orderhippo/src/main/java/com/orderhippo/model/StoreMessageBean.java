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
@Table(name = "STORE_MESSAGE")
@DynamicInsert(true)
@DynamicUpdate(true)

public class StoreMessageBean {
	
	@Id
	@ApiModelProperty(hidden = true)
	@JsonProperty("ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@JsonProperty("MESSAGE_ID")
	@Column(name = "MESSAGE_ID", updatable = false, unique = true)
	private String messageid;
	
	@JsonProperty("MESSAGE_NAME")
	@Column(name = "MESSAGE_NAME", unique = true)
	private String messagename;
	
	@JsonProperty("STORE_ID")
	@Column(name = "STORE_ID")
	private String storeid;
	
	@JsonProperty("MESSAGE_DESC")
	@Column(name = "MESSAGE_DESC", columnDefinition="LONGTEXT")
	private String messagedesc;
	
	@JsonProperty("MESSAGE_IMAGE")
	@Column(name = "MESSAGE_IMAGE")
	private String messageimage;    // 網址, 圖片多張的話，會佔記憶體太大，所以Max才會用Imgur API來解決
	
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
		return "StoreMessageBean [id=" + id + ", messageid=" + messageid + ", messagename=" + messagename + ", storeid="
				+ storeid + ", messagedesc=" + messagedesc + ", messageimage=" + messageimage + ", createid=" + createid
				+ ", createtime=" + createtime + ", revisetime=" + revisetime + ", reviseid=" + reviseid + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getMessagename() {
		return messagename;
	}

	public void setMessagename(String messagename) {
		this.messagename = messagename;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getMessagedesc() {
		return messagedesc;
	}

	public void setMessagedesc(String messagedesc) {
		this.messagedesc = messagedesc;
	}

	public String getMessageimage() {
		return messageimage;
	}

	public void setMessageimage(String messageimage) {
		this.messageimage = messageimage;
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
