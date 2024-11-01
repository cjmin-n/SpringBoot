package com.ohgiraffers.chap09websocket.server;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static Set<WebSocketSession> clients = Collections.synchronizedSet(new HashSet<>());  // synchronizedSet : 다수의 사용자가 사용하기에 안전한 set 이다.



}
