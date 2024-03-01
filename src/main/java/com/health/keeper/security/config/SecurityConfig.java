package com.health.keeper.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((auth) -> auth.disable());

        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/login", "/loginForm", "/join", "/joinForm").permitAll()
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/manager").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        //http.httpBasic(Customizer.withDefaults());

        http
                .formLogin((auth) -> auth.loginPage("/loginForm")
                        .loginProcessingUrl("/login")//컨트롤러에 /login 안만들어도 됨
                        .defaultSuccessUrl("/"));


        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId() // 세션 고정 보호 (해킹방지)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false));//다중 로그인시 기존계정 로그아웃



        return http.build();
    }
}
