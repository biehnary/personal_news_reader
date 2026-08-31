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
public class MkRealEstateSource implements NewsSource {

  @Override
  public String getSourceName() {
    return "MK Real Estate";
  }

  @Override
  public String getRssUrl() {
    return "https://www.mk.co.kr/rss/50300009/";
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

      Node descriptionNode = itemElement.getElementsByTagName("description")
          .item(0);
      String description = descriptionNode == null ? null : descriptionNode.getTextContent();

      Node pubDateNode = itemElement.getElementsByTagName("pubDate")
          .item(0);
      String publishedAt = pubDateNode == null ? null : pubDateNode.getTextContent();

      Node authorNode = itemElement.getElementsByTagName("author")
          .item(0);
      String author = authorNode == null ? null : authorNode.getTextContent();

      Node mediaNode = itemElement.getElementsByTagName("media:content")
          .item(0);
      Element mediaElement = (Element) mediaNode;
      String imageUrl = mediaElement == null ? null : mediaElement.getAttribute("url");

      NewsItem newsItem = new NewsItem(title, description, link, imageUrl, publishedAt, author,
          "MK Real Estate");

      newsItems.add(newsItem);


    }

    return newsItems;


  }
}
