package site.metacoding.firstapp.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import site.metacoding.firstapp.config.authfilter.JwtAuthenticationFilter;
import site.metacoding.firstapp.config.authfilter.JwtAuthorizationFilter;


@Configuration
public class FilterConfig {

    @Bean // 토큰 없으면 생성해주는 필터
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilterRegister() {
        FilterRegistrationBean<JwtAuthenticationFilter> bean = new FilterRegistrationBean<>(
                new JwtAuthenticationFilter());
        bean.addUrlPatterns("/login"); // 해당 url에 맞는 요청에 Filter가 적용된다.
        bean.setOrder(1);
        return bean;
    }
    
    @Bean // 토큰 있으면 해독해서 검증해주는 필터
    public FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilterRegister() {
        FilterRegistrationBean<JwtAuthorizationFilter> bean = new FilterRegistrationBean<>(
                new JwtAuthorizationFilter());
        bean.addUrlPatterns("/s/*"); // 해당 url에 맞는 요청에 Filter가 적용된다.
        bean.setOrder(1);
        return bean;
    }
}
