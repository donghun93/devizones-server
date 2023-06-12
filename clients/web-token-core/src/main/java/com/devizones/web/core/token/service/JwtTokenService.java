package com.devizones.web.core.token.service;

import com.devizones.redis.member.dto.MemberAccountCache;
import com.devizones.redis.member.service.MemberAccountCacheService;
import com.devizones.web.core.token.jwt.JwtTokenDto;
import com.devizones.web.core.token.jwt.JwtTokenException;
import com.devizones.web.core.token.model.MemberAccount;
import com.devizones.web.core.token.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.devizones.web.core.token.jwt.JwtTokenErrorCode.JWT_ACCESS_TOKEN_EXPIRED;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtTokenProvider tokenProvider;
    private final MemberAccountCacheService memberAccountCacheService;

    public MemberAccount getAuthentication(String token, boolean reissue) {
        try {
            tokenProvider.validateAccessToken(token);
            return getMemberAccount(token);
        } catch (JwtTokenException e) {
            if (e.getMemberErrorCode().equals(JWT_ACCESS_TOKEN_EXPIRED) && reissue) {
                return getMemberAccount(token);
            }
            throw e;
        }
    }

    public JwtTokenDto generateToken(MemberAccount memberAccount) {
        return tokenProvider.generateToken(memberAccount);
    }


    private MemberAccount getMemberAccount(String accessToken) {
        String email = tokenProvider.getEmail(accessToken);
        MemberAccountCache memberAccountCache = memberAccountCacheService.findByEmail(email);
        return MemberAccount.of(
                memberAccountCache.getId(),
                memberAccountCache.getEmail(),
                tokenProvider.getAuthorities(accessToken)
        );
    }
}
