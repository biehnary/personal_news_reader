package com.biehn.personal.news_reader.model;

import com.biehn.personal.news_reader.config.Section;
import java.util.List;

public class SectionViewModel {

  private final Section section;
  private final List<NewsSourceViewModel> newsSourceViewModels;

  public SectionViewModel(Section section, List<NewsSourceViewModel> newsSourceViewModels) {
    this.section = section;
    this.newsSourceViewModels = newsSourceViewModels;
  }

  public Section getSection() {
    return section;
  }

  public List<NewsSourceViewModel> getNewsSourceViewModels() {
    return newsSourceViewModels;
  }
}
