package com.health.keeper.controller;

import com.health.keeper.dto.MembershipDTO;
import com.health.keeper.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}

