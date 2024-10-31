package com.ohgiraffers.understand.controller;

import com.ohgiraffers.understand.dto.MenuDTO;
import com.ohgiraffers.understand.exception.NotInsertNameException;
import com.ohgiraffers.understand.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/menus/*")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("menus")
    public ModelAndView menus(ModelAndView mv) {
        List<MenuDTO> menus = menuService.selectAllMenu();

        if(Objects.isNull(menus)){ // isEmpty() 체크도 해주면 좋다.
            throw new NullPointerException();
        }

        mv.addObject("menus", menus);

        mv.setViewName("menus/allMenus");

        return mv;
    }


    @GetMapping("onemenu")
    public ModelAndView onemenu(ModelAndView mv) {
        mv.setViewName("menus/onemenu");
        return mv;
    }

    @GetMapping("onemenuaction")
    public ModelAndView selectOneMenu(ModelAndView mv, @RequestParam("code") int code){
        // MenuDTO menuDTO 를 매개변수로 사용하면 같은 이름의 필드변수에 자동 매핑됨. getter 로 꺼내쓸 수 있음.

        List<MenuDTO> menus = menuService.selectOneMenu(code);
        if(Objects.isNull(menus)){
            throw new NullPointerException();
        } else if(menus.isEmpty()){
            mv.addObject("txt", "없는 번호 입니다.");
        }
        mv.addObject("menus", menus);

        // 로직
        mv.setViewName("menus/allMenus");
        return mv;
    }


    @GetMapping("regist")
    public ModelAndView insert(ModelAndView mv){

        mv.setViewName("menus/regist");
        return mv;
    }

    @PostMapping("regist")
    public ModelAndView insertMenu(ModelAndView mv, MenuDTO menuDTO) throws NotInsertNameException {

        int regist = menuService.regist(menuDTO);

        if(regist <= 0){
            mv.addObject("message", "가격은 음수일 수 없습니다.");
            mv.setViewName("error/errorMessage");

        }else {
            mv.setViewName("menus/returnMessage");
        }


        return mv;
    }

    @GetMapping("update")
    public ModelAndView update(ModelAndView mv){
        mv.setViewName("menus/update");
        return mv;
    }

    @PostMapping("update")
    // 프론트 - 자바스크립트에서 onsubmit 으로 가공
//    public ModelAndView updateMenu(ModelAndView mv, MenuDTO menuDTO) {


     // 내가 한 방식
    /*public ModelAndView updateMenu(ModelAndView mv, @RequestParam Map<String,String> parameters) {

        int code = Integer.parseInt(parameters.get("code"));
        String name = parameters.get("name");
        String price = parameters.get("price");
        String categoryCode = parameters.get("categoryCode");

        int priceInt = 0;
        int categoryCodeInt = 0;
        if(price == null || price.isEmpty()){
            priceInt = 0;
        }else {
            priceInt = Integer.parseInt(price);
        }

        if(categoryCode == null || categoryCode.isEmpty()){
            categoryCodeInt = 0;
        }else {
            categoryCodeInt = Integer.parseInt(categoryCode);
        }

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setCode(code);
        menuDTO.setName(name);
        menuDTO.setPrice(priceInt);
        menuDTO.setCategoryCode(categoryCodeInt);*/
    
    // 선생님이 알려준 방식
    public ModelAndView updateMenu(ModelAndView mv,
                                   @RequestParam("code") int code,
                                   @RequestParam(name="name", defaultValue = "") String name,
                                   @RequestParam(name="price", defaultValue = "0") int price,
                                   @RequestParam(name="categoryCode",defaultValue = "0") int categoryCode) {

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setCode(code);
        menuDTO.setName(name);
        menuDTO.setPrice(price);
        menuDTO.setCategoryCode(categoryCode);

        int update = menuService.update(menuDTO);

        if(update <= 0){
            mv.addObject("message", "업데이트 실패");
            mv.setViewName("error/errorMessage");

        }else {
            mv.setViewName("menus/returnMessage");
        }

        return mv;
    }

    @GetMapping("delete")
    public ModelAndView delete(ModelAndView mv) {
        mv.setViewName("menus/delete");

        return mv;
    }

    @PostMapping("delete")
    public ModelAndView deleteMenu(ModelAndView mv, MenuDTO menuDTO) {
        int delete = menuService.delete(menuDTO);

        if(delete <= 0){
            mv.addObject("message", "삭제 실패");
            mv.setViewName("error/errorMessage");

        }else {
            mv.setViewName("menus/returnMessage");
        }

        return mv;
    }

}
