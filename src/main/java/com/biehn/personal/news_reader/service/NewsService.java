package com.biehn.personal.news_reader.service;

import com.biehn.personal.news_reader.model.NewsItem;
import com.biehn.personal.news_reader.source.NewsSource;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Service
public class NewsService {

  private final RestClient restClient;
  private final List<NewsSource> newsSources;

  // spring DI 고려
  public NewsService(RestClient restClient, List<NewsSource> newsSources) {
    this.restClient = restClient;
    this.newsSources = newsSources;
  }

  // Rss fetch
  private String fetchRss(String url) {
    return restClient.get()
        .uri(url)
        .exchange((request, response) -> {
          MediaType contentType = response.getHeaders().getContentType();
          Charset charset = contentType == null ? null : contentType.getCharset();

          if (charset == null) {
            charset = StandardCharsets.UTF_8;
          }

          byte[] body = response.getBody().readAllBytes();

          return new String(body, charset);
        });

  }

  // xml DOM parser
  private Document parseXml(String xml)
      throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();

    // InputSource
    InputSource inputSource = new InputSource(new StringReader(xml));
    Document doc = builder.parse(inputSource);
    return doc;
  }

  public Map<String, List<NewsItem>> getNews() throws Exception {
    Map<String, List<NewsItem>> newsMap = new HashMap<>();

    for (NewsSource newsSource : newsSources) {
      try {
        String xml = fetchRss(newsSource.getRssUrl());
        Document document = parseXml(xml);
        newsMap.put(newsSource.getSourceName(), newsSource.extract(document));
      } catch (Exception e) {
        System.out.println("Failed to fetch source: " + newsSource.getRssUrl());
        e.printStackTrace();
        continue;
      }
    }
    return newsMap;
  }
}


