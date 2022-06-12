// import { initializeApp } from "https://www.gstatic.com/firebasejs/9.6.11/firebase-app.js";
import {
    getAuth,
    createUserWithEmailAndPassword,
    sendEmailVerification,
    signOut
} from "https://www.gstatic.com/firebasejs/9.6.11/firebase-auth.js";

import { myFirebase } from "./firebase.js";

// // Initialize Firebase
const myLoginFirebase = new myFirebase();
myLoginFirebase.initFirebase();
// const firebaseConfig = {
//     apiKey: "AIzaSyDmqTvgYkvxv42UFqnHl8ejXg627WUow1k",
//     authDomain: "login-test-18ab1.firebaseapp.com",
//     projectId: "login-test-18ab1",
//     storageBucket: "login-test-18ab1.appspot.com",
//     messagingSenderId: "521236544499",
//     appId: "1:521236544499:web:be27cbcb82b1d84f09f086",
//     measurementId: "G-4QG29VS5S8"
// };

// Initialize Firebase
// initializeApp(firebaseConfig);

setSignBntStatus();

let name = document.getElementById("name");
let gender = document.getElementById("gender");
let birth = document.getElementById("birth");
let phone = document.getElementById("phone");
let address = document.getElementById("address");
let email = document.getElementById("email");
let passwd = document.getElementById("password");

let submitBnt = document.getElementById("submitBnt");
let form = document.querySelector('.needs-validation');

function userRegister(e) {
    e.preventDefault();

    const userMail = getUserMail();
    const userPassword = getUserPassword();
    const auth = getAuth();
    
    createUserWithEmailAndPassword(auth, userMail, userPassword)
        .then((userCredential) => {
            if (userCredential.user.emailVerified === false) {
                addUserInfoToSQL(auth.currentUser.uid);
                sendEmailVerification(auth.currentUser)
                .then(() => {
                })
                .catch((error) => {
                    const errorCode = error.code;
                    const errorMessage = error.message;
                    console.error(`${errorCode}-${errorMessage}`);
                });
            }
        })
        .catch((error) => {
            const errorCode = error.code;
            const errorMessage = error.message;
            console.error(`${errorCode}-${errorMessage}`);
        });
}

function welcomeToUse(userName) {
    Swal.fire({
        icon: 'success',
        title: `Hi, ${userName} 歡迎使用，已送出信箱驗證信，請至信箱收取，並點擊驗證`,
        showConfirmButton: false,
        timer: 1500
    });
}

function alreadyLogin() {
    Swal.fire({
        icon: 'success',
        title: `Hi, 已經登入囉！`,
        showConfirmButton: false,
        timer: 2500
    });
}

function addUserInfoToSQL(userId) {
	let USER_ID = userId;
    let USER_NAME = getUserName();
    let USER_GENDER = getUserGender();
    let USER_BIRTH = getUserBirth();
    let USER_PHONE = getUserPhone();
    let USER_ADDRESS = getUserAddress();
    let USER_MAIL = getUserMail();
    let CREATE_ID = userId;
    
    const userInfo = {USER_ID, USER_NAME, USER_GENDER, USER_BIRTH, USER_PHONE, USER_ADDRESS, USER_MAIL, CREATE_ID};
    fetch("http://localhost:8080/api/users", {
        method: "POST",
        headers: {"content-Type":"application/json"},
        body:JSON.stringify(userInfo)
    }).then(() => {
        console.log("Add New User.")
    })
}


function getUserName() {
    return name.value;
}
function getUserGender() {
    return gender.value;
}
function getUserBirth() {
    return birth.value;
}
function getUserPhone() {
    return phone.value;
}
function getUserAddress() {
    return address.value;
}
function getUserMail() {
    return email.value;
}
function getUserPassword() {
    return passwd.value;
}

function welcomeToUseCheck(userName) {
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

function setSignBntStatus() {
    const auth = getAuth();
    auth.onAuthStateChanged((user) =>{
        if (user) {
            welcomeToUseCheck(JSON.parse(localStorage.getItem('userinfo')).USER_NAME);
            setInterval(() => {
                window.location.href = "index.html"
            }, 1500); // 等待2秒導向回到登入頁面
        }
    });
}

submitBnt.addEventListener("click", (e) => {
    if (!form.checkValidity()) {
        e.preventDefault();
        e.stopPropagation();
    } else {
        userRegister(e);
        welcomeToUse(getUserName());
        setInterval(() => {
            window.location.href = "login.html"
        }, 2000); // 等待2秒導向回到登入頁面
    }
    form.classList.add('was-validated')
});