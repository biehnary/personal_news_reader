package com.biehn.personal.news_reader.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ArticleEntity {

  @Id @GeneratedValue
  private Long id;
  private String title;
  private String description;
  private String link;
  private String imageUrl;
  private String publishedAt;
  private String author;

  @ManyToOne
  private SourceEntity source_fk;
}
