package com.health.keeper.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //설정
public class WebConfig implements WebMvcConfigurer {
//    private String resourcePath = "/upload/**"; // view 에서 접근할 경로
//    private String savePath = "file:///C:/springboot_img/"; // 실제 파일 저장 경로

    // Board 파일 저장 경로
    private String boardResourcePath = "/board/upload/**";
    private String boardSavePath = "file:///C:/springboot_img/board/";

    // Menu 파일 저장 경로
    private String menuResourcePath = "/menu/upload/**";
    private String menuSavePath = "file:///C:/springboot_img/menu/";


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //board
        registry.addResourceHandler(boardResourcePath)
                .addResourceLocations(boardSavePath);

        //menu
        registry.addResourceHandler(menuResourcePath)
                .addResourceLocations(menuSavePath);






    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
