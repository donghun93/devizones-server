package com.devizones.web.core.oauth2.model;

import com.devizones.application.member.dto.MemberCreateCommand;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public MemberCreateCommand toCommand(ProviderUser providerUser) {
        return new MemberCreateCommand(
                providerUser.getEmail(),
                providerUser.getUsername(),
                providerUser.getProvider()
        );
    }
}
