package com.ContactMate.ContactMate.Config;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import com.ContactMate.ContactMate.Entities.Providers;
import com.ContactMate.ContactMate.Entities.User;
import com.ContactMate.ContactMate.Helper.AppConstants;
import com.ContactMate.ContactMate.Repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");

        // identify the provider

        var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);

        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        oauthUser.getAttributes().forEach((key, value) -> {
            logger.info("{} : {}", key, value);
        });

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("dummy");

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {

            user.setEmail(Objects.requireNonNull(oauthUser.getAttribute("email")).toString());
            user.setProfilePic(Objects.requireNonNull(oauthUser.getAttribute("picture")).toString());
            user.setName(Objects.requireNonNull(oauthUser.getAttribute("name")).toString());
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created using google");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {


            String email = oauthUser.getAttribute("email") != null ?
                     Objects.requireNonNull(oauthUser.getAttribute("email")).toString()
                    : Objects.requireNonNull(oauthUser.getAttribute("login")).toString() + "@gmail.com";
            String picture = Objects.requireNonNull(oauthUser.getAttribute("avatar_url")).toString();
            String name = Objects.requireNonNull(oauthUser.getAttribute("login")).toString();
            String providerUserId = oauthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProvider(Providers.GITHUB);

            user.setAbout("This account is created using github");
        }
        else {
            logger.info("OAuthAuthenticationSuccessHandler: Unknown provider");
        }
        User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (user2 == null) {
            userRepo.save(user);
            System.out.println("user saved:" + user.getEmail());
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

    }

}