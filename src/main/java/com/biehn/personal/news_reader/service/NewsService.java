package com.biehn.personal.news_reader.service;

import com.biehn.personal.news_reader.config.NewsProperties;
import com.biehn.personal.news_reader.config.NewsProperties.SectionConfig;
import com.biehn.personal.news_reader.config.NewsProperties.SourceConfig;
import com.biehn.personal.news_reader.config.Section;
import com.biehn.personal.news_reader.entity.ArticleEntity;
import com.biehn.personal.news_reader.entity.ArticleSnapshotEntity;
import com.biehn.personal.news_reader.entity.SectionEntity;
import com.biehn.personal.news_reader.entity.SnapshotEntity;
import com.biehn.personal.news_reader.entity.SourceEntity;
import com.biehn.personal.news_reader.model.NewsItem;
import com.biehn.personal.news_reader.model.NewsSourceViewModel;
import com.biehn.personal.news_reader.model.SectionViewModel;
import com.biehn.personal.news_reader.repository.ArticleRepository;
import com.biehn.personal.news_reader.repository.ArticleSnapshotRepository;
import com.biehn.personal.news_reader.repository.SectionRepository;
import com.biehn.personal.news_reader.repository.SnapshotRepository;
import com.biehn.personal.news_reader.repository.SourceRepository;
import com.biehn.personal.news_reader.source.NewsSource;
import com.biehn.personal.news_reader.source.SourceId;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
  private final ArticleRepository articleRepository;
  private final ArticleSnapshotRepository articleSnapshotRepository;
  private final SnapshotRepository snapshotRepository;
  private final SourceRepository sourceRepository;
  private final SectionRepository sectionRepository;

  // spring DI 고려


  public NewsService(RestClient restClient, List<NewsSource> newsSources,
      NewsProperties newsProperties, ArticleRepository articleRepository,
      ArticleSnapshotRepository articleSnapshotRepository, SnapshotRepository snapshotRepository,
      SourceRepository sourceRepository, SectionRepository sectionRepository) {
    this.restClient = restClient;
    this.newsSources = newsSources;
    this.newsProperties = newsProperties;
    this.articleRepository = articleRepository;
    this.articleSnapshotRepository = articleSnapshotRepository;
    this.snapshotRepository = snapshotRepository;
    this.sourceRepository = sourceRepository;
    this.sectionRepository = sectionRepository;
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

  /*
  Refresh Sources and DB sync
  작성 후 getNews() 수정하기
   */
  public void syncNews() {
    //SnapshotEntity 생성
    LocalDate today = LocalDate.now();
    SnapshotEntity snapshotEntity;

    Optional<SnapshotEntity> optionalSnapshotEntity = snapshotRepository.findByCollectedDate(today);

    if (optionalSnapshotEntity.isEmpty()) {
      snapshotEntity = snapshotRepository.save(new SnapshotEntity(today));
    } else {
      snapshotEntity = optionalSnapshotEntity.get();
    }

    for (NewsSource newsSource : newsSources) {
      try {
        String xml = fetchRss(newsSource.getRssUrl());
        Document document = parseXml(xml);
        List<NewsItem> newsItems = newsSource.extract(document);

        Map<SourceId, SourceConfig> configMap = buildConfigLookup();
        SourceConfig sourceConfig = configMap.get(newsSource.getSourceId());

        // SectionEntity 생성
        SectionEntity sectionEntity;
        Optional<SectionEntity> optionalSectionEntity = sectionRepository.findBySection(
            sourceConfig.getSection());

        if (optionalSectionEntity.isEmpty()) {
          sectionEntity = sectionRepository.save(new SectionEntity(sourceConfig.getSection()));
        } else {
          sectionEntity = optionalSectionEntity.get();
        }

        // SourceEntity 생성
        SourceEntity sourceEntity;
        Optional<SourceEntity> optionalSourceEntity = sourceRepository.findBySourceId(
            newsSource.getSourceId());

        if (optionalSourceEntity.isEmpty()) {
          sourceEntity = sourceRepository.save(
              new SourceEntity(newsSource.getSourceName(), newsSource.getSourceId(),
                  sectionEntity));
        } else {
          sourceEntity = optionalSourceEntity.get();
        }

        //ArticleEntity 생성
        List<ArticleEntity> articleEntityList = new ArrayList<>();
        for (NewsItem newsItem : newsItems) {
          ArticleEntity articleEntity;
          Optional<ArticleEntity> optionalArticleEntity = articleRepository.findByLink(
              newsItem.getLink());

          if (optionalArticleEntity.isEmpty()) {
            articleEntity = articleRepository.save(
                new ArticleEntity(newsItem.getTitle(), newsItem.getDescription(),
                    newsItem.getLink(), newsItem.getImageUrl(), newsItem.getPublishedAt(),
                    newsItem.getAuthor(), sourceEntity));
            articleEntityList.add(articleEntity);
          } else {
            articleEntity = optionalArticleEntity.get();
            articleEntityList.add(articleEntity);
          }
        }

        //ArticleSnapshotEntity 중복검사
        List<ArticleSnapshotEntity> articleSnapshotEntities = articleSnapshotRepository.findBySnapshot(
            snapshotEntity);
        for (ArticleSnapshotEntity articleSnapshotEntity : articleSnapshotEntities) {
          if (articleSnapshotEntity.getArticleEntity().getSourceEntity().getSourceId()
              == newsSource.getSourceId()) {

            boolean shouldDelete = true;

            for (NewsItem item : newsItems) {
              if (item.getLink().equals(articleSnapshotEntity.getArticleEntity().getLink())) {
                shouldDelete = false;
                break;
              }
            }

            if (shouldDelete) {
              articleSnapshotRepository.delete(articleSnapshotEntity);
            }
          }
        }

        articleSnapshotEntities = articleSnapshotRepository.findBySnapshot(snapshotEntity);
        for (ArticleEntity articleEntity : articleEntityList) {
          boolean flag = true;

          for (ArticleSnapshotEntity articleSnapshotEntity : articleSnapshotEntities) {
            if (articleEntity.getLink()
                .equals(articleSnapshotEntity.getArticleEntity().getLink())) {
              flag = false;
              break;
            }

          }
          if (flag) {
            articleSnapshotRepository.save(
                new ArticleSnapshotEntity(articleEntity, snapshotEntity));
          }
        }


      } catch (Exception e) {
        System.out.println("Failed to fetch source: " + newsSource.getRssUrl());
        e.printStackTrace();
        continue;
      }
    }
  }


  // Rss fetch
  private String fetchRss(String url) {
    return restClient.get().uri(url).exchange((request, response) -> {
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


}


