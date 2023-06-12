package com.devizones.web.core.oauth2.handler;

import com.devizones.redis.refreshtoken.service.RefreshTokenService;
import com.devizones.web.core.oauth2.config.OAuth2Properties;
import com.devizones.web.core.oauth2.model.CustomOAuth2User;
import com.devizones.web.core.oauth2.service.HttpCookieOAuth2AuthorizationRequestRepository;
import com.devizones.web.core.oauth2.util.CookieUtils;
import com.devizones.web.core.token.jwt.JwtTokenDto;
import com.devizones.web.core.token.jwt.JwtTokenResponse;
import com.devizones.web.core.token.model.MemberAccount;
import com.devizones.web.core.token.service.JwtTokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.devizones.web.core.oauth2.service.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final OAuth2Properties oAuth2Properties;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final ObjectMapper objectMapper;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                                                  .map(Cookie::getValue);

        boolean pass = false;
        if(redirectUri.isPresent()) {
            // - http://localhost:8080/swagger-ui/oauth2-redirect.html
            if(redirectUri.get().contains("swagger-ui/oauth2-redirect.html")) {
                pass = true;
            }
        }

        if (!pass && redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            //throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
            throw new RuntimeException();
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        log.info("targetUrl: {}", targetUrl);
        if(pass) targetUrl = "/authorize/redirect";

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        MemberAccount memberAccount = MemberAccount.of(
                oAuth2User.getId(),
                oAuth2User.getEmail(),
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
        );

        JwtTokenDto jwtTokenDto = jwtTokenService.generateToken(memberAccount);

        // TODO: Refresh Token Redis Save
        refreshTokenService.save(memberAccount.getEmail(), jwtTokenDto.refreshToken(), jwtTokenDto.refreshTokenExpirationTime());

        String token;
        try {
            token = objectMapper.writeValueAsString(JwtTokenResponse.of(jwtTokenDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return UriComponentsBuilder.fromUriString(targetUrl)
                                   .queryParam("token", token)
                                   .build()
                                   .toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return oAuth2Properties.getAuthorizedRedirectUris()
                               .stream()
                               .anyMatch(authorizedRedirectUri -> {
                                   // Only validate host and port. Let the clients use different paths if they want to
                                   URI authorizedURI = URI.create(authorizedRedirectUri);
                                   return authorizedURI.getHost()
                                                       .equalsIgnoreCase(clientRedirectUri.getHost())
                                           && authorizedURI.getPort() == clientRedirectUri.getPort();
                               });
    }
}