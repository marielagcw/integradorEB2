package com.digitalmedia.movies.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    @GetMapping(path ="/detail/{id}")
    public Map<String, Object> detailSecure(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient
            , Authentication auth
            , @PathVariable("id") Long idAccount) {
        Map<String, Object> response = new HashMap<>();
        response.put("clientName", authorizedClient.getClientRegistration().getClientName());
        response.put("id_account", idAccount);
        response.put("token", authorizedClient.getAccessToken().getTokenValue());

        return response;
    }
}
