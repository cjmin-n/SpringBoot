package com.ohgiraffers.chap08securitysession.user.service;

import com.ohgiraffers.chap08securitysession.user.dao.UserMapper;
import com.ohgiraffers.chap08securitysession.user.dto.LoginUserDTO;
import com.ohgiraffers.chap08securitysession.user.dto.SignupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder encoder;


    /*
    Transactional
    * 메소드가 정상적으로 완료되면 커밋함.
    * 실행 중 예외 발생 시 롤백함.
    * */
    // 트랜잭션 관련된 메소드 (없어도됨)
    @Transactional
    public int regist(SignupDTO signupDTO) {

        // required 이지만 추가 검증해준 것임.
        if(signupDTO.getUserId() == null || signupDTO.getUserId().isEmpty()){
            return 0;
        }
        if(signupDTO.getUserName() == null || signupDTO.getUserName().isEmpty()){
            return 0;
        }
        if(signupDTO.getUserPass() == null || signupDTO.getUserPass().isEmpty()){
            return 0;
        }

        signupDTO.setUserPass(encoder.encode(signupDTO.getUserPass())); // encode() 메소드로 원본을 인코딩해서 다시 넣어줌 - DB에 원본 안남음

        int result = userMapper.regist(signupDTO);

        return result;
    }


    public LoginUserDTO findByUserName(String username) {

        // security 가 정해준 username 은 식별정보 -> 이 프로젝트에서는 id
        LoginUserDTO login = userMapper.findByUserName(username);

        if(Objects.isNull(login)){
            return null;
        }else {
            return login;
        }
    }

    public boolean isUserIdExists(String userId) {

        LoginUserDTO login = userMapper.findByUserName(userId);
        if(Objects.isNull(login)){
            return false;
        }
        return true;
    }
}
