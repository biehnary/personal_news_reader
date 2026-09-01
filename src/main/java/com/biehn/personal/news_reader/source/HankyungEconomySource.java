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
public class HankyungEconomySource implements NewsSource {

  @Override
  public String getSourceName() {
    return "HanKyung Economy";
  }

  @Override
  public SourceId getSourceId() {
    return SourceId.HANKYUNG_ECONOMY;
  }

  @Override
  public String getRssUrl() {
    return "https://www.hankyung.com/feed/economy";
  }

  @Override
  public List<NewsItem> extract(Document document) {
    List<NewsItem> newsItems = new ArrayList<>();
    NodeList itemNodes = document.getElementsByTagName("item");

    for (int i = 0; i < itemNodes.getLength(); i++) {
      Node itemNode = itemNodes.item(i);
      Element itemElement = (Element) itemNode;

      NodeList titleNodes = itemElement.getElementsByTagName("title");
      Node titleNode = titleNodes
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

      Node pubDateNode = itemElement.getElementsByTagName("pubDate")
          .item(0);
      String publishedAt = pubDateNode == null ? null : pubDateNode.getTextContent();

      Node authorNode = itemElement.getElementsByTagName("author")
          .item(0);
      String author = authorNode == null ? null : authorNode.getTextContent();

      NewsItem newsItem = new NewsItem(title, null, link, null, publishedAt, author,
          "Hankyung Economy");

      newsItems.add(newsItem);


    }

    return newsItems;


  }
}
