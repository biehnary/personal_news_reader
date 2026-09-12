package com.biehn.personal.news_reader.repository;

import com.biehn.personal.news_reader.config.Section;
import com.biehn.personal.news_reader.entity.SectionEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<SectionEntity,Long> {

  abstract Optional<SectionEntity> findBySection(Section section);

}
