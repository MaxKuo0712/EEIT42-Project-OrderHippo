-- --------------------------------------------------------------------------------------------
-- 營養成分表(INGREDIENT)
create table INGREDIENT (
	INGREDIENT_ID varchar(100) PRIMARY KEY NOT NULL,
	INGREDIENT_DATA_CATEGORY varchar(100) not null,
	INGREDIENT_NAME varchar(100) not null, 
	INGREDIENT_CATEGORY varchar(100) not null,
	INGREDIENT_DESC varchar(100) not null,
	CALORIE double not null,
	CARB double not null,
	FAT double not null,
	PROTEIN double not null
);

select * from INGREDIENT

-- --------------------------------------------------------------------------------------------
-- 會員基本資料(USER_INFO)
create table USER_INFO (
	ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	-- USER_ID varchar(100) DEFAULT(CONCAT('U', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY NOT NULL,
	USER_ID varchar(100) UNIQUE NOT NULL,
	USER_NAME varchar(100) not null,
	USER_GENDER ENUM('M','F','X') not null,
	USER_PHONE varchar(100) not null,
	USER_MAIL varchar(200) UNIQUE not null,
	USER_BIRTH date not null,
	USER_AGE int DEFAULT (TIMESTAMPDIFF(YEAR, USER_BIRTH, CURRENT_DATE())),
	USER_ADDRESS varchar(300) not null,
	USER_TOKEN varchar(300) default (REPLACE(UUID(),'-','')),
	LAST_LOGININ_TIME datetime default null,
	CREATE_ID varchar(100) not null,
	CREATE_TIME datetime default (sysdate()) not null,
	REVISE_ID varchar(100) default null,
	REVISE_TIME datetime default null,
	CONSTRAINT CHK_USER_PHONE CHECK (USER_PHONE REGEXP '[^0-9]' = 0),
	CONSTRAINT CHK_USER_MAIL CHECK (USER_MAIL REGEXP '[a-zA-Z0-9_\-]+@([a-zA-Z0-9_\-]+\.)+(com|org|edu)' = 1),
	CONSTRAINT CHK_USER_AGE CHECK (USER_AGE > 0)
);

select * from USER_INFO

delete from USER_INFO

drop table USER_INFO;

-- --------------------------------------------------------------------------------------------
-- 店家基本資料(STORE_INFO)
create table STORE_INFO (
	ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	STORE_ID varchar(100) DEFAULT(CONCAT('S', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))) UNIQUE NOT NULL,
	-- STORE_ID varchar(100) default 'Admin' UNIQUE NOT NULL,
	STORE_NAME varchar(100) UNIQUE NOT NULL,
	STORE_ADDRESS varchar(300) UNIQUE NOT NULL,
	STORE_PHONE varchar(100) UNIQUE NOT NULL,
	STORE_MAIL varchar(200) UNIQUE NOT NULL,
	STORE_LOCATION varchar(200) UNIQUE NOT NULL,
	STORE_OPEN_STATUS BOOLEAN default false NOT NULL, -- 不會有default
	STORE_TOKEN varchar(300) default (REPLACE(UUID(),'-','')),
	CREATE_ID varchar(100) not null,
	CREATE_TIME datetime default (sysdate()) NOT NULL,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	CONSTRAINT CHK_STORE_PHONE CHECK (STORE_PHONE REGEXP '[^0-9]' = 0),
	CONSTRAINT CHK_STORE_MAIL CHECK (STORE_MAIL REGEXP '[a-zA-Z0-9_\-]+@([a-zA-Z0-9_\-]+\.)+(com|org|edu)' = 1)
);

insert STORE_INFO (STORE_NAME, STORE_ADDRESS, STORE_PHONE, STORE_MAIL, STORE_LOCATION, STORE_OPEN_STATUS, CREATE_ID)
values ('Order Hippo', '408台中市南屯區公益路二段51號', '091234569', 'eeit42orderhippo@gmail.com', '24.1505311,120.6488196', false, 'Admin')

update STORE_INFO 
SET STore_token = '$2a$10$fFBFr4RtQZCSgp4e5GND9umKFN.pQ2yaiZoUffSS3kz7Bj82cfXyu'
where STORE_ID  = 'S202205_2c157218dc9611ec95b9068cdc81eecb'

update STORE_INFO 
SET STORE_PHONE = '091234568'
where STORE_ID  = 'S202205_2c157218dc9611ec95b9068cdc81eecb'

select * from STORE_INFO

delete from STORE_INFO

drop table STORE_INFO;

-- --------------------------------------------------------------------------------------------
-- 商品分類(MEAL_CATEGORY)
create table MEAL_CATEGORY (
	ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	MEAL_CATEGORY_ID varchar(100) UNIQUE not null,
	MEAL_CATEGORY_NAME varchar(100) UNIQUE not null,
	MEAL_CATEGORY_DESC LONGTEXT not null
);

insert MEAL_CATEGORY (MEAL_CATEGORY_ID, MEAL_CATEGORY_NAME, MEAL_CATEGORY_DESC)
values ('HOTMEAL', '熱門餐點', '熱門餐點')

insert MEAL_CATEGORY (MEAL_CATEGORY_ID, MEAL_CATEGORY_NAME, MEAL_CATEGORY_DESC)
values ('HEALTHMEAL', '健康餐盒', '健康餐盒')

insert MEAL_CATEGORY (MEAL_CATEGORY_ID, MEAL_CATEGORY_NAME, MEAL_CATEGORY_DESC)
values ('SALAD', '沙拉', '沙拉')

insert MEAL_CATEGORY (MEAL_CATEGORY_ID, MEAL_CATEGORY_NAME, MEAL_CATEGORY_DESC)
values ('PASTA', '麵食', '麵食')

insert MEAL_CATEGORY (MEAL_CATEGORY_ID, MEAL_CATEGORY_NAME, MEAL_CATEGORY_DESC)
values ('DRINK', '飲料', '飲料')

select * from MEAL_CATEGORY mc 

drop table MEAL_CATEGORY;

-- --------------------------------------------------------------------------------------------
-- 商品資料(MEAL)
create table MEAL (
	ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	-- MEAL_ID varchar(100) UNIQUE KEY NOT NULL,
	MEAL_ID varchar(100) DEFAULT(CONCAT('M', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))) UNIQUE NOT NULL,
	MEAL_NAME varchar(100) UNIQUE not null,
	MEAL_CATEGORY_ID varchar(100) not null,
	MEAL_CATEGORY_NAME varchar(100) not null,
	STORE_ID varchar(100) not null,
	MEAL_IMAGE varchar(100) not null,
	MEAL_DESC LONGTEXT not null,
	MEAL_PRICE int not null,
	MEAL_CALORIE double not null,
	MEAL_CARB double not null,
	MEAL_FAT double not null,
	MEAL_PROTEIN double not null,
	MEAL_STATUS BOOLEAN default true NOT NULL, -- 不會有default
	CREATE_ID varchar(100) not null,
	CREATE_TIME datetime default (sysdate()) not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	FOREIGN KEY (MEAL_CATEGORY_ID) REFERENCES MEAL_CATEGORY(MEAL_CATEGORY_ID),
	FOREIGN KEY (MEAL_CATEGORY_NAME) REFERENCES MEAL_CATEGORY(MEAL_CATEGORY_NAME),
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID)
);

drop table MEAL;

-- --------------------------------------------------------------------------------------------
-- 訂單(ORDERS)
create table ORDERS (
	ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	ORDER_ID varchar(100) DEFAULT(CONCAT('O', DATE_FORMAT(CURRENT_DATE(),'%Y%m%d'), '_', REPLACE(UUID(),'-',''))) UNIQUE NOT NULL,
	STORE_ID varchar(100) not null,
	USER_ID varchar(100) not null,
	-- USER_MAIL varchar(200) not null,
	ORDERS_PRICE int not null,
	ORDER_STATUS ENUM('0','1','2','3') not null, -- 0取消的訂單 1未確認訂單, 2已確認訂單, 3已完成訂單
	CREATE_ID varchar(100) not null,
	CREATE_TIME datetime default (sysdate()) not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID),
	FOREIGN KEY (USER_ID) REFERENCES USER_INFO(USER_ID),
	-- FOREIGN KEY (USER_MAIL) REFERENCES USER_INFO(USER_MAIL),
	-- CONSTRAINT CHK_USER_MAIL CHECK (USER_MAIL REGEXP '[a-zA-Z0-9_\-]+@([a-zA-Z0-9_\-]+\.)+(com|org|edu)' = 1),
	CONSTRAINT CHK_ORDERS_PRICE CHECK (ORDERS_PRICE > 0)
);


drop table ORDERS;

-- --------------------------------------------------------------------------------------------
-- 顧客訂餐資訊(USER_ORDERS)
/*
create table USER_ORDERS (
	ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	USER_ID varchar(100) not null,
	ORDER_ID varchar(100) not null,
	MEAL_ID varchar(100) not null,
	MEAL_NAME varchar(100) not null,
	ORDER_MEAL_QTY int not null,
	MEAL_PRICE int not null,
	STORE_ID varchar(100) not null,
	USER_MAIL varchar(200) not null,
	ORDER_STATUS int not null, -- 0取消的訂單 1未確認訂單, 2已確認訂單, 3已完成訂單
	CREATE_ID varchar(100) not null,
	CREATE_TIME datetime default (sysdate()) not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID),
	FOREIGN KEY (USER_ID) REFERENCES USER_INFO(USER_ID),
	FOREIGN KEY (USER_MAIL) REFERENCES USER_INFO(USER_MAIL),
	FOREIGN KEY (MEAL_ID) REFERENCES MEAL(MEAL_ID),
	FOREIGN KEY (MEAL_NAME) REFERENCES MEAL(MEAL_NAME),
	CONSTRAINT CHK_USER_MAIL CHECK (USER_MAIL REGEXP '[a-zA-Z0-9_\-]+@([a-zA-Z0-9_\-]+\.)+(com|org|edu)' = 1),
	CONSTRAINT CHK_ORDER_MEAL_QTY CHECK (ORDER_MEAL_QTY > 0),
	CONSTRAINT CHK_MEAL_PRICE CHECK (MEAL_PRICE > 0)
);
drop table USER_ORDERS;
**/

-- 訂單餐點資訊(ORDER_MEALDETAIL)
create table ORDER_MEALDETAIL (
	ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	ORDER_ID varchar(100) not null,
	MEAL_ID varchar(100) not null,
	-- MEAL_NAME varchar(100) not null,
	ORDER_MEAL_QTY int not null,
	MEAL_PRICE int not null,
	CREATE_ID varchar(100) not null,
	CREATE_TIME datetime default (sysdate()) not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ORDER_ID),
	FOREIGN KEY (MEAL_ID) REFERENCES MEAL(MEAL_ID),
	-- FOREIGN KEY (MEAL_NAME) REFERENCES MEAL(MEAL_NAME),	
	CONSTRAINT CHK_ORDER_MEAL_QTY CHECK (ORDER_MEAL_QTY > 0),
	CONSTRAINT CHK_MEAL_PRICE CHECK (MEAL_PRICE > 0)
);

drop table ORDER_MEALDETAIL;

-- --------------------------------------------------------------------------------------------
-- 店家訊息公告(STORE_MESSAGE)
create table STORE_MESSAGE (
	ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	MESSAGE_ID varchar(100) DEFAULT(CONCAT('M', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))) UNIQUE NOT NULL,
	MESSAGE_NAME varchar(100) UNIQUE not null,
	STORE_ID varchar(100) not null,
	-- STORE_NAME varchar(100) not null,
	MESSAGE_DESC LONGTEXT not null,
	MESSAGE_IMAGE varchar(100) not null,
	CREATE_ID varchar(100) not null,
	CREATE_TIME datetime default (sysdate()) not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID)
	-- FOREIGN KEY (STORE_NAME) REFERENCES STORE_INFO(STORE_NAME)
);

drop table STORE_MESSAGE;

-- --------------------------------------------------------------------------------------------
-- 付款頁面(PAYMENT)
CREATE TABLE PAYMENT (
	ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	PAYMENT_ID varchar(100) DEFAULT(CONCAT('P', DATE_FORMAT(CURRENT_DATE(),'%Y%m%d'), '_', REPLACE(UUID(),'-',''))) UNIQUE NOT NULL,
	ORDER_ID varchar(100) NOT NULL,
	USER_ID varchar(100) NOT NULL,
	STORE_ID varchar(100) NOT NULL,
	PAYMENT_PRICE int NOT NULL,
	PAYMENT_CATEGORY ENUM('1','2','3') NOT NULL, -- 1. Credit Card, 2. Cash, 3. 3rd payment
	PAYMENT_STATUS BOOLEAN default true not null, -- true 有效 false 無效(退款)
	CREATE_ID varchar(100) not null,
	CREATE_TIME datetime default (sysdate()) NOT NULL,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ORDER_ID),
	FOREIGN KEY (USER_ID) REFERENCES USER_INFO(USER_ID),
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID),
	CONSTRAINT CHK_PAYMENT_PRICE CHECK (PAYMENT_PRICE > 0)
);

drop table PAYMENT;

-- --------------------------------------------------------------------------------------------
-- 餐點BOM(MEAL_BOM)
CREATE TABLE MEAL_BOM (
	ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	BOM_ID varchar(100) DEFAULT(CONCAT('MLB', DATE_FORMAT(CURRENT_DATE(),'%Y%m%d'), '_', REPLACE(UUID(),'-',''))) UNIQUE NOT NULL,
	MEAL_ID varchar(100) NOT NULL,
	-- MEAL_NAME varchar(100) NOT NULL,
	INGREDIENT_ID varchar(100) NOT NULL,
	INGREDIENT_NAME varchar(100) NOT NULL,
	MEAL_INGREDIENT_WEIGHT double not null, -- default 不會空的
	MEAL_INGREDIENT_CALORIE double not null,
	MEAL_INGREDIENT_CARB double not null,
	MEAL_INGREDIENT_FAT double not null,
	MEAL_INGREDIENT_PROTEIN double not null,
	CREATE_ID varchar(100) not null,
	CREATE_TIME datetime default (sysdate()) not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	FOREIGN KEY (MEAL_ID) REFERENCES MEAL(MEAL_ID),
	-- FOREIGN KEY (MEAL_NAME) REFERENCES MEAL(MEAL_NAME),
	FOREIGN KEY (INGREDIENT_ID) REFERENCES INGREDIENT(INGREDIENT_ID)
);

drop table MEAL_BOM;

-- --------------------------------------------------------------------------------------------
-- 優惠券(STORE_COUPON)
create table STORE_COUPON (
	ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	COUPON_ID varchar(100) DEFAULT(CONCAT('C', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))) UNIQUE NOT NULL,
	COUPON_NAME varchar(100) UNIQUE not null,
	STORE_ID varchar(100) not null,
	-- STORE_NAME varchar(100) not null,
	COUPON_DESC LONGTEXT not null,
	CREATE_ID varchar(100) not null,
	CREATE_TIME datetime default (sysdate()) not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID)
	-- FOREIGN KEY (STORE_NAME) REFERENCES STORE_INFO(STORE_NAME)
);

drop table STORE_COUPON;







