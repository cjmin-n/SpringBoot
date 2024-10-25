package com.ohgiraffers.chap07fileupload;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    // 정적 자원을 처리하기 위한 메소드
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Dispatcher Servlet 에서 처리할 요청 중 특정 요청을 정한대로 처리
        registry.addResourceHandler("/img/single/**") // 이 요청이 들어왔을 때
            .addResourceLocations("file:///C:/uploads/single/"); // 이 주소로 응답해라.

        registry.addResourceHandler("/img/multi/**") // 이 요청이 들어왔을 때
                .addResourceLocations("file:///C:/uploads/multi/"); // 이 주소로 응답해라.
    }
}
