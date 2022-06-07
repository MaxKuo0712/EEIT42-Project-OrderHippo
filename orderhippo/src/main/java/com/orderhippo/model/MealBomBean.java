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
@Table(name = "MEAL_BOM")
@DynamicInsert(true)
@DynamicUpdate(true)

public class MealBomBean {
	
	@Id
	@ApiModelProperty(hidden = true)
	@JsonProperty("ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@JsonProperty("BOM_ID")
	@Column(name = "BOM_ID", updatable = false, unique = true)
	private String bomid;
	
	@JsonProperty("MEAL_ID")
	@Column(name = "MEAL_ID")
	private String mealid;
	
	@JsonProperty("INGREDIENT_ID")
	@Column(name = "INGREDIENT_ID")
	private String ingredientid;
	
	@JsonProperty("INGREDIENT_NAME")
	@Column(name = "INGREDIENT_NAME")
	private String ingredientname;
	
	@JsonProperty("MEAL_INGREDIENT_WEIGHT")
	@Column(name = "MEAL_INGREDIENT_WEIGHT")
	private Double mealingredientweight;
	
	@JsonProperty("MEAL_INGREDIENT_CALORIE")
	@Column(name = "MEAL_INGREDIENT_CALORIE")
	private Double mealingredientcalorie;
	
	@JsonProperty("MEAL_INGREDIENT_CARB")
	@Column(name = "MEAL_INGREDIENT_CARB")
	private Double mealingredientcarb;
	
	@JsonProperty("MEAL_INGREDIENT_FAT")
	@Column(name = "MEAL_INGREDIENT_FAT")
	private Double mealingredientfat;
	
	@JsonProperty("MEAL_INGREDIENT_PROTEIN")
	@Column(name = "MEAL_INGREDIENT_PROTEIN")
	private Double mealingredientprotein;
	
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
		return "MealBomBean [id=" + id + ", bomid=" + bomid + ", mealid=" + mealid + ", ingredientid=" + ingredientid
				+ ", ingredientname=" + ingredientname + ", mealingredientweight=" + mealingredientweight
				+ ", mealingredientcalorie=" + mealingredientcalorie + ", mealingredientcarb=" + mealingredientcarb
				+ ", mealingredientfat=" + mealingredientfat + ", mealingredientprotein=" + mealingredientprotein
				+ ", createid=" + createid + ", createtime=" + createtime + ", revisetime=" + revisetime + ", reviseid="
				+ reviseid + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBomid() {
		return bomid;
	}

	public void setBomid(String bomid) {
		this.bomid = bomid;
	}

	public String getMealid() {
		return mealid;
	}

	public void setMealid(String mealid) {
		this.mealid = mealid;
	}

	public String getIngredientid() {
		return ingredientid;
	}

	public void setIngredientid(String ingredientid) {
		this.ingredientid = ingredientid;
	}

	public String getIngredientname() {
		return ingredientname;
	}

	public void setIngredientname(String ingredientname) {
		this.ingredientname = ingredientname;
	}

	public Double getMealingredientweight() {
		return mealingredientweight;
	}

	public void setMealingredientweight(Double mealingredientweight) {
		this.mealingredientweight = mealingredientweight;
	}

	public Double getMealingredientcalorie() {
		return mealingredientcalorie;
	}

	public void setMealingredientcalorie(Double mealingredientcalorie) {
		this.mealingredientcalorie = mealingredientcalorie;
	}

	public Double getMealingredientcarb() {
		return mealingredientcarb;
	}

	public void setMealingredientcarb(Double mealingredientcarb) {
		this.mealingredientcarb = mealingredientcarb;
	}

	public Double getMealingredientfat() {
		return mealingredientfat;
	}

	public void setMealingredientfat(Double mealingredientfat) {
		this.mealingredientfat = mealingredientfat;
	}

	public Double getMealingredientprotein() {
		return mealingredientprotein;
	}

	public void setMealingredientprotein(Double mealingredientprotein) {
		this.mealingredientprotein = mealingredientprotein;
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
