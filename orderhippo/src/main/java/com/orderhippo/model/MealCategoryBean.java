package com.orderhippo.model;

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
@Table(name = "MEAL_CATEGORY")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MealCategoryBean {
	
	@Id
	@ApiModelProperty(hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ID")
	@Column(name = "ID")
	private Integer id;
	
	@JsonProperty("MEAL_CATEGORY_ID")
	@Column(name = "MEAL_CATEGORY_ID", updatable = false, unique = true)
	private String mealcategoryid;
	
	@JsonProperty("MEAL_CATEGORY_NAME")
	@Column(name = "MEAL_CATEGORY_NAME")
	private String mealcategoryname;
	
	@JsonProperty("MEAL_CATEGORY_DESC")
	@Column(name = "MEAL_CATEGORY_DESC", columnDefinition="LONGTEXT")
	private String mealcategorydesc;

	@Override
	public String toString() {
		return "MealCategoryBean [id=" + id + ", mealcategoryid=" + mealcategoryid + ", mealcategoryname="
				+ mealcategoryname + ", mealcategorydesc=" + mealcategorydesc + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getMealcategorydesc() {
		return mealcategorydesc;
	}

	public void setMealcategorydesc(String mealcategorydesc) {
		this.mealcategorydesc = mealcategorydesc;
	}
}
