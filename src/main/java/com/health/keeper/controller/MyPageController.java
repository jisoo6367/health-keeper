package com.health.keeper.controller;

import com.health.keeper.dto.UserDTO;
import com.health.keeper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/mypage")
@RequiredArgsConstructor
@Controller
public class MyPageController {

    private final UserService userService;

    @GetMapping("/main")
    public String showMyPage(Principal principal, Model model){
        System.out.println("principal.getName() : " + principal.getName());

        UserDTO userDTO = userService.findByUsername(principal);
        System.out.println("유저 컨트롤러에서 main 결과 : " + userDTO);

        model.addAttribute("userDTO", userDTO);
        return "mypage";
    }

    @PostMapping("/update")
    public String update (UserDTO userDTO) {
        System.out.println("수정 요청한 userDTO = " + userDTO);

        UserDTO user = userService.update(userDTO);
        System.out.println("컨트롤러에서 update 결과 user : " + user);
        return "mypage";
    }
}

