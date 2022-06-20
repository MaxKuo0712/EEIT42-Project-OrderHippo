# EEIT42-Project-OrderHippo

## 網站網址：https://orderhippo.netlify.app/
## YouTube網址-訂餐Demo：https://youtu.be/MjTPmvcK6rE
## YouTube網址-後台管理系統Demo：https://youtu.be/BWk-ibRinKE

我們是OrderHippo，一間健康飲食餐廳的訂餐網站，除了餐點的訂購服務外，也包含了一般消費者較注重的熱量及三大營養素標示及計算(碳水化合物、脂肪、蛋白質)，食材資料來自政府公開資料約2000多筆，提供消費者在選購及享用的同時，可以更輕鬆及精準的去管理自己的飲食狀況，我們也提供Line的聊天機器人來提供菜單查詢，消費者可利用Google帳號來輕鬆登入，並可選擇使用現金、信用卡來輕鬆付款。

* 網站整體開發是前後端分離，共串接了6個API。
* 登入系統使用的是Firebase來進行登入管理，包含信箱登入及Google登入，同時可以進行信箱驗證及更改密碼。
* 串接Google Map API提供消費者查看店家位置。訂單資訊的傳遞是以WebSocket來進行前端使用者送出訂單後與店家後台的快速資訊同步，以確保訂單處理的即時性。
* 信用卡支付是使用Stripe金流，可以做到信用卡支付及退款的功能。資料庫是以Azure的MySQL雲端資料庫來進行資料存取。
* 串接使用imgur API來儲存餐點、廣告圖檔，以減少雲端資料庫的使用量。
* 後端API使用RESTful API，構建一個RESTful Web Service，提供前端畫面所需的資源，並且採用Token來管理API的使用權限。
* 在整體網站開發上是使用Swagger來提供可即時更新及測試API文件，以利前端開發時查看及測試API。

### 我是團隊組長，主要負責：
* 後端Spring Boot + Spring Data JPA
* 前後端Deploy(前端netlify, 後端Heroku)
* 串接Firebase SDK實作登入系統(信箱、Google)
* Azure雲端資料庫規劃及建置
* 前後端WebSocket建構
* RESTful API 開發
* Swagger API文件建置
* 實作RESTful API token驗證機制
* 前端API(前台、後台)需求溝通
* LINE 菜單機器人
* Google Map API串接
* imgur 圖床API串接
* 團隊專案管理(進度控管、派工、溝通協調)
* GitHub版本管理

<img width="1436" alt="截圖 2022-06-15 下午7 21 15" src="https://user-images.githubusercontent.com/68499661/173815781-1e9c75bb-b408-4a88-83f0-d7df50061f66.png">

<img width="827" alt="截圖 2022-06-16 下午4 43 43" src="https://user-images.githubusercontent.com/68499661/174030838-3f3e3d40-f3a9-4032-bf0e-b90a20f54099.png">
