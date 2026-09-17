package com.biehn.personal.news_reader;

import com.biehn.personal.news_reader.service.NewsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NewsScheduler {

  private final NewsService newsService;

  public NewsScheduler(NewsService newsService) {
    this.newsService = newsService;
  }

  @Scheduled(cron = "0 0 0,7,18 * * *")
  public void triggerSync() {
    newsService.syncNews();
  }


}
