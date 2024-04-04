package com.health.keeper.controller;

import com.health.keeper.dto.UserDTO;
import com.health.keeper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@RequestMapping("/exercise")
@Controller
public class ExerciseController {

    private final UserService userService;

    public ExerciseController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/guide")
    public String showExerciseGuide(Principal principal, Model model){
        UserDTO userDTO = userService.findByUsername(principal);

        model.addAttribute("userDTO", userDTO);
        return "exerciseGuide";
    }


}
