package com.devizones.web.core.oauth2.config;

import com.devizones.web.core.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.devizones.web.core.oauth2.service.CustomOAuth2UserService;
import com.devizones.web.core.oauth2.service.HttpCookieOAuth2AuthorizationRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(2)
public class OAuth2SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final CorsProperties corsProperties;

    @Bean
    public SecurityFilterChain oauth2FilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;


//        http
//                .authorizeHttpRequests(request ->
//                        request
//                                .requestMatchers(HttpMethod.OPTIONS, "/**")
//                                .permitAll()
//                                .requestMatchers("/swagger-ui/**", "/api-docs/**")
//                                .permitAll()
//                );

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

    CorsConfigurationSource corsConfigurationSource() {
        final var configuration = new CorsConfiguration();

        log.info("CORS PROPERTIES: {}", corsProperties);
        // configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(corsProperties.getOrigins());
        configuration.setAllowedMethods(corsProperties.getMethods());
        configuration.setAllowedHeaders(corsProperties.getAllowedHeaders());
        configuration.setExposedHeaders(corsProperties.getExposedHeaders());

//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3001"));
//        configuration.setAllowedMethods(corsProperties.getMethods());
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setExposedHeaders(List.of("*"));

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
