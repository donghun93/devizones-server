package com.devizones.web.core.oauth2.service;

import com.devizones.application.member.dto.MemberCreateCommand;
import com.devizones.application.member.dto.MemberDto;
import com.devizones.application.member.service.MemberCommandService;
import com.devizones.web.core.oauth2.converter.ProviderUserConverter;
import com.devizones.web.core.oauth2.converter.ProviderUserRequest;
import com.devizones.web.core.oauth2.model.CustomOAuth2User;
import com.devizones.web.core.oauth2.model.ProviderUser;
import com.devizones.web.core.oauth2.model.UserMapper;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class CustomOAuth2UserService extends AbstractOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserMapper userMapper;
    private final MemberCommandService memberCommandService;

    public CustomOAuth2UserService(ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter, UserMapper userMapper, MemberCommandService memberCommandService) {
        super(providerUserConverter);
        this.userMapper = userMapper;
        this.memberCommandService = memberCommandService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        // 인가 서버와 통신해서 사용자 정보를 가져온다.
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        ProviderUserRequest providerUserRequest = new ProviderUserRequest(clientRegistration, oAuth2User);

        ProviderUser providerUser = super.providerUser(providerUserRequest);
        MemberDto memberDto = registerOrLogin(providerUser);

        return CustomOAuth2User.create(oAuth2User, memberDto);
    }

    private MemberDto registerOrLogin(ProviderUser providerUser) {
        MemberCreateCommand command = userMapper.toCommand(providerUser);
        return memberCommandService.registerOrLogin(command);
    }
}
