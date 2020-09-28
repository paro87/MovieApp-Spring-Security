package tm.paro.clientmovieapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "authserver")
@Data
public class AuthServerConfigProperties {

    private String accessTokenUri;
    private String clientId;
    private String grantType;
    private String clientSecret;
    private String username;
    private String password;

}

