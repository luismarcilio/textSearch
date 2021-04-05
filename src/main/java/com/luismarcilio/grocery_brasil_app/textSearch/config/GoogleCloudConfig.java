package com.luismarcilio.grocery_brasil_app.textSearch.config;

import java.io.IOException;

import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleCloudConfig {

    @Bean 
    SecretManagerServiceClient getSecretManagerServiceClient() throws IOException{
        return SecretManagerServiceClient.create();
    }
    
}
