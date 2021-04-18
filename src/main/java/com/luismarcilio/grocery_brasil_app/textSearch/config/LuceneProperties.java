package com.luismarcilio.grocery_brasil_app.textSearch.config;

import com.luismarcilio.grocery_brasil_app.textSearch.WithDebug;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LuceneProperties {
    @Bean
    @ConfigurationProperties(prefix="lucene.directory")
	@WithDebug
    public DirectoryPath getDirectoryPath(){
        return new DirectoryPath();
    }
}
