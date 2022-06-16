// let postName = 'localhost:8080';
let postName = 'eeit42-order-hippo.herokuapp.com';

import {
    getAuth,
  } from "https://www.gstatic.com/firebasejs/9.6.11/firebase-auth.js";
  
  import { myFirebase } from "./firebase.js";
  
  // // Initialize Firebase
  const myLoginFirebase = new myFirebase();
  myLoginFirebase.initFirebase();

const formatDate = (date) => {
    let d = new Date(date);
    let month = (d.getMonth() + 1).toString().padStart(2, '0');
    let day = d.getDate().toString().padStart(2, '0');
    let year = d.getFullYear();
    return [year, month, day].join('/');
}

setSignBntStatus();

function setSignBntStatus() {
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
        setUserInfo();
      }
    });
  }

let submitBnt = document.getElementById("submitBnt");

submitBnt.addEventListener("click", (e) => {
    e.preventDefault();

    let USER_ID = JSON.parse(localStorage.getItem('userinfo')).USER_ID;
    let USER_NAME = document.getElementById("name").value;
    let USER_GENDER = document.getElementById("gender").value;
    let USER_PHONE = document.getElementById("phone").value;
    let USER_MAIL = document.getElementById("email").value;
    let USER_BIRTH = Date.parse(document.getElementById("birth").value);
    let USER_AGE = document.getElementById("age").value;
    let USER_ADDRESS = document.getElementById("address").value;
    let REVISE_ID = USER_ID;

    const target = {USER_ID, USER_NAME, USER_GENDER, USER_BIRTH, USER_AGE, USER_PHONE, USER_ADDRESS, USER_MAIL};

    addLocalstorage(target);

    fetch(`https://${postName}/api/getToken/${USER_ID}`, {
        method: "GET"
    }).then(res => {
        return res.text();
    }).then(resultToken => {
        localStorage.setItem('userToken', resultToken);

        fetch(`https://${postName}/api/${USER_ID}/users?token=${resultToken}`, {
            method: "PUT",
            headers: { "content-Type": "application/json" },
            body: JSON.stringify(target)
        }).then((e) => {
            welcomeToUse(USER_NAME);
            setInterval(() => {
                window.location.href = "index.html"
            }, 2000); // 等待2秒導向
        })
    })
});


function setUserInfo() {

    const localUserInfo = JSON.parse(localStorage.getItem('userinfo'));

    let USER_ID = localUserInfo.USER_ID;
    let USER_NAME = localUserInfo.USER_NAME;
    let USER_GENDER = localUserInfo.USER_GENDER;
    let USER_PHONE = localUserInfo.USER_PHONE;
    let USER_MAIL = localUserInfo.USER_MAIL;
    let USER_BIRTH = localUserInfo.USER_BIRTH;
    let USER_AGE = localUserInfo.USER_AGE;
    let USER_ADDRESS = localUserInfo.USER_ADDRESS;

    document.getElementById("name").value = USER_NAME;
    document.getElementById("gender").value = USER_GENDER;
    document.getElementById("phone").value = USER_PHONE;
    document.getElementById("email").value = USER_MAIL;
    document.getElementById("birth").value = "";
    document.getElementById("age").value = "";
    document.getElementById("address").value = USER_ADDRESS;
}

function addLocalstorage(userInfo) {
    localStorage.removeItem('userinfo');
    localStorage.setItem('userinfo', JSON.stringify(userInfo));
}


function welcomeToUse(userName) {
    if (userName === null) {
        userName = "";
    }
    Swal.fire({
        icon: 'success',
        title: `Hi, ${userName} 歡迎使用`,
        showConfirmButton: false,
        timer: 1500
    });
}