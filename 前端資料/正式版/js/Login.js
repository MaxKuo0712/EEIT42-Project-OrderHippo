// let postName = 'localhost:8080';
let postName = 'eeit42-order-hippo.herokuapp.com';
// import { initializeApp } from "https://www.gstatic.com/firebasejs/9.6.11/firebase-app.js";
import {
    getAuth,
    signOut,
    signInWithEmailAndPassword,
    GoogleAuthProvider,
    signInWithPopup,
    sendPasswordResetEmail
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
//     measurementId: "G-4QG29VS5S8",
// };

// Initialize Firebase
// initializeApp(firebaseConfig);

setSignBntStatus();

let email = document.getElementById("email");
let passwd = document.getElementById("password");
let mailLoginBnt = document.getElementById("loginBnt");
let googleLoginBnt = document.getElementById("googleLoginBnt");
let resetPasswd = document.getElementById("resetPasswd");

function userMailLogin(e) {
    e.preventDefault();

    const userMail = getUserMail();
    const userPassword = getUserPassword();
    const auth = getAuth();

    signInWithEmailAndPassword(auth, userMail, userPassword)
        .then((userCredential) => {
            // Signed in 
            const user = userCredential.user;
            const userName = user.displayName;
            const userMailVerified = auth.currentUser.emailVerified;

            if (userMailVerified === false) {
                Swal.fire({
                    icon: 'warning',
                    title: "信箱未驗證，請先到信箱收取驗證信件",
                    showConfirmButton: false,
                    timer: 2000
                });
            } else {
                let USER_ID = auth.currentUser.uid;

                fetch(`https://${postName}/api/getToken/${USER_ID}`, {
                    method: "GET"
                }).then(res => {
                    return res.text();
                }).then(resultToken => {
                    localStorage.setItem('userToken', resultToken);
                    fetch(`https://${postName}/api/${USER_ID}/users?userid=${USER_ID}&token=${resultToken}`, {
                        method: "GET"
                    }).then(res => {
                        return res.json();
                    }).then(result => {
                        let queryResult = result[0];
                        queryResult.LAST_LOGININ_TIME = new Date();
                        addlocalStorage(queryResult);

                        fetch(`https://${postName}/api/${USER_ID}/users?token=${resultToken}`, {
                            method: "PUT",
                            headers: { "content-Type": "application/json" },
                            body: JSON.stringify(queryResult)
                        }).then((e) => {
                            // localStorage.setItem('loginStatus', 'ok');
                            welcomeToUse(JSON.parse(localStorage.getItem('userinfo')).USER_NAME);
                            setInterval(() => {
                                window.location.href = "index.html"
                            }, 800); // 等待2秒導向
                        })
                    })
                })
            }
        })
        .catch((error) => {
            const errorCode = error.code;
            const errorMessage = error.message;
            console.error(`${errorCode}-${errorMessage}`);
            errorCodeDisplay(errorCode);
        });
}

function userGoogleLogin(e) {
    e.preventDefault();

    const auth = getAuth();
    const providerGoogle = new GoogleAuthProvider();

    signInWithPopup(auth, providerGoogle)
        .then((result) => {
            const credential = GoogleAuthProvider.credentialFromResult(result);
            const token = credential.accessToken;
            const user = result.user;
            const userName = user.displayName;
            // localStorage.setItem('loginStatus', 'ok');
            addGoogleUserInfo(result);
        }).catch((error) => {
            const errorCode = error.code;
            const errorMessage = error.message;
            const email = error.email;
            const credential = GoogleAuthProvider.credentialFromError(error);
        });
}

function userChangePasswd(e) {
    e.preventDefault();
    const userMail = getUserMail();
    const auth = getAuth();
    sendPasswordResetEmail(auth, userMail)
        .then(() => {
            sendUserPasswdAlert();
        })
        .catch((error) => {
            const errorCode = error.code;
            const errorMessage = error.message;
            console.error(`${errorCode}-${errorMessage}`);
        });
}

function errorCodeDisplay(errorCode) {
    let errorText = "";
    if (errorCode === "auth/wrong-password") {
        errorText = "密碼輸入錯誤";
        setErrorCodeDisplay(errorText);
    } else if (errorCode === "auth/user-not-found") {
        errorText = "帳號不存在";
        setErrorCodeDisplay(errorText);
    }
}

function getUserMail() {
    return email.value;
}
function getUserPassword() {
    return passwd.value;
}

function sendUserPasswdAlert() {
    Swal.fire({
        icon: 'success',
        title: "已寄送更改密碼連結到您的信箱",
        showConfirmButton: false,
        timer: 1500
    });
}

function setErrorCodeDisplay(errorText) {
    Swal.fire({
        icon: 'error',
        title: errorText,
        showConfirmButton: false,
        timer: 1500
    });
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

function addGoogleUserInfo(result) {

    const userId = result.user.uid;

    fetch(`https://${postName}/api/getToken/${userId}`, {
        method: "GET"
    }).then(res => {
        return res.text();
    }).then(resultToken => {
        if (resultToken === '使用者不存在 / 權限不足') {
            const USER_ID = userId;
            const USER_NAME = "";
            const USER_GENDER = "M";
            const USER_PHONE = "";
            const USER_MAIL = result.user.email;
            const USER_BIRTH = new Date("2021-01-01");
            const USER_ADDRESS = "";
            const CREATE_ID = userId;

            const userInfo = { USER_ID, USER_NAME, USER_GENDER, USER_BIRTH, USER_PHONE, USER_ADDRESS, USER_MAIL, CREATE_ID };
            console.log(userInfo);
            fetch(`https://${postName}/api/users`, {
                method: "POST",
                headers: { "content-Type": "application/json" },
                body: JSON.stringify(userInfo)
            }).then(() => {
                addlocalStorage(userInfo);
                setInterval(() => {
                    window.location.href = "changeUserInfo.html"
                }, 1000); // 等待1秒導向
            })
        } else {
            localStorage.setItem('userToken', resultToken);
            fetch(`https://${postName}/api/${userId}/users?userid=${userId}&token=${resultToken}`, {
                method: "GET"
            }).then(res => {
                return res.json();
            }).then(queryResult => {
                addlocalStorage(queryResult[0]);
                welcomeToUse(JSON.parse(localStorage.getItem("userinfo")).USER_NAME);
                setInterval(() => {
                    window.location.href = "index.html"
                }, 2000); // 等待2秒導向
            })
        }
    })
}

function addlocalStorage(userInfo) {
    localStorage.removeItem('userinfo');
    localStorage.setItem('userinfo', JSON.stringify(userInfo));
}

function setSignBntStatus() {
    if (localStorage.getItem('userinfo')) {
        setInterval(() => {
            window.location.href = "index.html";
        }, 1000); // 等待2秒導向回到登入頁面
    }

    // const auth = getAuth();
    // auth.onAuthStateChanged((user) =>{
    //     if (user) {
    //         // welcomeToUse(JSON.parse(localStorage.getItem('userinfo')).USER_NAME);
    //         setInterval(() => {
    //             window.location.href = "index.html";
    //         }, 1000); // 等待2秒導向回到登入頁面
    //     }
    // });
    // if (localStorage.getItem('userToken')) {
    //     alreadyLogin();
    //     setInterval(() => {
    //         window.location.href = "index.html"
    //     }, 2500); // 等待2秒導向回到登入頁面
    // }
}

mailLoginBnt.addEventListener("click", (e) => {
    userMailLogin(e);
});

googleLoginBnt.addEventListener("click", (e) => {
    userGoogleLogin(e);
});

resetPasswd.addEventListener("click", (e) => {
    userChangePasswd(e);
});

