package isc.etax.taxfiling.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {
	
	@Value("${app.client-id}")
    private String CLIENT_ID;
	
	@Value("${spring.security.oauth2.authorizationserver.issuer}")
    private String AUTH_ISSUER;
	
	@Value("${app.server-secret}")
    private String SERVER_SECRET;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = ClientRegistration
                .withRegistrationId(CLIENT_ID)
                .clientId(CLIENT_ID)
                .clientSecret(passwordEncoder.encode(SERVER_SECRET))
                .authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenUri(AUTH_ISSUER + "/oauth2/token")
                .build();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

}
