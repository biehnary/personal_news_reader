Article(
  pk,
  title,
  description,
  link,
  imageUrl,
  publishedAt,
  author,
  source_fk
)

Source(
  pk,
  sourceName,
  section_fk
)

Section(
  pk,
  sectionName
)

Snapshot(
  pk,
  collectedDate
)

ArticleSnapshot(
  article_fk,
  snapshot_fk
)

1. RSS fetch
2. Article 중복 확인
3. 새 Article 저장 또는 기존 Article 재사용
4. 오늘 Snapshot 조회/생성
5. Article ↔ Snapshot 관계 갱신
6. 7일치 Snapshot 조회
7. 화면 출력