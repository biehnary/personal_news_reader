package com.biehn.personal.news_reader.model;

import java.time.LocalDate;
import java.util.List;

public class PageViewModel {

  private final List<SectionViewModel> sectionViewModels;
  private final LocalDate selectedDate;
  private final List<LocalDate> snapshotDates;

  public PageViewModel(List<SectionViewModel> sectionViewModels, LocalDate selectedDate,
      List<LocalDate> snapshotDates) {
    this.sectionViewModels = sectionViewModels;
    this.selectedDate = selectedDate;
    this.snapshotDates = snapshotDates;
  }

  // getter for thymeleaf


  public List<SectionViewModel> getSectionViewModels() {
    return sectionViewModels;
  }

  public LocalDate getSelectedDate() {
    return selectedDate;
  }

  public List<LocalDate> getSnapshotDates() {
    return snapshotDates;
  }
}