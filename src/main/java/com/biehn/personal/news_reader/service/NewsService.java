package com.biehn.personal.news_reader.service;
import com.biehn.personal.news_reader.model.NewsItem;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.web.client.RestClient;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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

    System.out.println(xml);
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

  // DOM to Object
  private List<NewsItem> extractNews(Document document) {


  }

  public List<NewsItem>getNews() {
    String xml = fetchRss("https://news-ex.jtbc.co.kr/v1/get/rss/issue");
    return null;
  }


}

