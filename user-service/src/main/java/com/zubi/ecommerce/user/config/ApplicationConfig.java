package com.zubi.ecommerce.user.config;

import com.zubi.ecommerce.common.exceptions.GlobalExceptionHandler;
import com.zubi.ecommerce.user.utils.AppConstants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ApplicationConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(AppConstants.API_KEY, new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name(AppConstants.AUTHORIZATION)))
        .info(info());
    }

    private Info info(){
        return new Info()
                .title(AppConstants.TITLE)
                .description(AppConstants.DESC)
                .version(AppConstants.VERSION);
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler();
    }
}
