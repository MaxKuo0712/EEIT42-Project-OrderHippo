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
	USER_ID varchar(100) DEFAULT(CONCAT('U', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY NOT NULL,
	USER_NAME varchar(100) not null,
	USER_GENDER ENUM('M','F','X') not null,
	USER_PHONE varchar(100) not null,
	USER_MAIL varchar(200) UNIQUE not null,
	USER_BIRTH date not null,
	USER_AGE int DEFAULT (TIMESTAMPDIFF(YEAR, USER_BIRTH, CURRENT_DATE())),
	USER_ADDRESS varchar(300) not null,
	CREATE_TIME datetime default (sysdate()) not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	CONSTRAINT CHK_USER_PHONE CHECK (USER_PHONE REGEXP '[^0-9]' = 0),
	CONSTRAINT CHK_USER_MAIL CHECK (USER_MAIL REGEXP '[a-zA-Z0-9_\-]+@([a-zA-Z0-9_\-]+\.)+(com|org|edu)' = 1),
	CONSTRAINT CHK_USER_AGE CHECK (USER_AGE > 0)
);
-- --------------------------------------------------------------------------------------------
-- 店家基本資料(STORE_INFO)
create table STORE_INFO (
	STORE_ID varchar(100) DEFAULT(CONCAT('S', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY NOT NULL,
	STORE_NAME varchar(100) NOT NULL,
	STORE_ADDRESS varchar(300) NOT NULL,
	STORE_PHONE varchar(100) NOT NULL,
	STORE_MAIL varchar(200) UNIQUE NOT NULL,
	STORE_LOCATION varchar(200) UNIQUE NOT NULL,
	STORE_OPEN_STATUS BOOLEAN default false NOT NULL, -- 不會有default
	CREATE_TIME datetime default (sysdate()) NOT NULL,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	CONSTRAINT CHK_STORE_PHONE CHECK (STORE_PHONE REGEXP '[^0-9]' = 0),
	CONSTRAINT CHK_STORE_MAIL CHECK (STORE_MAIL REGEXP '[a-zA-Z0-9_\-]+@([a-zA-Z0-9_\-]+\.)+(com|org|edu)' = 1)
);
-- --------------------------------------------------------------------------------------------
-- 商品分類(MEAL_CATEGORY)
create table MEAL_CATEGORY (
	MEAL_CATEGORY_ID varchar(100) not null PRIMARY KEY,
	MEAL_CATEGORY_NAME varchar(100) not null,
	MEAL_CATEGORY_DESC LONGTEXT not null
);
-- --------------------------------------------------------------------------------------------
-- 商品資料(MEAL)
create table MEAL (
	MEAL_ID varchar(100) DEFAULT(CONCAT('ML', DATE_FORMAT(CURRENT_DATE(),'%Y%m%d'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY NOT NULL,
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
	CREATE_TIME datetime default (sysdate()) not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	FOREIGN KEY (MEAL_CATEGORY_ID) REFERENCES MEAL_CATEGORY(MEAL_CATEGORY_ID),
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID)
);
-- --------------------------------------------------------------------------------------------
-- 訂單(ORDERS)
create table ORDERS (
	ORDER_ID varchar(100) DEFAULT(CONCAT('O', DATE_FORMAT(CURRENT_DATE(),'%Y%m%d'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY NOT NULL,
	STORE_ID varchar(100) not null,
	USER_ID varchar(100) not null,
	USER_MAIL varchar(200) not null,
	MEAL_ID varchar(100) not null,
	MEAL_NAME varchar(100) not null,
	ORDER_MEAL_QTY TINYINT not null,
	MEAL_PRICE MEDIUMINT not null,
	ORDER_STATUS ENUM('0','1','2','3') not null, -- 0取消的訂單 1未確認訂單, 2已確認訂單, 3已完成訂單
	CREATE_TIME datetime default (sysdate()) not null,
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID),
	FOREIGN KEY (USER_ID) REFERENCES USER_INFO(USER_ID),
	FOREIGN KEY (MEAL_ID) REFERENCES MEAL(MEAL_ID),
	CONSTRAINT CHK_USER_MAIL CHECK (USER_MAIL REGEXP '[a-zA-Z0-9_\-]+@([a-zA-Z0-9_\-]+\.)+(com|org|edu)' = 1),
	CONSTRAINT CHK_ORDER_MEAL_QTY CHECK (ORDER_MEAL_QTY > 0),
	CONSTRAINT CHK_MEAL_PRICE CHECK (MEAL_PRICE > 0)
);
-- --------------------------------------------------------------------------------------------
-- 店家訊息公告(STORE_MESSAGE)
create table STORE_MESSAGE (
	MESSAGE_ID varchar(100) DEFAULT(CONCAT('M', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY NOT NULL,
	MESSAGE_NAME varchar(100) not null,
	STORE_ID varchar(100) not null,
	STORE_NAME varchar(100) not null,
	MESSAGE_DESC LONGTEXT not null,
	MESSAGE_STATUS Boolean not null, --刪除
	CREATE_TIME datetime default (sysdate()) not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID)
);
-- --------------------------------------------------------------------------------------------
-- 付款頁面(PAYMENT)
CREATE TABLE PAYMENT (
	PAYMENT_ID varchar(100) DEFAULT(CONCAT('P', DATE_FORMAT(CURRENT_DATE(),'%Y%m%d'), '_', REPLACE(UUID(),'-',''))) PRIMARY KEY NOT NULL,
	ORDER_ID varchar(100) NOT NULL,
	USER_ID varchar(100) NOT NULL,
	STORE_ID varchar(100) NOT NULL,
	PAYMENT_PRICE MEDIUMINT NOT NULL,
	PAYMENT_CATEGORY ENUM('1','2','3') NOT NULL, -- 1. Credit Card, 2. Cash, 3. 3rd payment
	PAYMENT_STATUS BOOLEAN default true not null, -- true 有效 false 無效(退款)
	CREATE_TIME datetime default (sysdate()) NOT NULL,
	FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ORDER_ID),
	FOREIGN KEY (USER_ID) REFERENCES USER_INFO(USER_ID),
	FOREIGN KEY (STORE_ID) REFERENCES STORE_INFO(STORE_ID),
	CONSTRAINT CHK_PAYMENT_PRICE CHECK (PAYMENT_PRICE > 0)
);
-- --------------------------------------------------------------------------------------------
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
	CREATE_TIME datetime default (sysdate()) not null,
	REVISE_TIME datetime default null,
	REVISE_ID varchar(100) default null,
	FOREIGN KEY (MEAL_ID) REFERENCES MEAL(MEAL_ID),
	FOREIGN KEY (INGREDIENT_ID) REFERENCES INGREDIENT(INGREDIENT_ID)
);









