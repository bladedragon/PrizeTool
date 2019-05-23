package com.cqupt.prizetool.config;


import com.cqupt.prizetool.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class LoginConfiguration implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有的controller
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns("/wx/token").excludePathPatterns("/getPrizeA/**")
                .excludePathPatterns("/getPrizeB/**").excludePathPatterns("/temp/**").excludePathPatterns("/wxOperate/**").excludePathPatterns("/init");
    }

}
