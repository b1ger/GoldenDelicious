package org.ontario.goldendelicious.config;

import org.ontario.goldendelicious.repositories.StaffRepository;
import org.ontario.goldendelicious.security.StaffDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsServiceConfiguration {

    private final StaffRepository staffRepository;

    @Autowired
    public UserDetailsServiceConfiguration(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new StaffDetailsService(staffRepository);
    }
}
