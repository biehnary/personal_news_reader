package com.biehn.personal.news_reader.model;

public class NewsItem {
  private String title;
  private String description;
  private String link;
  private String imageUrl;
  private String publishedAt;
  private String author;
  private String source;


  public NewsItem(String title, String description, String link, String imageUrl,
      String publishedAt,
      String author, String source) {
    this.title = title;
    this.description = description;
    this.link = link;
    this.imageUrl = imageUrl;
    this.publishedAt = publishedAt;
    this.author = author;
    this.source = source;
  }

  // getter
  public String getTitle() {
    return this.title;
  }

  public String getAuthor() {
    return author;
  }

  public String getDescription() {
    return description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getLink() {
    return link;
  }

  public String getPublishedAt() {
    return publishedAt;
  }

  public String getSource() {
    return source;
  }
}
