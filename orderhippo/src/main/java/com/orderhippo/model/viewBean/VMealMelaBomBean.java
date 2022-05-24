package com.orderhippo.model.viewBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_meal_mealbom")
public class VMealMelaBomBean {
	
	@Id
	@Column(name = "MEAL_ID")
	private String mealid;
	
	@Column(name = "MEAL_NAME")
	private String mealname;
	
	@Column(name = "MEAL_CATEGORY_NAME")
	private String mealcategoryname;
	
	@Column(name = "MEAL_IMAGE")
	private String mealimage;
	
	@Column(name = "MEAL_PRICE")
	private Integer mealprice;
	
	@Column(name = "INGREDIENT", columnDefinition = "mediumtext")
	private String ingredient;
	
	@Column(name = "MEAL_DESC", columnDefinition = "longtext")
	private String mealdesc;

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

	public String getMealcategoryname() {
		return mealcategoryname;
	}

	public void setMealcategoryname(String mealcategoryname) {
		this.mealcategoryname = mealcategoryname;
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

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public String getMealdesc() {
		return mealdesc;
	}

	public void setMealdesc(String mealdesc) {
		this.mealdesc = mealdesc;
	}
}
