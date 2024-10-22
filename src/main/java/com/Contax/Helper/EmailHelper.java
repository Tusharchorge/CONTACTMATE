package com.Contax.Helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class EmailHelper {

    @Value("${server.baseUrl}")
    private String baseUrl;

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        // If the user logged in via OAuth2
        if (authentication instanceof OAuth2AuthenticationToken) {

            OAuth2AuthenticationToken aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            String clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            String username = "";

            if (clientId.equalsIgnoreCase("google")) {
                // Sign in with Google
                System.out.println("Getting email from google");
                username = oauth2User.getAttribute("email").toString();

            } else if (clientId.equalsIgnoreCase("github")) {
                // Sign in with GitHub
                System.out.println("Getting email from github");
                username = oauth2User.getAttribute("email") != null ?
                        oauth2User.getAttribute("email").toString()
                        : oauth2User.getAttribute("login").toString() + "@gmail.com";
            }

            // Return the obtained username
            return username;

        } else {
            // If the user is authenticated locally, get the username from the authentication object
            System.out.println("Getting data from local database");
            return authentication.getName();
        }
    }

    public String getLinkForEmailVerificatiton(String emailToken) {
        return this.baseUrl + "/auth/verify-email?token=" + emailToken;
    }
}
