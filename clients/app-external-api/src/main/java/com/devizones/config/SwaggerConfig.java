package com.devizones.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@OpenAPIDefinition(
        info = @Info(
                title = "Devizones API",
                description = "Devizones API 명세",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                                    .scheme("bearer")
                                                    .bearerFormat("JWT"))
                        .addSecuritySchemes("oauth2", new SecurityScheme().type(SecurityScheme.Type.OAUTH2)
                                                                          .description("Oauth2 flow")
                                                                          .flows(new OAuthFlows()
                                                                                          .authorizationCode(new OAuthFlow()
                                                                                                  .authorizationUrl("/oauth2/authorize/google")
                                                                                                  .tokenUrl("/oauth2/callback")
//                                                                                                  .scopes(new Scopes()
//                                                                                                          .addString("email", "Allows read email")
//                                                                                                          .addString("profile", "Allows read profile")
//                                                                                                          .addString("openid", "Allows read openid")
//                                                                                                  )
                                                                                          )
                                                                                  )
                        ))
//                .addSecurityItem(new SecurityRequirement().addList("oauth2", Arrays.asList("email", "profile", "openid")));
                .addSecurityItem(new SecurityRequirement().addList("oauth2"));
    }

    @Bean
    public GroupedOpenApi tokenAPI() {
        String[] paths = {"/api/v1/token/**"};

        return GroupedOpenApi.builder()
                             .group("토큰")
                             .pathsToMatch(paths)
                             .build();
    }

    @Bean
    public GroupedOpenApi memberAPI() {
        String[] paths = {"/api/v1/members/**"};

        return GroupedOpenApi.builder()
                             .group("회원")
                             .pathsToMatch(paths)
                             .build();
    }

    @Bean
    public GroupedOpenApi postAPI() {
        String[] paths = {"/api/v1/posts/**"};

        return GroupedOpenApi.builder()
                             .group("게시글")
                             .pathsToMatch(paths)
                             .build();
    }
}
