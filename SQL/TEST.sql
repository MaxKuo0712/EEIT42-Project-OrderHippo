create table User_Info (
	ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	UserID varchar(100) DEFAULT(CONCAT('U', REPLACE(CURRENT_DATE(),'-',''), '_', REPLACE(UUID(),'-',''))),
	UserName varchar(100) UNIQUE not null,
	UserGender ENUM('男','女') not null,
	UserAge int DEFAULT TIMESTAMPDIFF(YEAR, UserBirth, SYSDATE()),
	UserBirth date not null
)

create table User_Info (
	ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	UserID varchar(100) DEFAULT(CONCAT('U', REPLACE(CURRENT_DATE(),'-',''), '_', REPLACE(UUID(),'-',''))),
	UserName varchar(100) UNIQUE not null,
	UserGender ENUM('男','女') not null,
	UserAge int DEFAULT TIMESTAMPDIFF(YEAR, UserBirth, SYSDATE()),
	UserBirth date not null
)

create table User_Info (
	ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	UserID varchar(100),
	UserName varchar(100) UNIQUE not null,
	UserAge int DEFAULT TIMESTAMPDIFF(YEAR, UserBirth, SYSDATE()),
	UserBirth date not null
)


insert into User_Info (UserName, UserBirth) values ('Max', '1993-07-12')

select * from User_Info

select CONCAT('U', DATE_FORMAT(CURRENT_DATE(),'%Y%m'), '_', REPLACE(UUID(),'-',''))

select CONCAT('U', DATE_FORMAT(CURRENT_DATE(),'%Y%m%d'), '_', REPLACE(UUID(),'-',''))

UPDATE User_Info
SET UserID = ;
