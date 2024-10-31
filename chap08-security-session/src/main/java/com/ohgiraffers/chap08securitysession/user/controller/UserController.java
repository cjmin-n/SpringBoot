package com.ohgiraffers.chap08securitysession.user.controller;

import com.ohgiraffers.chap08securitysession.user.dto.LoginUserDTO;
import com.ohgiraffers.chap08securitysession.user.dto.SignupDTO;
import com.ohgiraffers.chap08securitysession.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user/*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("signup")
    public ModelAndView signup(ModelAndView mv){
        mv.setViewName("user/signup");
        return mv;
    }

    @PostMapping("signup")
    public String signup(SignupDTO signupDTO, RedirectAttributes redirectAttributes){

        int result = userService.regist(signupDTO);
        String message;
        if(result > 0){
            message = "회원 가입이 완료 되었습니다.";
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/auth/login";
        }else {
            message = "회원 가입이 실패 하였습니다.";
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/user/signup";
        }
    }

    @GetMapping("checkUserId")
    public String checkUserId(@RequestParam String userId, RedirectAttributes redirectAttributes){

        if(userService.isUserIdExists(userId)){
            redirectAttributes.addFlashAttribute("message", "중복된 아이디 입니다. \n다시 입력해주세요.");
            return "redirect:/user/signup";
        }else {
            redirectAttributes.addFlashAttribute("message", "가입 가능한 아이디입니다.");
            redirectAttributes.addFlashAttribute("id", userId);
            return "redirect:/user/signup";
        }

    }


}
