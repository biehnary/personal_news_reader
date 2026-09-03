package com.biehn.personal.news_reader.config;

import com.biehn.personal.news_reader.source.SourceId;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("news")
public class NewsProperties {

  List<SourceConfig> sources = new ArrayList<>();

  public List<SourceConfig> getSources() {
    return sources;
  }

  public void setSources(
      List<SourceConfig> sources) {
    this.sources = sources;
  }

  public static class SourceConfig {
    SourceId id;
    int displayOrder;
    Section section;

    public SourceId getId() {
      return id;
    }

    public int getDisplayOrder() {
      return displayOrder;
    }

    public void setId(SourceId id) {
      this.id = id;
    }

    public void setDisplayOrder(int displayOrder) {
      this.displayOrder = displayOrder;
    }

    public Section getSection() {
      return section;
    }

    public void setSection(Section section) {
      this.section = section;
    }
  }



}
