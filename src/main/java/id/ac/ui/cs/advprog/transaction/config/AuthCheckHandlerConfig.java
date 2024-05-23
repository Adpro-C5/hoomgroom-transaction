package id.ac.ui.cs.advprog.transaction.config;

import id.ac.ui.cs.advprog.transaction.handler.AuthCheckHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthCheckHandlerConfig {
    @Bean
    public AuthCheckHandler authCheckHandler() {
        return new AuthCheckHandler();
    }
}
