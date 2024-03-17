package com.health.keeper.controller;

import com.health.keeper.dto.MenuDTO;
import com.health.keeper.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RequestMapping("/menu")
@Controller
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @Autowired


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
    public String save(@ModelAttribute MenuDTO menuDTO, Principal principal) throws IOException {
        System.out.println("menuDTO = " + menuDTO);
        menuService.save(menuDTO);
        return "redirect:/menu/list";
    }

}
