package com.health.keeper.controller;

import com.health.keeper.entity.UserEntity;
import com.health.keeper.repository.UserRepository;
import com.health.keeper.security.auth.PrincipalDetails;
import com.health.keeper.service.UserService;
import jdk.jfr.Unsigned;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class LoginController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginController (BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    UserRepository userRepository;


    @GetMapping("/loginForm")
    public String loginF(){
        return "/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinF(){
        return "/joinForm";
    }

    @PostMapping("/join")
    public String join (@ModelAttribute UserEntity user) {

        System.out.println("user = " + user);
        System.out.println("유저네임: " + user.getUsername());

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return "redirect:loginForm";
    }

    // 일반 로그인 정보 가져오는 방법1
    @GetMapping("test/login")
    public @ResponseBody String testLogin(Authentication authentication){

        System.out.println("authentication = " + authentication); //리턴타입: object
        //UsernamePasswordAuthenticationToken [Principal=PrincipalDetails(user=UserEntity(id=1, username=jisoo, password=$2a$10$F7MaDNxN0GnDhk.svptbeO1jrShU7oPMfhD9QVPnPcSLWPE2iRCmm, email=jisoo@nate.com, phone=01099998888, role=ROLE_ADMIN, provider=null, providerId=null, createDate=2024-03-01 21:37:28.466474), attributes=null), Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null], Granted Authorities=[com.health.keeper.security.auth.PrincipalDetails$1@3c377bba]]
        System.out.println(".getPrincipal() = " + authentication.getPrincipal());
        //PrincipalDetails(user=UserEntity(id=1, username=jisoo, password=$2a$10$F7MaDNxN0GnDhk.svptbeO1jrShU7oPMfhD9QVPnPcSLWPE2iRCmm, email=jisoo@nate.com, phone=01099998888, role=ROLE_ADMIN, provider=null, providerId=null, createDate=2024-03-01 21:37:28.466474), attributes=null)

        //상세 정보 뽑으려면
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println(".getUser() = " + principalDetails.getUser());
        //UserEntity(id=1, username=jisoo, password=$2a$10$F7MaDNxN0GnDhk.svptbeO1jrShU7oPMfhD9QVPnPcSLWPE2iRCmm, email=jisoo@nate.com, phone=01099998888, role=ROLE_ADMIN, provider=null, providerId=null, createDate=2024-03-01 21:37:28.466474)

        return "세션 정보 확인";
    }

    // 일반 로그인 정보 가져오는 방법2
//    @GetMapping("test/login")
//    public @ResponseBody String testLogin(@AuthenticationPrincipal PrincipalDetails userDetails){
//
//        System.out.println("userDetails = " + userDetails);
//        //PrincipalDetails(user=UserEntity(id=1, username=jisoo, password=$2a$10$F7MaDNxN0GnDhk.svptbeO1jrShU7oPMfhD9QVPnPcSLWPE2iRCmm, email=jisoo@nate.com, phone=01099998888, role=ROLE_ADMIN, provider=null, providerId=null, createDate=2024-03-01 21:37:28.466474), attributes=null
//        System.out.println(".getUsername() = " + userDetails.getUsername());
//        //jisoo
//        return "세션 정보 확인";
//    }

    // 소셜 로그인 정보 가져오는 방법1
//    @GetMapping("/test/oauth/login")
//    public @ResponseBody String testOauthLogin(Authentication authentication){
//
//        //oauth는 PrincipalDetails로 다운캐스팅하면 오류남
//        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//        System.out.println("oauth2User = "+ oauth2User);
//        //Name: [113341760605204311861], Granted Authorities: [[OAUTH2_USER, SCOPE_https://www.googleapis.com/auth/userinfo.email, SCOPE_https://www.googleapis.com/auth/userinfo.profile, SCOPE_openid]], User Attributes: [{sub=113341760605204311861, name=홍지수, given_name=지수, family_name=홍, picture=https://lh3.googleusercontent.com/a/ACg8ocKiSpCZIJTJlb37vzWuanMgGixX8dq01r7dTkL9N3Qk=s96-c, email=jisunalazzang@gmail.com, email_verified=true, locale=ko}]
//        System.out.println(".getAttributes() = "+ oauth2User.getAttributes());
//        //{sub=113341760605204311861, name=홍지수, given_name=지수, family_name=홍, picture=https://lh3.googleusercontent.com/a/ACg8ocKiSpCZIJTJlb37vzWuanMgGixX8dq01r7dTkL9N3Qk=s96-c, email=jisunalazzang@gmail.com, email_verified=true, locale=ko}
//
//            return "OAuth 세션 정보 확인";
//    }

    //소셜 로그인 정보 가져오는 방법2
    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(@AuthenticationPrincipal OAuth2User oauth){

        System.out.println("oauth2User = " + oauth);
        //Name: [113341760605204311861], Granted Authorities: [[OAUTH2_USER, SCOPE_https://www.googleapis.com/auth/userinfo.email, SCOPE_https://www.googleapis.com/auth/userinfo.profile, SCOPE_openid]], User Attributes: [{sub=113341760605204311861, name=홍지수, given_name=지수, family_name=홍, picture=https://lh3.googleusercontent.com/a/ACg8ocKiSpCZIJTJlb37vzWuanMgGixX8dq01r7dTkL9N3Qk=s96-c, email=jisunalazzang@gmail.com, email_verified=true, locale=ko}]
        System.out.println(".getAuthorities() = " + oauth.getAttributes());
        //{sub=113341760605204311861, name=홍지수, given_name=지수, family_name=홍, picture=https://lh3.googleusercontent.com/a/ACg8ocKiSpCZIJTJlb37vzWuanMgGixX8dq01r7dTkL9N3Qk=s96-c, email=jisunalazzang@gmail.com, email_verified=true, locale=ko}

        return "OAuth 세션 정보 확인";
    }


    @GetMapping("/user")
    public String user (@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        System.out.println("principalDetails.getUser() = " + principalDetails.getUser());
        //UserEntity(id=6, username=google_113341760605204311861, password=HealthKeeper, email=jisunalazzang@gmail.com, phone=null, role=ROLE_USER, provider=google, providerId=113341760605204311861, createDate=2024-03-02 22:51:03.851916)

        model.addAttribute("info", principalDetails.getUser());

        return "/user";
    }

    @PostMapping(value = "/confirm" , produces = {"application/json; charset=utf-8"})
    public @ResponseBody ResponseEntity<Boolean> confirmUsername(@PathVariable("username") String username){
        System.out.println("컨트롤러오긴함");

        boolean result = true;

        if(username.trim().isEmpty()) {
            System.out.println("===가져온 username : " + username);
            result = false;
        } else {
            System.out.println("===엘스문에서 username : " + username);
            if (userRepository.countByUsername(username)) {
                result = false;
            } else {
                result = true;
            }
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
