package com.ohgiraffers.understand.model;

import com.ohgiraffers.understand.dto.MenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuDAO {

    List<MenuDTO> selectAllMenu();

    List<MenuDTO> selectOneMenu(int code);

    int regist(MenuDTO menuDTO);

    int update(MenuDTO menuDTO);

    int delete(MenuDTO menuDTO);
}
