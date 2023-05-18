package com.server.server.config;

import com.netflix.discovery.AbstractDiscoveryClientOptionalArgs;
import org.springframework.context.annotation.Bean;

public class AppConfig {
    @Bean
    public AbstractDiscoveryClientOptionalArgs discoveryClientOptionalArgs() {
        return new MyDiscoveryClientOptionalArgs();
    }

    private static class MyDiscoveryClientOptionalArgs extends AbstractDiscoveryClientOptionalArgs {
        // Custom implementation here
    }
}
