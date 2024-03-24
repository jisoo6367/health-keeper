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
import java.util.List;


@RequestMapping("/menu")
@RequiredArgsConstructor
@Controller
public class MenuController {

    private final MenuService menuService;

//    public MenuController(MenuService menuService) {
//        this.menuService = menuService;
//    }



    @GetMapping("/list")
    public String showMenuManagement(Model model){
        List<MenuDTO> menuDTOList = menuService.findAll();
        System.out.println("컨트롤러에서 menuDTOList = " + menuDTOList);
        //왜 파일을 못가져오지?
        //[MenuDTO(id=1, menuWriter=null, menuComment=null, menuCategory=breakfast, menuCreatedTime=2024-03-17T12:33:24.887101, menuUpdatedTime=2024-03-17T12:33:24.887101, menuFile=null, originalFileName=null, storedFileName=null, fileAttached=0), MenuDTO(id=2, menuWriter=${username}, menuComment=순대맛있었음, menuCategory=breakfast, menuCreatedTime=2024-03-17T12:59:17.827470, menuUpdatedTime=2024-03-17T12:59:17.827470, menuFile=null, originalFileName=null, storedFileName=null, fileAttached=0), MenuDTO(id=3, menuWriter=${username}, menuComment=순대맛있었음, menuCategory=breakfast, menuCreatedTime=2024-03-17T13:03:49.930102, menuUpdatedTime=2024-03-17T13:03:49.930102, menuFile=null, originalFileName=null, storedFileName=null, fileAttached=0), MenuDTO(id=4, menuWriter=jisoo, menuComment=순대맛있었음, menuCategory=breakfast, menuCreatedTime=2024-03-17T13:05:55.236586, menuUpdatedTime=2024-03-17T13:05:55.236586, menuFile=null, originalFileName=null, storedFileName=null, fileAttached=0), MenuDTO(id=5, menuWriter=jisoo, menuComment=400kcal 전주다녀옴ㅋ, menuCategory=breakfast, menuCreatedTime=2024-03-17T14:08:29.375805, menuUpdatedTime=2024-03-17T14:08:29.376811, menuFile=null, originalFileName=null, storedFileName=null, fileAttached=0), MenuDTO(id=6, menuWriter=jisoo, menuComment=350kacl 샌드위치, menuCategory=breakfast, menuCreatedTime=2024-03-17T17:52:15.106003, menuUpdatedTime=2024-03-17T17:52:15.106003, menuFile=null, originalFileName=null, storedFileName=null, fileAttached=0)]

        model.addAttribute("menuList", menuDTOList);

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

    @GetMapping("/detail/{id}")
    public String showDetail (@PathVariable("id")Long id, Model model) {
        System.out.println("id = " + id);
        MenuDTO menuDTO = menuService.findById(id);

        model.addAttribute("menu", menuDTO);
        return "menuDetail";
    }

}
