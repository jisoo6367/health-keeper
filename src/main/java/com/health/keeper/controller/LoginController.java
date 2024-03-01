package com.health.keeper.controller;

import com.health.keeper.entity.UserEntity;
import com.health.keeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String index(){
        return "/index";
    }

    @GetMapping("/loginForm")
    public String loginF(){
        return "/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinF(){
        return "/joinForm";
    }

    @PostMapping("/join")
    public String join (@ModelAttribute UserEntity user) {

        System.out.println("user = " + user);
        System.out.println("유저네임: " + user.getUsername());

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return "redirect:loginForm";
    }

    @GetMapping("/user")
    public String user () {
        return "/user";
    }
}
