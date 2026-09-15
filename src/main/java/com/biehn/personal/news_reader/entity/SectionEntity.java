package com.biehn.personal.news_reader.entity;

import com.biehn.personal.news_reader.config.Section;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class SectionEntity {
  @Id
  @GeneratedValue
  private Long id;

  @Enumerated(EnumType.STRING)
  private Section section;

  public SectionEntity(Section section) {
    this.section = section;
  }

  public SectionEntity() {
  }

  public Section getSection() {
    return section;
  }
}
