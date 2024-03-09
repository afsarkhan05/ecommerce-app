package com.zubi.ecommerce.product.config;

import com.zubi.ecommerce.auth.common.filter.AuthTokenFilter;
import com.zubi.ecommerce.common.exceptions.GlobalExceptionHandler;
import com.zubi.ecommerce.product.utils.AppConstants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Slf4j
@EnableWebSecurity
public class ApplicationConfig {

    private AuthTokenFilter authTokenFilter;
    @Autowired
    public void authFilter(AuthTokenFilter authTokenFilter) {
        this.authTokenFilter = authTokenFilter;
        //return new AuthTokenFilter();
    }

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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                //.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/*", "/v3/api-docs", "/actuator/**").permitAll()

                                .requestMatchers("/api/test/**").permitAll()
                                .anyRequest().authenticated()
                );

        //http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
