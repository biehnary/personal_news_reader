package com.biehn.personal.news_reader.repository;

import com.biehn.personal.news_reader.entity.ArticleEntity;
import com.biehn.personal.news_reader.entity.ArticleSnapshotEntity;
import com.biehn.personal.news_reader.entity.ArticleSnapshotId;
import com.biehn.personal.news_reader.entity.SnapshotEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleSnapshotRepository extends
    JpaRepository<ArticleSnapshotEntity, ArticleSnapshotId> {

  abstract List<ArticleSnapshotEntity> findBySnapshot(SnapshotEntity snapshot);

  boolean existsByArticle(ArticleEntity articleEntity);

}
