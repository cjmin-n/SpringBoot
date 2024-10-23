package com.ohgiraffers.chap02handlermethod;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;


@Controller
@RequestMapping("/first/*")
@SessionAttributes("id") // 컨트롤러 내부에서만 사용할 수 있음. 모델에 담긴 값을 유지하는 역할. 여러 개 넣을 시에는 배열로 넣을 수 있다.
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
    /*@PostMapping("modify")
    public String modifyMenu(Model model, @RequestParam(required = false, name="modifyName") String modifyName, @RequestParam(defaultValue = "0", name="modifyPrice") int modifyPrice) {
        // @RequestParam(required = false) true 면 modifyName 을 못가져오면 에러가 난다. (빈 문자열도 가능)
        // @RequestParam(defaultValue = "0") 값이 제대로 넘어오지 않았을 때 기본값 설정

        String message = modifyName + " 메뉴 가격을 " + modifyPrice + "원으로 변경 하였습니다.";
        System.out.println(message);

        model.addAttribute("message", message);

        return "first/messagePrinter";
    }*/

    // 빈문자열 에러남
    @PostMapping("modify")
    public String modify(Model model, @RequestParam Map<String, String> parameters) {
        String modifyName = parameters.get("modifyName");
        int modifyPrice = Integer.parseInt(parameters.get("modifyPrice"));

        String message = modifyName + " 메뉴 가격을 " + modifyPrice + "원으로 변경 하였습니다.";
        System.out.println(message);

        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    @GetMapping("search")
    public void search(){}

    @PostMapping("search")
    public String searchMenu(@ModelAttribute("menu") MenuDTO menu) {
        System.out.println(menu);
        return "first/searchResult";
    }

    // 4. session 이용하기
    @GetMapping("login")
    public void login() {}

    // 4-1. HttpSession 을 매개변수로 선언하면 핸들러 메소드 호출 시 세션 객체를 호출함.
    @PostMapping("login")
    public String sessionTest1(HttpSession session, @RequestParam String id){
        session.setAttribute("id", id);
        return "first/loginResult";
    }

    @GetMapping("logout1")
    public String logoutTest1(HttpSession session) {
        session.invalidate(); // 세션만료 - 모든 전역/지역만료시킴
        return "first/login";
    }

    /*
    * 4-2. SessionAttribute 를 이용하여 session 에 값 담기
    * 클래스 레벨에 @SessionAttributes 어노테이션을 이용하여 세션에 값을 담을 key 를 설정해두면,
    * model 영역에 해당 key 로 값이 추가되는 경우 Session 에 자동 등록한다.
    *   (@SessionAttributes 로 지정된 속성은 해당 컨트롤러 내에서만 유효하다.)
    * */
    // HttpSession 은 브라우저 당 하나인 세션
    // Controller 의 @SetAttributes(key)지정 : 컨트롤러 모델에 귀속되는 세션
    @PostMapping("login2")
    public String sessionTest2(Model model, @RequestParam String id){
        model.addAttribute("id", id);
        return "first/loginResult";
    }

    // sessionAttribute 로 등록된 값은 session 의 상태를 관리하는
    // sessionStatus 의 setComplete() 메소드를 호출해야 사용이 만료된다.

    @GetMapping("logout2")
    public String logoutTest2(SessionStatus sessionStatus) {
        sessionStatus.setComplete(); // 지역 세션만 만료시키는데, 전역에 같은게 있으면 그것도 만료시킴.

        return "first/loginResult";
    }


}
