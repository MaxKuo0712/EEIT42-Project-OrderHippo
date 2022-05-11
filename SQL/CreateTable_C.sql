***********************
******* 參考資料 *******
***********************


drop table USER_INFO

delete from USER_INFO

insert into USER_INFO (USER_NAME, USER_GENDER, USER_PHONE, USER_MAIL, USER_BIRTH, USER_ADDRESS) 
values ('Max', 'M', '0955820712', 'maxkuo712@gmail.com', '2021-05-07', '台中市西屯區')

select * from USER_INFO


# 來自別人的，就不要設default
編號	交易編號	訂單編號	會員編號	店家編號	訂單金額	付款方式	付款時間
int	varchar	varchar	varchar	varchar	MEDIUMINT	EUMN	DATETIME
ID	PAYMENT_ID	ORDER_ID	USER_ID	STORE_ID	PAYMENT_PRICE	PAYMENT_CATEGORY	CREATE_TIME

# 不用設default
訂單編號	會員編號	店家編號
varchar	varchar	varchar
ORDER_ID	USER_ID	STORE_ID


年月日
CONCAT('U', DATE_FORMAT(CURRENT_DATE(),'%Y%m%d'), '_', REPLACE(UUID(),'-',''))

年月
CONCAT('U', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))

create table USER_INFO (
	ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	USER_ID varchar(100) DEFAULT(CONCAT('U', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))),
	USER_NAME varchar(100) not null,
	USER_GENDER ENUM('M','F','X') not null,
	USER_PHONE varchar(100) not null,
	USER_MAIL varchar(200) UNIQUE not null,
	USER_BIRTH date not null,
	USER_AGE int DEFAULT TIMESTAMPDIFF(YEAR, USER_BIRTH, CURRENT_DATE()),
	USER_ADDRESS varchar(200) not null,
	CREATE_TIME datetime default sysdate(),
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	CONSTRAINT CHK_USER_PHONE CHECK (USER_PHONE REGEXP '[^0-9]' = 0),
	CONSTRAINT CHK_USER_MAIL CHECK (USER_MAIL REGEXP '[a-zA-Z0-9_\-]+@([a-zA-Z0-9_\-]+\.)+(com|org|edu|nz|au)' = 1),
	CONSTRAINT CHK_USER_AGE CHECK (USER_AGE > 0)
)


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

-- default 不用給not null
-- 來自別人的，就不用設default
-- Store的CONSTRAINT，複製到USER的了

---------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------------------------------




-- 店家基本資料(STORE_INFO)
CREATE TABLE STORE_INFO (
	STORE_ID varchar(100) DEFAULT(CONCAT('S', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY NOT NULL,
	STORE_NAME varchar(100) NOT NULL,
	STORE_ADDRESS varchar(300) NOT NULL,
	STORE_PHONE varchar(100) NOT NULL,
	STORE_MAIL varchar(200) UNIQUE NOT NULL,
	STORE_LOCATION varchar(200) UNIQUE NOT NULL,
	STORE_OPEN_STATUS BOOLEAN default false NOT NULL, -- 不會有default
	CREATE_TIME datetime default sysdate() NOT NULL,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	CONSTRAINT CHK_STORE_PHONE CHECK (STORE_PHONE REGEXP '[^0-9]' = 0),
	CONSTRAINT CHK_STORE_MAIL CHECK (STORE_MAIL REGEXP '[a-zA-Z0-9_\-]+@([a-zA-Z0-9_\-]+\.)+(com|org|edu|nz|au)' = 1)
)

drop table STORE_INFO

-- 付款頁面(PAYMENT)
CREATE TABLE PAYMENT (
	PAYMENT_ID varchar(100) DEFAULT(CONCAT('P', DATE_FORMAT(CURRENT_DATE(),'%Y%m%d'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY NOT NULL,
	ORDER_ID varchar(100) NOT NULL,
	USER_ID varchar(100) NOT NULL,
	STORE_ID varchar(100) NOT NULL,
	PAYMENT_PRICE MEDIUMINT NOT NULL,
	PAYMENT_CATEGORY ENUM('1','2','3') NOT NULL, -- 1. Credit Card, 2. Cash, 3. 3rd payment
	PAYMENT_STATUS BOOLEAN default true not null, -- true 有效 false 無效(退款)
	CREATE_TIME datetime default sysdate() NOT NULL,
	FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ORDER_ID),
	FOREIGN KEY (USER_ID) REFERENCES USER_INFO(USER_ID),
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID),
	CONSTRAINT CHK_PAYMENT_PRICE CHECK (PAYMENT_PRICE > 0)
)

drop table PAYMENT

-- 餐點BOM(MEAL_BOM)
CREATE TABLE MEAL_BOM (
	BOM_ID varchar(100) DEFAULT(CONCAT('MLB', DATE_FORMAT(CURRENT_DATE(),'%Y%m%d'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY NOT NULL,
	MEAL_ID varchar(100) NOT NULL,
	MEAL_NAME varchar(100) NOT NULL,
	INGREDIENT_ID varchar(100) NOT NULL,
	INGREDIENT_NAME varchar(100) NOT NULL,
	MEAL_INGREDIENT_WEIGHT double not null, -- default 不會空的
	MEAL_INGREDIENT_CALORIE double not null,
	MEAL_INGREDIENT_CARB double not null,
	MEAL_INGREDIENT_FAT double not null,
	MEAL_INGREDIENT_PROTEIN double not null,
	CREATE_TIME datetime default sysdate() not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	FOREIGN KEY (MEAL_ID) REFERENCES MEAL(MEAL_ID),
	FOREIGN KEY (INGREDIENT_ID) REFERENCES INGREDIENT(INGREDIENT_ID)
)

drop table MEAL_BOM

/*
-- 分析資料(ANALYSIS_DATA)
CREATE TABLE ANALYSIS_DATA (
	STORE_ID varchar(100) DEFAULT(CONCAT('S', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY NOT NULL,
	STORE_NAME varchar(100) NOT NULL,
	STORE_MONTH_REVENUE int NOT NULL,  -- 從 PAYMENT.PAYMENT_PRICE 而來？
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID),
	CONSTRAINT CHK_STORE_MONTH_REVENUE CHECK (STORE_MONTH_REVENUE > 0)
)*/






