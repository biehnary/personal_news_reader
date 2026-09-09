package com.biehn.personal.news_reader.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.MapsId;

@Embeddable
public class ArticleSnapshotId {

  private Long articleId;
  private Long snapshotId;

}
