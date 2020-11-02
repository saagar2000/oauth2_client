package oauth.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@RequiredArgsConstructor
@Slf4j
@Component
public class ApiClient {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final RestTemplate restTemplate;
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public String invokeApi(String path) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://server:8080").path(path);

        RequestEntity.BodyBuilder requestBuilder =
                RequestEntity.method(HttpMethod.GET, builder.build().toUri());

        requestBuilder.contentType(MediaType.APPLICATION_JSON);

        Authentication principal = SecurityContextHolder.getContext().getAuthentication();

        OAuth2AuthorizeRequest oAuth2AuthorizeRequest =
            OAuth2AuthorizeRequest.withClientRegistrationId("server")
                .principal(principal.getName())
                .build();

        requestBuilder.header(AUTHORIZATION_HEADER, "Bearer " + authorizedClientManager.authorize(oAuth2AuthorizeRequest).getAccessToken().getTokenValue());

        RequestEntity<Object> requestEntity = requestBuilder.body(null);

        return restTemplate.exchange(requestEntity, String.class).getBody();
    }
}
