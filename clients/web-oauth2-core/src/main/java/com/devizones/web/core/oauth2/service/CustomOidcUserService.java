package com.devizones.web.core.oauth2.service;

import com.devizones.application.member.dto.MemberCreateCommand;
import com.devizones.application.member.dto.MemberDto;
import com.devizones.application.member.service.MemberCommandService;
import com.devizones.web.core.oauth2.converter.ProviderUserConverter;
import com.devizones.web.core.oauth2.converter.ProviderUserRequest;
import com.devizones.web.core.oauth2.model.CustomOAuth2User;
import com.devizones.web.core.oauth2.model.ProviderUser;
import com.devizones.web.core.oauth2.model.UserMapper;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends AbstractOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final UserMapper userMapper;
    private final MemberCommandService memberCommandService;

    public CustomOidcUserService(ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter, UserMapper userMapper, MemberCommandService memberCommandService) {
        super(providerUserConverter);
        this.userMapper = userMapper;
        this.memberCommandService = memberCommandService;
    }


    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService = new OidcUserService();

        // 인가 서버와 통신해서 사용자 정보를 가져온다.
        OidcUser oidcUser = oidcUserService.loadUser(userRequest);


        ProviderUserRequest providerUserRequest = new ProviderUserRequest(clientRegistration, oidcUser);
        ProviderUser providerUser = super.providerUser(providerUserRequest);


        MemberCreateCommand command = userMapper.toCommand(providerUser);
        MemberDto memberDto = memberCommandService.registerOrLogin(command);

        return CustomOAuth2User.create(oidcUser, memberDto);
    }
}
