package com.bwgjoseph.springsecuritycustomdslbug;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity(debug = true)
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http, RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter) throws Exception {
        return http
            .addFilter(requestHeaderAuthenticationFilter)
            // i expect it to be "activated" as it was configured in DummyDsl
            // but i have to enable this, and then no session will be stored
            // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(new MyPreAuthenticationProvider());
    }

    @Bean
    public RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter(AuthenticationManager authenticationManager) {
        RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter = new RequestHeaderAuthenticationFilter();
        requestHeaderAuthenticationFilter.setPrincipalRequestHeader("X-User");
        requestHeaderAuthenticationFilter.setExceptionIfHeaderMissing(true);
        requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager);

        return requestHeaderAuthenticationFilter;
    }
}
