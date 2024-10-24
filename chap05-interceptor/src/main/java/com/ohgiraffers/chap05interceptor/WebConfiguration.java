package com.ohgiraffers.chap05interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // spring 의 bean 설정 클래스
public class WebConfiguration implements WebMvcConfigurer {
                                // spring mvc 설정 추가 용도
    @Autowired
    private StopWatchInterceptor stopWatchInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
                // 인터셉터 등록
        registry.addInterceptor(stopWatchInterceptor)
                // /stopwatch 경로에 등록한 인터셉터 적용
                .addPathPatterns("/stopwatch")
                // 정적인 자원 제외 - 어떤 에러가 발생할지 모르기 때문
                .excludePathPatterns("/css/**") // css파일
                .excludePathPatterns("/images/**") // images 파일
                .excludePathPatterns("/js/**"); // js 파일

    }
}
