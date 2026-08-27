package com.biehn.personal.news_reader.source;

import com.biehn.personal.news_reader.model.NewsItem;
import java.util.List;
import org.w3c.dom.Document;

public interface NewsSource {
  String getRssUrl();
  List<NewsItem> extract(Document document);
}
