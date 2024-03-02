package com.health.keeper.security.auth.oauth.provider;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;
    // ↑↓ PrincipalOauth2UserService의 loadUser 메서드에서 oauth2User.getAttributes() 받아오기위함
    public GoogleUserInfo (Map<String,Object> attributes){
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "Google";
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getPhone() {
        return (String) attributes.get("phone");
    }
}
