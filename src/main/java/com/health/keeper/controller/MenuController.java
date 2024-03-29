package com.health.keeper.controller;

import com.health.keeper.dto.MenuDTO;
import com.health.keeper.entity.MenuEntity;
import com.health.keeper.entity.MenuFileEntity;
import com.health.keeper.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
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

    // 페이지 반환
    @GetMapping("/modify/{id}")
    public String showModifyForm(@PathVariable("id") Long id, Model model){
        System.out.println("id = " + id);
        MenuDTO menuDTO = menuService.findById(id);
        model.addAttribute("menuDTO", menuDTO);
        System.out.println("modify 페이지로 가면서 ====" + menuDTO);


        List<String> storedFileName = menuDTO.getStoredFileName();
        List<File> fileList = new ArrayList<>();

        if (storedFileName != null) {
            for (String fileName : storedFileName) {
                File file = new File("C:\\springboot-img\\menu\\" + fileName);
                fileList.add(file);
                System.out.println("컨트롤러에서 파일 객체 ==== " + file);
            }
        }
        model.addAttribute("fileList", fileList);

        List<String> originalFileName = menuDTO.getOriginalFileName();
        model.addAttribute("orgFileName", originalFileName);
        return "menuModify";
    }


    @PostMapping(value ="/update")
    public String update (MenuDTO menuDTO) throws IOException {

        System.out.println("menuDTO.getDelFiles = " + menuDTO.getDelFiles());
        System.out.println("menuDTO = " + menuDTO );
        System.out.println("menuDTO.getId = " + menuDTO.getId());
        System.out.println("menuDTO.getMenuWriter = " + menuDTO.getMenuWriter());
        System.out.println("menuDTO.getStoredFileName = " + menuDTO.getStoredFileName());

        MenuDTO menu = menuService.update(menuDTO);


        List<String> delFiles = menuDTO.getDelFiles();
        System.out.println("컨트롤러로 넘어온 삭제파일 List : " + delFiles);
        if(delFiles != null) {
            for (String storedFileName : delFiles){
                menuService.deleteFile(storedFileName);
            }
        }

        return "redirect:/menu/list";
    }

    @GetMapping(value = "/getFiles/{id}" , produces = {"application/json; charset=utf-8"})
    public @ResponseBody ResponseEntity<List<MenuDTO>> showAttachFiles(@PathVariable("id") MenuEntity menuEntity){
        System.out.println("첨부파일 가져오는 컨트롤러 ======");
        return new ResponseEntity<List<MenuDTO>>(menuService.getAttachFileList(menuEntity), HttpStatus.OK);
    }

}

