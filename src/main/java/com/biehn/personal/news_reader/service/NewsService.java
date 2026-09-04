package com.biehn.personal.news_reader.service;

import com.biehn.personal.news_reader.config.NewsProperties;
import com.biehn.personal.news_reader.config.NewsProperties.SectionConfig;
import com.biehn.personal.news_reader.config.NewsProperties.SourceConfig;
import com.biehn.personal.news_reader.config.Section;
import com.biehn.personal.news_reader.model.NewsItem;
import com.biehn.personal.news_reader.model.NewsSourceViewModel;
import com.biehn.personal.news_reader.model.SectionViewModel;
import com.biehn.personal.news_reader.source.NewsSource;
import com.biehn.personal.news_reader.source.SourceId;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
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
  private final NewsProperties newsProperties;

  // spring DI 고려
  public NewsService(RestClient restClient, List<NewsSource> newsSources,
      NewsProperties newsProperties) {
    this.restClient = restClient;
    this.newsSources = newsSources;
    this.newsProperties = newsProperties;

  }

  Map<SourceId, SourceConfig> buildConfigLookup() {
    Map<SourceId, SourceConfig> configMap = new HashMap<>();
    for (SourceConfig source : newsProperties.getSources()) {
      configMap.put(source.getId(), source);
    }
    return configMap;
  }

  Map<Section, SectionConfig> buildSectionConfigLookup() {
    Map<Section, SectionConfig> sectionConfigMap = new HashMap<>();
    for (SectionConfig section : newsProperties.getSections()) {
      sectionConfigMap.put(section.getSection(), section);
    }
    return sectionConfigMap;
  }

  List<NewsSource> sortNewsSources() {
    Map<SourceId, SourceConfig> configMap = buildConfigLookup();

    Comparator<NewsSource> newsSourceComparator = Comparator.comparingInt((NewsSource source) -> {
      SourceConfig sourceConfig = configMap.get(source.getSourceId());
      return sourceConfig.getDisplayOrder();
    });

    List<NewsSource> sortedList = new ArrayList<>(newsSources);
    sortedList.sort(newsSourceComparator);

    return sortedList;
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

  public List<SectionViewModel> getNews() throws Exception {
    Map<SourceId, SourceConfig> configMap = buildConfigLookup();
    Map<Section, List<NewsSourceViewModel>> sectionListMap = new HashMap<>();
    List<SectionViewModel> sectionViewModels = new ArrayList<>();

    for (NewsSource newsSource : sortNewsSources()) {

      try {
        String xml = fetchRss(newsSource.getRssUrl());
        Document document = parseXml(xml);
        List<NewsItem> newsItems = newsSource.extract(document);

        SourceConfig sourceConfig = configMap.get(newsSource.getSourceId());
        Section section = sourceConfig.getSection();

        NewsSourceViewModel newsSourceViewModel = new NewsSourceViewModel(
            newsSource.getSourceName(), newsItems);

        sectionListMap.computeIfAbsent(section, sectionAsKey -> new ArrayList<>())
            .add(newsSourceViewModel);

      } catch (Exception e) {
        System.out.println("Failed to fetch source: " + newsSource.getRssUrl());
        e.printStackTrace();
        continue;
      }
    }

    for (Map.Entry<Section, List<NewsSourceViewModel>> entry : sectionListMap.entrySet()) {
      SectionViewModel sectionViewModel =
          new SectionViewModel(entry.getKey(), entry.getValue());

      sectionViewModels.add(sectionViewModel);
    }

    Map<Section, SectionConfig> sectionConfigMap = buildSectionConfigLookup();
    Comparator<SectionViewModel> sectionViewModelComparator = Comparator.comparingInt(
        (SectionViewModel sectionView) -> {
          SectionConfig sectionConfig = sectionConfigMap.get(sectionView.getSection());
          return sectionConfig.getDisplayOrder();
        });

    sectionViewModels.sort(sectionViewModelComparator);

    return sectionViewModels;
  }
}


