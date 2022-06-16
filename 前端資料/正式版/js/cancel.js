// let postName = 'localhost:8080';
let postName = 'eeit42-order-hippo.herokuapp.com';

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
// redirect to homePage.html
var count = 10;
var redirect = "index.html";
function countDown() {
  if (count >= 0) {
    document.getElementById("timer").innerHTML = count--;
    setTimeout("countDown()", 1000);
  } else {
    window.location.href = redirect;
  }
}
countDown();