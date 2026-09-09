package com.biehn.personal.news_reader.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class SnapshotEntity {

  @Id
  @GeneratedValue
  private Long id;
  private LocalDate collectedDate;

}
