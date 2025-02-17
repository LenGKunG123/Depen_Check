package isc.etax.taxfiling.configs;

import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;
import org.springframework.security.oauth2.client.endpoint.ReactiveOAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.WebClientReactiveClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${app.client-id}")
    private String CLIENT_ID;
	
	@Value("${app.server-secret}")
    private String SERVER_SECRET;
	
	@Value("${app.URL}")
	private String url;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}


	@Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(CLIENT_ID)
                .clientSecret(passwordEncoder().encode(SERVER_SECRET))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(300))
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }
	
	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
	    return context -> {
	        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
	        	
	        	String clientId = context.getRegisteredClient().getClientId();
//	        	System.out.println(clientId);
//	        	System.out.println(url);
	            
	            // Check if it's the client_credentials flow
	            if (AuthorizationGrantType.CLIENT_CREDENTIALS.equals(context.getAuthorizationGrantType())) {

//	            	String role = "USER";
//	            	context.getClaims().claim("sub", context.getRegisteredClient().getClientId());
//	                context.getClaims().claim("role", role);


	            }
	        }
	    };
	}
	
	
    
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
		authorizationServerConfigurer.oidc(Customizer.withDefaults());
//
	    http.apply(authorizationServerConfigurer);
	    http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/oauth2/**", "/.well-known/**", "/api/**").permitAll()
	            .requestMatchers("/api/**").authenticated()
	            .anyRequest().denyAll()
	        )
	        .csrf(csrf -> csrf.disable())
	        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
//	        .exceptionHandling(exceptions -> exceptions
//	            .authenticationEntryPoint(new org.springframework.security.web.authentication.HttpStatusEntryPoint(org.springframework.http.HttpStatus.UNAUTHORIZED))
//	        );
	        .httpBasic(Customizer.withDefaults());

	    return http.build();
		
		
    }



}
