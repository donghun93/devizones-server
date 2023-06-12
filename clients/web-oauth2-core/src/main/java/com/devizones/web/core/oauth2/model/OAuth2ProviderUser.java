package com.devizones.web.core.oauth2.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

public abstract class OAuth2ProviderUser implements ProviderUser {

    private final Map<String, Object> attributes;
    private final OAuth2User oAuth2User;
    private final ClientRegistration clientRegistration;
    private boolean isCertificated;

    public OAuth2ProviderUser(Map<String, Object> attributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        this.attributes = attributes;
        this.oAuth2User = oAuth2User;
        this.clientRegistration = clientRegistration;
    }


    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getProvider() {
        return clientRegistration.getRegistrationId();
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
//        return oAuth2User.getAuthorities().stream().map(
//                grantedAuthority -> new SimpleGrantedAuthority(grantedAuthority.getAuthority())
//        ).collect(Collectors.toList());
        return oAuth2User.getAuthorities()
                         .stream()
                         .toList();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
