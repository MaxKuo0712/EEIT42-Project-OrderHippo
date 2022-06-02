-- 菜單頁顯示
CREATE or REPLACE  view V_MEAL_MEALBOM as
select meal.MEAL_ID, meal.MEAL_NAME, meal.MEAL_CATEGORY_NAME, meal.MEAL_IMAGE, meal.MEAL_HOT,
	meal.MEAL_PRICE, GROUP_CONCAT(CONCAT(bom.INGREDIENT_NAME, bom.MEAL_INGREDIENT_WEIGHT, 'g') SEPARATOR ';') as INGREDIENT,
	meal.MEAL_DESC
from MEAL as meal
	inner join MEAL_BOM as bom on bom.MEAL_ID  = meal.MEAL_ID
	inner join STORE_INFO as s on s.STORE_ID = meal.STORE_ID
group by meal.MEAL_ID, meal.MEAL_NAME, meal.MEAL_CATEGORY_NAME, meal.MEAL_IMAGE, meal.MEAL_HOT, meal.MEAL_PRICE, meal.MEAL_DESC

-- 訂單筆數 1未確認訂單, 2已確認訂單, 3已完成訂單 4取消的訂單
CREATE or REPLACE  view V_ORDER_STATUS_COUNT as
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
CREATE or REPLACE  view V_SALE_RANK as
select meal.MEAL_NAME, meal.MEAL_PRICE, count(*) as 'COUNT'
from ORDERS as orders
	inner join ORDER_MEALDETAIL as detail on detail.ORDER_ID = orders.ORDER_ID
	inner join MEAL as meal on meal.MEAL_ID = detail.MEAL_ID
where orders.ORDER_STATUS = 3 and meal.MEAL_STATUS = true
group by meal.MEAL_NAME, meal.MEAL_PRICE
order by count(*) DESC, meal.MEAL_PRICE

-- 每月營收 - 3已完成訂單
CREATE or REPLACE  view V_MONTH_REVENUE as
SELECT EXTRACT(YEAR from CREATE_TIME) as 'YEAR', EXTRACT(MONTH from CREATE_TIME) as 'MONTH', 
	sum(ORDERS_PRICE) as REVENUE_OF_MONTH
FROM orders
where ORDER_STATUS = 3
group by EXTRACT(YEAR from CREATE_TIME), EXTRACT(MONTH from CREATE_TIME)

-- 年齡數量 - 百分比可用於圓餅圖
CREATE or REPLACE  view V_AGE_CHART as
select queryResult.AGE_RANGE, queryResult.QTY, round((queryResult.QTY/usercount.USERCOUNT)*100, 2) as 'PERCENTAGE'
from (
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
			ELSE '80+'
		END as 'AGE_RANGE', COUNT(*) as QTY
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
		ELSE '80+'
	end) as queryResult
	inner join (select count(*) as USERCOUNT from user_info ui) as usercount
order by queryResult.AGE_RANGE

-- 性別數量 - 百分比可用於圓餅圖 -- 待查
CREATE or REPLACE  view V_GENDER_CHART as
select userGanderCount.USER_GENDER, userGanderCount.GENDER_COUNT, 
	round((userGanderCount.GENDER_COUNT/userqty.count)*100, 2) as 'PERCENTAGE'
from ( 
	select USER_GENDER, count(*) as 'GENDER_COUNT'
	from USER_INFO as userinfo
	group by USER_GENDER
) as userGanderCount
	inner join (select count(*) as 'count' from user_info) as userqty
order by round((userGanderCount.GENDER_COUNT/userqty.count)*100, 2) desc

-- 訂單頁面顯示 -- 改MEAL_PRICE改成折扣後價格 -- 加上以參數搜尋
CREATE or REPLACE view V_ORDER_DISPLAY as
select orders.ORDER_ID , orders.ORDER_STATUS, userinfo.USER_NAME, userinfo.USER_PHONE, 
	GROUP_CONCAT(CONCAT(meal.MEAL_NAME, ' * ', ordersdetail.ORDER_MEAL_QTY) SEPARATOR ', ') as 'MEAL_ORDER_QTY',
	CONCAT('$', orders.ORDERS_PRICE) as 'ORDERS_PRICE',
	orders.CREATE_TIME
from ORDERS as orders
	inner join ORDER_MEALDETAIL as ordersdetail on ordersdetail.ORDER_ID = orders.ORDER_ID
	inner join MEAL as meal on meal.MEAL_ID = ordersdetail.MEAL_ID
	inner join USER_INFO as userinfo on userinfo.USER_ID = orders.USER_ID
group by orders.ORDER_ID , orders.ORDER_STATUS, CONCAT('$', orders.ORDERS_PRICE), 
	userinfo.USER_NAME, orders.CREATE_TIME, userinfo.USER_PHONE
order by orders.ORDER_ID

select * from ORDER_MEALDETAIL om where ORDER_ID ='O20220527_00bdee0cdd7111eca4fa068cdc81eecc'

-- 更改菜單顯示 -- 待查 --增加MEAL_ID查詢
CREATE or REPLACE view V_REVISE_MEAL_DISPLAY as
select meal.MEAL_ID, meal.MEAL_NAME, meal.MEAL_HOT, meal.MEAL_VEGAN, meal.MEAL_IMAGE, meal.MEAL_PRICE, bom.INGREDIENT_ID, bom.INGREDIENT_NAME,
	bom.MEAL_INGREDIENT_WEIGHT, meal.MEAL_CALORIE, meal.MEAL_CARB, meal.MEAL_FAT, meal.MEAL_PROTEIN, meal.MEAL_DESC
from meal as meal 
	inner join meal_bom as bom
where meal.MEAL_STATUS = true 
	
-- 各類別售出圓餅圖
CREATE or REPLACE view V_SALE_CATEGORY as
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
order by round((queryResult.QTY/sumResult.SUM_QTY) * 100,2) desc

-- 用於金流支付
CREATE or REPLACE view V_PAYMENT_DETAIL as
select orders.ORDER_ID, sum(ordersdetail.MEAL_PRICE) as 'MEAL_PRICE',
	GROUP_CONCAT(meal.MEAL_NAME SEPARATOR ', ') as 'MEALS'
from ORDERS as orders
	inner join ORDER_MEALDETAIL as ordersdetail on ordersdetail.ORDER_ID = orders.ORDER_ID
	inner join MEAL as meal on meal.MEAL_ID = ordersdetail.MEAL_ID
group by ORDER_ID

-- 訂單結帳後的確認頁
CREATE or REPLACE view V_AFTER_PAYMENT_PAGE as
select o.ORDER_ID, si.STORE_NAME, p.PAYMENT_PRICE, 
	CASE
		WHEN p.PAYMENT_CATEGORY = 1 THEN '信用卡'
		WHEN p.PAYMENT_CATEGORY = 2 THEN '現金'
	END as 'PAYMENT_CATEGORY'
from ORDERS o 
	inner join STORE_INFO si on si.STORE_ID = o.STORE_ID 
	inner join PAYMENT p on p.ORDER_ID = o.ORDER_ID



