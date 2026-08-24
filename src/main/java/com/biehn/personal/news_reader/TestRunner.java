package com.biehn.personal.news_reader;

import com.biehn.personal.news_reader.service.NewsService;
import org.springframework.web.client.RestClient;

public class TestRunner {

  public static void main(String[] args) {
    RestClient restClient = RestClient.create();
    NewsService service = new NewsService(restClient);
    service.getNews();
  }
}
