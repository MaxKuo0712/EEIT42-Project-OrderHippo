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

let signBnt = document.getElementById("signBnt");

// setSignBntStatus
setSignBntStatus();

// 登出
function storeSignOut() {
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
    auth.onAuthStateChanged((store) =>{
        if (!store) { // 有登入
            window.location.href = "login.html";
        }
    });
}

signBnt.addEventListener("click", (e) => {
    storeSignOut();
    localStorage.removeItem("storeinfo");
    localStorage.removeItem("storeToken");
    window.location.href = "login.html";
});
