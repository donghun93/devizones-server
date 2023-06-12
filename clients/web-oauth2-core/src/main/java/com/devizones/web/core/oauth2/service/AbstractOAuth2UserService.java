package com.devizones.web.core.oauth2.service;

import com.devizones.web.core.oauth2.converter.ProviderUserConverter;
import com.devizones.web.core.oauth2.converter.ProviderUserRequest;
import com.devizones.web.core.oauth2.model.GoogleUser;
import com.devizones.web.core.oauth2.model.KakaoUser;
import com.devizones.web.core.oauth2.model.NaverUser;
import com.devizones.web.core.oauth2.model.ProviderUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public abstract class AbstractOAuth2UserService {

    private final ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

    protected ProviderUser providerUser(ProviderUserRequest providerUserRequest) {
        return providerUserConverter.converter(providerUserRequest);
    }
}
