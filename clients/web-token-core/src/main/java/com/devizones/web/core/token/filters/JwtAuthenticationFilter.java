package com.devizones.web.core.token.filters;

import com.devizones.web.core.token.model.MemberAccount;
import com.devizones.web.core.token.model.MemberAccountAdapter;
import com.devizones.web.core.token.service.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = getHeaderAccessToken(request);

            if(hasText(accessToken)) {
                MemberAccount memberAccount = jwtTokenService.getAuthentication(accessToken, isReissue(request));
                setSecurityContextHolderAuthentication(memberAccount);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            Result result = new Result("fail", e.getMessage());
            new ObjectMapper().writeValue(response.getWriter(), result);
        }

        filterChain.doFilter(request, response);
    }

    private void setSecurityContextHolderAuthentication(MemberAccount memberAccount) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new MemberAccountAdapter(memberAccount), "",
                memberAccount.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private boolean isReissue(HttpServletRequest request) {
        return request.getServletPath().equals("/api/v1/token/reissue");
    }

    private String getHeaderAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader(AUTHORIZATION);
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.replace("Bearer ", "");
        }
        return accessToken;
    }

    @Getter
    @NoArgsConstructor
    static class Result {
        private String status;
        private String message;

        public Result(String status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
