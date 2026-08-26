package com.biehn.personal.news_reader.service;

import com.biehn.personal.news_reader.model.NewsItem;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Service
public class NewsService {

  private final RestClient restClient;

  // spring DI 고려
  public NewsService(RestClient restClient) {
    this.restClient = restClient;
  }

  // Rss fetch
  private String fetchRss(String url) {
    String xml = restClient.get()
        .uri(url)
        .retrieve()
        .body(String.class);

    return xml;
  }

  // xml DOM parser
  private Document parseXml(String xml) throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();

    // InputSource
    InputSource inputSource = new InputSource(new StringReader(xml));
    Document doc = builder.parse(inputSource);
    return doc;
  }

  // Convert DOM nodes to NewsItem Objects.
  private List<NewsItem> extractJtbcTop10News(Document document) {
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
      String title = titleNode.getTextContent();

      String link = itemElement
          .getElementsByTagName("link")
          .item(0)
          .getTextContent();

      String description = itemElement
          .getElementsByTagName("description")
          .item(0)
          .getTextContent();

      String publishedAt = itemElement
          .getElementsByTagName("pubDate")
          .item(0)
          .getTextContent();

      NewsItem newsItem = new NewsItem(title, description, link, null, publishedAt, null, null);
      newsItems.add(newsItem);
    }

    return newsItems;
  }

  public List<NewsItem> getNews() throws Exception {

    String xml = fetchRss("https://news-ex.jtbc.co.kr/v1/get/rss/issue");
    Document document = parseXml(xml);
    return extractJtbcTop10News(document);
  }
}

