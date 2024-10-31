package com.ohgiraffers.chap08securitysession.auth.model;

import com.ohgiraffers.chap08securitysession.user.dto.LoginUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


// 시큐리티 구현 시 ! 구현하지 않으면 안되는 메소드
public class AuthDetail implements UserDetails {

    private LoginUserDTO loginUserDTO; // 시큐리티에 사용하라고 주는 것

    public AuthDetail() {
    }

    public AuthDetail(LoginUserDTO loginUserDTO) {
        this.loginUserDTO = loginUserDTO;
    }

    public LoginUserDTO getLoginUserDTO() {
        return loginUserDTO;
    }

    public void setLoginUserDTO(LoginUserDTO loginUserDTO) {
        this.loginUserDTO = loginUserDTO;
    }


    // 특정 유저에 관해 설정할 수 있는 메소드

    // 권한 정보 반환 메소드 확인
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(String role : loginUserDTO.getRole()){
            authorities.add(new GrantedAuthority() { // GrantedAuthority:인터페이스
                @Override
                public String getAuthority() {
                    return role; // role 이 GrantedAuthority 타입으로 들어간다.
                }
            });
        }
        return authorities;
    }

    // 시큐리티가 입력한 값과 DB 에 있는 값과 비교하기 위해서 구현해놓은 메소드들
    // 사용자의 비밀번호를 반환하는
    @Override
    public String getPassword() {
        return loginUserDTO.getPassword();
    }

    // 사용자의 아이디를 반환하는 메소드 (고유 식별자 비교)
    @Override
    public String getUsername() {
        return loginUserDTO.getUserId();
    }

    // 계정 만료 여부를 표현하는 메소드 - false 이면 해당 계정을 사용할 수 없다.
    @Override
    public boolean isAccountNonExpired() {
        return true;
        // DB 에 계정만료할 값을 넣어놓은 후 이 메소드에서 비교해서 false 를 주면 계정 만료가 된다.
    }

    // 잠겨있는 계정을 확인하는 메소드 - false 면 사용할 수 없다.
    // 비밀번호 몇번 실패했거나 오랜 시간 사용하지 않았을 때 휴면처리 시 사용한다.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 탈퇴 계정 여부 표현 메소드 - false 면 사용할 수 없다.
    // 탈퇴 유저의 경우, 바로 DB 에서 지우는 것이 아니라 여기에서 false 로 줘서 로그인을 못하게 한다.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 비활성화로 사용자가 사용 못하는 경우
    @Override
    public boolean isEnabled() {
        return true;
    }


}
