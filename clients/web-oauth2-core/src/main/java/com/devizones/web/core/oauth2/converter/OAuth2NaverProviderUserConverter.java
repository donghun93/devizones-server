package com.devizones.web.core.oauth2.converter;

import com.devizones.web.core.oauth2.model.NaverUser;
import com.devizones.web.core.oauth2.model.ProviderUser;

import static com.devizones.web.core.oauth2.enums.SocialType.NAVER;

public class OAuth2NaverProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        if (!providerUserRequest.getClientRegistration()
                                .getRegistrationId()
                                .equals(NAVER.getSocialName())) {
            return null;
        }


        return new NaverUser(
                providerUserRequest.getOAuth2User(),
                providerUserRequest.getClientRegistration());
    }
}