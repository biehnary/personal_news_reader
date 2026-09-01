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
public class JtbcNewsSource implements NewsSource {

  @Override
  public String getSourceName() {
    return "JTBC Issues TOP 10";
  }

  @Override
  public SourceId getSourceId() {
    return SourceId.JTBC_ISSUE_TOP_10;
  }

  @Override
  public String getRssUrl() {
    return "https://news-ex.jtbc.co.kr/v1/get/rss/issue";
  }

  @Override
  public List<NewsItem> extract(Document document) {
    // item (title, description, link, pubDate)
    List<NewsItem> newsItems = new ArrayList<>();

    NodeList itemNodes = document.getElementsByTagName("item");
    for (int i = 0; i < itemNodes.getLength(); i++) {
      Node itemNode = itemNodes.item(i);
      // Cast Node to Element to use Element method.
      Element itemElement = (Element) itemNode;
      // Extract title. This can be written using method chaining.
      // String title = itemElement.getElementsByTagName("title").item(0).getTextContent();
      NodeList titleNodeList = itemElement.getElementsByTagName("title");
      Node titleNode = titleNodeList.item(0);
      if (titleNode == null) {
        continue;
      }
      String title = titleNode.getTextContent();

      Node linkNode = itemElement
          .getElementsByTagName("link")
          .item(0);
      if (linkNode == null) {
        continue;
      }
      String link = linkNode.getTextContent();

      Node descriptionNode = itemElement
          .getElementsByTagName("description")
          .item(0);
      String description = descriptionNode == null ? null : descriptionNode.getTextContent();

      Node pubDateNode = itemElement
          .getElementsByTagName("pubDate")
          .item(0);
      String publishedAt = pubDateNode == null ? null : pubDateNode.getTextContent();

      NewsItem newsItem = new NewsItem(title, description, link, null, publishedAt, null, "JTBC Issue Top 10");
      newsItems.add(newsItem);
    }

    return newsItems;
  }
}
