// let postName = 'localhost:8080';
let postName = 'eeit42-order-hippo.herokuapp.com';

//onload
// 拿取Token跟id
// console.log(localStorage.getItem('storeToken'));
// console.log(JSON.parse(localStorage.getItem('storeinfo')).STORE_ID);
let newToken = localStorage.getItem('storeToken');
localStorage.getItem('storeToken') == null ? function () { Swal.fire('Any fool can use a computer'); window.location.href = "login.html" }() : newToken = localStorage.getItem('storeToken');
let storeId = JSON.parse(localStorage.getItem('storeinfo')).STORE_ID;
let localhost = 8080;

toastr.options = {
    "closeButton": true,
    "debug": false,
    "newestOnTop": false,
    "progressBar": true,
    "positionClass": "toast-top-center",
    "preventDuplicates": false,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "2000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}


/**------------------------------------------------------------------------------- */
// WebSocket
let websocket = null;

connWebSocket(JSON.parse(localStorage.getItem('storeinfo')).STORE_ID);

function connWebSocket(storeInfo) {
    if ('WebSocket' in window) {
        websocket = new WebSocket(`wss://${postName}/websocket/${storeInfo}`);
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

if (localStorage.getItem('storeinfo')) {
    websocket.onmessage = (e) => {
        // console.log(e.data);
        receiveMsg(e.data);
    }
    if (!JSON.parse(localStorage.getItem('receiveMsg')) || JSON.parse(localStorage.getItem('receiveMsg')).length == 0) {
        bellValue.innerText = 0;
        document.querySelector('#btnGroupDrop1 i').style.color = '#373a3c';
        document.querySelector('#bellValue').classList.remove('bg-danger');
        document.querySelector('#bellValue').classList.add('bg-secondary');
    } else {
        bellValue.innerText = JSON.parse(localStorage.getItem('receiveMsg')).length;
        document.querySelector('#btnGroupDrop1 i').style.color = '#F00000';
        document.querySelector('#bellValue').classList.remove('bg-secondary');
        document.querySelector('#bellValue').classList.add('bg-danger');
    }
}

function sednMessage(sendMsg, orderID) {
    fetch(`https://${postName}/api/${storeId}/payments?token=${newToken}&orderid=${orderID}`,
        {
            method: "GET"
        }).then(function (response) {
            return response.json();
        })
        .then(function (paymentinfo) {
            if (paymentinfo[0] && paymentinfo[0].ORDER_ID == orderID) {
                let sendToWho = paymentinfo[0].USER_ID;
                let paymentID = paymentinfo[0].PAYMENT_ID;

                if (orderID != null && orderID != "" && paymentID != null && paymentID != ""
                    && sendToWho != null && sendToWho != "") {
                    sendMsg = `${sendMsg}#${orderID}#${paymentID}#${sendToWho}`;
                    websocket.send(sendMsg);
                }
            } else {
                console.log('取得付款資訊錯誤');
            }
        }).catch((err) => {
            console.log('取得付款資訊錯誤');
        });
}

// 收到訊息，確認有一筆新訂單後需要做什麼事情
function receiveMsg(message) {
    let msg = message.split('#')[0];
    let orderID = message.split('#')[1];
    let paymentID = message.split('#')[2];
    let userID = message.split('#')[3];

    let receiveMessage = { msg, orderID, paymentID, userID };
    let bellValue = document.querySelector("#bellValue");

    if (msg == "有一筆新訂單") {
        let receiveInfo = JSON.parse(localStorage.getItem('receiveMsg')) || [];
        receiveInfo.push(receiveMessage);
        localStorage.setItem("receiveMsg", JSON.stringify(receiveInfo));
        bellValue.innerText = JSON.parse(localStorage.getItem('receiveMsg')).length;
        homepageOrderDisplay();
        document.querySelector('#btnGroupDrop1 i').style.color = '#F00000';
        document.querySelector('#bellValue').classList.remove('bg-secondary');
        document.querySelector('#bellValue').classList.add('bg-danger');

        $('#ordercomranka').empty();

        $.ajax({
            url: `https://${postName}/api/${storeId}/vorderstatuscount?token=${newToken}`,
            method: 'GET',
            success: (res, status) => {
                var ranka = "";
    
                for (var vorder of res) {
                    if (vorder.orderstatus == "1") {
                        ranka = vorder.ordercount
                    }
                }
                if (ranka != "") {
                    $('#ordercomranka').append(ranka);
                } else {
                    $('#ordercomranka').append("0");
                }
            },
            error: err => {
                // console.log("vorderstatuscountHomepage fale")
                console.log(err)
            },
        });

        toastr.info("您有一筆新訂單");
    }
}

document.getElementById("bell").addEventListener("click", () => {
    if (localStorage.getItem('storeinfo')) {
        let bellMsg = JSON.parse(localStorage.getItem('receiveMsg'));

        $("#bellInfo").empty();

        bellMsg.forEach(e => {
            let color = "";
            if (e.msg.includes("新訂單")) {
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

                bellValue.innerText = parseInt(bellValue.innerText) - 1;

                $('#padleft .active').removeClass('active');
                $('#pills-tabContent>div').removeClass('active');
                $('#pills-tabContent>div').removeClass('show');
                $('#ordera').attr('class', 'nav-link text-white bg-transparent text-start');
                $('#pills-order').attr('class', 'show active container tab-pane ');
                Orderpage();
                bellDropdown[i].remove();
            });
        }
    }
});

$('#btnGroupDrop1 i').on('click', function () {
    document.querySelector('#btnGroupDrop1 i').style.color = '#373a3c';
    document.querySelector('#bellValue').classList.remove('bg-danger');
    document.querySelector('#bellValue').classList.add('bg-secondary');
})

function homepageOrderDisplay() {
    $('#vorderstatuscountHomepage').empty();
    $.ajax({
        url: `https://${postName}/api/${storeId}/vorderstatuscount?token=${newToken}`,
        method: 'GET',
        success: (res, status) => {
            // console.log(res)
            console.log("vorderstatuscountHomepage ok")
            for (var vorder of res) {
                $('#vorderstatuscountHomepage').append(
                    '<li>' + `${vorder.orderstatusdesc}` + ":" + `${vorder.ordercount}` + '</li>'
                )
            }
        },
        error: err => {
            // console.log("vorderstatuscountHomepage fale")
            console.log(err)
        },
    });
}

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
/*firebase*/
import { initializeApp } from "https://www.gstatic.com/firebasejs/9.6.11/firebase-app.js";
import {
    getAuth,
    signOut,
    signInWithEmailAndPassword,
    GoogleAuthProvider,
    signInWithPopup,
    sendPasswordResetEmail
} from "https://www.gstatic.com/firebasejs/9.6.11/firebase-auth.js";

// Initialize Firebase
const firebaseConfig = {
    apiKey: "AIzaSyBqQ5T0uy3_68BVFhfTqS98VNWUmgLkir0",
    authDomain: "orderhippo-store.firebaseapp.com",
    projectId: "orderhippo-store",
    storageBucket: "orderhippo-store.appspot.com",
    messagingSenderId: "777247881627",
    appId: "1:777247881627:web:6bc31c0a8fd80a4def493e"
};


// Initialize Firebase
initializeApp(firebaseConfig);

//setSignBntStatus()
setSignBntStatus()

function storeSignOut() {
    const auth = getAuth();
    signOut(auth).then(() => {
        signOutOK();
    }).catch((error) => {
        console.error(error);
    });
}


function setSignBntStatus() {

    const auth = getAuth();
    // const signBnt = document.getElementById("signBnt");
    auth.onAuthStateChanged((store) => {
        if (!store) { // 沒有登入
            window.location.href = "login.html";
        }
    });

}

// signBnt.addEventListener("click", (e) => {

// });
/*------------榮庭--------------*/
window.onload = function () {

    $.ajax({
        url: `https://${postName}/api/${storeId}/stores?token=${newToken}`,
        method: 'GET',
        success: (res, status) => {
            // console.log(res[0].STORE_OPEN_STATUS)

            $("#switch")[0].checked = res[0].STORE_OPEN_STATUS;

        },
        error: err => {
            // console.log('status change fail')
            console.log(err)
        },
    }).fail(function () { });
    broadcastTable();

}
$(document).ready(function () {
    setTimeout(function () {
        $('body').removeAttr('class')
        $('body').removeAttr('style')
        $('section').removeAttr('class')
        $('section').removeAttr('style')
        $('section').removeAttr('role')
        $('section>div').removeClass('visually-hidden')
    }, 500)


})

//總開關處理
$("#switch")[0].checked = false;
// window.onload = openstatus;
let openstatus;
// console.log(typeof openStatus)

if (localStorage.getItem('openstatus') == "true") {
    $("#switch")[0].checked = true;
} else {
    $("#switch")[0].checked = false;
}


function openSwitch(isOpen) {
    $.ajax({
        url: `https://${postName}/api/${storeId}/stores?token=${newToken}`,
        method: 'GET',
        success: (res, status) => {
            localStorage.setItem('openstatus', $("#switch")[0].checked)
            // console.log('status change ok')
            res[0].STORE_OPEN_STATUS = isOpen
            // console.log(typeof JSON.stringify(res[0]))
            $.ajax({
                url: `https://${postName}/api/${storeId}/stores?token=${newToken}`,
                method: 'PUT',
                contentType: "application/json",
                data: JSON.stringify(res[0]),
                success: function () { },
                error: function () { }
            })


        },
        error: err => {
            // console.log('status change fail')
            console.log(err)
        },
    }).fail(function () { });

}
document.getElementById("switch").addEventListener('click', () => {
    openSwitch($("#switch")[0].checked);
});

$('#belltest').on('click', function () {
    $('#padleft .active').removeClass('active');
    $('#pills-tabContent>div').removeClass('active');
    $('#pills-tabContent>div').removeClass('show');
    $('#ordera').attr('class', 'nav-link text-white bg-transparent text-start');
    $('#pills-order').attr('class', 'show active container tab-pane ');
    Orderpage();

})

$('img[src="./img/group2.png"]').on('click', function () {
    $('#padleft .active').removeClass('active');
    $('#pills-tabContent>div').removeClass('active');
    $('#pills-tabContent>div').removeClass('show');
    $('a[data-bs-target="#pills-order"]').attr('class', 'nav-link text-white bg-transparent text-start');
    $('#pills-order').attr('class', 'show active container tab-pane ');
    Orderpage();
});
$('img[src="./img/group3.png"]').on('click', function () {
    $('#padleft .active').removeClass('active');
    $('#pills-tabContent>div').removeClass('active');
    $('#pills-tabContent>div').removeClass('show');
    $('a[data-bs-target="#pills-analyze"]').attr('class', 'nav-link text-white bg-transparent text-start');
    $('#pills-analyze').attr('class', 'show active container tab-pane ');
    Analyzepage();
});
$('img[src="./img/group4.png"]').on('click', function () {
    $('#padleft .active').removeClass('active');
    $('#pills-tabContent>div').removeClass('active');
    $('#pills-tabContent>div').removeClass('show');
    $('a[data-bs-target="#pills-reconciliationStatement"]').attr('class', 'nav-link text-white bg-transparent text-start');
    $('#pills-reconciliationStatement').attr('class', 'show active container tab-pane ');
    ReconciliationStatementpage();
});
//img src="./img/456.png"
//首頁TODO
$('#orderhippo').on('click', function () {
    $('#padleft .active').removeClass('active');
    $('#pills-tabContent>div').removeClass('active');
    $('#pills-tabContent>div').removeClass('show');
    $('a[data-bs-target="#pills-home"]').attr('class', 'nav-link text-white  bg-transparent text-start');
    $('#pills-home').attr('class', 'container tab-pane  show active');
    Homepage();
});
//登出按鍵
$('#signOutbtn').on('click', () => {

    openSwitch(false);
    localStorage.removeItem("storeinfo");
    localStorage.removeItem("storeToken");
    setInterval(function () {
        storeSignOut();
        localStorage.removeItem("openstatus");
    }, 200);
    // window.location.href = "login.html";
})
//頁面管理
//Analyzepage Orderpage Memberpage Broadcastpage ReconciliationStatementpage
//#pills-analyze #pills-order #pills-member #pills-broadcast #pills-reconciliationStatement
$("a[data-bs-target='#pills-home']").on('click', Homepage);

$("a[data-bs-target='#pills-analyze']").on('click', Analyzepage);
$("a[data-bs-target='#pills-order']").on('click', Orderpage);
$("a[data-bs-target='#pills-member']").on('click', Memberpage);
// $("a[data-bs-target='#pills-broadcast']").on('click', Broadcastpage);
$("a[data-bs-target='#pills-broadcast']").on('click', couponTable);
$("a[data-bs-target='#pills-reconciliationStatement']").on('click', ReconciliationStatementpage);
$("a[data-bs-target='#pills-broadcast']").on('click', broadcastTable);
$("a[data-bs-target='#pills-nutrientContent']").on('click', ingredientsTable);
$("a[data-bs-target='#pills-menu']").on('click', mealIngredients);
$("a[data-bs-target='#pills-home']").on('click', delay);
function delay() {
      
      $("a[data-bs-target='#pills-home']").attr("disabled", true);//開始讓他不可按
      $("a[data-bs-target='#pills-home']").css("pointer-events","none");
      setTimeout(function(){
        $("a[data-bs-target='#pills-home']").attr("disabled", false);
        $("a[data-bs-target='#pills-home']").css("pointer-events","auto");
      }, 1000);
}
var Chart3;
var Chart4;
var Chart5;
var Chart6;
var Chart7;
var Chart8;
var Chart9;
var Chart10;

//首頁頁面處理
function Homepage() {
    //首頁銷售前三//分析資料-前10熱賣
    $('#pills-home ol').empty();
    $('#pills-home ul').empty();
    // $('.Chart3').empty();
    $('#vsalerankHomepage-fst').empty();
    $('#vsalerankHomepage-sed').empty();
    $('#vsalerankHomepage-thr').empty();
    $('#ordercomranka').empty();
    $('#ordercomrankb').empty();
    $('#vmonthreveueHompage').empty();


    $.ajax({
        url: `https://${postName}/api/${storeId}/vsalerank?token=${newToken}`,
        method: 'GET',
        success: (res, status) => {
            var labels = [];
            var data = [];
            var output = [];
            // console.log("vsalerankHomepage ok")
            // console.log(res)
            var backgroundColor = colorRandom(res.length, 0.5)
             //排序 10名作輸出
             function res_count(a, b) {
                if (a.count < b.count) {
                    return 1;
                }
                if (a.count > b.count) {
                    return -1;
                }
                return 0;
            }
            if (res) {
                res.sort(res_count);
            }
            var rankdata = res.slice(0, 3)
            // console.log(rankdata)
            for (var salserank of rankdata) {
                // console.log(salserank   )

                output.push(`${salserank.mealname}`)

            }
            // console.log(output[0])

            if (output[0] != undefined) {
                $('#vsalerankHomepage-fst').append(`${output[0]}`)
            } else {
                $('#vsalerankHomepage-fst').append('沒有資料')
            }
            if (output[1] != undefined) {
                $('#vsalerankHomepage-sed').append(`${output[1]}`)
            } else {
                $('#vsalerankHomepage-sed').append('沒有資料')
            }
            if (output[2] != undefined) {
                $('#vsalerankHomepage-thr').append(`${output[2]}`)
            } else {
                $('#vsalerankHomepage-thr').append('沒有資料')
            }


           

            // console.log("rES="+res);
            var rankdata = res.slice(0, 10);
            // console.log(rankdata);
            for (var i = 0; i < rankdata.length; i++) {
                labels.push(rankdata[i].mealname);
                data.push(rankdata[i].count)
            }



            // console.log(document.getElementsByClassName('Chart3'))
            if (Chart3 instanceof Chart) {
                Chart3.destroy();
            }
            Chart3 = new Chart(document.getElementsByClassName('Chart3')[0], {
                type: 'bar', //圖表類型
                data: {

                    labels: labels,
                    datasets: [{
                        label: '', //這些資料都是在講什麼，也就是data 300 500 100是什麼
                        data: data, //每一塊的資料分別是什麼，台北：300、台中：50..
                        backgroundColor: backgroundColor,
                        hoverOffset: 4
                    }]
                }, //設定圖表資料
                options: {
                    plugins: {
                        title: {
                            display: true,
                            text: ''
                        }
                    }

                } //圖表的一些其他設定，像是hover時外匡加粗
            })
            Chart3.clear();
        },
        error: err => {
            // console.log("vsalerankHomepage fale")
            console.log(err)
        },
    });
    //首頁移交情況TODO
    $.ajax({
        url: `https://${postName}/api/${storeId}/vorderstatuscount?token=${newToken}`,
        method: 'GET',
        success: (res, status) => {
            // console.log(res)
            var ranka = "";
            var rankb = "";
            // console.log("vorderstatuscountHomepage ok")

            for (var vorder of res) {
                // $('#vorderstatuscountHomepage').append(
                //     '<li>' + `${vorder.orderstatusdesc}` + ":" + `${vorder.ordercount}` + '</li>'
                // )
                if (vorder.orderstatus == "1") {
                    ranka = vorder.ordercount
                }
                // if (vorder.orderstatus == "3") {
                //     rankb = vorder.ordercount
                // }
            }
            // console.log("ranka=" + ranka)
            // console.log("ranka=" + rankb)
            if (ranka != "") {
                $('#ordercomranka').append(ranka);
            } else {
                $('#ordercomranka').append("0");
            }
            // if (rankb != "") {
            //     $('#ordercomrankb').append(rankb);
            // } else {
            //     $('#ordercomranka').append("0");
            // }

        },

        error: err => {
            // console.log("vorderstatuscountHomepage fale")
            console.log(err)
        },
    });
    //目前總營利//分析資料-月營業額
    $.ajax({
        url: `https://${postName}/api/${storeId}/vmonthreveue?token=${newToken}`,
        method: 'GET',
        success: (res, status) => {
            $('#nowMon').empty();
            var date = new Date()
            var nowYear = date.getFullYear();
            var nowMonth = date.getMonth() + 1;
            // console.log(res)
            // console.log("vmonthreveueHompage ok")
            var labels = [];//月份
            var data = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];//金額
            var backgroundColor = colorRandom(res.length, 0.5)
            var monthEnglish = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Spt", "Oct", "Nov", "Dec"];
            for (var vmonth of res) {
                if (nowYear == vmonth.year) {
                    if (vmonth.month == nowMonth) {
                        $('#nowMon').prepend(`${vmonth.month}月目前總營收`)
                        $('#vmonthreveueHompage').append(
                            // '<li>' + `${vmonth.year}` + "年" + `${vmonth.month}` + '月目前總營收:' + `${vmonth.revenueofmonth}` + '</li>'
                            `$${vmonth.revenueofmonth}`
                        )

                    }

                    data.splice(vmonth.month - 1, 1, vmonth.revenueofmonth)
                }

            }

            var datarank = data.slice(0, 3)
            var labelsrank = labels.slice(0, 3)
            // console.log(document.getElementsByClassName('Chart3'))
            if (Chart4 instanceof Chart) {
                Chart4.destroy();
            }
            Chart4 = new Chart(document.getElementsByClassName('Chart4')[0], {
                type: 'bar', //圖表類型
                data: {
                    labels: monthEnglish,
                    datasets: [{
                        label: '月營收', //這些資料都是在講什麼，也就是data 300 500 100是什麼
                        data: data, //每一塊的資料分別是什麼，台北：300、台中：50..
                        backgroundColor: backgroundColor,
                        lineTension: 0,//曲線哲度
                        hoverOffset: 4
                    }]
                }, //設定圖表資料
                //圖表的一些其他設定，像是hover時外匡加粗
                options: {
                    // responsive: true,
                    legend: { //是否要顯示圖示
                        display: true,
                    },
                    tooltips: { //是否要顯示 tooltip
                        enabled: true
                    },

                }
            })

        },
        error: err => {
            // console.log("vmonthreveueHompage fale")
            console.log(err)
        },
    });
}
Homepage();

//分析資料頁面處理
function Analyzepage() {
    $.ajax({
        url: `https://${postName}/api/${storeId}/vsalerank?token=${newToken}`,
        method: 'GET',
        success: (res, status) => {
            var labels = [];
            var data = [];
            // console.log("vsalerankHomepage ok")
            // console.log(res)
            var backgroundColor = colorRandom(res.length, 0.5)
            //排序 10名作輸出
            function res_count(a, b) {
                if (a.count < b.count) {
                    return 1;
                }
                if (a.count > b.count) {
                    return -1;
                }
                return 0;
            }
            if (res) {
                res.sort(res_count);
            }
            // console.log(res);
            var rankdata = res.slice(0, 10);
            // console.log(rankdata);
            for (var i = 0; i < rankdata.length; i++) {
                labels.push(rankdata[i].mealname);
                data.push(rankdata[i].count)
            }

            // console.log(document.getElementsByClassName('Chart3'))
            if (Chart3 instanceof Chart) {
                Chart3.destroy();
            }
            Chart3 = new Chart(document.getElementsByClassName('Chart3')[1], {
                type: 'bar', //圖表類型
                data: {
                    labels: labels,
                    datasets: [{
                        label: '', //這些資料都是在講什麼，也就是data 300 500 100是什麼
                        data: data, //每一塊的資料分別是什麼，台北：300、台中：50..
                        backgroundColor: backgroundColor,
                        hoverOffset: 4
                    }]
                }, //設定圖表資料
                options: {} //圖表的一些其他設定，像是hover時外匡加粗
            })
            Chart3.clear();
        },
        error: err => {
            // console.log("vsalerankHomepage fale")
            console.log(err)
        },
    });
    //目前總營利//分析資料-月營業額
    $.ajax({
        url: `https://${postName}/api/${storeId}/vmonthreveue?token=${newToken}`,
        method: 'GET',
        success: (res, status) => {
            var date = new Date()
            var nowYear = date.getFullYear();
            var nowMonth = date.getMonth() + 1;
            // console.log(res)
            var backgroundColor = colorRandom(res.length, 0.5);
            // console.log("vmonthreveueHompage ok")
            var labels = [];//月份
            var data = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];//金額
            var backgroundColor = colorRandom(res.length, 0.5)
            var monthEnglish = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Spt", "Oct", "Nov", "Dec"];
            for (var vmonth of res) {
                if (nowYear == vmonth.year) {
                    if (vmonth.month == nowMonth) {
                        $('#nowMon').prepend(`${vmonth.month}`)
                        $('#vmonthreveueHompage').append(
                            // '<li>' + `${vmonth.year}` + "年" + `${vmonth.month}` + '月目前總營收:' + `${vmonth.revenueofmonth}` + '</li>'
                            `$${vmonth.revenueofmonth}`
                        )

                    }

                    data.splice(vmonth.month - 1, 1, vmonth.revenueofmonth)
                }

            }

            var datarank = data.slice(0, 3)
            var labelsrank = labels.slice(0, 3)
            // console.log(document.getElementsByClassName('Chart3'))
            if (Chart4 instanceof Chart) {
                Chart4.destroy();
            }
            Chart4 = new Chart(document.getElementsByClassName('Chart4')[1], {
                type: 'bar', //圖表類型
                data: {
                    labels: monthEnglish,
                    datasets: [{
                        label: '月營收', //這些資料都是在講什麼，也就是data 300 500 100是什麼
                        data: data, //每一塊的資料分別是什麼，台北：300、台中：50..
                        backgroundColor: backgroundColor,
                        lineTension: 0,//曲線哲度
                        hoverOffset: 4
                    }]
                }, //設定圖表資料
                //圖表的一些其他設定，像是hover時外匡加粗
                options: {
                    responsive: true
                }
            })

        },
        error: err => {
            // console.log("vmonthreveueHompage fale")
            console.log(err)
        },
    });
    //分析資料-年齡客群TODO
    $.ajax({
        url: `https://${postName}/api/${storeId}/vagechart?token=${newToken}`,
        method: 'GET',
        success: (res, status) => {
            // console.log(res);
            // console.log("vagechart ok");
            // console.log("vsalerankHomepage ok");
            var labels = [];
            var data = [];



            var backgroundColor = colorRandom(res.length, 0.2)

            for (var vage of res) {
                labels.push(vage.agerange + `歲:${vage.qty}人`)
                data.push(vage.qty)
            }
            if (Chart5 instanceof Chart) {
                Chart5.destroy();
            }
            Chart5 = new Chart(document.getElementsByClassName('Chart5'), {
                type: 'pie', //圖表類型
                data: {
                    labels: labels,
                    datasets: [{
                        label: '各層年齡(人數)', //這些資料都是在講什麼，也就是data 300 500 100是什麼
                        data: data, //每一塊的資料分別是什麼，台北：300、台中：50..
                        backgroundColor: backgroundColor,
                        borderColor: backgroundColor,
                        Position: 'left'
                    }]
                }, //設定圖表資料
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            position: 'left',
                        }
                    }
                },

                //圖表的一些其他設定，像是hover時外匡加粗
            })

        },
        error: err => {
            // console.log("vagechart fale")
            console.log(err)
        },
    });
    //分析資料-類別圓餅TODO 類別跟餐盒*4
    //{mealid: 'M202206_bc9105cee4de11ecad989828a620e5eb', mealcategoryname: '健康餐盒', mealname: '雞胸餐盒', percentage: 85.71}
    $.ajax({
        url: `https://${postName}/api/${storeId}/vsalecategory?token=${newToken}`,
        method: 'GET',
        success: (res, status) => {
            // console.log(res)
            // console.log("vsalecategory ok");
            var healthmealLabels = [];
            var healthmealData = [];
            var saladLabels = [];
            var saladData = [];
            var pastaLabels = [];
            var pastaData = [];
            var drinkLabels = [];
            var drinkData = [];
            var backgroundColor = colorRandom(res.length, 0.5)
            // console.log("vsalerankHomepage ok")
            // console.log(res)
            for (var salecategory of res) {
                if (salecategory.mealcategoryname == '健康餐盒') {
                    healthmealLabels.push(salecategory.mealname + `${salecategory.percentage}%`);
                    healthmealData.push(salecategory.percentage);
                } else if (salecategory.mealcategoryname == '沙拉') {
                    saladLabels.push(salecategory.mealname + `${salecategory.percentage}%`);
                    saladData.push(salecategory.percentage);
                } else if (salecategory.mealcategoryname == '義大利麵') {
                    pastaLabels.push(salecategory.mealname + `${salecategory.percentage}%`);
                    pastaData.push(salecategory.percentage);
                } else if (salecategory.mealcategoryname == '飲料') {
                    drinkLabels.push(salecategory.mealname + `${salecategory.percentage}%`);
                    drinkData.push(salecategory.percentage);
                } else {
                    console.log('mealcategoryname return error')
                }
            }
            var Chart6bgColor = backgroundColor.slice(0, healthmealLabels.length);
            var Chart7bgColor = backgroundColor.slice(
                healthmealLabels.length - 1, healthmealLabels.length + saladLabels.length
            );
            var Chart8bgColor = backgroundColor.slice(
                healthmealLabels.length + saladLabels.length - 1, healthmealLabels.length + saladLabels.length + pastaLabels.length
            );
            var Chart9bgColor = backgroundColor.slice(
                healthmealLabels.length + saladLabels.length + pastaLabels.length - 1,
                healthmealLabels.length + saladLabels.length + pastaLabels.length + drinkLabels.length
            )
            if (Chart6 instanceof Chart) {
                Chart6.destroy();
                Chart7.destroy();
                Chart8.destroy();
                Chart9.destroy();
            }

            Chart6 = new Chart(document.getElementsByClassName('Chart6'), {
                type: 'pie', //圖表類型
                data: {
                    labels: healthmealLabels,
                    datasets: [{
                        label: '健康餐盒銷售比例(%)', //這些資料都是在講什麼，也就是data 300 500 100是什麼
                        data: healthmealData, //每一塊的資料分別是什麼，台北：300、台中：50..
                        backgroundColor: Chart6bgColor,
                        hoverOffset: 4
                    }]
                }, //設定圖表資料
                options: {
                    plugins: {
                        legend: {
                            position: 'left',
                        }
                    }
                } //圖表的一些其他設定，像是hover時外匡加粗
            })
            Chart7 = new Chart(document.getElementsByClassName('Chart7'), {
                type: 'pie', //圖表類型
                data: {
                    labels: saladLabels,
                    datasets: [{
                        label: '沙拉銷銷售比例(%)', //這些資料都是在講什麼，也就是data 300 500 100是什麼
                        data: saladData, //每一塊的資料分別是什麼，台北：300、台中：50..
                        backgroundColor: Chart7bgColor,
                        hoverOffset: 4
                    }]
                }, //設定圖表資料
                options: {
                    plugins: {
                        legend: {
                            position: 'left',
                        }
                    }
                } //圖表的一些其他設定，像是hover時外匡加粗
            })
            Chart8 = new Chart(document.getElementsByClassName('Chart8'), {
                type: 'pie', //圖表類型
                data: {
                    labels: pastaLabels,
                    datasets: [{
                        label: '義大利麵銷售比例(%)', //這些資料都是在講什麼，也就是data 300 500 100是什麼
                        data: pastaData, //每一塊的資料分別是什麼，台北：300、台中：50..
                        backgroundColor: Chart8bgColor,
                        hoverOffset: 4
                    }]
                }, //設定圖表資料
                options: {
                    plugins: {
                        legend: {
                            position: 'left',
                        }
                    }
                } //圖表的一些其他設定，像是hover時外匡加粗
            })
            Chart9 = new Chart(document.getElementsByClassName('Chart9'), {
                type: 'pie', //圖表類型
                data: {
                    labels: drinkLabels,
                    datasets: [{
                        label: '飲料', //這些資料都是在講什麼，也就是data 300 500 100是什麼
                        data: drinkData, //每一塊的資料分別是什麼，台北：300、台中：50..
                        backgroundColor: Chart9bgColor,
                        hoverOffset: 4
                    }]
                }, //設定圖表資料
                options: {
                    plugins: {
                        legend: {
                            position: 'left',
                        }
                    }
                } //圖表的一些其他設定，像是hover時外匡加粗
            })
        },
        error: err => {
            // console.log("vsalecategory fale")
            console.log(err)
        },
    });
    //分析資料-男女比例TODO
    //{usergender: 'F', gendercount: 5, percentage: 45.45}
    $.ajax({
        url: `https://${postName}/api/${storeId}/vgenderchart?token=${newToken}`,
        method: 'GET',
        success: (res, status) => {
            // console.log(res)
            // console.log("vgenderchart ok")
            var labels = [];
            var data = [];
            var backgroundColor = colorRandom(3, 0.5);

            for (var vgender of res) {

                data.push(vgender.percentage)
            }
            labels.push(`男顧客佔比${data[0]}%`);
            labels.push(`女顧客佔比${data[1]}%`);
            labels.push(`中性顧客佔比${data[2]}%`);
            if (Chart10 instanceof Chart) {
                Chart10.destroy();
            }
            Chart10 = new Chart(document.getElementsByClassName('Chart10'), {
                type: 'pie', //圖表類型
                data: {
                    labels: labels,
                    datasets: [{
                        label: '男女中性占比', //這些資料都是在講什麼，也就是data 300 500 100是什麼
                        data: data, //每一塊的資料分別是什麼，台北：300、台中：50..
                        backgroundColor: backgroundColor,
                        borderColor: backgroundColor,

                    }]
                }, //設定圖表資料
                options: {
                    plugins: {
                        legend: {
                            position: 'left',
                        }
                    }
                } //圖表的一些其他設定，像是hover時外匡加粗
            })
        },
        error: err => {
            // console.log("vgenderchart fale")
            console.log(err)
        },
    });
}
//訂單管理頁面處理
function Orderpage() {

    //1:orderPreview 2:orderAfteview 4:orderCancel 3:orderComplete
    $.ajax({
        url: `https://${postName}/api/${storeId}/vorderdisplay?token=${newToken}`,
        method: 'GET',
        success: (res, status) => {
            // console.log(res);
            // console.log("ordersTable ok");
            $('#orderPreview').empty();
            $('#orderAfteview').empty();
            $('#orderCancel').empty();
            $('#orderComplete').empty();
            for (var orderInfo of res) {

                if (orderInfo.orderstatus == "1") {
                    $('#orderPreview').append(
                        '<tr class=" orderTrr">' +
                        '<td class="textBlock orderTr" style="display: none;">' + `${orderInfo.orderid}` + '</td>' +
                        '<td class="textBlock">' + `未確認定單` + '</td>' +
                        '<td class="textBlock">' + `${orderInfo.username}` + '</td>' +
                        '<td >' + `${orderInfo.userphone}` + '</td>' +
                        '<td >' + `${orderInfo.mealorderqty}` + '</td>' +
                        '<td >' + `${orderInfo.orderdesc}` + '</td>' +
                        '<td >' + `${orderInfo.ordersprice}` + '</td>' +
                        '<td class="textBlock">' + `${formatDate(orderInfo.createtime)}` + '</td>' +
                        '<td class="textBlock"><button  class="orderBtn btn btn-primary">確認</button><button  class="orderCBtn btn btn-danger">取消</button></td>' +
                        '</tr>'
                    )
                } else if (orderInfo.orderstatus == "2") {
                    $('#orderAfteview').append(
                        '<tr  class="orderTrrcom">' +
                        '<td class="orderTrcom" style="display: none;">' + `${orderInfo.orderid}` + '</td>' +
                        '<td class="textBlock">' + `已確認定單` + '</td>' +
                        '<td class="textBlock">' + `${orderInfo.username}` + '</td>' +
                        '<td class="textBlock">' + `${orderInfo.userphone}` + '</td>' +
                        '<td >' + `${orderInfo.mealorderqty}` + '</td>' +
                        '<td >' + `${orderInfo.orderdesc}` + '</td>' +
                        '<td class="textBlock">' + `${orderInfo.ordersprice}` + '</td>' +
                        '<td class="textBlock">' + `${formatDate(orderInfo.createtime)}` + '</td>' +
                        '<td class="textBlock"><button class="ordercomBtn btn btn-dark">完成</button></td>' +

                        '</tr>'
                    )
                } else if (orderInfo.orderstatus == "4") {
                    $('#orderCancel').append(
                        '<tr>' +
                        '<td  class="textBlock">' + `已取消訂單` + '</td>' +
                        '<td class="textBlock">' + `${orderInfo.username}` + '</td>' +
                        '<td class="textBlock">' + `${orderInfo.userphone}` + '</td>' +
                        '<td >' + `${orderInfo.mealorderqty}` + '</td>' +
                        '<td >' + `${orderInfo.orderdesc}` + '</td>' +
                        '<td class="textBlock">' + `${orderInfo.ordersprice}` + '</td>' +
                        '<td class="textBlock">' + `${formatDate(orderInfo.createtime)}` + '</td>' +
                        '</tr>'
                    )
                } else if (orderInfo.orderstatus == "3") {
                    $('#orderComplete').append(
                        '<tr>' +
                        '<td  class="textBlock">' + `已完成訂單` + '</td>' +
                        '<td class="textBlock">' + `${orderInfo.username}` + '</td>' +
                        '<td class="textBlock">' + `${orderInfo.userphone}` + '</td>' +
                        '<td >' + `${orderInfo.mealorderqty}` + '</td>' +
                        '<td >' + `${orderInfo.orderdesc}` + '</td>' +
                        '<td class="textBlock">' + `${orderInfo.ordersprice}` + '</td>' +
                        '<td class="textBlock">' + `${formatDate(orderInfo.createtime)}` + '</td>' +
                        '</tr>'
                    )
                } else { console.log("info error") }

            }
            //縮放
            // $('#orderPreview tr').on('click', function () {
            //     if ($(this).find('td').css('word-break') == 'normal') {
            //         $(this).find('td').css({//可以改變多個css
            //             "word-break": "break-all",
            //             'white-spㄌace': 'normal'

            //         })
            //     } else {
            //         $(this).find('td').css({
            //             "word-break": "normal",
            //             'white-space': 'nowrap'
            //         })
            //     }
            // });
            // $('#orderAfteview tr').on('click', function () {
            //     if ($(this).find('td').css('word-break') == 'normal') {
            //         $(this).find('td').css({//可以改變多個css
            //             "word-break": "break-all",
            //             'white-space': 'normal'

            //         })
            //     } else {
            //         $(this).find('td').css({
            //             "word-break": "normal",
            //             'white-space': 'nowrap'
            //         })
            //     }
            // });
            // $('#orderCancel tr').on('click', function () {
            //     if ($(this).find('td').css('word-break') == 'normal') {
            //         $(this).find('td').css({//可以改變多個css
            //             "word-break": "break-all",
            //             'white-space': 'normal'

            //         })
            //     } else {
            //         $(this).find('td').css({
            //             "word-break": "normal",
            //             'white-space': 'nowrap'
            //         })
            //     }
            // });
            // $('#orderComplete tr').on('click', function () {
            //     if ($(this).find('td').css('word-break') == 'normal') {
            //         $(this).find('td').css({//可以改變多個css
            //             "word-break": "break-all",
            //             'white-space': 'normal'

            //         })
            //     } else {
            //         $(this).find('td').css({
            //             "word-break": "normal",
            //             'white-space': 'nowrap'
            //         })
            //     }
            // });
            //完成按鈕
            $('.ordercomBtn').click(function () {
                var orderid = $('.orderTrcom')[$(this).closest("tr").index()].innerText
                // console.log(orderid)
                $.ajax({
                    url: `https://${postName}/api/${storeId}/orders?token=${newToken}&orderid=${orderid}`,
                    method: 'GET',
                    success: (res, status) => {
                        res[0].ORDER_STATUS = "3"
                        var putres = {
                            "ORDER_ID": `${res[0].ORDER_ID}`,
                            "STORE_ID": `${res[0].STORE_ID}`,
                            "USER_ID": `${res[0].USER_ID}`,
                            "ORDERS_PRICE": res[0].ORDERS_PRICE,
                            "ORDER_STATUS": `${res[0].ORDER_STATUS}`,
                            "ORDER_DESC": `${res[0].ORDER_DESC}`
                        }

                        $.ajax({
                            url: `https://${postName}/api/${storeId}/order?token=${newToken}`,
                            method: 'PUT',
                            contentType: "application/JSON",
                            data: JSON.stringify(putres),
                            success: (res, status) => {
                                // console.log(res)


                            }, error: err => {
                                // console.log("ordersTable put fale")
                                console.log(err)
                            },
                        })

                    }, error: err => {
                        // console.log("ordersTable get fale")
                        console.log(err)
                    },
                })
                $('.orderTrrcom')[$(this).closest("tr").index()].style.display = 'none';
            });
            //刪除
            $('.orderCBtn').click(function () {
                var orderid = $('.orderTr')[$(this).closest("tr").index()].innerText;

                // 送出訊息給前端
                sednMessage('有一筆取消的訂單', orderid);

                $.ajax({
                    url: `https://${postName}/api/${storeId}/orders?token=${newToken}&orderid=${orderid}`,
                    method: 'GET',
                    success: (res, status) => {
                        res[0].ORDER_STATUS = "4"
                        var putress = {
                            "ORDER_ID": `${res[0].ORDER_ID}`,
                            "STORE_ID": `${res[0].STORE_ID}`,
                            "USER_ID": `${res[0].USER_ID}`,
                            "ORDERS_PRICE": res[0].ORDERS_PRICE,
                            "ORDER_STATUS": `${res[0].ORDER_STATUS}`,
                            "ORDER_DESC": `${res[0].ORDER_DESC}`
                        }

                        // 信用卡刷退流程
                        $.ajax({
                            url: `https://${postName}/api/${storeId}/payments?token=${newToken}&orderid=${putress.ORDER_ID}`,
                            method: 'GET',
                            success: (res, status) => {
                                if (res[0].PAYMENT_CATEGORY === '1') {
                                    $.ajax({
                                        url: `https://${postName}/api/${storeId}/striperefund?token=${newToken}&paymentintent=${res[0].PAYMENT_ID}`,
                                        method: 'POST',
                                        success: (res, status) => {
                                            console.log("退款成功");
                                        }, error: err => {
                                            console.log(err)
                                        },
                                    })
                                }
                            }, error: err => {
                                console.log(err)
                            },
                        })     

                        $.ajax({
                            url: `https://${postName}/api/${storeId}/order?token=${newToken}`,
                            method: 'PUT',
                            contentType: "application/JSON",
                            data: JSON.stringify(putress),
                            success: (res, status) => {
                                // console.log(res)


                            }, error: err => {
                                // console.log("ordersTable put fale")
                                console.log(err)
                            },
                        })

                    }, error: err => {
                        // console.log("ordersTable get fale")
                        console.log(err)
                    },
                })
                $(' .orderTrr')[$(this).closest("tr").index()].style.display = 'none';
            })
            //確定
            var getcompletevalue = [];
            $('.orderBtn').click(function () {

                var orderid = $('.orderTr')[$(this).closest("tr").index()].innerText

                var selectData = [];
                //['O20220607_d299ac13e5c611ec9c159828a620e5eb', '未確認定單', 'Admin', '0912345679', '雞胸餐盒 * 5', '$100000', '2022年5月7日2時31分', '<']
                // 0 :O20220607_d299ac13e5c611ec9c159828a620e5eb
                // 1 未確認定單
                // 2 Admin
                // 3 0912345679
                // 4 雞胸餐盒 * 5
                // 5 $100000
                // 6 2022年5月7日2時31分
                // 7
                for (var i = 0; i < 7; i++) {
                    selectData.push($(this).closest("tr")[0].children[i].innerText)

                }
                // console.log(selectData)
                var nowDate = new Date();
                var nowTime = nowDate.getFullYear() + "年" + (nowDate.getMonth() + 1) + "月" +
                    nowDate.getDate() + "日" + nowDate.getHours() + "時" + nowDate.getMinutes() +
                    "分";
                // console.log(b)
                getcompletevalue.push(selectData[0])
                $('#orderAfteview').append(
                    '<tr  class="orderTrrcom">' +
                    '<td class="orderTrcom" style="display: none;">' + `${selectData[0]}` + '</td>' +
                    '<td class="textBlock">' + `已確認定單` + '</td>' +
                    '<td class="textBlock">' + `${selectData[2]}` + '</td>' +
                    '<td class="textBlock">' + `${selectData[3]}` + '</td>' +
                    '<td>' + `${selectData[4]}` + '</td>' +
                    '<td>' + `${selectData[5]}` + '</td>' +
                    '<td class="textBlock">' + `${selectData[6]}` + '</td>' +
                    '<td class="textBlock">' + `${nowTime}` + '</td>' +
                    '<td class="textBlock"><button  class="ordercomBtn btn btn-dark">完成</button></td>' +

                    '</tr>'
                )

                // 送出訊息給前端
                sednMessage('有一筆確認的訂單', orderid);

                var doc = new jsPDF();

                doc.addFont('SourceHanSans-Normal.ttf', 'SourceHanSans-Normal', 'normal');
                doc.setFont('SourceHanSans-Normal');
                var order = selectData[4]
                var orders = order.replace(/,/g, `\r`)
                // console.log(orders)
                doc.text(20, 20, `訂單單號:${selectData[0]}\r會員姓名:${selectData[2]}\r點餐時間:${nowTime}\r餐點內容:\r${orders}\r餐點備註:${selectData[5]}\r餐點總金額:${selectData[6]}`);
                doc.save(`${selectData[2]}的點單.pdf`);


                $.ajax({
                    url: `https://${postName}/api/${storeId}/orders?token=${newToken}&orderid=${orderid}`,
                    method: 'GET',
                    success: (res, status) => {
                        //完成按鈕
                        $('.ordercomBtn').click(function () {
                            var orderid = $('.orderTrcom')[$(this).closest("tr").index()].innerText
                            // console.log(orderid)
                            $.ajax({
                                url: `https://${postName}/api/${storeId}/orders?token=${newToken}&orderid=${orderid}`,
                                method: 'GET',
                                success: (res, status) => {

                                    // console.log("orderid")
                                    res[0].ORDER_STATUS = "3"
                                    var putres = {
                                        "ORDER_ID": `${res[0].ORDER_ID}`,
                                        "STORE_ID": `${res[0].STORE_ID}`,
                                        "USER_ID": `${res[0].USER_ID}`,
                                        "ORDERS_PRICE": res[0].ORDERS_PRICE,
                                        "ORDER_STATUS": `${res[0].ORDER_STATUS}`,
                                        "ORDER_DESC": `${res[0].ORDER_DESC}`
                                    }
                                    // console.log(putres)
                                    $.ajax({
                                        url: `https://${postName}/api/${storeId}/order?token=${newToken}`,
                                        method: 'PUT',
                                        contentType: "application/JSON",
                                        data: JSON.stringify(putres),
                                        success: (res, status) => {
                                            // console.log(res)
                                            // console.log("okookokkokkoo")

                                        }, error: err => {
                                            // console.log("ordersTable put fale")
                                            console.log(err)
                                        },
                                    })

                                }, error: err => {
                                    // console.log("ordersTable get fale")
                                    console.log(err)
                                },
                            })
                            $('.orderTrrcom')[$(this).closest("tr").index()].style.display = 'none';
                        });
                        res[0].ORDER_STATUS = "2"
                        var putres = {
                            "ORDER_ID": `${res[0].ORDER_ID}`,
                            "STORE_ID": `${res[0].STORE_ID}`,
                            "USER_ID": `${res[0].USER_ID}`,
                            "ORDERS_PRICE": res[0].ORDERS_PRICE,
                            "ORDER_STATUS": `${res[0].ORDER_STATUS}`,
                            "ORDER_DESC": `${res[0].ORDER_DESC}`
                        }

                        $.ajax({
                            url: `https://${postName}/api/${storeId}/order?token=${newToken}`,
                            method: 'PUT',
                            contentType: "application/JSON",
                            data: JSON.stringify(putres),
                            success: (res, status) => {
                                // console.log(res)


                            }, error: err => {
                                // console.log("ordersTable put fale")
                                console.log(err)
                            },
                        })

                    }, error: err => {
                        // console.log("ordersTable get fale")
                        console.log(err)
                    },
                })
                $(' .orderTrr')[$(this).closest("tr").index()].style.display = 'none';
            });





        },
        error: err => {
            // console.log("ordersTable get fale")
            console.log(err)
        },
    });
}

function comOder() {
    $.ajax({
        url: xxx,
        method: 'PUT',
        data: {

        },
        success: (res, status) => {

        },
        error: err => {
            // console.log("ordersTable fale")
            console.log(err)
        }
    })
}
//preafterOrder cancelOrder completeOrder
$('#preafterOrder').on('click', Orderpage)
$('#cancelOrder').on('click', Orderpage)
$('#completeOrder').on('click', Orderpage)
//顧客管理頁面處理
function Memberpage() {
    $.ajax({
        url: `https://${postName}/api/${storeId}/users?token=${newToken}`,
        method: 'GET',
        success: function (res, status) {
            $('#tableMemberbody').empty();
            // console.log("memberTable ok")

            for (var member of res) {

                $('#tableMemberbody').append(
                    '<tr>' +
                    '<td class="textBlock">' + `${member.USER_ID}` + '</td>' +
                    '<td>' + `${member.USER_NAME}` + '</td>' +
                    '<td>' + `${member.USER_GENDER}` + '</td>' +
                    '<td>' + `${member.USER_PHONE}` + '</td>' +
                    '<td class="textBlock">' + `${member.USER_MAIL}` + '</td>' +
                    '<td class="textBlock">' + `${formatDatenotime(member.USER_BIRTH)}` + '</td>' +
                    '<td>' + `${member.USER_AGE}歲` + '</td>' +
                    '</tr>'
                )


            }
            $('#tableMemberbody tr').on('click', function () {
                // console.log($(this))
                if ($(this).find('td').css('word-break') == 'normal') {
                    $(this).find('td').css({//可以改變多個css
                        "word-break": "break-all",
                        'white-space': 'normal'

                    })
                } else {
                    $(this).find('td').css({
                        "word-break": "normal",
                        'white-space': 'nowrap'
                    })
                }
            })
        },
        error: function () {
            console.log("memberTable fail")
        }
    })
}
//廣播訊息設定頁面處理

//對帳單頁面處理
function ReconciliationStatementpage() {
    $('#tablepayments').bootstrapTable({
        url: `https://${postName}/api/${storeId}/payments?token=${newToken}`,         //請求後臺的 URL（*）
        striped: false,
        method: 'get',                      //請求方式（*）
        // data: data,                      //當不使用上面的後臺請求時，使用data來接收資料
        toolbar: '#toolbar',                //工具按鈕用哪個容器
        showFullscreen: true,                    //全平按鈕
        showColumns: true,
        silentSort: true,
        showPaginationSwitch: true,
        showButtonIcons: false,            //沒用
        showButtonText: true,                //有用
        striped: false,                      //是否顯示行間隔色
        cache: false,
        buttonsPrefix: 'btn-sm btn',                       //是否使用快取，預設為 true，所以一般情況下需要設定一下這個屬性（*）
        pagination: true,                   //是否顯示分頁（*）
        sortable: true,                    //是否啟用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "client",           //分頁方式：client 使用者端分頁，server 伺服器端分頁（*）
        pageNumber: 1,                       //初始化載入第一頁，預設第一頁
        pageSize: 5,                        //每頁的記錄行數（*）
        pageList: [5, 10],        //可供選擇的每頁的行數（*）
        search: true,                       //是否顯示錶格搜尋，此搜尋是使用者端搜尋，不會進伺服器端，所以個人感覺意義不大
        strictSearch: false,                 //啟用嚴格搜尋。禁用比較檢查。                  //是否顯示所有的列
        showRefresh: true,                  //是否顯示重新整理按鈕
        minimumCountColumns: 2,             //最少允許的列數
        clickToSelect: true,                //是否啟用點選選中行
        // height: 1000,                        //行高，如果沒有設定 height 屬性，表格自動根據記錄條數覺得表格高度
        uniqueId: "ID",                     //每一行的唯一標識，一般為主鍵列
        showToggle: true,                    //是否顯示詳細檢視和列表檢視的切換按鈕
        cardView: true,                    //是否顯示詳細檢視
        detailView: false,                  //是否顯示父子表
        showExport: true,                   //是否顯示匯出
        exportTypes: ['json', 'xml', 'csv', 'txt', 'sql', 'excel'],
        // exportDataType: "selected",            //basic', 'all', 'selected'.
        columns: [{
            field: 'USER_ID', title: '會員ID'       //我們取json中id的值，並將表頭title設定為ID
        }, {
            field: 'PAYMENT_ID', title: '結帳單ID'         //我們取 json 中 username 的值，並將表頭 title 設定為使用者名稱
        }, {
            field: 'ORDER_ID', title: '單據ID'                //我們取 json 中 sex 的值，並將表頭 title 設定為性別
        }, {
            field: 'PAYMENT_PRICE', title: '價格', formatter: function (value, row, index) {
                // console.log(value);
                // console.log(row);
                // console.log(index);

                return '$' + value;
            }
        }, {
            field: 'PAYMENT_CATEGORY', title: '結帳方式', formatter: function (value, row, index){
                if(value=="1"){
                    return "信用卡結帳"
                }else if(value=="2"){
                    return "現金結帳"
                }else{
                    return "無法判斷結帳方式"
                }
            }               //我們取 json 中 sign 的值，並將表頭 title 設定為簽名
        }, {
            field: 'CREATE_TIME', title: '結帳時間', formatter: function (value, row, index) {
                // console.log(value);
                // console.log(row);
                // console.log(index);

                return formatDate(value);
            }          //我們取 json 中 classify 的值，並將表頭 title 設定為分類
        },

        ],
        // responseHandler: function (res) {
        //     return res.data      //在載入遠端資料之前，處理響應資料格式.
        //     // 我們取的值在data欄位中，所以需要先進行處理，這樣才能獲取我們想要的結果
        // }
    });
}
//顧客管理表格

// $('#couponBtn').on('click',couponTable )
//廣播與優惠卷設定
function couponTable() {
    document.getElementById('couponName').value = "";
    document.getElementById('couponText').value = "";
    var couponid = ""
    // $('#couponInput').attr('value','');
    $.ajax({

        url: `https://${postName}/api/${storeId}/coupons?token=${newToken}`,
        method: 'GET',
        success: function (res, status) {
            $('#couponTbody').empty();
            // console.log("couponTbody ok")
            // console.log(res)
            $(".couponclass").unbind();
            $("#couponDelete").unbind();
            $("#couponSave").unbind();
            $("#couponInsert").unbind();
            for (var coupon of res) {

                $('#couponTbody').append(
                    '<tr class="couponTr">' +
                    '<td>' + `<input  class="couponclass" type="radio" value="${coupon.COUPON_ID}"
                    name="couponRadio"
                    class="form-check-input couponClass">` + '</td>' +
                    '<td style="display:none;">' + `${coupon.COUPON_ID}` + '</td>' +
                    '<td class="teet" id="couponNAME" style="word-break: break-all;min-width: 50px;max-width: 200px;">' + `${coupon.COUPON_NAME}` + '</td>' +
                    '<td id="couponDESC">' + `${coupon.COUPON_DESC}` + '</td>' +
                    '</tr>'
                )


            }


            $('.couponclass').on('change', function () {
                // var couponid = $('input:radio:checked[name="couponRadio"]').val();
                var len = $('input:radio:checked[name="couponRadio"]').closest('tr').index();
                //.innerText.split("  ")[0].split('\t');
                //子節點集合
                var Texta = $('.couponTr')[len].childNodes[2].innerText;
                var Textb = $('.couponTr')[len].childNodes[3].innerText;

                document.getElementById('couponName').value = Texta;
                document.getElementById('couponText').value = Textb;

            })
            // [$(this).closest("tr").index()]
            //修改
            $('#couponSave').on('click', function () {
                if (document.getElementById('couponName').value == "" || document.getElementById('couponText').value == "") {
                    Swal.fire('請輸入完整值');

                } else {

                    couponid = $('input:radio:checked[name="couponRadio"]').val();
                    var couponName = document.getElementById('couponName').value;
                    var couponText = document.getElementById('couponText').value;
                    var data = {
                        "COUPON_DESC": `${couponText}`,
                        "COUPON_ID": `${couponid}`,
                        "COUPON_NAME": `${couponName}`,
                        "STORE_ID": `${storeId}`
                    }

                    $.ajax({
                        url: `https://${postName}/api/${storeId}/coupon?token=${newToken}`,
                        method: 'PUT',
                        contentType: "application/json",
                        data: JSON.stringify(data),
                        success: function (e) { Swal.fire('修改成功'); couponTable(); },
                        error: function (err) {
                            if (err.status == "500") {

                                Swal.fire('修改錯誤')
                            } if (err.status == "404") {
                                Swal.fire('請選擇欄位')
                            }
                        }


                    })
                }



            });
            //新增
            $('#couponInsert').on('click', function () {
                if (document.getElementById('couponName').value == "" || document.getElementById('couponText').value == "") {
                    Swal.fire('請輸入完整值')
                } else {
                    // var couponid = $('input:radio:checked[name="couponRadio"]').val();
                    var couponName = document.getElementById('couponName').value;
                    var couponText = document.getElementById('couponText').value;
                    var data = {
                        "COUPON_DESC": `${couponText}`,
                        "COUPON_NAME": `${couponName}`,
                        "STORE_ID": `${storeId}`
                    }
                    $.ajax({
                        url: `https://${postName}/api/${storeId}/coupon?token=${newToken}`,
                        method: 'POST',
                        contentType: "application/json",
                        data: JSON.stringify(data),
                        success: function (e) { Swal.fire('新增成功'); console.log(e); couponTable(); },
                        error: function (err) {
                            if (err.status == "500") {
                                Swal.fire('名稱重複')
                            } else if (err.status == "404") {
                                Swal.fire('新增失敗')
                            }

                        }


                    })

                }


            });
            //刪除
            $('#couponDelete').on('click', function () {
                var couponid = $('input:radio:checked[name="couponRadio"]').val();
                Swal.fire({
                    title: "是否要刪除",
                    
                    icon: 'warning',
                    showCancelButton: true
                }).then(function(result){
                    if(result.value){
                        $.ajax({
                            url: `https://${postName}/api/${storeId}/coupon?token=${newToken}&couponid=${couponid}`,
                            method: 'DELETE',
                            contentType: "application/json",
                            success: function (e) { Swal.fire('刪除成功'); console.log(e); couponTable(); },
                            error: function (err) {
                                if (err.status == "400") {
                                    Swal.fire('請選擇欄位')
                                } else if (err.status == "404") {
                                    Swal.fire('請選擇欄位')
                                }
        
                            }
        
        
                        })
                    }
                })
                



            });

        },
        error: function () {
            console.log("memberTable fail")
        }
    })
}
// 

function broadcastTable() {
    document.getElementById('broadcastName').value = "";
    document.getElementById('broadcastText').value = "";
    document.getElementById('broadcastimage').value = "";
    // document.getElementById('')
    document.getElementById('showImg').src = "";
    document.getElementById('preview-img').src = "";
    // var broadcastid = ""
    $.ajax({
        //         CREATE_ID: "l3rH7uT47PTrQSteWO2V9XqbpRn1"
        // CREATE_TIME: "2022-06-10T15:27:07.000+00:00"
        // ID: 1
        // MESSAGE_DESC: "廣告訊息1"
        // MESSAGE_ID: "M202206_c966341ae8d111eca0149828a620e5eb"
        // MESSAGE_IMAGE: "廣告訊息1"
        // MESSAGE_NAME: "廣告訊息1"
        // REVISE_ID: null
        // REVISE_TIME: null
        // STORE_ID: "l3rH7uT47PTrQSteWO2V9XqbpRn1"
        url: `https://${postName}/api/${storeId}/messages?token=${newToken}`,
        method: 'GET',
        success: function (res, status) {
            $('#broadcastTbody').empty();
            // console.log("couponTbody ok")
            // console.log(res)
            $(".broadcastclass").unbind();
            $("#broadcastSave").unbind();
            $("#broadcastInsert").unbind();
            $("#broadcastDelete").unbind();
            for (var broadcast of res) {

                $('#broadcastTbody').append(
                    '<tr class="broadcastTr">' +
                    '<td class="text-center">' + `<input class="broadcastclass " type="radio" value="${broadcast.MESSAGE_ID}"
                    name="broadcastRadio" class="form-check-input broadcastClass">` + '</td>' +
                    '<td style="display:none;">' + `${broadcast.MESSAGE_ID}` + '</td>' +
                    '<td class="broadcastcs" id="broadcastNAME">' + `${broadcast.MESSAGE_NAME}` + '</td>' +
                    '<td id="broadcastDESC" style="word-break: break-all;min-width: 50px;max-width: 200px;">' + `${broadcast.MESSAGE_DESC}` + '</td>' +
                    '<td>' + `<img src="${broadcast.MESSAGE_IMAGE}"  referrerpolicy="no-referrer" style="height:150px">` + '</td>' +
                    '</tr>'
                )


            }


            $('.broadcastclass').on('change', function () {
                // var couponid = $('input:radio:checked[name="couponRadio"]').val();
                var len = $('input:radio:checked[name="broadcastRadio"]').closest('tr').index();
                //.innerText.split("  ")[0].split('\t');
                //子節點集合
                document.getElementById('broadcastimage').value = "";
                var Texta = $('.broadcastTr')[len].childNodes[2].innerText;
                var Textb = $('.broadcastTr')[len].childNodes[3].innerText;
                var Textc = $('.broadcastTr')[len].childNodes[4].childNodes[0].src


                document.getElementById('broadcastName').value = Texta;
                document.getElementById('broadcastText').value = Textb;
                document.getElementById('broadcastimage').value = Textc;
                document.getElementById('preview-img').src = Textc;
                // console.log(Textc)

            })
            // [$(this).closest("tr").index()]
            //修改
            var flag = false;
            $('#upload').on('change', function () {
                flag = true;
            })
            $('#broadcastSave').on('click', function () {
                $('#myDiv').attr({ "class": "spinner-border", "role": "status" });
                $('#myDiv>label').attr("class", "visually-hidden");
                $('#myDiv>input').attr("class", "visually-hidden");
                $('#myDiv>img[id="preview-img"]').attr("class", "visually-hidden");




                if (document.getElementById('broadcastName').value == "" || document.getElementById('broadcastText').value == "") {
                    Swal.fire('請輸入完整值');
                    $('#myDiv').removeAttr("class", "spinner-border")
                    $('#myDiv').removeAttr("role", "status")

                    $('#myDiv>label').removeAttr("class", "visually-hidden")
                    $('#myDiv>input').removeAttr("class", "visually-hidden")
                    $('#myDiv>img[id="preview-img"]').removeAttr("class", "visually-hidden");
                } else {
                    var broadcastid = $('input:radio:checked[name="broadcastRadio"]').val();
                    var broadcastName = document.getElementById('broadcastName').value;
                    var broadcastText = document.getElementById('broadcastText').value;
                    var broadcastimage = document.getElementById('broadcastimage').value;
                    // console.log(broadcastimage);

                    var completesbmit = false;
                    if (flag) {
                        completesbmit = submit();
                        // console.log("ok")
                    }
                    // console.log(submit())

                    setTimeout(function () {
                        if (completesbmit) {
                            broadcastimage = document.getElementById('broadcastimage').value;

                            // console.log("注意=" + broadcastimage)
                            // console.log(document.getElementById("showImg").src)

                            // console.log(document.getElementById('preview-img').src)
                            // console.log(document.getElementById('broadcastimage').value)

                            var data = {
                                "MESSAGE_DESC": `${broadcastText}`,
                                "MESSAGE_ID": `${broadcastid}`,
                                "MESSAGE_NAME": `${broadcastName}`,
                                "STORE_ID": `${storeId}`,
                                "MESSAGE_IMAGE": `${broadcastimage}`,
                            }

                            $.ajax({
                                url: `https://${postName}/api/${storeId}/message?token=${newToken}`,
                                method: 'PUT',
                                contentType: "application/json",
                                data: JSON.stringify(data),
                                success: function (e) {
                                    flag = false;
                                    $('#myDiv').removeAttr("class", "spinner-border")
                                    $('#myDiv').removeAttr("role", "status")

                                    $('#myDiv>label').removeAttr("class", "visually-hidden")
                                    $('#myDiv>input').removeAttr("class", "visually-hidden")
                                    $('#myDiv>img[id="preview-img"]').removeAttr("class", "visually-hidden")
                                    Swal.fire('修改成功'); console.log(e); broadcastTable();
                                },
                                error: function (err) {
                                    if (err.status == "500") {

                                        Swal.fire('名稱重複')
                                    } if (err.status == "404") {
                                        Swal.fire('請選擇欄位')
                                    }
                                    $('#myDiv').removeAttr("class", "spinner-border")
                                    $('#myDiv').removeAttr("role", "status")

                                    $('#myDiv>label').removeAttr("class", "visually-hidden")
                                    $('#myDiv>input').removeAttr("class", "visually-hidden")
                                    $('#myDiv>img[id="preview-img"]').removeAttr("class", "visually-hidden");
                                }


                            })
                        } else {
                            $('#myDiv').removeAttr("class", "spinner-border");
                            $('#myDiv').removeAttr("role", "status");

                            $('#myDiv>label').removeAttr("class", "visually-hidden");
                            $('#myDiv>input').removeAttr("class", "visually-hidden");
                            $('#myDiv>img[id="preview-img"]').removeAttr("class", "visually-hidden");
                            Swal.fire('修改失敗(檔案太大/網速太慢)')
                        }
                    }, 5000)



                }
            });



            //新增
            $('#broadcastInsert').on('click', function () {
                $('#myDiv').attr({ "class": "spinner-border", "role": "status" });
                $('#myDiv>label').attr("class", "visually-hidden");
                $('#myDiv>input').attr("class", "visually-hidden");
                $('#myDiv>img[id="preview-img"]').attr("class", "visually-hidden");
                if (document.getElementById('broadcastName').value == "" || document.getElementById('broadcastText').value == "") {
                    Swal.fire('請輸入完整值')
                    $('#myDiv').removeAttr("class", "spinner-border");
                    $('#myDiv').removeAttr("role", "status");
                    $('#myDiv>label').removeAttr("class", "visually-hidden");
                    $('#myDiv>input').removeAttr("class", "visually-hidden");
                    $('#myDiv>img[id="preview-img"]').removeAttr("class", "visually-hidden");

                } else {
                    // var couponid = $('input:radio:checked[name="couponRadio"]').val();
                    var broadcastName = document.getElementById('broadcastName').value;
                    var broadcastText = document.getElementById('broadcastText').value;
                    var previewimg = document.getElementById('preview-img').src;
                    var broadcastimage = document.getElementById('broadcastimage').value;
                    // console.log(previewimg)
                    var completesbmit = false;
                    completesbmit = submit();

                    setTimeout(function () {
                        if (completesbmit) {
                            broadcastimage = document.getElementById('broadcastimage').value;


                            var data = {
                                "MESSAGE_DESC": `${broadcastText}`,
                                "MESSAGE_NAME": `${broadcastName}`,
                                "STORE_ID": `${storeId}`,
                                "MESSAGE_IMAGE": `${broadcastimage}`
                            }
                            $.ajax({
                                url: `https://${postName}/api/${storeId}/message?token=${newToken}`,
                                method: 'POST',
                                contentType: "application/json",
                                data: JSON.stringify(data),
                                success: function (e) {

                                    $('#myDiv').removeAttr("class", "spinner-border")
                                    $('#myDiv').removeAttr("role", "status")

                                    $('#myDiv>label').removeAttr("class", "visually-hidden")
                                    $('#myDiv>input').removeAttr("class", "visually-hidden")
                                    $('#myDiv>img[id="preview-img"]').removeAttr("class", "visually-hidden");
                                    Swal.fire('新增成功'); console.log(e); broadcastTable();
                                },
                                error: function (err) {
                                    if (err.status == "500") {
                                        broadcastTable(); Swal.fire('名稱重複')
                                    } else if (err.status == "404") {
                                        broadcastTable(); Swal.fire('新增失敗')
                                    }
                                    $('#myDiv').removeAttr("class", "spinner-border");
                                    $('#myDiv').removeAttr("role", "status");
                                    $('#myDiv>label').removeAttr("class", "visually-hidden");
                                    $('#myDiv>input').removeAttr("class", "visually-hidden");
                                    $('#myDiv>img[id="preview-img"]').removeAttr("class", "visually-hidden");
                                }


                            })
                        } else { Swal.fire('新增失敗(檔案太大/網速太慢)') }
                    }, 5000)



                }






            });
            //刪除
            $('#broadcastDelete').on('click', function () {
                var broadcastid = $('input:radio:checked[name="broadcastRadio"]').val();
                Swal.fire({
                    title: "是否要刪除",
                    
                    icon: 'warning',
                    showCancelButton: true
                }).then(function(result){
                    if(result.value){
                        $.ajax({
                            url: `https://${postName}/api/${storeId}/message?token=${newToken}&messageid=${broadcastid}`,
                            method: 'DELETE',
                            contentType: "application/json",
                            success: function (e) { Swal.fire('刪除成功'); console.log(e); broadcastTable(); },
                            error: function (err) {
                                if (err.status == "400") {
                                    Swal.fire('請選擇欄位')
                                } else if (err.status == "404") {
                                    Swal.fire('請選擇欄位')
                                }
        
                            }
        
        
                        })   
                    }
                    
                })
                
            


                });

        },
        error: function () {
            console.log("fail")
        }
    })
}




//營養表格 菜單印出營養素 重點輸出  
function ingredientsTable() {
    $('#ingredientsTable').bootstrapTable({
        url: `https://${postName}/api/${storeId}/ingredients?token=${newToken}`,         //請求後臺的 URL（*）
        striped: false,
        method: 'get',                      //請求方式（*）
        // data: data,                      //當不使用上面的後臺請求時，使用data來接收資料
        toolbar: '#toolbar',                //工具按鈕用哪個容器
        showFullscreen: true,                    //全平按鈕
        showExport: true,               //是否顯示匯出
        showColumns: true,
        silentSort: true,
        showPaginationSwitch: true,
        showButtonIcons: false,            //沒用
        showButtonText: true,                //有用
        striped: false,                      //是否顯示行間隔色
        cache: false,
        buttonsPrefix: 'btn-sm btn',                       //是否使用快取，預設為 true，所以一般情況下需要設定一下這個屬性（*）
        pagination: true,                   //是否顯示分頁（*）
        sortable: true,                    //是否啟用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "client",           //分頁方式：client 使用者端分頁，server 伺服器端分頁（*）
        pageNumber: 1,                       //初始化載入第一頁，預設第一頁
        pageSize: 10,                        //每頁的記錄行數（*）
        pageList: [10, 20],        //可供選擇的每頁的行數（*）
        search: true,                       //是否顯示錶格搜尋，此搜尋是使用者端搜尋，不會進伺服器端，所以個人感覺意義不大
        strictSearch: false,                 //啟用嚴格搜尋。禁用比較檢查。                  //是否顯示所有的列
        showRefresh: true,                  //是否顯示重新整理按鈕
        minimumCountColumns: 2,             //最少允許的列數
        clickToSelect: true,                //是否啟用點選選中行
        // height: 400,                        //行高，如果沒有設定 height 屬性，表格自動根據記錄條數覺得表格高度
        uniqueId: "ID",                     //每一行的唯一標識，一般為主鍵列
        showToggle: false,                    //是否顯示詳細檢視和列表檢視的切換按鈕
        cardView: false,                    //是否顯示詳細檢視
        detailView: false,                  //是否顯示父子表

        // exportDataType: "selected",            //basic', 'all', 'selected'.
        exportTypes: ['json', 'xml', 'csv', 'txt', 'sql', 'excel',],
        exportDataType: $(this).val(),

        columns: [{
            field: 'ingredientname', title: '俗名'       //我們取json中id的值，並將表頭title設定為ID
        }, {
            field: 'ingredientcategory', title: '食材分類'         //我們取 json 中 username 的值，並將表頭 title 設定為使用者名稱
        }, {
            field: 'ingredientdesc', title: '食材描述'                //我們取 json 中 sex 的值，並將表頭 title 設定為性別
        }, {
            field: 'calorie', title: '熱量', formatter: function (value, row, index) {
                // console.log(value);
                // console.log(row);
                // console.log(index);

                return value + '(kcal)';
            }
        }, {
            field: 'carb', title: '碳水化合物', formatter: function (value, row, index) {
                // console.log(value);
                // console.log(row);
                // console.log(index);

                return value + '(g)';
            }
        }, {
            field: 'fat', title: '脂肪', formatter: function (value, row, index) {
                // console.log(value);
                // console.log(row);
                // console.log(index);

                return value + '(g)';
            }
        }, {
            field: 'protein', title: '蛋白質', formatter: function (value, row, index) {
                // console.log(value);
                // console.log(row);
                // console.log(index);

                return value + '(g)';
            }
        },

        ],
        // responseHandler: function (res) {
        //     return res.data      //在載入遠端資料之前，處理響應資料格式.
        //     // 我們取的值在data欄位中，所以需要先進行處理，這樣才能獲取我們想要的結果
        // }
    });

}





/*utils*/


//隨機顏色包

var colorRandom = function (j, x) {
    var color = []
    for (var i = 0; i < j; i++) {

        color.push(`rgba(${(parseInt(Math.random() * 256))},${(parseInt(Math.random() * 256))},${(parseInt(Math.random() * 256))},${x})`);

    }

    return color;
}
//處理時間
function formatDate(newDate) {
    let date = new Date(newDate);
    return date.getFullYear() + "年" + (date.getMonth() + 1) + "月" +
        date.getDate() + "日" + date.getHours() + "時" + date.getMinutes() +
        "分";
}

function formatDatenotime(newDate) {
    let date = new Date(newDate);
    return date.getFullYear() + "年" + (date.getMonth() + 1) + "月" + date.getDate() + '日'
}

//搜尋
$("#nutrientContentsearch").keyup(function () {
    var value = this.value.toLowerCase().trim();

    $("#nutrientContenttable tr").each(function (index) {
        if (!index) return;
        $(this).find("td").each(function () {
            var id = $(this).text().toLowerCase().trim();
            var unfound = (id.indexOf(value) == -1);
            $(this).closest('tr').toggle(!unfound);
            return unfound;
        });
    });
});
var eleval = document.getElementById("searchMenu");
$("#menuBtn").on("click", function () {
    // console.log(eleval.value);
    //將輸入值轉為小寫去除前後空格
    var keyword = eleval.value.toLowerCase().trim();

    $("#tableMenu tr").each(function (index) {
        if (!index) return;
        $(this).find("td").each(function () {
            var id = $(this).text().toLowerCase().trim();
            var unfound = (id.indexOf(keyword) == -1);
            //.closest()由tr開始搜尋/tr結束
            //.toggle()=>hide()、show切換
            $(this).closest('tr').toggle(!unfound);
            return unfound;
        });
    });
});

var eleval2 = document.getElementById("searchMember");
$("#memberBtn").on("click", function () {
    // console.log(eleval2.value);
    //將輸入值轉為小寫去除前後空格
    var keyword = eleval2.value.toLowerCase().trim();

    $("#tableMember tr").each(function (index) {
        if (!index) return;
        $(this).find("td").each(function () {
            var id = $(this).text().toLowerCase().trim();
            var unfound = (id.indexOf(keyword) == -1);
            //.closest()由tr開始搜尋/tr結束
            //.toggle()=>hide()、show切換
            $(this).closest('tr').toggle(!unfound);
            return unfound;
        });
    });
});

var eleval1 = document.getElementById("searchOrder");
$("#orderBtn").on("click", function () {
    // console.log(eleval1.value);
    //將輸入值轉為小寫去除前後空格
    var keyword = eleval1.value.toLowerCase().trim();

    $("#orderDiv tr").each(function (index) {
        if (!index) return;
        $(this).find("td").each(function () {
            var id = $(this).text().toLowerCase().trim();
            var unfound = (id.indexOf(keyword) == -1);
            //.closest()由tr開始搜尋/tr結束
            //.toggle()=>hide()、show切換
            $(this).closest('tr').toggle(!unfound);
            return unfound;
        });
    });
});


/*找類別*/
function mealIngredients() {
    if (document.querySelectorAll('#mealIngredients option').length == 0) {

        $.ajax({
            url: `https://${postName}/api/${storeId}/ingredients?token=${newToken}`,
            method: 'GET',
            success: function (res, status) {

            },
            error: function () { }
        }).done(function (index) {
            // console.log("mealIngredients ok")
            for (var meal of index) {

                $('#mealIngredients').append(
                    `<option value = "${meal.ingredientname}">`
                )


            }
        })
    }
}

// 輸出圖片
const token = '22e7ba553d6239bc5de2bf1520c9187b611a760d'; // 填入 token

// const actionBtn = document.getElementById("myBtn"); // 送出按鈕
const uploadInput = document.getElementById("upload"); // upload上傳物件的地方
const showImg = document.getElementById("showImg");
const url = document.getElementById("url");

// 建立file class物件
class file {
    // 建構式
    constructor() {
        this.uploadFile = null;
        this.fileName = null;
        this.fileSize = null;
        this.fileThumbnail = null;
        this.fileTitle = null;
        this.fileDesc = null;
    }

    // setter
    setuploadFile(uploadFile) {
        this.uploadFile = uploadFile;
    }
    setfileName(fileName) {
        this.fileName = fileName;
    }
    setfileSize(fileSize) {
        this.fileSize = fileSize;
    }
    setfileThumbnail(fileThumbnail) {
        this.fileThumbnail = fileThumbnail;
    }
    setfileTitle(fileTitle) {
        this.fileTitle = fileTitle;
    }
    setfileDesc(fileDesc) {
        this.fileDesc = fileDesc;
    }

    // getter
    getuploadFile() {
        return this.uploadFile;
    }
    getfileName() {
        return this.fileName;
    }
    getfileSize() {
        return this.fileSize;
    }
    getfileThumbnail() {
        return this.fileThumbnail;
    }
    getfileTitle() {
        return this.fileTitle;
    }
    getfileDesc() {
        return this.fileDesc;
    }
}

function addImg(imgURL) {
    // showImg.src = imgURL;
    // console.log(imgURL)
    imgURL;

    // url.innerText = '圖檔網址：' + imgURL;
}

// 上傳的function
async function submit() {
    // api
    let settings = {
        async: true,
        crossDomain: true,
        processData: false,
        contentType: false,
        type: 'POST',
        url: 'https://api.imgur.com/3/image',
        headers: {
            Authorization: 'Bearer ' + token
        },
        mimeType: 'multipart/form-data'
    };

    // data傳遞
    let form = new FormData();
    form.append('image', inputFile.getuploadFile());
    form.append('title', inputFile.getfileTitle());
    form.append('description', inputFile.getfileDesc());

    settings.data = form;

    // 傳遞資料
    await $.ajax(settings).done(function (res) {
        const imgURL = JSON.parse(res).data.link;
        document.getElementById('broadcastimage').value = imgURL;
        addImg(imgURL);
        // console.log(document.getElementById('broadcastimage').value);
        // console.log(JSON.parse(res)); // 可以看見上傳成功後回傳的URL
        // console.log(JSON.parse(res).data.link)
        // Swal.fire('上傳完成');

    });
    document.getElementById("preview-img").src = "";
    return true;
}

// 建議物件
const inputFile = new file();

// input:file的監聽器
uploadInput.addEventListener("change", (e) => {
    inputFile.setuploadFile(uploadInput.files[0]); // input type="file" 的值
    inputFile.setfileName(inputFile.uploadFile.name); // input的圖檔名稱
    inputFile.setfileSize(Math.floor(inputFile.uploadFile.size * 0.001) + 'KB'); // input的圖片大小
    inputFile.setfileThumbnail(window.URL.createObjectURL(inputFile.uploadFile)); // input的圖片縮圖
    inputFile.setfileTitle(inputFile.uploadFile.name); // 預設 input 的圖檔名稱為圖片上傳時的圖片標題
    inputFile.setfileDesc(inputFile.uploadFile.name); // 圖片描述
    var file = uploadInput.files[0], imageType = /^image\//, reader = '';
    // 檔案是否為圖片
    if (!imageType.test(file.type)) {
        Swal.fire("請選擇圖片檔案！");
        return;
    }
    // 判斷是否支援FileReader  // IE9及以下不支援FileReader
    if (window.FileReader) {
        reader = new FileReader();
    } else {
        Swal.fire("您的瀏覽器不支援圖片預覽功能，如需該功能請升級您的瀏覽器！");
        return;
    }
    // 讀取完成    
    reader.onload = function (event) {
        // 獲取圖片DOM

        var img = document.getElementById("preview-img");
        // 圖片路徑設定為讀取的圖片    
        // console.log(img.src)
        // document.getElementById('broadcastimage').value=img.src;
        img.src = event.target.result;
        // console.log(img)
        // var img = document.getElementById("preview-img");

    };
    reader.readAsDataURL(file);
    document.getElementById("showImg").src = ""

});

// Botton的監聽器
// $('#actionBtn').on("click", () => {
//     submit();
// });




/*utils*/
/*畫分析圖表*/
// const labels = [
//     'aaaa',
//     'February',
//     'March',
//     'April',
//     'May',
//     'June',
// ];

// const data = {
//     labels: labels,
//     datasets: [{
//         label: 'My First dataset',
//         backgroundColor: 'rgb(255, 99, 132)',
//         borderColor: 'rgb(255, 99, 132)',
//         data: [100, 10, 5, 2, 20, 30, 45],
//     }]
// };

// const config = {
//     type: 'line',
//     data: data,
//     options: {}
// };
// const Chart1 = new Chart(
//     document.getElementById('Chart1'),
//     config
// );
// const Chart2 = new Chart(
//     document.getElementById('Chart2'),
//     config
// );
// let chart3 = new Chart(document.getElementById('Chart3'), {
//     type: 'pie', //圖表類型
//     data: {
//         labels: [ //圓餅圖的每一塊，分別叫做什麼名字
//             '台北', //第一塊名字
//             '台中', //第二塊名字
//             '高雄'
//         ],
//         datasets: [{
//             label: '三都人口', //這些資料都是在講什麼，也就是data 300 500 100是什麼
//             data: [57.69, 19.23, 23.08], //每一塊的資料分別是什麼，台北：300、台中：50..
//             backgroundColor: [ //設定每一塊的顏色，可以用rgba來寫
//                 'rgb(255, 99, 132)',
//                 'rgb(54, 162, 235)',
//                 'rgb(255, 205, 86)'
//             ],
//             hoverOffset: 4
//         }]
//     }, //設定圖表資料
//     options: {} //圖表的一些其他設定，像是hover時外匡加粗
// })

// let chart = new Chart(document.getElementById('Chart4'), {
//     type: 'pie', //圖表類型
//     data: {
//         labels: [ //圓餅圖的每一塊，分別叫做什麼名字
//             '台北', //第一塊名字
//             '台中', //第二塊名字
//             '高雄'
//         ],
//         datasets: [{
//             label: '三都人口', //這些資料都是在講什麼，也就是data 300 500 100是什麼
//             data: [300, 50, 100], //每一塊的資料分別是什麼，台北：300、台中：50..
//             backgroundColor: [ //設定每一塊的顏色，可以用rgba來寫
//                 'rgb(255, 99, 132)',
//                 'rgb(54, 162, 235)',
//                 'rgb(255, 205, 86)'
//             ],
//             hoverOffset: 4
//         }]
//     }, //設定圖表資料
//     options: {} //圖表的一些其他設定，像是hover時外匡加粗
// })
/*畫分析圖表*/


/*---------------嘉彬------------*/
