package com.luismarcilio.grocery_brasil_app.textSearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@PropertySource("classpath:gcp.properties")
class GoogleCloudProperties {
    private String projectId;
    private String secretId;    
}
