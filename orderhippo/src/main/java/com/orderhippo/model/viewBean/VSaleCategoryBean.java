package com.orderhippo.model.viewBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 9. 各類別售出圓餅圖
@Entity
@Table(name="v_sale_category")

public class VSaleCategoryBean {
	
	@Id
	@Column(name = "MEAL_ID")
	private String mealid;
	
	@Column(name = "MEAL_CATEGORY_NAME")
	private String mealcategoryname;
	
	@Column(name = "MEAL_NAME")
	private String mealname;
	
	@Column(name = "PERCENTAGE", columnDefinition = "DECIMAL")
	private Double percentage;

	public String getMealid() {
		return mealid;
	}

	public void setMealid(String mealid) {
		this.mealid = mealid;
	}

	public String getMealcategoryname() {
		return mealcategoryname;
	}

	public void setMealcategoryname(String mealcategoryname) {
		this.mealcategoryname = mealcategoryname;
	}

	public String getMealname() {
		return mealname;
	}

	public void setMealname(String mealname) {
		this.mealname = mealname;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
}
