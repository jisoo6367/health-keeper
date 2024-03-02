package com.health.keeper.security.auth;

import com.health.keeper.entity.UserEntity;
import com.health.keeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //이 서비스 함수가 종료될 때 @AuthenticationPrincipal 어노테이션이 만들어짐

        //로그인 하는 폼태그 안 인풋의 name이 username이 아닌 경우에는 못 받아오기 때문에
        //securityConfig 에서 .usernameParameter("username2") 이렇게 바꿔줘야함!

        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity != null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
