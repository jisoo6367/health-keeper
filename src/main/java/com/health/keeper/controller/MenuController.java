package com.health.keeper.controller;

import com.health.keeper.dto.MenuDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/menu")
@Controller
public class MenuController {

    @GetMapping("/list")
    public String showMenuManagement(){
        return "menuManagement";
    }

    @GetMapping("/register")
    public String registerForm(Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("username", username);
        return "menuRegister";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MenuDTO menuDTO, Principal principal){
        System.out.println("menuDTO = " + menuDTO);
        System.out.println("카테고리 = " + menuDTO.getMenuCategory());
        System.out.println("principal.getName() = " + principal.getName());
        return "redirect:/menu/list";
    }

}
