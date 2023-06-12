package com.devizones.web.core.oauth2.model;

import com.devizones.application.member.dto.MemberDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OidcUser {
    private final OAuth2User delegate;

    private final Map<String, Object> additionalAttributes;

    private CustomOAuth2User(OAuth2User delegate) {
        this.delegate = delegate;
        this.additionalAttributes = new HashMap<>();
    }

    public static CustomOAuth2User create(OAuth2User oAuth2User, MemberDto memberDto) {
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(oAuth2User);
        customOAuth2User.addAttribute("aud", memberDto.id());
        customOAuth2User.addAttribute("sub", memberDto.email());
        return customOAuth2User;
    }

    public Long getId() {
        return getAttribute("aud");
    }

    public String getEmail() {
        return getAttribute("sub");
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>(delegate.getAttributes());
        attributes.putAll(additionalAttributes);
        return attributes;
    }

    public void addAttribute(String name, Object value) {
        additionalAttributes.put(name, value);
    }

    public void removeAttribute(String name) {
        additionalAttributes.remove(name);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return delegate.getAuthorities();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public Map<String, Object> getClaims() {
        if(delegate instanceof OidcUser) {
            return ((OidcUser) delegate).getClaims();
        }
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        if(delegate instanceof OidcUser) {
            return ((OidcUser) delegate).getUserInfo();
        }
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        if(delegate instanceof OidcUser) {
            return ((OidcUser) delegate).getIdToken();
        }
        return null;
    }
}
