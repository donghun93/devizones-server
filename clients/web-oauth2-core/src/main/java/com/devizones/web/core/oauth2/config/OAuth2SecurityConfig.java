package com.devizones.web.core.oauth2.config;

import com.devizones.web.core.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.devizones.web.core.oauth2.service.CustomOAuth2UserService;
import com.devizones.web.core.oauth2.service.HttpCookieOAuth2AuthorizationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(2)
public class OAuth2SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;


    @Bean
    public SecurityFilterChain oauth2FilterChain(HttpSecurity http) throws Exception {

//        http
//                .authorizeHttpRequests((authz) -> authz
//                        .mvcMatchers("/foo",
//                                "/bar",
//                                "/v3/api-docs/**",
//                                "/swagger-ui/**",
//                                "/swagger-ui.html")
//                        .permitAll()
//                        .anyRequest().authenticated())
//                .oauth2Client();

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;

        http
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http
                .oauth2Login(oauth2 -> oauth2
// front -> server로 보낼때 "http://localhost:8080/oauth2/authorization/google -> /oauth2/authorize
//                        .authorizationEndpoint(endpoint -> endpoint.baseUri("/oauth2/authorize"))
                .authorizationEndpoint(endpoint -> endpoint.baseUri("/oauth2/authorize"))
// 인증 서버에서 server로 리다이렉트 할 때 http://localhost:8778/oauth2/callback/{provider}
// spring.security.oauth2.client.registration.google.redirect_uri=http://localhost:8778/oauth2/callback/google
//                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
                .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
                .authorizationEndpoint(endpoint -> endpoint.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
                .userInfoEndpoint(endpoint -> endpoint.userService(customOAuth2UserService))
                .successHandler(oAuth2AuthenticationSuccessHandler)
                )
        ;

        return http.build();
    }

    @Bean
    public GrantedAuthoritiesMapper customAuthorityMapper() {
        return new CustomAuthorityMapper();
    }
}
