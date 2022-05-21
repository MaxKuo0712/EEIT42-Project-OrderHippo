package com.orderhippo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INGREDIENT")
public class IngredientBean {
	
	@Id
	@Column(name = "INGREDIENT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String ingredientid;
	
	@Column(name = "INGREDIENT_DATA_CATEGORY")
	private String ingredientdatacategory;
	
	@Column(name = "INGREDIENT_NAME")
	private String ingredientname;
	
	@Column(name = "INGREDIENT_CATEGORY")
	private String ingredientcategory;
	
	@Column(name = "INGREDIENT_DESC")
	private String ingredientdesc;
	
	@Column(name = "CALORIE")
	private Double calorie;
	
	@Column(name = "CARB")
	private Double carb;
	
	@Column(name = "FAT")
	private Double fat;
	
	@Column(name = "PROTEIN")
	private Double protein;

	@Override
	public String toString() {
		return "IngredientBean [ingredientid=" + ingredientid + ", ingredientdatacategory=" + ingredientdatacategory
				+ ", ingredientname=" + ingredientname + ", ingredientcategory=" + ingredientcategory
				+ ", ingredientdesc=" + ingredientdesc + ", calorie=" + calorie + ", carb=" + carb + ", fat=" + fat
				+ ", protein=" + protein + "]";
	}

	public String getIngredientid() {
		return ingredientid;
	}

	public void setIngredientid(String ingredientid) {
		this.ingredientid = ingredientid;
	}

	public String getIngredientdatacategory() {
		return ingredientdatacategory;
	}

	public void setIngredientdatacategory(String ingredientdatacategory) {
		this.ingredientdatacategory = ingredientdatacategory;
	}

	public String getIngredientname() {
		return ingredientname;
	}

	public void setIngredientname(String ingredientname) {
		this.ingredientname = ingredientname;
	}

	public String getIngredientcategory() {
		return ingredientcategory;
	}

	public void setIngredientcategory(String ingredientcategory) {
		this.ingredientcategory = ingredientcategory;
	}

	public String getIngredientdesc() {
		return ingredientdesc;
	}

	public void setIngredientdesc(String ingredientdesc) {
		this.ingredientdesc = ingredientdesc;
	}

	public Double getCalorie() {
		return calorie;
	}

	public void setCalorie(Double calorie) {
		this.calorie = calorie;
	}

	public Double getCarb() {
		return carb;
	}

	public void setCarb(Double carb) {
		this.carb = carb;
	}

	public Double getFat() {
		return fat;
	}

	public void setFat(Double fat) {
		this.fat = fat;
	}

	public Double getProtein() {
		return protein;
	}

	public void setProtein(Double protein) {
		this.protein = protein;
	}
}
