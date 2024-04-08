package com.health.keeper.controller;

import com.health.keeper.dto.MembershipDTO;
import com.health.keeper.dto.UserDTO;
import com.health.keeper.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@RequestMapping("/manager")
@RequiredArgsConstructor
@Controller
public class ManagerController {

    @GetMapping("/main")
    public String showManagerPage () {


        return "/manager";
    }

    @PostMapping("/save")
    public String membershipSave(UserDTO user, MembershipDTO membership){

        System.out.println("폰번호 받아오나..? : " + user.getPhone());
        System.out.println("membership 정보 : " + membership);
        //폰정보로 userId가져와서 그걸로 membership테이블에 insert 해줘야함

        return "/manager";
    }
}

