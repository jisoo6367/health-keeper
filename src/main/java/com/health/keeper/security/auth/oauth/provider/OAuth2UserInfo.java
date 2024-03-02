package com.health.keeper.security.auth.oauth.provider;

public interface OAuth2UserInfo {

    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
    String getPhone();

}
