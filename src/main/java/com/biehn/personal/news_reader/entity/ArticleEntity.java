package com.biehn.personal.news_reader.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ArticleEntity {

  protected ArticleEntity() {
  }

  public ArticleEntity(String title, String description, String link, String imageUrl,
      String publishedAt, String author, SourceEntity source_fk) {
    this.title = title;
    this.description = description;
    this.link = link;
    this.imageUrl = imageUrl;
    this.publishedAt = publishedAt;
    this.author = author;
    this.source_fk = source_fk;
  }

  @Id @GeneratedValue
  private Long id;
  private String title;
  @Column(columnDefinition = "TEXT")
  private String description;
  private String link;
  private String imageUrl;
  private String publishedAt;
  private String author;

  @ManyToOne
  private SourceEntity source_fk;

  public SourceEntity getSourceEntity() {
    return source_fk;
  }

  public String getLink() {
    return link;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getPublishedAt() {
    return publishedAt;
  }

  public String getAuthor() {
    return author;
  }

}
