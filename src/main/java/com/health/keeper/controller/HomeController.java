package com.health.keeper.controller;

import com.health.keeper.dto.UserDTO;
import com.health.keeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String showHome(Principal principal, Model model){
        System.out.println("principal.getName() = " + principal.getName());


        UserDTO userDTO = userService.findByUsername(principal);
        String role = userDTO.getRole();
        System.out.println("롤 가져오는지 : " + role);

        model.addAttribute("userDTO" , userDTO);


        return "/homePage";
    }

    @GetMapping("/about")
    public String about(){
        return "/about";
    }

    @GetMapping("/contact")
    public String contact(){
        return "/contact";
    }

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
