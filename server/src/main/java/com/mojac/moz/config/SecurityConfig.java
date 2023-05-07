package com.mojac.moz.config;

import com.mojac.moz.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final AuthenticationFilter authenticationFilter;
    private final OAuthService oAuthService;
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2Login()
                .successHandler(authenticationSuccessHandler)
                .userInfoEndpoint()
                .userService(oAuthService);

        http.addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class);

        return http.build();
    }
}
