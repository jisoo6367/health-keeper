package com.health.keeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/menu")
@Controller
public class MenuController {

    @GetMapping("/management")
    public String showMenuManagement(){
        return "menuManagement";
    }
}
