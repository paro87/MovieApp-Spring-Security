package tm.paro.clientmovieapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LoadBalanceConfig {

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
