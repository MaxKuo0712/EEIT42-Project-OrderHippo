import { initializeApp } from "https://www.gstatic.com/firebasejs/9.6.11/firebase-app.js";

export class myFirebase {

    initFirebase() {
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
    };
}