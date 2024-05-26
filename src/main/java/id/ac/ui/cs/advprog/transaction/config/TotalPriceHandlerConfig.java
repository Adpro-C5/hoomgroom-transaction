package id.ac.ui.cs.advprog.transaction.config;

import id.ac.ui.cs.advprog.transaction.handler.TotalPriceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TotalPriceHandlerConfig {
    @Bean
    public TotalPriceHandler totalPriceHandler() {
        return new TotalPriceHandler();
    }
}
