package com.ContactMate.ContactMate.Helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EmailHelper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken aOAuth2AuthenticationToken) {

            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User) authentication.getPrincipal();
            String username = "";
            if (clientId.equalsIgnoreCase("google")) {

                System.out.println("Getting email from google");
                username = Objects.requireNonNull(oauth2User.getAttribute("email")).toString();

            } else if (clientId.equalsIgnoreCase("github")) {

                System.out.println("Getting email from github");
                username = oauth2User.getAttribute("email") != null ? Objects.requireNonNull(oauth2User.getAttribute("email")).toString()
                        : Objects.requireNonNull(oauth2User.getAttribute("login")).toString() + "@gmail.com";
            }
            return username;

        } else {
            System.out.println("Getting data from local database");
            return authentication.getName();
        }

    }

}