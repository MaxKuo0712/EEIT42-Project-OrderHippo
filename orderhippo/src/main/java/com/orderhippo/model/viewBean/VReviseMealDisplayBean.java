package com.orderhippo.model.viewBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 8.更改菜單顯示
@Entity
@Table(name="v_revise_meal_display")
public class VReviseMealDisplayBean {
	
	@Column(name = "MEAL_ID")
	private String mealid;
	
	@Id
	@Column(name = "BOM_ID")
	private String bomid;
	
	@Column(name = "MEAL_NAME")
	private String mealname;
	
	@Column(name = "MEAL_HOT", columnDefinition = "BIT")
	private Integer mealhot;
	
	@Column(name = "MEAL_VEGAN", columnDefinition = "BIT")
	private Integer mealvegan;
	
	@Column(name = "MEAL_IMAGE")
	private String mealimage;
	
	@Column(name = "MEAL_PRICE")
	private Integer mealprice;
	
	@Column(name = "INGREDIENT_ID")
	private String ingredientid;
	
	@Column(name = "INGREDIENT_NAME")
	private String ingredientname;
	
	@Column(name = "MEAL_INGREDIENT_WEIGHT")
	private Double mealingredientweight;
	
	@Column(name = "MEAL_CALORIE")
	private Double mealcalorie;
	
	@Column(name = "MEAL_CARB")
	private Double mealcarb;
	
	@Column(name = "MEAL_FAT")
	private Double mealfat;
	
	@Column(name = "MEAL_PROTEIN")
	private Double mealprotein;
	
	@Column(name = "MEAL_DESC", columnDefinition = "LONGTEXT")
	private String mealdesc;

	public String getMealid() {
		return mealid;
	}

	public void setMealid(String mealid) {
		this.mealid = mealid;
	}

	public String getBomid() {
		return bomid;
	}

	public void setBomid(String bomid) {
		this.bomid = bomid;
	}

	public String getMealname() {
		return mealname;
	}

	public void setMealname(String mealname) {
		this.mealname = mealname;
	}

	public Integer getMealhot() {
		return mealhot;
	}

	public void setMealhot(Integer mealhot) {
		this.mealhot = mealhot;
	}

	public Integer getMealvegan() {
		return mealvegan;
	}

	public void setMealvegan(Integer mealvegan) {
		this.mealvegan = mealvegan;
	}

	public String getMealimage() {
		return mealimage;
	}

	public void setMealimage(String mealimage) {
		this.mealimage = mealimage;
	}

	public Integer getMealprice() {
		return mealprice;
	}

	public void setMealprice(Integer mealprice) {
		this.mealprice = mealprice;
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

	public String getMealdesc() {
		return mealdesc;
	}

	public void setMealdesc(String mealdesc) {
		this.mealdesc = mealdesc;
	}
}
