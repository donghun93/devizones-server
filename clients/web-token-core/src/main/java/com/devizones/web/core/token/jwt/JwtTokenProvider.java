package com.devizones.web.core.token.jwt;

import com.devizones.web.core.token.config.JwtTokenProperties;
import com.devizones.web.core.token.model.MemberAccount;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.devizones.web.core.token.jwt.JwtTokenErrorCode.*;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final Long accessTokenValidSecondsTime;
    private final Long refreshTokenValidSecondsTime;

    public JwtTokenProvider(JwtTokenProperties jwtTokenProperties) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtTokenProperties.getTokenSecret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidSecondsTime = jwtTokenProperties.getAccessTokenExpirationSeconds() * 1000;
        this.refreshTokenValidSecondsTime = jwtTokenProperties.getRefreshTokenExpirationSeconds() * 1000;
    }

    public JwtTokenDto generateToken(MemberAccount memberAccount) {
        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + accessTokenValidSecondsTime);

        String accessToken = Jwts.builder()
                                 .setClaims(createClaims(memberAccount))
                                 .setIssuer("devizones.com")
                                 .setExpiration(accessTokenExpiresIn)
                                 .signWith(key, SignatureAlgorithm.HS256)
                                 .compact();

        // Refresh Token 생성
        Date refreshTokenExpiresIn = new Date(now + refreshTokenValidSecondsTime);
        String refreshToken = Jwts.builder()
                                  .setExpiration(refreshTokenExpiresIn)
                                  .signWith(key, SignatureAlgorithm.HS256)
                                  .compact();

        return JwtTokenDto.builder()
                          .grantType("Bearer")
                          .accessToken(accessToken)
                          .refreshToken(refreshToken)
                          .refreshTokenExpirationTime(this.refreshTokenValidSecondsTime)
                          .build();
    }

    /**
     * sub (subject)   - 사용자의 고유 식별자나 사용자의 아이디 값을 넣습니다.
     * iss (issuer)    - 토큰을 발급한 서버의 식별자 값을 넣습니다.
     * aud (audience)  - 토큰을 사용할 수신자의 식별자 값을 넣습니다.
     * iat (issued at) - 토큰이 발급된 시간을 설정합니다.
     */
    public Claims createClaims(MemberAccount memberAccount) {
        Claims claims = Jwts.claims();

        claims.setSubject(memberAccount.getEmail());
        claims.setAudience(memberAccount.getId().toString());
        claims.setIssuedAt(new Date());

//        claims.put("roles", oAuth2User.getAuthorities()
//                                      .stream()
//                                      .map(GrantedAuthority::getAuthority)
//                                      .collect(Collectors.toList()));
        claims.put("roles", memberAccount.getAuthorities());
        return claims;
    }

    public void validateAccessToken(String accessToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new JwtTokenException(JWT_ACCESS_TOKEN_MALFORMED_ERROR); // 잘못된 JWT 서명
        } catch (ExpiredJwtException e) {
            throw new JwtTokenException(JWT_ACCESS_TOKEN_EXPIRED); // 만료된 JWT 토큰
        } catch (UnsupportedJwtException e) {
            throw new JwtTokenException(JWT_UNSUPPORTED_TOKEN); // 지원되지 않는 JWT 토큰
        } catch (IllegalArgumentException e) {
            throw new JwtTokenException(JWT_CLAIMS_EMPTY); // JWT 토큰이 잘못
        } catch (Exception e) {
            throw new JwtTokenException(JWT_INVALID_TOKEN);
        }
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public String getEmail(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        String subject = claims.getSubject();
        if (!StringUtils.hasText(subject)) {
            throw new JwtTokenException(JWT_MEMBER_NOT_FOUND);
        }
        return subject;
    }


    public List<String> getAuthorities(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);


        Class<List<String>> clazz = (Class) List.class;
        return claims.get("roles", clazz);
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(key)
                       .build()
                       .parseClaimsJws(accessToken)
                       .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}