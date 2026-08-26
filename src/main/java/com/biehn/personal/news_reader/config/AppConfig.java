package com.biehn.personal.news_reader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@org.springframework.context.annotation.Configuration
public class AppConfig {

  @Bean
  public RestClient restClient() {
    return RestClient.create();
  }
}
