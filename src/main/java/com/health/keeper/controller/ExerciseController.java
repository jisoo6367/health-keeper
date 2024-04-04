package com.health.keeper.controller;

import com.health.keeper.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/exercise")
@Controller
public class ExerciseController {

    @GetMapping("/guide")
    public String showExerciseGuide(){
        return "exerciseGuide";
    }

    @PostMapping("/update")
    public String interestUpdate(UserDTO userDTO){
        System.out.println("관심운동 등록 컨트롤러 도착");
        System.out.println("interest = " + userDTO.getInterest());

        return "redirect:/mypage";

    }
}
