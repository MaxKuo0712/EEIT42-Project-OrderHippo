// import { initializeApp } from "https://www.gstatic.com/firebasejs/9.6.11/firebase-app.js";
import {
    getAuth,
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
//     measurementId: "G-4QG29VS5S8",
// };

// Initialize Firebase
// initializeApp(firebaseConfig);

let signBnt = document.getElementById("signBnt");

// setSignBntStatus
setSignBntStatus();

function userSignOut() {
    const auth = getAuth();
    signOut(auth).then(() => {
        signOutOK();
    }).catch((error) => {
        console.error(error);
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

function setSignBntStatus() {
    const auth = getAuth();
    const signBnt = document.getElementById("signBnt");
    auth.onAuthStateChanged((user) =>{
        if (user) {
            // signBnt.innerText = "Sign out";
            // window.location.href = "homepage.html";
        } else {
            // signBnt.innerText = "Sign in";
            // window.location.href = "homepage.html";
        }
    });
}

signBnt.addEventListener("click", (e) => {
    // let signText = signBnt.innerText;
    // if (signText === "Sign in") {
    //     window.location.href = "login.html";
    // } else if (signText === "Sign out") {

    // }
    userSignOut();
    localStorage.removeItem("storeinfo");
    localStorage.removeItem("storeToken");
    window.location.href = "login.html";
});
