package com.devizones.web.core.oauth2.converter;

import com.devizones.web.core.oauth2.model.GoogleUser;
import com.devizones.web.core.oauth2.model.ProviderUser;

import static com.devizones.web.core.oauth2.enums.SocialType.GOOGLE;

public class OAuth2GoogleProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        if (!providerUserRequest.getClientRegistration()
                                .getRegistrationId()
                                .equals(GOOGLE.getSocialName())) {
            return null;
        }

        return new GoogleUser(
                providerUserRequest.getOAuth2User(),
                providerUserRequest.getClientRegistration());
    }
}