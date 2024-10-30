package com.ohgiraffers.understand.service;

import com.ohgiraffers.understand.dto.MenuDTO;
import com.ohgiraffers.understand.model.MenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
