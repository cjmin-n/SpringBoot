package com.ohgiraffers.chap10websocketreact.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.chap10websocketreact.dto.Message;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    // 세션 관리 Set
    private static Set<WebSocketSession> clients = Collections.synchronizedSet(new HashSet<>());

    // json 으로 넘어오는 데이터를 매핑시키기 위한 객체
    private static ObjectMapper objectMapper = new ObjectMapper();



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        clients.add(session);
        System.out.println("웹소켓 연결 : " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("메세지 전송 : " + session.getId() + " : " + message.getPayload());
        String payload = message.getPayload(); // json
        Message msg = objectMapper.readValue(payload, Message.class); // payload 에 담겨 있는 것중 Message 에 있는것을 매핑함

        synchronized (clients){
            for(WebSocketSession client : clients){
                if(!client.equals(session)){
                    if("join".equals(msg.getType())) {
                        client.sendMessage(new TextMessage(msg.getUserId() + "님이 입장하셨습니다."));
                    }else if("leave".equals(msg.getType())){
                        client.sendMessage(new TextMessage(msg.getUserId() + "님이 퇴장하셨습니다."));
                    }else if("message".equals(msg.getType())){
                        client.sendMessage(new TextMessage(msg.getUserId() + " : " + msg.getMessage()));
                    }

                }
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("에러 발생 : " + session.getId());
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        clients.remove(session);
        System.out.println("웹소켓 종료 : " + session.getId());
    }



}
