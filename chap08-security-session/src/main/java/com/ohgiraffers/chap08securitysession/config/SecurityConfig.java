package com.ohgiraffers.chap08securitysession.config;

import com.ohgiraffers.chap08securitysession.common.UserRole;
import com.ohgiraffers.chap08securitysession.config.handler.AuthFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 시큐리티 리소스 관련된 빈들을 모아놓은 곳
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthFailHandler authFailHandler;

    // 비밀번호 인코딩 Bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 정적 리소스 요청 제외 Bean
    // security 는 람다(Lambda Expression)로 정의되어있음.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 필터체인 커스텀
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> { // 서버의 리소스에 접근 가능한 권한 설정
            // 이 요청들은 모든 사용자에게 허용 - 인증 필요 없음.
            auth.requestMatchers("/auth/login", "/user/signup", "/auth/fail", "/", "/user/checkUserId").permitAll();
            // Role_ADMIN 에게만 권한을 허용하겠다.
            auth.requestMatchers("/admin/*").hasAnyAuthority(UserRole.ADMIN.getRole());
            // Role_USER 에게만 허용
            auth.requestMatchers("/user/*").hasAnyAuthority(UserRole.USER.getRole());

            auth.anyRequest().authenticated(); // 모든 요청을 인증된 사용자에게 허용 해주겠다.. - 위에 거른것 빼고 나머지

        }).formLogin(login -> {
            login.loginPage("/auth/login"); // 이 페이지가 존재하고 get 요청이 매핑되어있을 때, post 요청이 날라오면 작동
            login.usernameParameter("user"); // login html 에서 보내는 input name 값
            login.passwordParameter("pass");
            login.defaultSuccessUrl("/"); // 로그인 성공 시 보낼 곳 설정. mapping 이 존재해야 함.
            login.failureHandler(authFailHandler); // 실패 시 처리
        }).logout(logout -> {
            // 로그아웃 시 요청을 날릴 url 설정 - 페이지 만들 필요없음 (security 가 처리)
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"));
            // 사용자가 세션을 쓰지 못하게 제거
            logout.deleteCookies("JSESSIONID"); // 서버에서 세션에 아이디를 발급해주면 cookie 에 JSESSIONID 가 생성됨 -> 이걸 지우면 세션을 못쓰게 됨
            logout.invalidateHttpSession(true); // 세션이 소멸하는 걸 허용하는 메소드
            logout.logoutSuccessUrl("/"); // 로그아웃 완료 후 이동할 페이지 설정

        }).sessionManagement(session -> { // 세션을 컨트롤하기 위한 체이닝
            session.maximumSessions(1); // 세션의 갯수 제한 1로 설정 시 중복 로그인 X - 여러 디바이스 로그인 불가
            session.invalidSessionUrl("/"); //세션 만료 시 이동할 페이지
        }).csrf(csrf -> {
            csrf.disable(); // csrf 처리 안함
            // csrf 공격관련 설정 : 사용자가 의도하지 않은 요청을 날리는 것을 처리하지 않겠다는 의미
            // 허용하면 모든 곳에 csrf 토큰을 발급해야함.
        });

        return http.build();
    }

}
