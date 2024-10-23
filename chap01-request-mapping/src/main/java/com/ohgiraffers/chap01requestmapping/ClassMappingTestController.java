package com.ohgiraffers.chap01requestmapping;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/order/*")
public class ClassMappingTestController {


    @GetMapping("/regist")
    public String registOrder(Model model) {
        model.addAttribute("message", "Get 방식의 주문 등록용 핸들러 메소드 호출");

        return "mappingResult"; // Controller 일때만 viewResolver 가 해당 이름의 html 파일 찾음 !
    }

    // GetMapping/PostMapping은 하나의 요청만 받을 수 있는데
    // RequestMapping 은 한 번에 받을 수 있다.
    //    @RequestMapping(value={"modify", "delete"}, method = RequestMethod.POST)
    @RequestMapping(value="modify", method = RequestMethod.POST)
    public String modifyOrder(Model model) {
        model.addAttribute("message", "post 방식의 주문 정보 수정 핸들러 메소드 호출");

        return "mappingResult";
    }

    /*
    * PathVariable
    * @PathVariable 어노테이션을 이용해 변수를 받아올 수 있다.
    * path variable 로 전달되는 {변수명} 은 반드시 매개변수명과 동일해야 한다.
    * 만약 동일하지 않으면 @PathVariable("이름")을 설정해주어야 한다.
    *
    * 안전하게 @PathVariable("orderNo") int orderNo 전부 동일하게 입력해주는게 좋다.
    * */

    @GetMapping("/detail/{orderNo}")
    public String selectOrderDetail(Model model, @PathVariable("orderNo") int orderNo) {
        model.addAttribute("message", orderNo + "번 주문 상세 내용 조회용 핸들러 메소드 호출");
        return "mappingResult";
    }

    // order 하위 모든 것
    @RequestMapping
    public String otherRequest(Model model) {
        model.addAttribute("message", "order 요청이긴 하지만 다른 기능이 준비되지 않음");
        return "mappingResult";

    }


}
