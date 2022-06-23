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
    console.log("WebSocket斷線");
    websocket.close();
  }

window.onbeforeunload = () => {
    if (websocket) {
        closeWebSocket();
    }
}

function sednMessage(sendMsg, orderID, paymentID) {
    // const token = localStorage.getItem("token");
    // const userID = localStorage.getItem('userinfo').USER_ID;
    // const token = "$2a$10$I9C00diMOlpiHLsgv.taueZ2wrJ1Ot8AfgFn05vMzUWld9EFSYn7q";
    // const userID = "iGImodKQRvQU1dYUfPfyM4HBD6r2";
    const token = localStorage.getItem('userToken');
    const userID = JSON.parse(localStorage.getItem('userinfo')).USER_ID;
    fetch(`https://${postName}/api/${userID}/stores?token=${token}`, {
        method: "GET"
    }).then(res => {
        return res.json();
    }).then(result => {
        const sendToWho = result[0].STORE_ID;
        if (orderID != null && orderID != "" && paymentID != null && paymentID != ""
            && sendToWho != null && sendToWho != "") {
            sendMsg = `${sendMsg}#${orderID}#${paymentID}#${sendToWho}`;
            console.log(sendMsg);
            websocket.send(sendMsg);
        }
    });
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
        ${msg}
      </button>
    </li>`
    );
    // ${msg}
}
/**------------------------------------------------------------------------------- */
// let userID = 'iGImodKQRvQU1dYUfPfyM4HBD6r2';
let userID = JSON.parse(localStorage.getItem('userinfo')).USER_ID;
// let requestID = 'iGImodKQRvQU1dYUfPfyM4HBD6r2';
let requestID = JSON.parse(localStorage.getItem('userinfo')).USER_ID;
// let token = '$2a$10$I9C00diMOlpiHLsgv.taueZ2wrJ1Ot8AfgFn05vMzUWld9EFSYn7q';
let token = localStorage.getItem('userToken');

let order = JSON.parse(localStorage.getItem("ORDERS"));
let orderDetail = JSON.parse(localStorage.getItem("orderDetail"));
let payment = JSON.parse(localStorage.getItem("PAYMENT"));

if (order != null && orderDetail != null && payment != null) {
    insertData();
} else {
    window.location.href = "./index.html";
}
// redirect to homePage.html
var count = 10;
function countDown() {
    if (count >= 0) {
        document.getElementById("timer").innerHTML = count--;
        setTimeout("countDown()", 1000);
    } else {
        window.location.href = "index.html";
    }
}

// Api Integration
function thankYouDetails(orderid) {
    fetch(`https://${postName}/api/${requestID}/vafterpaymentpage?token=${token}&orderid=${orderid}`, {
        method: "GET"
    }).then(Res => {
        return Res.json();
    }).then(Result => {
        $('#orderid').append(Result[0].orderid);
        $('#storename').append(Result[0].storename);
        $('#paymentprice').append(Result[0].paymentprice);
        $('#paymentcategory').append(Result[0].paymentcategory);
        $('#totalprice').append("NT$ " + Result[0].paymentprice);
    })
}


function insertData() {
    fetch(`https://${postName}/api/${requestID}/order?token=${token}`, {
        method: "POST",
        headers: { "content-Type": "application/json" },
        body: JSON.stringify(order)
    }).then(function (response) {
        fetch(`https://${postName}/api/${requestID}/orders?token=${token}&userid=${userID}`, {
            method: "GET",
        }).then(function (res) {
            return res.json();
        }).then(function (result) {
            orderDetail.forEach(e => {
                e.ORDER_ID = result[result.length - 1].ORDER_ID;
            });
            payment.ORDER_ID = result[result.length - 1].ORDER_ID;
            localStorage.setItem("ORDERS", JSON.stringify(result[result.length - 1]));
            fetch(`https://${postName}/api/${requestID}/ordermealdetail?token=${token}`, {
                method: "POST",
                headers: { "content-Type": "application/json" },
                body: JSON.stringify(orderDetail)
            }).then(function (res) {
                return res.json();
            }).then(function (result) {
                if (result == 1) {
                    fetch(`https://${postName}/api/${requestID}/payment?token=${token}`, {
                        method: "POST",
                        headers: { "content-Type": "application/json" },
                        body: JSON.stringify(payment)
                    }).then(function (res) {
                        return res.json();
                    }).then(function (result) {
                        let orderID = JSON.parse(localStorage.getItem('ORDERS')).ORDER_ID;
                        let paymentID = JSON.parse(localStorage.getItem('PAYMENT')).PAYMENT_ID;

                        if (paymentID == null) {
                            let orderid = JSON.parse(localStorage.getItem('ORDERS')).ORDER_ID;
                            fetch(`https://${postName}/api/${requestID}/payments?token=${token}&orderid=${orderid}`, {
                                method: "GET",
                            }).then(function (res) {
                                return res.json();
                            }).then(function (result) {
                                // localStorage.setItem('PAYMENT', JSON.stringify(result[0]));
                                sednMessage("有一筆新訂單", orderID, result[0].PAYMENT_ID);
                            });
                        }
                        sednMessage("有一筆新訂單", orderID, paymentID);
                        thankYouDetails(JSON.parse(localStorage.getItem("ORDERS")).ORDER_ID);

                        let carUserID = JSON.parse(localStorage.getItem('userinfo')).USER_ID;
                        let shoppingCarList = JSON.parse(localStorage.getItem('shoppingCarList')) || [];
                        let userCarIndex = shoppingCarList.findIndex(carList => carList.USER_ID == carUserID);
                        if (userCarIndex > -1) {
                            shoppingCarList.splice(userCarIndex, 1);
                            localStorage.setItem("shoppingCarList", JSON.stringify(shoppingCarList));
                        }

                        localStorage.removeItem("item");
                        localStorage.removeItem("ORDERS");
                        localStorage.removeItem("orderDetail");
                        localStorage.removeItem("PAYMENT");
                        localStorage.removeItem("allTotal");
                        countDown();
                    })
                }
            })

        })
    });
}
