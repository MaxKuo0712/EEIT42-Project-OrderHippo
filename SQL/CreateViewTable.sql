CREATE or REPLACE  view V_MEAL_MEALBOM as

select meal.MEAL_ID, meal.MEAL_NAME, meal.MEAL_CATEGORY_NAME, meal.MEAL_IMAGE, meal.MEAL_HOT,
	meal.MEAL_PRICE, GROUP_CONCAT(CONCAT(bom.INGREDIENT_NAME, bom.MEAL_INGREDIENT_WEIGHT, 'g') SEPARATOR ';') as INGREDIENT,
	meal.MEAL_DESC
from MEAL as meal
	inner join MEAL_BOM as bom on bom.MEAL_ID  = meal.MEAL_ID
	inner join STORE_INFO as s on s.STORE_ID = meal.STORE_ID

-- 本月營收
SELECT CONCAT(EXTRACT(YEAR from CREATE_TIME), '-', EXTRACT(MONTH from CREATE_TIME)) as 'YEAR_MONTH', 
	sum(ORDERS_PRICE) as REVENUE_OF_MONTH
FROM orders
where EXTRACT(MONTH from CREATE_TIME) = EXTRACT(MONTH from SYSDATE()) and ORDER_STATUS = 3
group by CONCAT(EXTRACT(YEAR from CREATE_TIME), '-', EXTRACT(MONTH from CREATE_TIME))

-- 訂單筆數 1未確認訂單, 2已確認訂單, 3已完成訂單 4取消的訂單
select ORDER_STATUS,
	CASE
		WHEN ORDER_STATUS = 1 THEN '未確認訂單'
		WHEN ORDER_STATUS = 2 THEN '已確認訂單'
		WHEN ORDER_STATUS = 3 THEN '已完成訂單'
		WHEN ORDER_STATUS = 4 THEN '取消的訂單'
	END as 'ORDER_STATUS_DESC', count(*) as 'ORDER_COUNT'
from orders
group by ORDER_STATUS

-- 銷售前10名
select meal.MEAL_NAME, meal.MEAL_PRICE, count(*)
from ORDERS as orders
	inner join ORDER_MEALDETAIL as detail on detail.ORDER_ID = orders.ORDER_ID
	inner join MEAL as meal on meal.MEAL_ID = detail.MEAL_ID
where orders.ORDER_STATUS = 3 and meal.MEAL_STATUS = true
group by meal.MEAL_NAME, meal.MEAL_PRICE
order by count(*) DESC, meal.MEAL_PRICE

-- 每月營收 - 3已完成訂單
SELECT CONCAT(EXTRACT(YEAR from CREATE_TIME), '-', EXTRACT(MONTH from CREATE_TIME)) as 'YEAR_MONTH', 
	sum(ORDERS_PRICE) as REVENUE_OF_MONTH
FROM orders
where ORDER_STATUS = 3
group by CONCAT(EXTRACT(YEAR from CREATE_TIME), '-', EXTRACT(MONTH from CREATE_TIME))

-- 年齡數量
select 
	CASE 
		WHEN USER_AGE BETWEEN 1 and 10 THEN '1-10'
		WHEN USER_AGE BETWEEN 11 and 20 THEN '11-20'
		WHEN USER_AGE BETWEEN 21 and 30 THEN '21-30'
		WHEN USER_AGE BETWEEN 31 and 40 THEN '31-40'
		WHEN USER_AGE BETWEEN 41 and 50 THEN '41-50'
		WHEN USER_AGE BETWEEN 51 and 60 THEN '51-60'
		WHEN USER_AGE BETWEEN 61 and 70 THEN '61-70'
		WHEN USER_AGE BETWEEN 71 and 80 THEN '71-80'
		WHEN USER_AGE BETWEEN 81 and 90 THEN '81-90'
		WHEN USER_AGE BETWEEN 91 and 100 THEN '91-100'
		ELSE '100+'
	END as 'AGE_RANGE', COUNT(*) 
from USER_INFO
group by 
	CASE 
		WHEN USER_AGE BETWEEN 1 and 10 THEN '1-10'
		WHEN USER_AGE BETWEEN 11 and 20 THEN '11-20'
		WHEN USER_AGE BETWEEN 21 and 30 THEN '21-30'
		WHEN USER_AGE BETWEEN 31 and 40 THEN '31-40'
		WHEN USER_AGE BETWEEN 41 and 50 THEN '41-50'
		WHEN USER_AGE BETWEEN 51 and 60 THEN '51-60'
		WHEN USER_AGE BETWEEN 61 and 70 THEN '61-70'
		WHEN USER_AGE BETWEEN 71 and 80 THEN '71-80'
		WHEN USER_AGE BETWEEN 81 and 90 THEN '81-90'
		WHEN USER_AGE BETWEEN 91 and 100 THEN '91-100'
		ELSE '100+'
	END

-- 性別數量
select USER_GENDER, count(*)
from USER_INFO
group by USER_GENDER

-- 訂單頁面顯示
select orders.ORDER_ID , orders.ORDER_STATUS, userinfo.USER_NAME, 
	GROUP_CONCAT(CONCAT(meal.MEAL_NAME, ' * ', ordersdetail.ORDER_MEAL_QTY) SEPARATOR ', '),
	GROUP_CONCAT(CONCAT('$',ordersdetail.MEAL_PRICE) SEPARATOR ', '),
	orders.CREATE_TIME, userinfo.USER_PHONE
from ORDERS as orders
	inner join ORDER_MEALDETAIL as ordersdetail on ordersdetail.ORDER_ID = orders.ORDER_ID
	inner join MEAL as meal on meal.MEAL_ID = ordersdetail.MEAL_ID
	inner join USER_INFO as userinfo on userinfo.USER_ID = orders.USER_ID
group by orders.ORDER_ID , orders.ORDER_STATUS, userinfo.USER_NAME, orders.CREATE_TIME, userinfo.USER_PHONE
order by orders.ORDER_ID
	
-- 各類別售出圓餅圖
select meal.MEAL_ID, meal.MEAL_CATEGORY_ID, meal.MEAL_CATEGORY_NAME, meal.MEAL_NAME, sum(ordersdetail.ORDER_MEAL_QTY) as QTY
from ORDERS as orders 
	inner join ORDER_MEALDETAIL as ordersdetail on ordersdetail.ORDER_ID = orders.ORDER_ID
	inner join MEAL as meal on meal.MEAL_ID = ordersdetail.MEAL_ID
where orders.ORDER_STATUS = 3
group by meal.MEAL_ID, meal.MEAL_CATEGORY_ID, meal.MEAL_CATEGORY_NAME, meal.MEAL_NAME

select queryResult.MEAL_ID, queryResult.MEAL_CATEGORY_NAME, queryResult.MEAL_NAME, 
	round((queryResult.QTY/sumResult.SUM_QTY) * 100,2) as 'PERCENTAGE'
from (
	select meal.MEAL_ID, meal.MEAL_CATEGORY_ID, meal.MEAL_CATEGORY_NAME, meal.MEAL_NAME, sum(ordersdetail.ORDER_MEAL_QTY) as QTY
	from ORDERS as orders 
		inner join ORDER_MEALDETAIL as ordersdetail on ordersdetail.ORDER_ID = orders.ORDER_ID
		inner join MEAL as meal on meal.MEAL_ID = ordersdetail.MEAL_ID
	where orders.ORDER_STATUS = 3
	group by meal.MEAL_ID, meal.MEAL_CATEGORY_ID, meal.MEAL_CATEGORY_NAME, meal.MEAL_NAME
) as queryResult
	inner join (
		select meal.MEAL_CATEGORY_ID , meal.MEAL_CATEGORY_NAME, sum(ordersdetail.ORDER_MEAL_QTY) as SUM_QTY
		from ORDERS as orders 
			inner join ORDER_MEALDETAIL as ordersdetail on ordersdetail.ORDER_ID = orders.ORDER_ID
			inner join MEAL as meal on meal.MEAL_ID = ordersdetail.MEAL_ID
		where orders.ORDER_STATUS = 3
		group by meal.MEAL_CATEGORY_ID , meal.MEAL_CATEGORY_NAME
	) as sumResult on sumResult.MEAL_CATEGORY_ID = queryResult.MEAL_CATEGORY_ID

	
		select meal.MEAL_CATEGORY_ID , meal.MEAL_CATEGORY_NAME, sum(ordersdetail.ORDER_MEAL_QTY) as SUM_QTY
		from ORDERS as orders 
			inner join ORDER_MEALDETAIL as ordersdetail on ordersdetail.ORDER_ID = orders.ORDER_ID
			inner join MEAL as meal on meal.MEAL_ID = ordersdetail.MEAL_ID
		where orders.ORDER_STATUS = 3
		group by meal.MEAL_CATEGORY_ID , meal.MEAL_CATEGORY_NAME
	
	
	group by meal.MEAL_CATEGORY_NAME, meal.MEAL_NAME


select * from MEAL m 

select * from ORDERS where ORDER_STATUS = 3

select * from ORDER_MEALDETAIL



