package com.health.keeper.controller;

import com.health.keeper.dto.MembershipDTO;
import com.health.keeper.service.MembershipService;
import com.health.keeper.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/membership")
@RequiredArgsConstructor
@Controller
public class MembershipController {

    private final MembershipService membershipService;


    @GetMapping("/main")
    public String showMembership (@AuthenticationPrincipal Principal principal, Model model) {

        List<MembershipDTO> membershipDTOList = membershipService.findByUserId(principal);
        System.out.println("컨트롤러 결과 : "+ membershipDTOList);


        if (membershipDTOList.isEmpty()){
            model.addAttribute("membership", null);
        } else {
            model.addAttribute("membership", membershipDTOList);
        }


        return "/membership";
    }
}

