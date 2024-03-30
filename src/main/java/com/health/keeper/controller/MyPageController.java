package com.health.keeper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/mypage")
@RequiredArgsConstructor
@Controller
public class MyPageController {

    @GetMapping("/main")
    public String showMyPage(){
        return "mypage";
    }

}

