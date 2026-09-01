package com.biehn.personal.news_reader.source;

import com.biehn.personal.news_reader.model.NewsItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class HaniPopularSource implements NewsSource {

  @Override
  public String getSourceName() {
    return "Hani Popular";
  }

  @Override
  public SourceId getSourceId() {
    return SourceId.HANI_POPULAR;
  }

  @Override
  public String getRssUrl() {
    return "https://www.hanion.co.kr/rss/clickTop.xml";
  }

  @Override
  public List<NewsItem> extract(Document document) {
    List<NewsItem> newsItems = new ArrayList<>();

    NodeList itemNodes = document.getElementsByTagName("item");

    for (int i = 0; i < itemNodes.getLength(); i++) {
      Node itemNode = itemNodes.item(i);
      Element itemElement = (Element) itemNode;

      Node titleNode = itemElement.getElementsByTagName("title")
          .item(0);
      if (titleNode == null) {
        continue;
      }
      String title = titleNode.getTextContent();

      Node linkNode = itemElement.getElementsByTagName("link")
          .item(0);
      if (linkNode == null) {
        continue;
      }
      String link = linkNode.getTextContent();

      Node descriptionNode = itemElement.getElementsByTagName("description")
          .item(0);
      String description = descriptionNode == null ? null : descriptionNode.getTextContent();

      Node pubDateNode = itemElement.getElementsByTagName("pubDate")
          .item(0);
      String publishedAt = pubDateNode == null ? null : pubDateNode.getTextContent();

      Node authorNode = itemElement.getElementsByTagName("author")
          .item(0);
      String author = authorNode == null ? null : authorNode.getTextContent();

      NewsItem newsItem = new NewsItem(title, description, link, null, publishedAt, author,
          "Hani 한겨레");

      newsItems.add(newsItem);
    }

    return newsItems;
  }

}
