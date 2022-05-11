-- --------------------------------------------------------------------------------------------
create table USER_INFO (
	USER_ID varchar(100) DEFAULT(CONCAT('U', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY,
	USER_NAME varchar(100) not null,
	USER_GENDER ENUM('M','F','X') not null,
	USER_PHONE varchar(100) not null,
	USER_MAIL varchar(200) UNIQUE not null,
	USER_BIRTH date not null,
	USER_AGE int DEFAULT TIMESTAMPDIFF(YEAR, USER_BIRTH, CURRENT_DATE()),
	USER_ADDRESS varchar(300) not null,
	CREATE_TIME datetime default sysdate() not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	CONSTRAINT CHK_USER_PHONE CHECK (USER_PHONE REGEXP '[^0-9]' = 0),
	CONSTRAINT CHK_USER_MAIL CHECK (USER_MAIL REGEXP '[a-zA-Z0-9_\-]+@([a-zA-Z0-9_\-]+\.)+(com|org|edu)' = 1),
	CONSTRAINT CHK_USER_AGE CHECK (USER_AGE > 0)
)

drop table USER_INFO

delete from USER_INFO

insert into USER_INFO (USER_NAME, USER_GENDER, USER_PHONE, USER_MAIL, USER_BIRTH, USER_ADDRESS) 
values ('Max', 'M', '0955820712', 'maxkuo712@gmail.com', '2021-05-07', '台中市西屯區')

select * from USER_INFO

-- --------------------------------------------------------------------------------------------

create table ORDERS (
	ORDER_ID varchar(100) DEFAULT(CONCAT('O', DATE_FORMAT(CURRENT_DATE(),'%Y%m%d'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY,
	STORE_ID varchar(100) not null,
	USER_ID varchar(100) not null,
	USER_MAIL varchar(200) not null,
	MEAL_ID varchar(100) not null,
	MEAL_NAME varchar(100) not null,
	ORDER_MEAL_QTY TINYINT not null,
	MEAL_PRICE MEDIUMINT not null,
	ORDER_STATUS ENUM('0','1','2','3') not null, -- 0取消的訂單 1未確認訂單, 2已確認訂單, 3已完成訂單
	CREATE_TIME datetime default sysdate() not null,
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID),
	FOREIGN KEY (USER_ID) REFERENCES USER_INFO(USER_ID),
	FOREIGN KEY (MEAL_ID) REFERENCES MEAL(MEAL_ID),
	CONSTRAINT CHK_USER_MAIL CHECK (USER_MAIL REGEXP '[a-zA-Z0-9_\-]+@([a-zA-Z0-9_\-]+\.)+(com|org|edu)' = 1),
	CONSTRAINT CHK_ORDER_MEAL_QTY CHECK (ORDER_MEAL_QTY > 0),
	CONSTRAINT CHK_MEAL_PRICE CHECK (MEAL_PRICE > 0)
)

drop table ORDERS

delete from ORDERS

insert into ORDERS (USER_NAME, USER_GENDER, USER_PHONE, USER_MAIL, USER_BIRTH, USER_ADDRESS) 
values ('Max', 'M', '0955820712', 'maxkuo712@gmail.com', '2021-05-07', '台中市西屯區')

select * from ORDERS

-- --------------------------------------------------------------------------------------------

create table MEAL (
	MEAL_ID varchar(100) DEFAULT(CONCAT('ML', DATE_FORMAT(CURRENT_DATE(),'%Y%m%d'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY,
	MEAL_NAME varchar(100) not null,
	MEAL_CATEGORY_ID varchar(100) not null,
	MEAL_CATEGORY_NAME varchar(100) not null,
	STORE_ID varchar(100) not null,
	MEAL_IMAGE LONGBLOB not null,
	MEAL_DESC LONGTEXT not null,
	MEAL_PRICE MEDIUMINT not null,
	MEAL_CALORIE double not null,
	MEAL_CARB double not null,
	MEAL_FAT double not null,
	MEAL_PROTEIN double not null,
	CREATE_TIME datetime default sysdate() not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	FOREIGN KEY (MEAL_CATEGORY_ID) REFERENCES MEAL_CATEGORY(MEAL_CATEGORY_ID),
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID)
)

drop table MEAL

-- --------------------------------------------------------------------------------------------

create table STORE_MESSAGE (
	MESSAGE_ID varchar(100) DEFAULT(CONCAT('M', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY,
	MESSAGE_NAME varchar(100) not null,
	STORE_ID varchar(100) not null,
	STORE_NAME varchar(100) not null,
	MESSAGE_DESC LONGTEXT not null,
	MESSAGE_STATUS Boolean not null,
	CREATE_TIME datetime default sysdate() not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID)
)

drop table STORE_MESSAGE

-- --------------------------------------------------------------------------------------------
create table MEAL_CATEGORY (
	MEAL_CATEGORY_ID varchar(100) not null PRIMARY KEY,
	MEAL_CATEGORY_NAME varchar(100) not null,
	MEAL_CATEGORY_DESC LONGTEXT not null
)

drop table MEAL_CATEGORY

-- 雞、豬、羊、牛、魚、沙拉、飲料

-- --------------------------------------------------------------------------------------------

create table INGREDIENT (
	INGREDIENT_ID varchar(100) PRIMARY KEY,
	INGREDIENT_DATA_CATEGORY varchar(100) not null,
	INGREDIENT_NAME varchar(100) not null,
	INGREDIENT_CATEGORY varchar(100) not null,
	INGREDIENT_DESC varchar(100) not null,
	CALORIE double not null,
	CARB double not null,
	FAT double not null,
	PROTEIN double not null
)

select 'INGREDIENT' as INGREDIENT_Obj , GROUP_CONCAT(
	JSON_OBJECT(
	'INGREDIENT_ID',INGREDIENT_ID, 
	'INGREDIENT_DATA_CATEGORY',INGREDIENT_DATA_CATEGORY,
	'INGREDIENT_NAME',INGREDIENT_NAME,
	'INGREDIENT_CATEGORY',INGREDIENT_CATEGORY,
	'INGREDIENT_DESC',INGREDIENT_DESC,
	'CALORIE',CALORIE,
	'CARB',CARB,
	'FAT',FAT,
	'PROTEIN',PROTEIN
) SEPARATOR ',') from INGREDIENT
GROUP BY INGREDIENT_Obj

select distinct  INGREDIENT_CATEGORY  from INGREDIENT

select count(distinct INGREDIENT_ID)  from INGREDIENT

delete from INGREDIENT

insert into INGREDIENT
select
	food.FOOD_ID, -- INGREDIENT_ID
	food.DATA_CATEGORY, -- INGREDIENT_DATA_CATEGORY
	IF((food.FOOD_NOEMAL_NAME = ""), food.FOOD_NAME, CONCAT(food.FOOD_NAME, "：", food.FOOD_NOEMAL_NAME)), -- INGREDIENT_NAME
	food.FOOD_CATEGORY, -- INGREDIENT_CATEGORY
	food.FOOD_DISC, -- INGREDIENT_DESC
	ROUND((food.VALUE_PER_UNIT_HUNDRED/100), 2),
	ROUND((c.VALUE_PER_UNIT_HUNDRED/100), 2),
	ROUND((f.VALUE_PER_UNIT_HUNDRED/100), 2),
	ROUND((p.VALUE_PER_UNIT_HUNDRED/100), 2)
from (select * from test.Food_Data_csv where FOOD_ANALYSIS_NAME = '熱量') as food
	inner join (select FOOD_ID, VALUE_PER_UNIT_HUNDRED from test.Food_Data_csv where FOOD_ANALYSIS_NAME = '總碳水化合物') as c
		on c.FOOD_ID = food.FOOD_ID
	inner join (select FOOD_ID, VALUE_PER_UNIT_HUNDRED from test.Food_Data_csv where FOOD_ANALYSIS_NAME = '粗脂肪') as f
		on f.FOOD_ID = food.FOOD_ID
	inner join (select FOOD_ID, VALUE_PER_UNIT_HUNDRED from test.Food_Data_csv where FOOD_ANALYSIS_NAME = '粗蛋白') as p
		on p.FOOD_ID = food.FOOD_ID


UPDATE INGREDIENT as fooddb
SET CARB = ROUND((food.VALUE_PER_UNIT_HUNDRED/100), 2)
FROM (select * from test.Food_Data_csv WHERE FOOD_ANALYSIS_NAME = '總碳水化合物') as food
where fooddb.INGREDIENT_ID = food.FOOD_ID


select * from test.Food_Data_csv 

select FOOD_CATEGORY, DATA_CATEGORY, FOOD_ID, 
	IF((FOOD_NOEMAL_NAME = ""), FOOD_NAME, CONCAT(FOOD_NAME, "：", FOOD_NOEMAL_NAME)) as FOOD_NAME, FOOD_DISC,
	FOOD_ANALYSIS_CATEGORY, FOOD_ANALYSIS_NAME, UNIT_OF_WEIGHT, VALUE_PER_UNIT_HUNDRED, ROUND((VALUE_PER_UNIT_HUNDRED/100), 2) as VALUE_PER_UNIT
from test.Food_Data_csv
where FOOD_ID  = 'E1700101'
order by FOOD_ID, FOOD_ANALYSIS_NAME

UPDATE test.Food_Data_csv
SET FOOD_ID = 'J0707102'
WHERE FOOD_NAME = '鮪魚肚'


