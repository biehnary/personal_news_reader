package com.biehn.personal.news_reader.repository;

import com.biehn.personal.news_reader.entity.SnapshotEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnapshotRepository extends JpaRepository<SnapshotEntity, Long> {

  abstract Optional<SnapshotEntity> findByCollectedDate(LocalDate localDate);

  List<SnapshotEntity> findAll();
}
