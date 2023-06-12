package com.devizones.web.core.token.config;

import com.devizones.web.core.token.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(3)
public class JwtTokenSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain jwtTokenFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(request ->
//                        request
//                                .requestMatchers("/api/v1/token/reissue")
//                                .hasRole("USER")
//                                .anyRequest()
//                                .permitAll()
//                );

        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
