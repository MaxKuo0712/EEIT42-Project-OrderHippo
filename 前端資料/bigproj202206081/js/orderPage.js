import {
  getAuth,
  signOut
} from "https://www.gstatic.com/firebasejs/9.6.11/firebase-auth.js";

import { myFirebase } from "./firebase.js";

// // Initialize Firebase
const myLoginFirebase = new myFirebase();
myLoginFirebase.initFirebase();

checkSignBntStatus();

let loginBnt = document.getElementById("loginBnt");

setSignBntStatus();

function userSignOut() {
  const auth = getAuth();
  signOut(auth).then(() => {
    signOutOK();
  }).catch((error) => {
    console.error(error);
  });
}

function setSignBntStatus() {
  const auth = getAuth();
  const loginBnt = document.getElementById("loginBnt");
  auth.onAuthStateChanged((user) => {
    if (user) {
      loginBnt.innerText = "登出";
    } else {
      loginBnt.innerText = "登入";
    }
  });
}

function signOutOK() {
  Swal.fire({
    icon: 'success',
    title: "登出成功，謝謝",
    showConfirmButton: false,
    timer: 1500
  });
}

function checkSignBntStatus() {
  const auth = getAuth();
  auth.onAuthStateChanged((user) => {
    if (!user) {
      Swal.fire({
        icon: 'warning',
        title: "請先登入哦！",
        showConfirmButton: false,
        timer: 1500
      });
      setInterval(() => {
        window.location.href = "index.html";
      }, 1500); // 等待2秒導向回到登入頁面
    } else {
      orderDetail(1);
    }
  });
}

function setShoppingCarList() {
  let shoppingCar = {
      USER_ID: JSON.parse(localStorage.getItem('userinfo')).USER_ID,
      shoppingCar: JSON.parse(localStorage.getItem('item'))
  };
  let shoppingCarList = JSON.parse(localStorage.getItem('shoppingCarList')) || [];
  let userCarIndex = shoppingCarList.findIndex(carList => carList.USER_ID == shoppingCar.USER_ID);
  if (userCarIndex > -1) {
      shoppingCarList.splice(userCarIndex, 1);
      localStorage.setItem("shoppingCarList", JSON.stringify(shoppingCarList));
  }
  shoppingCarList.push(shoppingCar);
  localStorage.setItem("shoppingCarList", JSON.stringify(shoppingCarList));
}

loginBnt.addEventListener("click", (e) => {
  let signText = loginBnt.innerText;
  if (signText === "登入") {
    window.location.href = "login.html";
  } else if (signText === "登出") {
    setShoppingCarList();
    userSignOut();
    // localStorage.removeItem('loginStatus');
    setInterval(() => {
      localStorage.removeItem('allTotal');
      localStorage.removeItem('item');
      localStorage.removeItem("userinfo");
      localStorage.removeItem("userToken");
      window.location.reload();
    }, 1500);
  }
});

/**------------------------------------------------------------------------------- */
// WebSocket
let websocket = null;

if (JSON.parse(localStorage.getItem('userinfo'))) {
  connWebSocket(JSON.parse(localStorage.getItem('userinfo')).USER_ID);
}

function connWebSocket(userInfo) {
  if ('WebSocket' in window) {
    websocket = new WebSocket(`ws://localhost:8080/websocket/${userInfo}`);
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
  console.log(color);
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
//USER ID iGImodKQRvQU1dYUfPfyM4HBD6r2
//$2a$10$I9C00diMOlpiHLsgv.taueZ2wrJ1Ot8AfgFn05vMzUWld9EFSYn7q TOOKEN

//http://localhost:8080/api/l3rH7uT47PTrQSteWO2V9XqbpRn1/ordermealdetail?token=$2a$10$kz537bPMcWJrY40CIkayQO0cfKA0wBPMCIaFiT7HGgpfGS0dnB.ue

//預設載入
function load(index) {
  for (var order of index) {
    $('#orderCa').append(
      '<div class="row d-flex align-content-around flex-wrap text-center">' +
      `<div class="col-2">${limitWords(order.orderid)}</div>` +
      '<div class="col-2 ">' +
      `<p>${order.mealorderqty}</p>` +
      '</div>' +
      '<div class="col-2">' +
      `<span>${order.ordersprice}</span>` +
      '</div>' +
      '<div class="col-3">' +
      `<span>${formatDate(order.createtime)}</span>` +
      '</div>' +
      '<div class="col-3">' +
      `<span>${order.orderstatusname}</span>` +
      '</div>' +
      '</div>' +
      '<hr />'
    );
  }
}
//未確認訂單
$('#showUncon').on('click', function () {
  $('#orderCa').empty();
  orderDetail(1);
});
//已確認訂單
$('#showCon').on('click', function () {
  $('#orderCa').empty();
  orderDetail(2);
});
//完成訂單
$('#showFinish').on('click', function () {
  $('#orderCa').empty();
  orderDetail(3);
});
//取消訂單
$('#showCancel').on('click', function () {
  $('#orderCa').empty();
  orderDetail(4);
});
//GET API
function orderDetail(orderStatus) {
  let userId = JSON.parse(localStorage.getItem('userinfo')).USER_ID;
  let token = localStorage.getItem('userToken');
  fetch(`http://localhost:8080/api/${userId}/vorderdisplay?token=${token}&orderstatus=${orderStatus}&userid=${userId}`,
    {
      method: "GET"
    }).then(function (response) {
        return response.json();
    }).then(function (myJson) {
      load(myJson);
    }).catch((error) => {
      console.log(error);
    });
}
//日期顯示
function formatDate(newDate) {
  let date = new Date(newDate);
  return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
}
//訂單編號太長縮寫
function limitWords(txt) {
  var str = txt;
  str = str.substr(10, 5) + '...';
  return str;
}

//餐點內容太長
function mealDe(st) {
  let k = st.split(", ");

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


//感謝頁 車子
function bagClick() {
  var myCart = document.getElementById("cart");
  console.log(myCart);
  var computedStyle = document.defaultView.getComputedStyle(myCart, null);
  if (computedStyle.display == "block") {
    myCart.style.display = "none";
  } else {
    cartView();
    myCart.style.display = "block";
  }
}

function cartView() {
  var cartArray = localStorage.item ? JSON.parse(localStorage.getItem("item")) : [];
  var allTotal = localStorage.allTotal ? parseInt(localStorage.getItem("allTotal")) : 0;
  $("#cartHeader>ul").empty();
  for (let index = 0; index < cartArray.length; index++) {
    var item = cartArray[index];
    var vegan;
    if (item.MEAL_VEGAN) {
      vegan = "(素)"
    } else {
      vegan = ""
    }
    var divHtml =
      `<li class="cartLi">
            <div class="row">
              <div class="col-3">
                <img src="${item.MEAL_IMAGE}" alt="food" width="108" height="72" referrerpolicy="no-referrer">
              </div>
              <div class="col-2"></div>
              <div class="col-7">
                <h5>${item.MEAL_NAME}${vegan}</h5>
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



// 清空購物車
function cartClear() {
  localStorage.removeItem("item");
  localStorage.removeItem("allTotal");
  $(".total1").text(0);
  cartView();
}