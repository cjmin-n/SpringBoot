package com.ohgiraffers.chap08securitysession.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth/*")
public class AuthController {

    @GetMapping("login")
    public ModelAndView login(ModelAndView mv){
        mv.setViewName("auth/login");
        return mv;
    }

    @GetMapping("fail")
    public ModelAndView fail(ModelAndView mv, @RequestParam("message") String message){
        mv.addObject("message", message);
        mv.setViewName("auth/fail");
        return mv;
    }
}
