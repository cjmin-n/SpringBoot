package com.ohgiraffers.understand.service;

import com.ohgiraffers.understand.dto.MenuDTO;
import com.ohgiraffers.understand.exception.NotInsertNameException;
import com.ohgiraffers.understand.model.MenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuDAO menuDAO;

    public List<MenuDTO> selectAllMenu() {
        // 데이터 검증 한번더 해주면 좋다. controller / service 같은거 해줘도 되고, 다른거 해줘도..
        List<MenuDTO> menus = menuDAO.selectAllMenu();

        return menus;
    }

    public List<MenuDTO> selectOneMenu(int code) {
        List<MenuDTO> menus = menuDAO.selectOneMenu(code);


        return menus;
    }

    public int regist(MenuDTO menuDTO) throws NotInsertNameException {

        List<MenuDTO> menus = menuDAO.selectAllMenu();
        // 이름만 불러오는 요청 만들어서 날리는게 더 좋다!

        List<String> names = new ArrayList<>();

        for(MenuDTO menu : menus) {
            names.add(menu.getName());
        }

        if(names.contains(menuDTO.getName()) || menuDTO.getName().isEmpty()) {
            throw new NotInsertNameException("");
        }// GlobalExceptionHandler 에서 처리할 것이기 때문에 try-catch 가 아니라, 에러를 던져야 한다.

        if(menuDTO.getPrice() <= 0){ // 가격이 0 이거나 음수일 때
            return 0;
        }

        int result = menuDAO.regist(menuDTO);

        return result;
    }
}
