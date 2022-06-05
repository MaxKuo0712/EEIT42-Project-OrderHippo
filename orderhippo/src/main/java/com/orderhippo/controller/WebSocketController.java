package com.orderhippo.controller;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@ServerEndpoint("/websocket/{requestID}")
@CrossOrigin
public class WebSocketController {
	// 儲存使用者及WebSocketController
	private static ConcurrentHashMap<String, WebSocketController> webSocketMap = new ConcurrentHashMap<>();
	
	private Session session;
	private static Logger log = LogManager.getLogger(WebSocketController.class);
	private String requestID = "";
	
	@OnOpen
	public void onOpen(@PathParam(value = "requestID") String requestID, Session session) {
		this.session = session;
		this.requestID = requestID;
		webSocketMap.put(requestID, this); // 連線放入資料

		try {
			sendMessage("Hi, "+ requestID +" 連線成功");
			System.out.println("OK:" + requestID + session);
			System.out.println(webSocketMap);
		} catch (Exception e) {
			log.error("WebSocket IO異常");
		}

	}
	
	@OnClose
	public void onClose() {
		webSocketMap.remove(requestID); // 關閉連線移除資料
		System.out.println(requestID + "斷線");
		System.out.println(webSocketMap);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		log.info("客戶端訊息"+this.requestID+"："+ message);
		
		StringBuffer sendMessage = new StringBuffer(message.split("#")[0]);
		String orderID = message.split("#")[1];
		String paymentID = message.split("#")[2];
		String sendUserID = message.split("#")[3];
		
		sendMessage
			.append("#")
			.append(orderID)
			.append("#")
			.append(paymentID)
			.append("#")
			.append(this.requestID);
		System.out.println(sendMessage.toString());
//		sendMessage = sendMessage.concat("#").concat(orderID).concat("#").concat(paymentID).concat("#").concat(this.requestID);
		
		try {
			// 如果sendUserID是SendToAllUser，則發送給所有使用者；否則就是發送給指定使用者
			if (sendUserID.equals("SendToAllUser")) {
				sendToAll(sendMessage.toString());
			} else {
				sendToUser(sendMessage.toString(), sendUserID);
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	public void sendToUser(String message, String sendUserID) throws IOException {
		if (webSocketMap.get(sendUserID) != null) {
			if (!this.requestID.equals(sendUserID)) {
				webSocketMap.get(sendUserID).sendMessage(message);
			} else {
				webSocketMap.get(sendUserID).sendMessage(message);
			}
		} else {
			sendToUser("該使用者不在家", this.requestID);
		}
	}
	
	public void sendToAll(String message) throws IOException {
		for (String key : webSocketMap.keySet()) {
			try {
				webSocketMap.get(key).sendMessage(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}
}
