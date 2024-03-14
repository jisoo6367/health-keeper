package com.health.keeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHome(){
        return "/index";
    }

    @GetMapping("/about")
    public String about(){
        return "/about";
    }

    @GetMapping("/contact")
    public String contact(){
        return "/contact";
    }

//    @GetMapping("/index2")
//    public String index2(){
//        return "/index2";
//    }

    @GetMapping("/services")
    public String services(){
        return "/services";
    }

    @GetMapping("/works")
    public String works(){
        return "/works";
    }

    @GetMapping("/works_single")
    public String works_single(){
        return "/worksSingle";
    }
}
