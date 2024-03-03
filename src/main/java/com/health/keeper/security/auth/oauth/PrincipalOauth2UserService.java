package com.health.keeper.security.auth.oauth;

import com.health.keeper.entity.UserEntity;
import com.health.keeper.repository.UserRepository;
import com.health.keeper.security.auth.PrincipalDetails;
import com.health.keeper.security.auth.oauth.provider.FacebookUserInfo;
import com.health.keeper.security.auth.oauth.provider.GoogleUserInfo;
import com.health.keeper.security.auth.oauth.provider.NaverUserInfo;
import com.health.keeper.security.auth.oauth.provider.OAuth2UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

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
    //이 서비스 함수가 종료될 때 @AuthenticationPrincipal 어노테이션이 만들어짐
        //userRequest.getClientRegistration(); -> {registrationId='google', clientId='234356...}
        //userRequest.getAccessToken(); -> @4d3e1df0
        //super.loadUser(userRequest).getAuthorities(); -> {sub=1235...}

        OAuth2User oauth2User = super.loadUser(userRequest);
        System.out.println("oauth2User.getAttributes() = " + oauth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map) oauth2User.getAttributes().get("response"));
            //naver는 {resultcode=00, message=success, response{id=14113, email=@@, name=jisoo}
            //한번더 감싸져있기때문에 Map타입의 결과에서 response 키 값을 꺼내와야함
        }else{
            System.out.println("구글/페이스북 외 소셜 로그인 요청");
        }


        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String password = "HealthKeeper";
        String email = oAuth2UserInfo.getEmail();
        String phone = oAuth2UserInfo.getPhone();
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

        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
    }
}
