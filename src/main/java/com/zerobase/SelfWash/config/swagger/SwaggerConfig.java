package com.zerobase.SelfWash.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    // Security 스키마 정의
    SecurityScheme securityScheme = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT");

    // Security 요구사항 추가
    SecurityRequirement securityRequirement = new SecurityRequirement()
        .addList("BearerAuth");

    return new OpenAPI()
        .info(new Info().title("OpenAPI definition").version("1.0"))
        .addSecurityItem(securityRequirement)
        .components(new io.swagger.v3.oas.models.Components()
            .addSecuritySchemes("BearerAuth", securityScheme));
  }
}
