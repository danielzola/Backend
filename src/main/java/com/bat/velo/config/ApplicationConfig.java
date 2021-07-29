package com.bat.velo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {
    
    @Autowired
    protected CorsInterceptor corsInterceptor;
    
    @Autowired
    protected AuthInterceptor authInterceptor;
    
    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    protected ObjectMapper objectMapper() {
        
        return new ObjectMapper();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
        registry.addInterceptor(corsInterceptor);
        registry.addInterceptor(authInterceptor);
    }
}
