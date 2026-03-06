package com.energy.userauth.infrastructure.configuration;

import com.energy.userauth.domain.service.IdentityLinkDomainService;
import com.energy.userauth.domain.service.UserDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public UserDomainService userDomainService() {
        return new UserDomainService();
    }

    @Bean
    public IdentityLinkDomainService identityLinkDomainService() {
        return new IdentityLinkDomainService();
    }
}
