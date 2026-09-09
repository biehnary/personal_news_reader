package com.biehn.personal.news_reader.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class SourceEntity {

  @Id
  @GeneratedValue
  private Long id;
  private String sourceName;

  @ManyToOne
  private SectionEntity section;
}
