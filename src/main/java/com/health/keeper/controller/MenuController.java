package com.health.keeper.controller;

import com.health.keeper.dto.MenuDTO;
import com.health.keeper.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
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

    //게시글& 첨부파일 삭제
    @PostMapping("/delete")
    public String delete (@RequestBody MenuDTO menuDTO){
        System.out.println("menuDTO.getId = " + menuDTO.getId());
        System.out.println("menuDTO.getStoredFileName = " + menuDTO.getStoredFileName());

        menuService.delete(menuDTO.getId());

        List<String> storedFileName = menuDTO.getStoredFileName();

        if(storedFileName != null){
            for(String fileName : storedFileName){
                File file = new File("C:\\springboot-img\\menu\\" + fileName);
                boolean deleted = file.delete();
                if(deleted){
                    System.out.println("파일 삭제 성공 : " + fileName);
                }else {
                    System.out.println("파일 삭제 실패 : " + fileName);
                }
            }
        }

        return "redirect:/menu/list";
    }

}
