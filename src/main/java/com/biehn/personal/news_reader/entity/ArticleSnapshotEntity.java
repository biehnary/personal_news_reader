package com.biehn.personal.news_reader.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class ArticleSnapshotEntity {

  @EmbeddedId
  private ArticleSnapshotId articleSnapshotId;

  @ManyToOne
  @MapsId("articleId")
  private ArticleEntity article;
  @ManyToOne
  @MapsId("snapshotId")
  private SnapshotEntity snapshot;

}
