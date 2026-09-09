package com.biehn.personal.news_reader.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class SectionEntity {
  @Id
  @GeneratedValue
  private Long id;
  private String sectionName;
}
