package com.bwgjoseph.springsecuritycustomdslbug;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

import jakarta.servlet.http.HttpServletRequest;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity(debug = true)
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http, RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter) throws Exception {
        return http
                .addFilter(requestHeaderAuthenticationFilter)
                // Authorization is added to make it clear what's intended by this filter chain
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()
            )
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(new MyPreAuthenticationProvider());
    }

    @Bean
    public RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter = new RequestHeaderAuthenticationFilter();
        requestHeaderAuthenticationFilter.setPrincipalRequestHeader("X-User");
        requestHeaderAuthenticationFilter.setExceptionIfHeaderMissing(true);
        requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager);
        requestHeaderAuthenticationFilter.setAuthenticationDetailsSource(authenticationDetailsSource);

        RequestAttributeSecurityContextRepository securityContextRepository =
            new RequestAttributeSecurityContextRepository();
        requestHeaderAuthenticationFilter.setSecurityContextRepository(securityContextRepository);

        return requestHeaderAuthenticationFilter;
    }
}
