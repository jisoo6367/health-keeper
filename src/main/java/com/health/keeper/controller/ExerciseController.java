package com.health.keeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/exercise")
@Controller
public class ExerciseController {

    @GetMapping("/guide")
    public String showExerciseGuide(){
        return "exerciseGuide";
    }
}
