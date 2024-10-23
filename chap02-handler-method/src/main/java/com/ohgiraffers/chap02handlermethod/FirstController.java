package com.ohgiraffers.chap02handlermethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;


@Controller
@RequestMapping("/first/*")
public class FirstController {

    // request mapping 메소드를 void 로 만들고
    // templates 내부에 first 폴더 만들면
    // 따로 설정할 필요없이 해당 경로로 찾아 들어간다.
    @GetMapping("regist")
    public void regist() {}

    @PostMapping("regist")
    public String registMenu(Model model, WebRequest request) { // WebRequest : 파라미터를 전달받을 곳

        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));
        String message = name + "을(를) 신규 메뉴 목록의 " + categoryCode + "번 카테고리에 " + price + "원으로 등록 하셨습니다.";
        System.out.println(message);
        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    @GetMapping("modify")
    public void modify() {}

    // required = 파라미터의 포함 여부, name = 이름, defaultValue = 기본 값
    @PostMapping("modify")
    public String modifyMenu(Model model, @RequestParam(required = false) String modifyName, @RequestParam(defaultValue = "0") int modifyPrice) {
        // @RequestParam(required = false) true 면 modifyName 을 못가져오면 에러가 난다.
        // @RequestParam(defaultValue = "0") 값이 제대로 넘어오지 않았을 때 기본값 설정

        return "first/messagePrinter";
    }
}
