package id.ac.ui.cs.advprog.transaction.config;

import id.ac.ui.cs.advprog.transaction.auth.AuthHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class AuthHelperConfiguration {
    @Bean
    public AuthHelper authHelper() {
        return new AuthHelper();
    }
}
