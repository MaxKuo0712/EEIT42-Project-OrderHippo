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
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "USER_INFO")
@DynamicInsert(true)
@DynamicUpdate(true)
public class UserInfoBean {
	
	@Id
	@ApiModelProperty(hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ID")
	@Column(name = "ID")
	private Integer id;
	
	@ApiModelProperty(hidden = true)
	@JsonProperty("USER_ID")
	@Column(name = "USER_ID", updatable = false, unique = true)
	private String userid;
	
	@JsonProperty("USER_NAME")
	@Column(name = "USER_NAME")
	private String username;
	
	@JsonProperty("USER_GENDER")
	@Column(name = "USER_GENDER", columnDefinition="ENUM")
	private String usergender;
	
	@JsonProperty("USER_PHONE")
	@Column(name = "USER_PHONE")
	private String userphone;
	
	@JsonProperty("USER_MAIL")
	@Column(name = "USER_MAIL", unique = true)
	private String usermail;
	
	@JsonProperty("USER_BIRTH")
	@Column(name = "USER_BIRTH")
	private Date userbirth;
	
	@JsonProperty("USER_AGE")
	@Column(name = "USER_AGE", insertable = false)
	private Integer userage;
	
	@JsonProperty("USER_ADDRESS")
	@Column(name = "USER_ADDRESS")
	private String useraddress;
	
	@JsonProperty("USER_TOKEN")
	@Column(name = "USER_TOKEN", insertable = false)
	private String usertoken;

	@JsonProperty("LAST_LOGININ_TIME")
	@Column(name = "LAST_LOGININ_TIME", insertable = false)	
	private Date lastlogintime;
	
	@ApiModelProperty(hidden = true)
	@JsonProperty("CREATE_ID")
	@Column(name = "CREATE_ID", updatable = false)
	private String createid;
	
	@ApiModelProperty(hidden = true)
	@JsonProperty("CREATE_TIME")
	@Column(name = "CREATE_TIME", insertable = false, updatable = false)
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
		return "UserInfoBean [id=" + id + ", userid=" + userid + ", username=" + username + ", usergender=" + usergender
				+ ", userphone=" + userphone + ", usermail=" + usermail + ", userbirth=" + userbirth + ", userage="
				+ userage + ", useraddress=" + useraddress + ", usertoken=" + usertoken + ", lastlogintime="
				+ lastlogintime + ", createid=" + createid + ", createtime=" + createtime + ", reviseid=" + reviseid
				+ ", revisetime=" + revisetime + "]";
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsergender() {
		return usergender;
	}

	public void setUsergender(String usergender) {
		this.usergender = usergender;
	}

	public String getUserphone() {
		return userphone;
	}

	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}

	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}

	public Date getUserbirth() {
		return userbirth;
	}

	public void setUserbirth(Date userbirth) {
		this.userbirth = userbirth;
	}

	public Integer getUserage() {
		return userage;
	}

	public void setUserage(Integer userage) {
		this.userage = userage;
	}

	public String getUseraddress() {
		return useraddress;
	}

	public void setUseraddress(String useraddress) {
		this.useraddress = useraddress;
	}

	public String getUsertoken() {
		return usertoken;
	}

	public void setUsertoken(String usertoken) {
		this.usertoken = usertoken;
	}

	public Date getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
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
