package com.Contax.Config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Value("${cloudinary.cloud.name}")
    private String cloud_name;

    @Value("${cloudinary.api.key}")
    private String api_key;

    @Value("${cloudinary.api.secret}")
    private String api_secret;

    @Bean
    public Cloudinary cloudinary(){

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name",cloud_name,
                "api_key",api_key,
                "api_secret",api_secret
        ));
    }
}
