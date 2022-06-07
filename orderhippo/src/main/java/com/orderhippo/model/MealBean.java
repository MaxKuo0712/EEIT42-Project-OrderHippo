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
@Table(name = "MEAL")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MealBean {
	
	@Id
	@ApiModelProperty(hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ID")
	@Column(name = "ID")
	private Integer id;
	
	@JsonProperty("MEAL_ID")
	@Column(name = "MEAL_ID", updatable = false, unique = true)
	private String mealid;
	
	@JsonProperty("MEAL_NAME")
	@Column(name = "MEAL_NAME")
	private String mealname;
	
	@JsonProperty("MEAL_CATEGORY_ID")
	@Column(name = "MEAL_CATEGORY_ID")
	private String mealcategoryid;
	
	@JsonProperty("MEAL_CATEGORY_NAME")
	@Column(name = "MEAL_CATEGORY_NAME")
	private String mealcategoryname;
	
	@JsonProperty("STORE_ID")
	@Column(name = "STORE_ID")
	private String storeid;
	
	@JsonProperty("MEAL_IMAGE")
	@Column(name = "MEAL_IMAGE")
	private String mealimage;
	
	@JsonProperty("MEAL_DESC")
	@Column(name = "MEAL_DESC", columnDefinition = "LONGTEXT")
	private String mealdesc;
	
	@JsonProperty("MEAL_PRICE")
	@Column(name = "MEAL_PRICE")
	private Integer mealprice;
	
	@JsonProperty("MEAL_CALORIE")
	@Column(name = "MEAL_CALORIE")
	private Double mealcalorie;
	
	@JsonProperty("MEAL_CARB")
	@Column(name = "MEAL_CARB")
	private Double mealcarb;
	
	@JsonProperty("MEAL_FAT")
	@Column(name = "MEAL_FAT")
	private Double mealfat;
	
	@JsonProperty("MEAL_PROTEIN")
	@Column(name = "MEAL_PROTEIN")
	private Double mealprotein;
	
	@JsonProperty("MEAL_VEGAN")
	@Column(name = "MEAL_VEGAN")
	private Boolean mealvegan;
	
	@JsonProperty("MEAL_HOT")
	@Column(name = "MEAL_HOT")
	private Boolean mealhot;
	
	@JsonProperty("MEAL_STATUS")
	@Column(name = "MEAL_STATUS")
	private Boolean mealstatus;
	
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
		return "MealBean [id=" + id + ", mealid=" + mealid + ", mealname=" + mealname + ", mealcategoryid="
				+ mealcategoryid + ", mealcategoryname=" + mealcategoryname + ", storeid=" + storeid + ", mealimage="
				+ mealimage + ", mealdesc=" + mealdesc + ", mealprice=" + mealprice + ", mealcalorie=" + mealcalorie
				+ ", mealcarb=" + mealcarb + ", mealfat=" + mealfat + ", mealprotein=" + mealprotein + ", mealvegan="
				+ mealvegan + ", mealhot=" + mealhot + ", mealstatus=" + mealstatus + ", createtid=" + createtid
				+ ", createtime=" + createtime + ", reviseid=" + reviseid + ", revisetime=" + revisetime + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getMealcategoryid() {
		return mealcategoryid;
	}

	public void setMealcategoryid(String mealcategoryid) {
		this.mealcategoryid = mealcategoryid;
	}

	public String getMealcategoryname() {
		return mealcategoryname;
	}

	public void setMealcategoryname(String mealcategoryname) {
		this.mealcategoryname = mealcategoryname;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getMealimage() {
		return mealimage;
	}

	public void setMealimage(String mealimage) {
		this.mealimage = mealimage;
	}

	public String getMealdesc() {
		return mealdesc;
	}

	public void setMealdesc(String mealdesc) {
		this.mealdesc = mealdesc;
	}

	public Integer getMealprice() {
		return mealprice;
	}

	public void setMealprice(Integer mealprice) {
		this.mealprice = mealprice;
	}

	public Double getMealcalorie() {
		return mealcalorie;
	}

	public void setMealcalorie(Double mealcalorie) {
		this.mealcalorie = mealcalorie;
	}

	public Double getMealcarb() {
		return mealcarb;
	}

	public void setMealcarb(Double mealcarb) {
		this.mealcarb = mealcarb;
	}

	public Double getMealfat() {
		return mealfat;
	}

	public void setMealfat(Double mealfat) {
		this.mealfat = mealfat;
	}

	public Double getMealprotein() {
		return mealprotein;
	}

	public void setMealprotein(Double mealprotein) {
		this.mealprotein = mealprotein;
	}

	public Boolean getMealvegan() {
		return mealvegan;
	}

	public void setMealvegan(Boolean mealvegan) {
		this.mealvegan = mealvegan;
	}

	public Boolean getMealhot() {
		return mealhot;
	}

	public void setMealhot(Boolean mealhot) {
		this.mealhot = mealhot;
	}

	public Boolean getMealstatus() {
		return mealstatus;
	}

	public void setMealstatus(Boolean mealstatus) {
		this.mealstatus = mealstatus;
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
