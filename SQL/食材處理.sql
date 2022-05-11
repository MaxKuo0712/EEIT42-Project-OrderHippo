select *
from Food_Data_csv
where FOOD_NOEMAL_NAME = '去皮雞胸肉' and FOOD_ANALYSIS_CATEGORY = '一般成分'
	and FOOD_ANALYSIS_NAME  in ('熱量', '總碳水化合物', '粗蛋白', '粗脂肪') and VALUE_PER_UNIT_HUNDRED 
order by FOOD_ID 

select *
from Food_Data_csv
where FOOD_NOEMAL_NAME = '去皮雞胸肉'
order by FOOD_ID 

select *
from Food_Data_csv
order by FOOD_ID, FOOD_ANALYSIS_NAME

-- 待處理-刪除：FOOD_ANALYSIS_CATEGORY, VALUE_WEIGHT_PER_UNIT

select FOOD_CATEGORY, DATA_CATEGORY, FOOD_ID, FOOD_NAME, FOOD_NOEMAL_NAME, FOOD_DISC,
	FOOD_ANALYSIS_CATEGORY, FOOD_ANALYSIS_NAME, UNIT_OF_WEIGHT, VALUE_PER_UNIT_HUNDRED, ROUND((VALUE_PER_UNIT_HUNDRED/100), 2) as VALUE_PER_UNIT
from Food_Data_csv
order by FOOD_ID, FOOD_ANALYSIS_NAME

-- 待處理 --
select FOOD_CATEGORY, DATA_CATEGORY, FOOD_ID, 
	IF((FOOD_NOEMAL_NAME = ""), FOOD_NAME, CONCAT(FOOD_NAME, "：", FOOD_NOEMAL_NAME)) as FOOD_NAME, FOOD_DISC,
	FOOD_ANALYSIS_CATEGORY, FOOD_ANALYSIS_NAME, UNIT_OF_WEIGHT, VALUE_PER_UNIT_HUNDRED, ROUND((VALUE_PER_UNIT_HUNDRED/100), 2) as VALUE_PER_UNIT
from Food_Data_csv
where FOOD_NOEMAL_NAME = '去皮雞胸肉'
order by FOOD_ID, FOOD_ANALYSIS_NAME
-- 待處理 END --

select distinct FOOD_NAME 
from Food_Data_csv a
where DATA_CATEGORY = '樣品平均值'
order by FOOD_ID, DATA_CATEGORY , FOOD_ANALYSIS_NAME

select *
from Food_Data_csv
where FOOD_ANALYSIS_NAME in ('熱量', '總碳水化合物', '粗蛋白', '粗脂肪')
order by FOOD_ID, FOOD_ANALYSIS_NAME

select SUBSTR(FOOD_ID, 1, 6)  
from Food_Data_csv fdc 
where FOOD_NAME like '%滷蛋%' 
order by FOOD_ID

select a.*
from Food_Data_csv as a
	inner join (
		select distinct FOOD_ID
		from Food_Data_csv fdc 
		where DATA_CATEGORY = '樣品平均值'
		order by FOOD_ID	
	) as b on SUBSTR(a.FOOD_ID, 1, 6) = b.FOOD_ID and a.FOOD_ID <> b.FOOD_ID
order by FOOD_ID

select *
from Food_Data_csv fdc 
where FOOD_ID like 'D11002%'
order by FOOD_ID


where VALUE_PER_UNIT is null and VALUE_WEIGHT_PER_UNIT  is null