export class useWebSocket {
    websocket;

    constructor() {
        this.websocket = null;
    }
    
    connWebSocket(userInfo) {
        if ('WebSocket' in window) {
            this.websocket = new WebSocket(`ws://localhost:8080/websocket/${userInfo}`);
        } else {
            console.log("WebSocket連線失敗");
        }
    }

    closeWebSocket() {
        this.websocket.close();
    }

    sednMessage(sendMsg, orderID, paymentID) {
        // const token = localStorage.getItem("token");
        // const userID = localStorage.getItem("userinfo").USER_ID;
        // const token = "$2a$10$I9C00diMOlpiHLsgv.taueZ2wrJ1Ot8AfgFn05vMzUWld9EFSYn7q";
        // const userID = "iGImodKQRvQU1dYUfPfyM4HBD6r2";
        const token = localStorage.getItem('userToken');
        const userID = JSON.parse(localStorage.getItem('userinfo')).USER_ID;
        fetch(`http://localhost:8080/api/${userID}/stores?token=${token}`, {
            method: "GET"
        }).then(res => {
            return res.json();
        }).then(result => {
            const sendToWho = result[0].STORE_ID;
            if (orderID != null && orderID != "" && paymentID != null && paymentID != "" 
                && sendToWho != null && sendToWho != "") {
                sendMsg = `${sendMsg}#${orderID}#${paymentID}#${sendToWho}`;
                this.websocket.send(sendMsg);
            }
        });
    }
}