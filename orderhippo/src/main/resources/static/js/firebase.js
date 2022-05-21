import { initializeApp } from "https://www.gstatic.com/firebasejs/9.6.11/firebase-app.js";

export class myFirebase {

    initFirebase() {
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
    };
}