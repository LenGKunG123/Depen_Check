package isc.etax.taxfiling.services;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
public class OAuth2ClientService {
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public OAuth2ClientService(ClientRegistrationRepository clientRegistrationRepository,
                               OAuth2AuthorizedClientRepository authorizedClientRepository) {
        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();

        this.authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);
        ((DefaultOAuth2AuthorizedClientManager) this.authorizedClientManager)
                .setAuthorizedClientProvider(authorizedClientProvider);
    }

    public String getAccessToken() {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("server-client")
                .principal("client_credentials")
                .build();

        OAuth2AuthorizedClient authorizedClient = this.authorizedClientManager.authorize(authorizeRequest);

        if (authorizedClient == null || authorizedClient.getAccessToken() == null) {
            throw new IllegalStateException("Failed to retrieve access token");
        }

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        return accessToken.getTokenValue();
    }
    
}