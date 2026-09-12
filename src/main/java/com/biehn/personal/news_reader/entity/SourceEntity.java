package com.biehn.personal.news_reader.entity;

import com.biehn.personal.news_reader.source.SourceId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class SourceEntity {

  protected SourceEntity() {
  }

  public SourceEntity(String sourceName, SourceId sourceId, SectionEntity section) {
    this.sourceName = sourceName;
    this.sourceId = sourceId;
    this.section = section;
  }

  public SourceId getSourceId() {
    return sourceId;
  }

  @Id
  @GeneratedValue
  private Long id;
  private String sourceName;


  @Enumerated(EnumType.STRING)
  private SourceId sourceId;

  @ManyToOne
  private SectionEntity section;
}
