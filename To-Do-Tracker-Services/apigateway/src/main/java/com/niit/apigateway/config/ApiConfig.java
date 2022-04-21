package com.niit.apigateway.config;

import com.niit.apigateway.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(p->p.path("/niit/user/auth/**").uri("lb://user-auth-service"))
                .route(p->p.path("/niit/user/task/**","/niit/user/register").uri("lb://user-task-service")).build();
    }
    @Bean
    FilterRegistrationBean jwtFilter(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new JwtFilter());
        filterRegistrationBean.addUrlPatterns("/niit/user/task/*");
        return filterRegistrationBean;
    }
}
