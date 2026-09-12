package com.biehn.personal.news_reader.repository;

import com.biehn.personal.news_reader.entity.SourceEntity;
import com.biehn.personal.news_reader.source.SourceId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepository extends JpaRepository<SourceEntity, Long> {

  abstract Optional<SourceEntity> findBySourceId(SourceId sourceId);
}
