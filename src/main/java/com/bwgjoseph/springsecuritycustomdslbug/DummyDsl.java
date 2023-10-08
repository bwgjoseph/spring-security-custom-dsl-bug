package com.bwgjoseph.springsecuritycustomdslbug;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class DummyDsl extends AbstractHttpConfigurer<DummyDsl, HttpSecurity> {

    @Override
    public void init(HttpSecurity http) throws Exception {
        http
            .formLogin(AbstractHttpConfigurer::disable);


        // one possible alternative is to register RequestHeaderAuthenticationFilter at DummyDsl
        // ApplicationContext context = http.getSharedObject(ApplicationContext.class);

        // AuthenticationManager authenticationManager = context.getBean(AuthenticationManager.class);
        // // actually i'm not sure how to grab generic type of bean from context correctly
        // AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = context.getBean(AuthenticationDetailsSource.class);

        // RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter = new RequestHeaderAuthenticationFilter();
        // requestHeaderAuthenticationFilter.setPrincipalRequestHeader("X-User");
        // requestHeaderAuthenticationFilter.setExceptionIfHeaderMissing(true);
        // requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager);
        // requestHeaderAuthenticationFilter.setAuthenticationDetailsSource(authenticationDetailsSource);

        // RequestAttributeSecurityContextRepository securityContextRepository =
        //     new RequestAttributeSecurityContextRepository();
        // requestHeaderAuthenticationFilter.setSecurityContextRepository(securityContextRepository);
    }

    public static DummyDsl dummyDsl() {
        return new DummyDsl();
    }

    public HttpSecurity build() {
        return super.getBuilder();
    }
}
