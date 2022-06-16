// let postName = 'localhost:8080';
let postName = 'eeit42-order-hippo.herokuapp.com';
/**------------------------------------------------------------------------------- */
// WebSocket
let websocket = null;

connWebSocket(JSON.parse(localStorage.getItem('userinfo')).USER_ID);

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
  closeWebSocket();
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

//結帳頁面   表格生成
function read() {
  var cartArray = localStorage.item ? JSON.parse(localStorage.getItem("item")) : [];
  for (let index = 0; index < cartArray.length; index++) {
    var meal = cartArray[index];
    $('#foodCa').append(
      //取資料  圖片拿ID
      "<div class='mealitem row d-flex align-items-center text-center text-dark'>" +
      "<div class='col-3 img-box '>" +
      `<img src=${meal.MEAL_IMAGE}>` +
      `<p class="mealname">${meal.MEAL_NAME}</p>` +
      `</div>` +
      "<div class='col-2 align-items-center '>" +
      `<p>碳水:${Math.floor(meal.MEAL_CARB * 100) / 100}g</p>` +
      `<p>蛋白:${Math.floor(meal.MEAL_PROTEIN * 100) / 100}g</p>` +
      `<p>脂肪:${Math.floor(meal.MEAL_FAT * 100) / 100}g</p>` +
      `<p>${  ((Math.floor(meal.MEAL_CARB * 100) / 100 * 4) + 
              (Math.floor(meal.MEAL_FAT * 100) / 100 * 9) + 
              (Math.floor(meal.MEAL_PROTEIN * 100) / 100 * 4)).toFixed(2) }大卡</p>` +
      `</div>` +
      `<div class="col-2 align-items-center">` +
      `$` +
      `<span class="unitPrice">${meal.MEAL_PRICE}</span>` +
      `</div>` +
      "<div class='col-2 d-flex justify-content-start align-items-center '>" +
      `<input class="checkoutReduce" id="checkoutReduce" type="button" value="-" >` +
      `<input class="checkoutText text-center" id="checkoutText" type="text" value=${meal.count} disabled="disabled" style="width: 40px; background-color: white;">` +
      `<input class="checkoutAdd" id="checkoutAdd" type="button" value="+">` +
      `</div>` +
      `<div class="col-1 align-self-center">` +
      `$` +
      `<span class="total">${meal.MEAL_PRICE * meal.count}</span>` +
      `</div>` +
      "<div class='col-2 align-self-center'>" +
      '<button type="button" class="del btn-close" aria-label="Close"></button>' +
      "</div>" +
      "<hr>"
    );

    $('#priceCa').append(
      "<div class='orderDetail d-flex justify-content-between mb-3'>" +
      `<span>${meal.MEAL_NAME}</span>` +
      `<span class="newtotal">NT$ ${meal.MEAL_PRICE * meal.count}</span>` +
      "</div>"
    )

  }

}
read();

// '<button type="button" class="del btn-outline-info">X</button>' +
// 結帳頁
// 商品數量增減
var count = document.querySelectorAll(".checkoutText");
var inc = document.querySelectorAll(".checkoutAdd");
var dec = document.querySelectorAll(".checkoutReduce");
var total = document.querySelectorAll(".total");
var price = document.querySelectorAll(".unitPrice");
let newtotal = document.querySelectorAll(".newtotal");
let shppingCar = JSON.parse(localStorage.getItem("item"));
let del = document.querySelectorAll(".del");
let mealitem = document.querySelectorAll(".mealitem");
let orderDetail = document.querySelectorAll(".orderDetail");
//訂單摘要 總計
let allTotal = document.querySelector(".allTotal");
let mealName = document.querySelectorAll(".mealname");
//+、- 按鈕的事件
function addBtnEvent(i) {

  inc[i].onclick = function () {
    let index = shppingCar.map(x => x.MEAL_NAME).indexOf(mealName[i].innerHTML);
    count[i].value = parseInt(count[i].value) + 1;
    //total變動
    total[i].innerHTML = parseInt(price[i].innerHTML) * parseInt(count[i].value);
    //訂單摘要 金額變動
    newtotal[i].innerHTML = "NT$ ".concat(total[i].innerHTML);
    //存入localStorage
    shppingCar[index].count = count[i].value;
    shppingCar[index].total = shppingCar[index].count * shppingCar[index].MEAL_PRICE;
    localStorage.setItem("item", JSON.stringify(shppingCar));
    //訂單摘要 總計
    // localStorage.setItem("allTotal", parseInt(localStorage.getItem("allTotal")) + parseInt(shppingCar[index].price));
    let carTotal = shppingCar.map(car => car.total).reduce((a, b) => a + b);
    localStorage.setItem("allTotal", parseInt(carTotal));
    allTotal.innerHTML = "NT$ ".concat(localStorage.getItem("allTotal"));
    $("#disc").remove();
    $("#getDiscount")[0].value = null;
    $(".discountDisplay").before(
      '<p id="disc">現在沒有使用到優惠券喔!</p>'
    )
    $(".discount").attr("disabled", false);
  };

  dec[i].onclick = function () {
    let index = shppingCar.map(x => x.MEAL_NAME).indexOf(mealName[i].innerHTML);
    if (count[i].value > 1) {
      count[i].value = parseInt(count[i].value) - 1;
      //total變動
      total[i].innerHTML = parseInt(price[i].innerHTML) * parseInt(count[i].value);
      //訂單摘要 金額變動     
      newtotal[i].innerHTML = "NT$ ".concat(total[i].innerHTML);
      //存入localStorage
      shppingCar[index].count = count[i].value;
      shppingCar[index].total = shppingCar[index].count * shppingCar[index].MEAL_PRICE;
      localStorage.setItem("item", JSON.stringify(shppingCar));
      //訂單摘要 總計
      let carTotal = shppingCar.map(car => car.total).reduce((a, b) => a + b);
      // localStorage.setItem("allTotal", parseInt(localStorage.getItem("allTotal")) - parseInt(shppingCar[index].price));
      localStorage.setItem("allTotal", parseInt(carTotal));
      allTotal.innerHTML = "NT$ ".concat(localStorage.getItem("allTotal"));
      $("#disc").remove();
      $("#getDiscount")[0].value = null;
      $(".discountDisplay").before(
        '<p id="disc">現在沒有使用到優惠券喔!</p>'
      )
      $(".discount").attr("disabled", false);
    }
  }

  del[i].onclick = function () {
    //彈出視窗
    Swal.fire({
      title: `確定要刪除"${mealName[i].innerHTML}"嗎?`,
      text: "刪除後，按繼續購物回到主頁可以加回商品!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '確定',
      cancelButtonText: "取消"
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire(
          '完成!',
          `您已刪除"${mealName[i].innerHTML}"`,
          'success'
        )
        let index = shppingCar.map(x => x.MEAL_NAME).indexOf(mealName[i].innerHTML);
        let carTotal = 0;
        if (shppingCar.length == 1) {
          shppingCar.pop();
        } else {
          shppingCar.splice(index, 1);
          carTotal = shppingCar.map(car => car.total).reduce((a, b) => a + b);
        }

        localStorage.setItem("item", JSON.stringify(shppingCar));
        mealitem[i].remove();
        orderDetail[i].remove();


        localStorage.setItem("allTotal", parseInt(carTotal));
        allTotal.innerHTML = "NT$ ".concat(localStorage.getItem("allTotal"));
        $("#disc").remove();
        $("#getDiscount")[0].value = null;
        $(".discountDisplay").before(
          '<p id="disc">現在沒有使用到優惠券喔!</p>'
        )
        $(".discount").attr("disabled", false);
      }
    })
  }

}

for (let i = 0; i < inc.length; i++) {
  addBtnEvent(i);
}

//使用優惠
$(".discount").on("click", () => {
  let getDiscount = $("#getDiscount")[0].value;
  let userId = JSON.parse(localStorage.getItem('userinfo')).USER_ID;
  let token = localStorage.getItem('userToken');
  if (getDiscount != "") {
    fetch(`https://${postName}/api/${userId}/coupons?couponname=${getDiscount}&token=${token}`,
      {
        method: "GET"
      }).then(function (response) {
        return response.json();
      })
      .then(function (myJson) {
        console.log(getDiscount);
        allTotal.innerHTML = "NT$ ".concat(Math.round(parseInt(localStorage.getItem("allTotal")) * parseFloat(myJson[0].COUPON_DESC)));
        localStorage.setItem("allTotal", allTotal.innerHTML.split('$')[1]);
        $("#disc").remove();
        $(".discountDisplay").before(
          `<p id="disc">打${parseFloat(myJson[0].COUPON_DESC) * 10}折</p>`
        );
        $(".discount").attr("disabled", true);
      }).catch((err) => {
        alert("沒有這個優惠碼,請重新輸入");
      });
  }
})



//總價
function totalDisplay() {
  let ttotal = 0;
  for (let i = 0; i < shppingCar.length; i++) {
    ttotal += parseInt(shppingCar[i].total);
  }
  allTotal.innerHTML = "NT$ ".concat(ttotal);
  localStorage.setItem("allTotal", ttotal);
}
totalDisplay();

//按下結帳
$(".orderFinish").on("click", () => {
  let userId = JSON.parse(localStorage.getItem('userinfo')).USER_ID;
  let token = localStorage.getItem('userToken');
  checkOutFinal(userId, token);

  // if (!localStorage.getItem('loginStatus')) {
  //   Swal.fire({
  //     icon: 'warning',
  //     title: "請先登入後再結帳",
  //     showConfirmButton: false,
  //     timer: 1500
  //   });
  //   setInterval(() => {
  //     window.location.href = "login.html";
  //   }, 1500); // 等待2秒導向回到登入頁面
  // } else {
  //   let userId = JSON.parse(localStorage.getItem('userinfo')).USER_ID;
  //   let token = localStorage.getItem('userToken');
  //   if (getDiscount != "") {
  //     fetch(`https://${postName}/api/${userId}/stores?token=${token}`,
  //       {
  //         method: "GET"
  //       }).then(function (response) {
  //         return response.json();
  //       })
  //       .then(function (storeinfo) {
  //         if (storeinfo[0].STORE_OPEN_STATUS === true) {
  //           checkOutFinal(userId, token);
  //         } else {
  //           Swal.fire({
  //             icon: 'warning',
  //             title: "現在還不是營業時間哦！",
  //             showConfirmButton: false,
  //             timer: 1500
  //           });
  //         }
  //       }).catch((err) => {
  //         console.log('取得店家資訊錯誤');
  //       });
  //   }
  // }
})

function checkOutFinal(userId, token) {
  let shoppingCar = JSON.parse(localStorage.getItem("item"));
  if (shoppingCar.length > 0) {
    //送出 訂單
    let ORDERS = {
      "STORE_ID": "l3rH7uT47PTrQSteWO2V9XqbpRn1",
      // "USER_ID": "iGImodKQRvQU1dYUfPfyM4HBD6r2",
      "USER_ID": JSON.parse(localStorage.getItem('userinfo')).USER_ID,
      "ORDERS_PRICE": localStorage.getItem("allTotal"),
      "ORDER_STATUS": "1",
      "ORDER_DESC": document.getElementById("order-remarks").value
    };
    localStorage.setItem("ORDERS", JSON.stringify(ORDERS));

    //訂單餐點資訊
    let meal = "";

    let orderDetailList = [];
    let orderDetail = {};

    for (let index = 0; index < shoppingCar.length; index++) {
      orderDetail = {
        "ORDER_ID": "",
        "MEAL_ID": shoppingCar[index].MEAL_ID,
        "ORDER_MEAL_QTY": shoppingCar[index].count,
        "MEAL_PRICE": shoppingCar[index].total
      };
      orderDetailList.push(orderDetail);
      if (index == 0) {
        meal = shoppingCar[index].MEAL_NAME.concat(" * ").concat(shoppingCar[index].count);
      } else {
        // meal = meal.concat(" ,").concat(shoppingCar[index].name);
        meal = meal.concat(`, ${shoppingCar[index].MEAL_NAME} * ${shoppingCar[index].count}`);
      }
    }
    let MEALS = {
      "orderid": "",
      "mealprice": localStorage.getItem("allTotal"),
      "meals": meal
    };
    localStorage.setItem("orderDetail", JSON.stringify(orderDetailList));

    //給 付款頁面
    let PAYMENT = {
      "PAYMENT_ID": "",
      "ORDER_ID": "",
      // "USER_ID": "iGImodKQRvQU1dYUfPfyM4HBD6r2",
      "USER_ID": JSON.parse(localStorage.getItem('userinfo')).USER_ID,
      "STORE_ID": "l3rH7uT47PTrQSteWO2V9XqbpRn1",
      "PAYMENT_PRICE": localStorage.getItem("allTotal"),
      "PAYMENT_CATEGORY": $("[name='flexRadioDefault']:checked").val(),
      "PAYMENT_STATUS": "true"
    };
    //判斷 現金2 OR 信用卡1
    if ($("[name='flexRadioDefault']:checked").val() === "2") {
      //現金跳感謝
      delete PAYMENT.PAYMENT_ID;
      localStorage.setItem("PAYMENT", JSON.stringify(PAYMENT));
      window.location.href = "thanks.html";
    } else {
      fetch(`https://${postName}/api/${userId}/stripepayment?token=${token}`, {
        method: "post",
        headers: { "content-Type": "application/json" },
        body: JSON.stringify(MEALS)
      }).then(function (response) {
        return response.json();
      }).then(res => {
        PAYMENT.PAYMENT_ID = res.payment_intent;
        localStorage.setItem("PAYMENT", JSON.stringify(PAYMENT));
        //信用卡跳付款頁面
        window.location.href = res.url;
      });

    }
  } else {
    Swal.fire({
      icon: 'warning',
      title: `請先將商品加入購物車，再結帳唷`,
      showConfirmButton: false,
      timer: 1500
    })
  }
}
