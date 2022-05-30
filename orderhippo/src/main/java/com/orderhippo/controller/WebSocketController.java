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

@Controller
@ServerEndpoint("/websocket/{requestID}")
public class WebSocketController {
	
//	private static int onlineCount = 0;
	private static ConcurrentHashMap<String, WebSocketController> webSocketMap = new ConcurrentHashMap<>();
	
	private Session session;
	private static Logger log = LogManager.getLogger(WebSocketController.class);
	private String requestID = "";
	
	@OnOpen
	public void onOpen(@PathParam(value = "requestID") String requestID, Session session) {
		this.session = session;
		this.requestID = requestID;
		webSocketMap.put(requestID, this);
//		addOnlineCount();

		try {
			sendMessage("Hi, "+ requestID +" 歡迎進入聊天室");
			System.out.println("OK:" + requestID + session);
			System.out.println(webSocketMap);
		} catch (Exception e) {
			log.error("WebSocket IO異常");
		}

	}
	
	@OnClose
	public void onClose() {
		webSocketMap.remove(requestID);
		System.out.println(requestID + "斷線");
		System.out.println(webSocketMap);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		log.info("客戶端訊息"+session+"："+ message);
		
		String sendMessage = message.split("#")[0];
		String sendUserID = message.split("#")[1];
		
		try {
			sendToUser(sendMessage, sendUserID);
			System.out.println("OK");
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
			System.out.println(this.requestID);
			sendToUser("該使用者不在家", this.requestID);
		}
	}
	
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}
	
//	public void addOnlineCount() {
//		onlineCount++;
//	}
//	
//	public void subOnlineCount() {
//		onlineCount--;
//	}
}
