package com.biehn.personal.news_reader.model;

import java.util.List;

public class NewsSourceViewModel {

  private final String sourceName;
  private final List<NewsItem> newsItems;

  public NewsSourceViewModel(String sourceName, List<NewsItem> newsItems) {
    this.sourceName = sourceName;
    this.newsItems = newsItems;
  }

  public List<NewsItem> getNewsItems() {
    return newsItems;
  }

  public String getSourceName() {
    return sourceName;
  }
}
