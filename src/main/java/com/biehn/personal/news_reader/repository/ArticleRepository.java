package com.biehn.personal.news_reader.repository;

import com.biehn.personal.news_reader.entity.ArticleEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

  abstract Optional<ArticleEntity> findByLink(String link);
}
