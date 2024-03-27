package com.health.keeper.controller;

import com.health.keeper.dto.MenuDTO;
import com.health.keeper.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
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
    public String showMenuManagement(Principal principal, Model model){
        //List<MenuDTO> menuDTOList = menuService.findAll();
        List<MenuDTO> menuDTOList = menuService.findByMenuWriter(principal.getName());

        System.out.println("컨트롤러에서 menuDTOList = " + menuDTOList);

        model.addAttribute("menuList", menuDTOList);
        model.addAttribute("username", principal.getName());

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

    @GetMapping("/detail/{date}")
    public String showDetail (@PathVariable("date")LocalDate menuCreatedTime, Principal principal, Model model) {
        System.out.println("detail로 가는 컨트롤러에서 menuCreatedTime = " + menuCreatedTime);
        //MenuDTO menuDTO = menuService.findById(id);
        List<MenuDTO> menuDTOList = menuService.findByMenuWriterAndMenuCreatedTime(principal.getName(),menuCreatedTime);

        System.out.println("컨트롤러에서 ========" + menuDTOList);
        model.addAttribute("menuList", menuDTOList);
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

    // ajax로 정보 받아서
    @PostMapping(value ="/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update (@RequestBody MenuDTO menuDTO, Model model){
        System.out.println("menuDTO.getId = " + menuDTO.getId());
        System.out.println("menuDTO.getStoredFileName = " + menuDTO.getStoredFileName());

        model.addAttribute("menuDTO", menuDTO);
        return ResponseEntity.ok().build(); // 응답 반환
    }

    // 페이지 반환
    @GetMapping("/modify/{id}")
    public String showModifyForm(@PathVariable("id") Long id, Model model){
        System.out.println("id = " + id);
        MenuDTO menuDTO = menuService.findById(id);
        model.addAttribute("menuDTO", menuDTO);
        System.out.println("modify 페이지로 가면서 ====" + menuDTO);
        return "menuModify";
    }
}

