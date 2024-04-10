package com.health.keeper.controller;

import com.health.keeper.dto.MembershipDTO;
import com.health.keeper.dto.UserDTO;
import com.health.keeper.service.MembershipService;
import com.health.keeper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/lesson")
@RequiredArgsConstructor
@Controller
public class LessonController {


    @GetMapping("/main")
    public String showLessonReservation () {

        return "lessonReservation";

    }
}

