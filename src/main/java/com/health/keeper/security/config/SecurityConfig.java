package com.health.keeper.security.config;

import com.health.keeper.security.auth.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((auth) -> auth.disable());

        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers( "/login", "/loginForm", "/join", "/joinForm","/confirm", "/mail").permitAll()
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

        http
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/loginForm")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(principalOauth2UserService)));



        return http.build();
    }
}
