package com.luismarcilio.grocery_brasil_app.textSearch.config;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.*;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public String getApiKey(){
        AccessSecretVersionResponse response = client.accessSecretVersion(SecretVersionName.of(this.projectId, this.secretId, "latest"));
        return response.getPayload().getData().toStringUtf8();
    }

}
