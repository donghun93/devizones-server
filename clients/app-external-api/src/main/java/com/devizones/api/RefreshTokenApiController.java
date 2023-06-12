package com.devizones.api;

import com.devizones.api.dto.BaseResponse;
import com.devizones.redis.refreshtoken.service.RefreshTokenService;
import com.devizones.web.core.token.jwt.JwtTokenDto;
import com.devizones.web.core.token.jwt.JwtTokenProvider;
import com.devizones.web.core.token.jwt.JwtTokenResponse;
import com.devizones.web.core.token.model.CurrentMember;
import com.devizones.web.core.token.model.MemberAccount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "token", description = "토큰 관련 api 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/token")
public class RefreshTokenApiController {
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;

//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "OK",
//                    content = @Content(schema = @Schema(implementation = JwtTokenResponse.class))),
//            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
//            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
//            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
//    })
    @Operation(
            summary = "access token 재발급",
            description = "access token 만료 시 재발급 할 수 있습니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @PostMapping("/reissue")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<JwtTokenResponse> reissue(@CurrentMember MemberAccount memberAccount) {
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateToken(memberAccount);
        refreshTokenService.save(
                memberAccount.getEmail(),
                jwtTokenDto.refreshToken(),
                jwtTokenDto.refreshTokenExpirationTime()
        );
        return BaseResponse.success(JwtTokenResponse.of(jwtTokenDto));
    }
}

