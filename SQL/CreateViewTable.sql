CREATE or REPLACE  view V_MEAL_MEALBOM as
select meal.MEAL_ID, meal.MEAL_NAME, meal.MEAL_CATEGORY_NAME, meal.MEAL_IMAGE,
	meal.MEAL_PRICE, GROUP_CONCAT(CONCAT(bom.INGREDIENT_NAME, bom.MEAL_INGREDIENT_WEIGHT, 'g') SEPARATOR ';') as INGREDIENT,
	meal.MEAL_DESC
from MEAL as meal
	inner join MEAL_BOM as bom on bom.MEAL_ID  = meal.MEAL_ID
	inner join STORE_INFO as s on s.STORE_ID = meal.STORE_ID
	

-- 性別數量
select USER_GENDER, count(*)
from USER_INFO
group by USER_GENDER