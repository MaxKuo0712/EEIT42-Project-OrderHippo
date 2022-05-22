import { initializeApp } from "https://www.gstatic.com/firebasejs/9.6.11/firebase-app.js";
import {
    getAuth,
    signOut,
    signInWithEmailAndPassword,
    GoogleAuthProvider,
    signInWithPopup,
    // FacebookAuthProvider,
    sendPasswordResetEmail
} from "https://www.gstatic.com/firebasejs/9.6.11/firebase-auth.js";

//import { myFirebase } from "./firebase.mjs";

// // Initialize Firebase
//const myLoginFirebase = new myFirebase();
//myLoginFirebase.initFirebase();
const firebaseConfig = {
    apiKey: "AIzaSyDmqTvgYkvxv42UFqnHl8ejXg627WUow1k",
    authDomain: "login-test-18ab1.firebaseapp.com",
    projectId: "login-test-18ab1",
    storageBucket: "login-test-18ab1.appspot.com",
    messagingSenderId: "521236544499",
    appId: "1:521236544499:web:be27cbcb82b1d84f09f086",
    measurementId: "G-4QG29VS5S8",
};

// Initialize Firebase
initializeApp(firebaseConfig);

let email = document.getElementById("email");
let passwd = document.getElementById("password");

let mailLoginBnt = document.getElementById("loginBnt");
let googleLoginBnt = document.getElementById("googleLoginBnt");
let facebookLoginBnt = document.getElementById("facebookLoginBnt");
let resetPasswd = document.getElementById("resetPasswd");

let modalDisplay = document.getElementById("modal-content");

// Get the modal
let modal = document.getElementById("myModal");

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
            let REVISE_ID = auth.currentUser.uid;
            let USER_ID = auth.currentUser.uid;
            const userLoginInfo = {LAST_LOGININ_TIME};
            if (userMailVerified === false) {
                Swal.fire({
                    icon: 'warning',
                    title: "信箱未驗證，請先到信箱收取驗證信件",
                    showConfirmButton: false,
                    timer: 2000
                });
            } else {
                let REVISE_ID = auth.currentUser.uid;
                let USER_ID = auth.currentUser.uid;
                let LAST_LOGININ_TIME = new Date();
                const userLoginInfo = {LAST_LOGININ_TIME};
                fetch(`http://localhost:8080/api/users/${REVISE_ID}`, {
                    method: "PUT",
                    headers: {"content-Type":"application/json"},
                    body:JSON.stringify(userLoginInfo)
                }).then((e) => {
                    console.log(e.status)
                    welcomeToUse(userName);
                    setInterval(() => {
                        //window.location.href = "homepage"
                    }, 2000); // 等待2秒導向回到登入頁面
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
            welcomeToUse(userName);
            console.log(auth.currentUser);
            setInterval(() => {
                window.location.href = "homepage"
            }, 2000); // 等待2秒導向回到上一頁(登入頁面)
        }).catch((error) => {
            const errorCode = error.code;
            const errorMessage = error.message;
            const email = error.email;
            const credential = GoogleAuthProvider.credentialFromError(error);
        });
}

// function userFaceBookLogin(e) {
//     e.preventDefault();

//     const auth = getAuth();
//     const providerFaceBook = new FacebookAuthProvider();

//     signInWithPopup(auth, providerFaceBook)
//         .then((result) => {
//             // The signed-in user info.
//             const user = result.user;
//             // This gives you a Facebook Access Token. You can use it to access the Facebook API.
//             const credential = FacebookAuthProvider.credentialFromResult(result);
//             const accessToken = credential.accessToken;

//             modal.style.display = "block";
//             setInterval(() => {
//                 window.location.href = "homepage.html"
//             }, 2000); // 等待2秒導向回到登入頁面
//         })
//         .catch((error) => {
//             const errorCode = error.code;
//             const errorMessage = error.message;
//             const email = error.email;
//             const credential = FacebookAuthProvider.credentialFromError(error);
//         });
// }

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
    if(userName === null) {
        userName = "";
    }
    Swal.fire({
        icon: 'success',
        title: `Hi, ${userName} 歡迎使用`,
        showConfirmButton: false,
        timer: 1500
    });
}

mailLoginBnt.addEventListener("click", (e) => {
    userMailLogin(e);
});

googleLoginBnt.addEventListener("click", (e) => {
    userGoogleLogin(e);
});

// facebookLoginBnt.addEventListener("click", (e) => {
//     userFaceBookLogin(e);
// });

resetPasswd.addEventListener("click", (e) => {
    userChangePasswd(e);
});

