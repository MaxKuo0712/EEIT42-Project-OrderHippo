// let postName = 'localhost:8080';
let postName = 'eeit42-order-hippo.herokuapp.com';

let shoppingCarList = [];

if (localStorage.getItem('shoppingCarList')) {
  shoppingCarList = JSON.parse(localStorage.getItem('shoppingCarList'));
}

localStorage.setItem('shoppingCarList', JSON.stringify(shoppingCarList));

if (JSON.parse(localStorage.getItem('userinfo'))) {
  let carUserID = JSON.parse(localStorage.getItem('userinfo')).USER_ID;
  let shoppingCarList = JSON.parse(localStorage.getItem('shoppingCarList')) || [];
  let userCarIndex = shoppingCarList.findIndex(carList => carList.USER_ID == carUserID);
  let shoppingCar = null;
  if (userCarIndex > -1) {
    shoppingCar = shoppingCarList[userCarIndex].shoppingCar;
    if (shoppingCar && shoppingCar.length > 0) {
      localStorage.setItem('item', JSON.stringify(shoppingCar));
      localStorage.setItem('allTotal', shoppingCar.map(car => car.total).reduce((a,b) => a+b));
    }
   
  } else if (!localStorage.getItem('item')) {
    shoppingCar = [];
    localStorage.setItem('item', JSON.stringify(shoppingCar));
    localStorage.setItem('allTotal', 0);
  }
}

/**------------------------------------------------------------------------------- */
// WebSocket
let websocket = null;

if (JSON.parse(localStorage.getItem('userinfo'))) {
  connWebSocket(JSON.parse(localStorage.getItem('userinfo')).USER_ID);
}

function connWebSocket(userInfo) {
  if ('WebSocket' in window) {
    websocket = new WebSocket(`wss://${postName}/websocket/${userInfo}`);
  } else {
    console.log("WebSocket連線失敗");
  }
}

function closeWebSocket() {
  websocket.close();
}

window.onbeforeunload = () => {
  if (websocket) {
    closeWebSocket();
  }
}

// if (localStorage.getItem('loginStatus')) {
if (localStorage.getItem('userinfo')) {
  websocket.onmessage = (e) => {
    // console.log(e.data); 
    receiveMsg(e.data);
  }
  if (!JSON.parse(localStorage.getItem('receiveMsg'))) {
    bellValue.innerText = 0;
  } else {
    bellValue.innerText = JSON.parse(localStorage.getItem('receiveMsg')).length;
  }
}

// 收到訊息，確認有一筆新訂單後需要做什麼事情
function receiveMsg(message) {
  let msg = message.split('#')[0];
  let orderID = message.split('#')[1];
  let paymentID = message.split('#')[2];
  let storeID = message.split('#')[3];

  let receiveMessage = { msg, orderID, paymentID, storeID };
  let bellValue = document.querySelector("#bellValue");

  if (msg == "有一筆確認的訂單") {
    let receiveInfo = JSON.parse(localStorage.getItem('receiveMsg')) || [];
    receiveInfo.push(receiveMessage);
    localStorage.setItem("receiveMsg", JSON.stringify(receiveInfo));

    // alert(`${msg}：${orderID}, ${paymentID} 來自${storeID}`);
    bellValue.innerText = JSON.parse(localStorage.getItem('receiveMsg')).length;
  } else if (msg == "有一筆取消的訂單") {
    let receiveInfo = JSON.parse(localStorage.getItem('receiveMsg')) || [];
    receiveInfo.push(receiveMessage);
    localStorage.setItem("receiveMsg", JSON.stringify(receiveInfo));

    // alert(`${msg}：${orderID}, ${paymentID} 來自${storeID}`);
    bellValue.innerText = JSON.parse(localStorage.getItem('receiveMsg')).length;
  }
}

document.getElementById("bell").addEventListener("click", () => {
  // if (localStorage.getItem('loginStatus')) {
  if (localStorage.getItem('userinfo')) {
    let bellMsg = JSON.parse(localStorage.getItem('receiveMsg'));

    $("#bellInfo").empty();

    bellMsg.forEach(e => {
      let color = "";
      if (e.msg.includes("確認")) {
        color = "#D4FFD4";
      } else if (e.msg.includes("取消")) {
        color = "#FFD4D4";
      }
      addBellInfo(e.msg, e.orderID, e.paymentID, color);
    });

    let bellDropdown = document.querySelectorAll(".bellDropdown");
    let bellAry = JSON.parse(localStorage.getItem('receiveMsg'));

    for (let i = 0; i < bellDropdown.length; i++) {
      bellDropdown[i].addEventListener("click", () => {
        let bellIndex = bellAry.map(x => x.orderID).indexOf(bellAry[i].orderID);

        if (bellAry.length == 1) {
          bellAry.pop();
        } else {
          bellAry.splice(bellIndex, 1);
        }

        localStorage.setItem('receiveMsg', JSON.stringify(bellAry));
        bellDropdown[i].remove();
        window.location.href = 'orderPage.html';
      });
    }
  }
});

function addBellInfo(msg, orderID, paymentID, color) {
  $("#bellInfo").append(
      `<li>
    <div style="background-color: #FFFFDE;">
      <img src="./img/789.png" alt="logo" width="30" height="30" style="display: inline-block;">
    </div>
    <button class="bellDropdown dropdown-item" type="button" 
      style="background-color: ${color}; font-size: 12px; overflow:hidden;
      white-space: nowrap; text-overflow: ellipsis;">
      ${msg}：<br> ${orderID}, <br> ${paymentID}
    </button>
  </li>`
  );
}
/**------------------------------------------------------------------------------- */

// 自動載入畫面
// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();

var foodName1;
// 點擊類別後切換畫面
function openFood(evt, foodName) {

  foodName1 = foodName;
  scrollToView();

  // Declare all variables
  var i, tabcontent, tablinks;

  // Get all elements with class="tabcontent" and hide them
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  // Get all elements with class="tablinks" and remove the class "active"
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }

  // Show the current tab, and add an "active" class to the link that opened the tab
  document.getElementById(foodName).style.display = "block";
  evt.currentTarget.className += " active";

  // 標題圖片及文字切換
  switch (foodName) {
    case 'HOT':
      // downloadAndRefresh("", true);
      document.getElementById("foodType").innerHTML = '<img src="./img/confetti.png" alt="food" width="50" height="50">   人氣精選';
      break;
    case 'SALAD':
      // downloadAndRefresh(foodName, "");
      document.getElementById("foodType").innerHTML = '<img src="./img/salad.png" alt="food" width="50" height="50">   沙拉';
      break;
    case 'HEALTHMEAL':
      // downloadAndRefresh(foodName, "");
      document.getElementById("foodType").innerHTML = '<img src="./img/bibimbap.png" alt="food" width="50" height="50">   健康餐盒';
      break;
    case 'PASTA':
      // downloadAndRefresh(foodName, "");
      document.getElementById("foodType").innerHTML = '<img src="./img/pasta.png" alt="food" width="50" height="50">   義大利麵';
      break;
    case 'DRINK':
      // downloadAndRefresh(foodName, "");
      document.getElementById("foodType").innerHTML = '<img src="./img/drink.png" alt="food" width="50" height="50">   飲料';
      break;
  }

  loadMeal(foodName);
}


// 點擊類別後自動捲到開頭
function scrollToView() {
  document.getElementById('pos').scrollIntoView({
    block: 'start',
    inline: 'nearest',
    behavior: 'smooth'
  })
}

// 內容從資料庫撈出JSON(舊)
// var foodsList = "";
// $.ajax({
//   url: "https://localhost:3000/home/foods",
//   method: "GET",
//   dataType: "json",
//   success: (res, status) => {
//     // console.log(res);
//     foodsList = res;
//   },
//   error: err => {
//     console.log("XX")
//   }
// })
// $.get("https://localhost:3000/home/foods", function (e) {
//   foodsList = JSON.parse(e);
// })

var foodsList;
// 內容從資料庫撈出JSON(新)
function loadMeal(foodName) {
  foodsList = "";
  // let userId = "iGImodKQRvQU1dYUfPfyM4HBD6r2";
  // let token = "$2a$10$I9C00diMOlpiHLsgv.taueZ2wrJ1Ot8AfgFn05vMzUWld9EFSYn7q";
  // const token = localStorage.getItem('userToken');
  // const userId = JSON.parse(localStorage.getItem('userinfo')).USER_ID;
  if (foodName == "HOT") {
    var cId = "";
    var ishot = "true";
  } else {
    var cId = foodName;
    var ishot = "";
  }
  $.ajax({
    // url: `https://${postName}/api/${userId}/meals?mealcategoryid=${cId}&mealhot=${ishot}&token=${token}`,
    url: `https://${postName}/api/meals?mealcategoryid=${cId}&mealhot=${ishot}`,
    method: "GET",
    dataType: "json",
    success: (res, status) => {
      // console.log(res);
      foodsList = res;
      printMeal();
      // downloadAndRefresh(foodName);
    },
    error: err => {
      console.log("XX")
    }
  })
}

// 更新類別內容
function downloadAndRefresh(foodName) {
  // console.log('in');
  $(`#${foodName}>ul`).empty();
  for (let i = 0; i < foodsList.length; i++) {
    var item = foodsList[i];
    // var vegan;
    // if (item.MEAL_VEGAN) {
    //   vegan = "(素)"
    // } else {
    //   vegan = ""
    // }
    var divHtml =
      `<li class="col-lg-4">
            <div class="foodItem" onclick="showFood(${i})">
                <img src="${item.MEAL_IMAGE}" alt="food" width="240" height="160" referrerpolicy="no-referrer">
                <h3>${item.MEAL_NAME}</h3>
              <p>熱量:${item.MEAL_CALORIE}大卡<br>碳水化合物:${item.MEAL_CARB}公克<br>蛋白質:${item.MEAL_PROTEIN}公克<br>脂肪:${item.MEAL_FAT}公克<br>$${item.MEAL_PRICE}</p>
            </div>
        </li>`;
    $(divHtml).appendTo(`#${foodName}>ul`);
  }
  // console.log('finish');  
}

// 排序下拉式選單選擇後
function printMeal() {
  var sortType = $("#sort").prop("value");
  foodsList.sort((a, b) => {
    switch (sortType) {
      case "priceLToH":
        return a.MEAL_PRICE - b.MEAL_PRICE;
      case "priceHToL":
        return b.MEAL_PRICE - a.MEAL_PRICE;
      case "CALORIE":
        return a.MEAL_CALORIE - b.MEAL_CALORIE;
      case "water":
        return b.MEAL_CARB - a.MEAL_CARB;
      case "egg":
        return b.MEAL_PROTEIN - a.MEAL_PROTEIN;
      case "fat":
        return a.MEAL_FAT - b.MEAL_FAT;
      default:
        break;
    }
  });
  downloadAndRefresh(foodName1);
}

// 對話框
// Get the modal
var modal = document.getElementById("myModal");
var modal1 = document.getElementById("cart");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function () {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
  if (event.target == modal1) {
    modal1.style.display = "none";
  }
}

// 跳出對話框
var price;
var total;
function showFood(i) {
  $(".modal-body1").empty();
  price = parseInt(foodsList[i].MEAL_PRICE);
  total = price;
  $("#total").text(total);
  // var vegan;
  // if (foodsList[i].MEAL_VEGAN) {
  //   vegan = "(素)"
  // } else {
  //   vegan = ""
  // }
  var divHtml =
    `<div class="row">
        <div class="col-10">
            <h1>${foodsList[i].MEAL_NAME}</h1>
        </div>
        <div class="col-2 d-flex flex-row-reverse">
            <p>$${foodsList[i].MEAL_PRICE}</p>
        </div>
      </div>
      <p>${foodsList[i].MEAL_DESC}</P>
      <hr>
      <p>熱量:${foodsList[i].MEAL_CALORIE}大卡<br>碳水化合物:${foodsList[i].MEAL_CARB}公克<br>蛋白質:${foodsList[i].MEAL_PROTEIN}公克<br>脂肪:${foodsList[i].MEAL_FAT}公克</p>`;
  $(divHtml).appendTo(".modal-body1");
  $(".modal-header1").css("background-image", `url('${foodsList[i].MEAL_IMAGE}')`);
  $(".cartButton").attr("onclick", `addCart(${i});`);
  $("#count").text(1);
  modal.style.display = "block";
}

// 對話框內加減按鈕
function plusButton() {
  var textval = parseInt($("#count").text()) + 1;
  total = textval * price;
  $("#count").text(textval);
  $("#total").text(total);
}

function minusButton() {
  var textval = parseInt($("#count").text()) - 1;
  total = textval * price;
  if (textval > 0) {
    $("#count").text(textval);
    $("#total").text(total);
  }
}

// 加入購物車
// function addCart(id) {
//   var item = foodsList[id];
//   item["count"] = parseInt($("#count").text());
//   item["total"] = parseInt($("#total").text());
//   var cartArray = localStorage.item ? JSON.parse(localStorage.getItem("item")) : [];
//   Array.from(cartArray);
//   cartArray.push(item);
//   localStorage.setItem("item", JSON.stringify(cartArray));
//   var allTotal = localStorage.allTotal ? parseInt(localStorage.getItem("allTotal")) : 0;
//   allTotal += parseInt($("#total").text());
//   localStorage.setItem("allTotal", allTotal);
//   modal.style.display = "none";
//   $("#cart").css("display", "block");
//   cartView();
// }
// // 加入購物車
function addCart(id) {
  var item = foodsList[id];
  var cartArray = localStorage.item ? JSON.parse(localStorage.getItem("item")) : [];
  Array.from(cartArray);
  var isInside = false;
  for (i = 0; i < cartArray.length; i++) {
    if (cartArray[i].ID == item.ID) {
      cartArray[i].count += parseInt($("#count").text());
      cartArray[i].total = cartArray[i].count * cartArray[i].MEAL_PRICE;
      localStorage.setItem("item", JSON.stringify(cartArray));

      var allTotal = parseInt(localStorage.getItem("allTotal"));
      allTotal += parseInt($("#count").text()) * cartArray[i].MEAL_PRICE;
      localStorage.setItem("allTotal", allTotal);
      isInside = true;
      break;
    }
  }
  if (!isInside) {
    item["count"] = parseInt($("#count").text());
    item["total"] = parseInt($("#total").text());
    cartArray.push(item);
    localStorage.setItem("item", JSON.stringify(cartArray));
    var allTotal = localStorage.allTotal ? parseInt(localStorage.getItem("allTotal")) : 0;
    allTotal += parseInt($("#total").text());
    localStorage.setItem("allTotal", allTotal);
  }
  modal.style.display = "none";
  $("#cart").css("display", "block");
  cartView();
}

// 清空購物車
function cartClear() {
  localStorage.removeItem("item");
  localStorage.removeItem("allTotal");
  $(".total1").text(0);
  cartView();
}

// 畫面顯示
function cartView() {
  var cartArray = localStorage.item ? JSON.parse(localStorage.getItem("item")) : [];
  var allTotal = localStorage.allTotal ? parseInt(localStorage.getItem("allTotal")) : 0;
  $("#cartHeader>ul").empty();
  for (let index = 0; index < cartArray.length; index++) {
    var item = cartArray[index];
    // var vegan;
    // if (item.MEAL_VEGAN) {
    //   vegan = "(素)"
    // } else {
    //   vegan = ""
    // }
    var divHtml =
      `<li class="cartLi">
          <div class="row">
            <div class="col-3">
              <img src="${item.MEAL_IMAGE}" alt="food" width="108" height="72" referrerpolicy="no-referrer">
            </div>
            <div class="col-2"></div>
            <div class="col-7">
              <h5>${item.MEAL_NAME}</h5>
              <p>數量:${item.count}<br>$${item.MEAL_PRICE}</p>
            </div>
          </div>
          <div class="row">
            <div class="col-2">
              <button type="button" class="btn btn-danger btn1" onclick="deleteButton1(${index})"><img src="./img/bin.png" alt="" width="15" height="15"></button>
            </div>
            <div class="col-6" id="wrap">
              <div class="circle2" onclick="minusButton1(${index})">–</div>
              <span class="spanType2">${item.count}</span>
              <div class="circle2" onclick="plusButton1(${index})">+</div>
            </div>
            <div class="col-4">
              <p class="text-end">小計:$${item.total}</p>
            </div>
          </div>
        </li><br>`;
    $(divHtml).appendTo("#cartHeader>ul");
    $(".total1").text(allTotal);
  }
}

// 導覽列購物車圖示點擊事件
function bagClick() {
  var myCart = document.getElementById("cart");
  var computedStyle = document.defaultView.getComputedStyle(myCart, null);
  if (computedStyle.display == "block") {
    myCart.style.display = "none";
  } else {
    cartView();
    myCart.style.display = "block";
  }
}

// 購物車內加減按鈕
function plusButton1(i) {
  var cartArray = JSON.parse(localStorage.getItem("item"));
  Array.from(cartArray);
  cartArray[i].count++;
  cartArray[i].total = cartArray[i].count * cartArray[i].MEAL_PRICE;
  localStorage.setItem("item", JSON.stringify(cartArray));

  var allTotal = parseInt(localStorage.getItem("allTotal"));
  allTotal += cartArray[i].MEAL_PRICE;
  localStorage.setItem("allTotal", allTotal);
  cartView();
}

function minusButton1(i) {
  var cartArray = JSON.parse(localStorage.getItem("item"));
  Array.from(cartArray);
  if (cartArray[i].count > 1) {
    cartArray[i].count--;
    cartArray[i].total = cartArray[i].count * cartArray[i].MEAL_PRICE;
    localStorage.setItem("item", JSON.stringify(cartArray));

    var allTotal = parseInt(localStorage.getItem("allTotal"));
    allTotal -= cartArray[i].MEAL_PRICE;
    localStorage.setItem("allTotal", allTotal);
    cartView();
  }
}

// 購物車內刪除按鈕
function deleteButton1(i) {
  var cartArray = JSON.parse(localStorage.getItem("item"));
  Array.from(cartArray);

  var allTotal = parseInt(localStorage.getItem("allTotal"));
  allTotal -= cartArray[i].total;
  localStorage.setItem("allTotal", allTotal);

  var newCartArray = [];
  for (let index = 0; index < i; index++) {
    newCartArray[index] = cartArray[index];
  }
  for (let index = i + 1; index < cartArray.length; index++) {
    newCartArray[index - 1] = cartArray[index];
  }
  localStorage.setItem("item", JSON.stringify(newCartArray));
  $(".total1").text(allTotal);
  cartView();
}

// 撈出輪播圖資料
var ImageList;
function loadImage() {
  ImageList = "";
  // let userId = "iGImodKQRvQU1dYUfPfyM4HBD6r2";
  // let token = "$2a$10$I9C00diMOlpiHLsgv.taueZ2wrJ1Ot8AfgFn05vMzUWld9EFSYn7q";
  // const token = localStorage.getItem('userToken');
  // const userId = JSON.parse(localStorage.getItem('userinfo')).USER_ID;
  $.ajax({
    // url: `https://${postName}/api/${userId}/messages?token=${token}`,
    url: `https://${postName}/api/messages`,
    method: "GET",
    dataType: "json",
    success: (res, status) => {
      // console.log(res);
      ImageList = res;
      refreshImage();
      // console.log(ImageList);
    },
    error: err => {
      console.log("XX")
    }
  })
}
// loadImage();

// 更新輪播圖內容
function refreshImage() {
  // console.log('in');
  $(".carousel-indicators").empty();

  for (let i = 0; i < ImageList.length; i++) {
    var item = ImageList[i];
    if (i == 0) {
      var divHtml = `
        <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="0" class="active"
          aria-current="true" aria-label="Slide 1"></button>`;
      var divHtml1 = `
        <div class="carousel-item active" style="">
          <img src="${item.MESSAGE_IMAGE}" referrerpolicy="no-referrer"
              class="d-block" alt="..." style="object-fit: cover">
          <div class="carousel-caption d-none d-md-block">
          <!--  <h5>First slide label</h5>
               <p>${item.MESSAGE_DESC}</p> -->
          </div>
        </div>`;
    } else {
      var divHtml = `
        <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="${i}"
          aria-label="Slide ${i + 1}"></button>`;
      var divHtml1 = `
        <div class="carousel-item">
          <img src="${item.MESSAGE_IMAGE}" referrerpolicy="no-referrer" class="d-block"
              alt="..." style="object-fit: cover">
          <div class="carousel-caption d-none d-md-block">
          <!-- <h5>Second slide label</h5>
              <p>${item.MESSAGE_DESC}</p> -->
          </div>
        </div>`;
    }
    $(divHtml).appendTo(".carousel-indicators");
    $(divHtml1).appendTo(".carousel-inner");
  }
  // console.log('finish');
}
// refreshImage();
$(document).ready(loadImage());


document.getElementById("checkoutBtn").addEventListener("click", () => {
    fetch(`https://${postName}/api/storestatus`,
      {
        method: "GET"
      }).then(function (response) {
        return response.text();
      })
      .then(function (storeOpenStatus) {
        if (storeOpenStatus === 'true') {
          // if (!localStorage.getItem('loginStatus')) {
          if (!localStorage.getItem('userinfo')) {
            Swal.fire({
              icon: 'warning',
              title: "請先登入後再結帳",
              showConfirmButton: false,
              timer: 1500
            });
          } else {
            window.location.href = 'checkoutPage.html';
          }
        } else {
          Swal.fire({
            icon: 'warning',
            title: "現在還不是營業時間哦！",
            showConfirmButton: false,
            timer: 1500
          });
        }
      }).catch((err) => {
        console.log('取得店家資訊錯誤');
      });
});