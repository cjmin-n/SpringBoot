package com.ohgiraffers.chap05interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/*")
public class InterceptorTestController {

    @Autowired
    private InterceptorService interceptorService;
    
    @PostMapping("stopwatch")
    public String handlerMethod(Model model) throws InterruptedException {
        model.addAttribute("test", "모델 테스트");
        // Interceptor 와 Controller 가 같은 모델을 공유하고 있다.
        System.out.println("핸들러 메소드 호출함");
        interceptorService.method();
        Thread.sleep(1000); // 프로그램 1초 대기
        // Thread - 싱글 스레드(기본) / 자바는 멀티 스레드 지원
        return "result";
    }
}
