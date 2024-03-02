package com.health.keeper.security.auth.oauth;

import com.health.keeper.entity.UserEntity;
import com.health.keeper.repository.UserRepository;
import com.health.keeper.security.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

//    private final BCryptPasswordEncoder;
//
//    public PrincipalOauth2UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }

    @Autowired
    private UserRepository userRepository;

    //구글 로그인 후처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //userRequest.getClientRegistration(); -> {registrationId='google', clientId='234356...}
        //userRequest.getAccessToken(); -> @4d3e1df0
        //super.loadUser(userRequest).getAuthorities(); -> {sub=1235...}

        OAuth2User oauth2User = super.loadUser(userRequest);
        System.out.println("oauth2User.getAttributes() = " + oauth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oauth2User.getAttribute("sub");
        String username = provider + "_" + providerId;
        String password = "HealthKeeper";
        String email = oauth2User.getAttribute("email");
        String phone = oauth2User.getAttribute("phone");
        String role = "ROLE_USER";

        //회원가입 되어있는지확인
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null){
            userEntity= UserEntity.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .phone(phone)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }

        //*** 이 리턴이 다시 뜯어보기 ***
        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
    }
}
