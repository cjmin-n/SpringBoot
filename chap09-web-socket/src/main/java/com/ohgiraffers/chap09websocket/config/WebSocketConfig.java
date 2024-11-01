package com.ohgiraffers.chap09websocket.config;

import com.ohgiraffers.chap09websocket.server.ChatWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 구현해놓으면 스프링이 자동호출하는 메소드
        // WebSocket 핸들러(웹소켓에 관한 것을 설정-ChatWebSocketHandler)를 등록하는 메소드

        registry.addHandler(new ChatWebSocketHandler(), // 만든 핸들러 등록
                "/chattingServer") // 경로
                .setAllowedOrigins("*"); // 모든 곳(아무 사용자)에서 접근가능하도록

    }
}
