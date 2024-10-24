package com.ohgiraffers.chap04exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Exception 이 발생 했을 때 핸들링 해주는 클래스를 만드는 어노테이션
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler(NullPointerException e) {
        System.out.println("Global(서버에서 발생하는 모든) 레벨의 Exception 처리");
        return "error/nullPointer";
    }

    @ExceptionHandler(MemberRegistException.class)
    public String userExceptionHandler(Model model, MemberRegistException e){
        System.out.println("Global 레벨의 exception 처리");
        model.addAttribute("exception", e);
        return "error/memberRegist";
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        System.out.println("나머지 exception 발생함");
        return "error/default";
    }
}
