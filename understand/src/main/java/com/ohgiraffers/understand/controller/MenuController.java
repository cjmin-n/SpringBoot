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

}
