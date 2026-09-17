package com.biehn.personal.news_reader;

import com.biehn.personal.news_reader.service.NewsService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NewsBootstrap {

  private final NewsService newsService;

  public NewsBootstrap(NewsService newsService) {
    this.newsService = newsService;
  }

@EventListener(ApplicationReadyEvent.class)
  public void startupSync() {
    newsService.syncOnStartupIfNeeded();
  }
}




