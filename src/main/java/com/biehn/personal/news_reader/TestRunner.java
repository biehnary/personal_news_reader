package com.biehn.personal.news_reader;

import com.biehn.personal.news_reader.model.NewsItem;
import com.biehn.personal.news_reader.service.NewsService;
import java.util.List;
import org.springframework.web.client.RestClient;

public class TestRunner {

  public static void main(String[] args) {

    try {
      RestClient restClient = RestClient.create();
      NewsService service = new NewsService(restClient);
      List<NewsItem> news = service.getNews();
      System.out.println(news);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
