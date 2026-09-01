package com.biehn.personal.news_reader.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(NewsProperties.class)
public class AppConfig {

  @Bean
  public RestClient restClient() {
    return RestClient.create();
  }
}
