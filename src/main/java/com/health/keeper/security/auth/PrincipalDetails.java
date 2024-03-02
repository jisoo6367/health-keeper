package com.health.keeper.security.auth;

import com.health.keeper.entity.UserEntity;
import com.health.keeper.security.auth.oauth.PrincipalOauth2UserService;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

//시큐리티 session에 들어갈 Authentication 객체는 User 오브젝트 타입, 얘는 UserDetails 타입 객체
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private UserEntity user;

    //일반 로그인 생성자
    public PrincipalDetails(UserEntity user){
        this.user = user;
    }

    private Map<String, Object> attributes;

    //OAuth 로그인 생성자
    public PrincipalDetails(UserEntity user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {

        return attributes;
    }

    //권한 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //계정 안 만료
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 안 잠김
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호 안 만료
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //활성화 여부
    @Override
    public boolean isEnabled() {
        // 1년동안 회원로그인을 안할시 휴면 계정 처리 하기로 했다면
        // user.getLoginDate(); (이건 User model에 추가하고)
        // 현재시간- 로그인시간 => 1년 초과하면 return false로 하면 됨
        return true;
    }

    @Override
    public String getName() {
        //return attribute.get("sub");
        return null;
    }
}
