package com.ohgiraffers.chap01requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller// 요청이 들어왔을 때 받는 곳
public class MethodMappingTestController {

    // RequestMapping 은 GET/POST 둘다 쓸 수 있는데, 보통 나눠서 사용한다.
    @RequestMapping("/menu/regist")
    public String registMenu(Model model) {
        // Model 은 응답을 위한 객체이다.
        // Servlet 에서 request 에 attribute 담은 것처럼 똑같이 사용할 수 있다.
        model.addAttribute("message", "신규 메뉴 등록용 핸들러 메소드 호출");

        return "mappingResult"; // viewResolver 가 templates 에서 해당 파일을 찾는다. .html 은 자동으로 붙여줌.
    }


    // 첫번째 방법
    @RequestMapping(value="/menu/modify", method= RequestMethod.GET)
    public String modifyMenu(Model model) {
        model.addAttribute("message", "Get 방식의 메뉴 수정 호출");

        return "mappingResult";
    }

    // 두번째 방법
    /*
    * 요청 메소드 전용 어노테이션
    *    요청 메소드            어노테이션
    *       post            @PostMapping
    *       get             @GetMapping
    *       Put             @PutMapping     (전체 수정 - 기존꺼 지움) // 수정 리소스의 모든 것을 업데이트
    *       Delete          @DeleteMapping
    *       Patch           @PatchMapping   (일부분 수정) // 수정 리소스의 일부 업데이트
    * */

    @GetMapping("/menu/delete")
    public String getDeleteMenu(Model model) {

        model.addAttribute("message", "Get 방식의 삭제 핸들러 메소드 호출");

        return "mappingResult";
    }

    @PostMapping("/menu/delete")
    public String postDeleteMenu(Model model) {
        model.addAttribute("message", "Post 방식의 삭제 핸들러 메소드 호출");

        return "mappingResult";
    }



}
