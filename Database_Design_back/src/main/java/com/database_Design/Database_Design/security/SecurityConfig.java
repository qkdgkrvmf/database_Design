package com.database_Design.Database_Design.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 비밀번호 암호화를 위한 BCryptPasswordEncoder Bean 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring Security 필터 체인 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (테스트용)
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/users/register", "/users/login", "/users/{loinId}").permitAll() // 인증 없이 접근 가능
                        //.anyRequest().authenticated() // 나머지 요청은 인증 필요
                        .anyRequest().permitAll() // 모든 요청 인증 없이 허용
                );

        return http.build();
    }
}
