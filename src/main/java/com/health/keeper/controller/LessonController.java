package com.health.keeper.controller;

import com.health.keeper.dto.LessonDTO;
import com.health.keeper.dto.MembershipDTO;
import com.health.keeper.dto.UserDTO;
import com.health.keeper.service.LessonService;
import com.health.keeper.service.MembershipService;
import com.health.keeper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/lesson")
@RequiredArgsConstructor
@Controller
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/main")
    public String showLessonReservation () {
        return "lessonReservation";
    }

    @GetMapping("/list/{selectedDate}")
    public String findById (@PathVariable("selectedDate")String reservationDate, Model model) {


        List<LessonDTO> lessonDTOList = lessonService.findById(reservationDate);

        System.out.println("이거왜안나와 : " +lessonDTOList);

        model.addAttribute("lesson", lessonDTOList);

        return "lessonReservation";
    }


}

