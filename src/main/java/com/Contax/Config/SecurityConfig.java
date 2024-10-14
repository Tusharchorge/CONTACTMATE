package com.Contax.Config;

import com.Contax.Services.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    SecurityUserService userService;

    @Autowired
    AuthFailureHandler authFailureHandler;

    @Autowired
    private OAuthAuthenticationSuccessHandler oauthAuthenticationSuccessHandler;

    public SecurityConfig(OAuthAuthenticationSuccessHandler oauthAuthenticationSuccessHandler) {
        this.oauthAuthenticationSuccessHandler = oauthAuthenticationSuccessHandler;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
         DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
         provider.setUserDetailsService(userService);
         provider.setPasswordEncoder(passwordEncoder());
         return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorizeRequests -> {
             authorizeRequests.requestMatchers("/user/**").authenticated();
             authorizeRequests.anyRequest().permitAll();
        });
        http.formLogin(formLogin->{
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
//            formLogin.failureUrl("/login?error");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

            formLogin.failureHandler(authFailureHandler);

        });

        http.csrf(AbstractHttpConfigurer::disable);
        http.logout(logout->{
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout");

        });

         http.oauth2Login(login->{
             login.loginPage("/login");
             login.successHandler(oauthAuthenticationSuccessHandler);
         });

        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
