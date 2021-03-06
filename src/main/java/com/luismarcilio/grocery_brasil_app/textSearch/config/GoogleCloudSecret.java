package com.luismarcilio.grocery_brasil_app.textSearch.config;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.luismarcilio.grocery_brasil_app.textSearch.repository.ProductRepositoryRuntimeException;

import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.secretmanager.v1.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableConfigurationProperties(GoogleCloudProperties.class)
public class GoogleCloudSecret {
    private final String projectId;
    private final String secretId;
    private final SecretManagerServiceClient client;
    public GoogleCloudSecret(GoogleCloudProperties googleCloudProperties,SecretManagerServiceClient client) {
        this.projectId = googleCloudProperties.getProjectId();
        this.secretId = googleCloudProperties.getSecretId();
        this.client = client;
    }
    
    @Bean(name="ApiKey")
    public String getApiKey() throws ParseException{
        try {
            log.info("Google Credentials: {}", GoogleCredentials.getApplicationDefault());
        } catch (IOException e) {
            throw new ProductRepositoryRuntimeException(e);
        }
        AccessSecretVersionResponse response = client.accessSecretVersion(SecretVersionName.of(this.projectId, this.secretId, "latest"));
        String secretPayload = response.getPayload().getData().toStringUtf8();
        return getApiKeyFromJson(secretPayload);
    }

    private String getApiKeyFromJson(String json) throws ParseException{
        JSONParser parser = new JSONParser();    
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        return (String) jsonObject.get("apiId");
    }
}
