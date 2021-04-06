package com.luismarcilio.grocery_brasil_app.textSearch.config;

import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class GoogleCloudConfig {

    @Bean 
    SecretManagerServiceClient getSecretManagerServiceClient() throws IOException{
        log.info("Google Credentials: {}", GoogleCredentials.getApplicationDefault());
        return SecretManagerServiceClient.create();
    }
    
}
