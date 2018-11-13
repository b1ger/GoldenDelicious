package org.ontario.goldendelicious.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("org.ontario.goldendelicious.repositories")
@EnableTransactionManagement
public class DataBaseConfiguration {
}
