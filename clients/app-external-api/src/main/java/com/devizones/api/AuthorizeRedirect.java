package com.devizones.api;

import com.devizones.api.dto.BaseResponse;
import com.devizones.web.core.token.jwt.JwtTokenDto;
import com.devizones.web.core.token.jwt.JwtTokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthorizeRedirect {

    private final ObjectMapper objectMapper;

    @RequestMapping("/authorize/redirect")
    public BaseResponse<JwtTokenRedirect> redirect(@RequestParam String token) {

        JwtTokenRedirect jwtTokenRedirect = null;
        try {
            jwtTokenRedirect = objectMapper.readValue(token, JwtTokenRedirect.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return BaseResponse.success(jwtTokenRedirect);
    }

    @Data
    static class JwtTokenRedirect {
        private String grantType;
        private String accessToken;
        private String refreshToken;
    }
}
