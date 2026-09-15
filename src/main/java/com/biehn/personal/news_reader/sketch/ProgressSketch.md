DB의 SnapshotEntity 전부 조회
→ for문으로 cutoff 이전 Snapshot만 선택
→ 해당 Snapshot의 ArticleSnapshotEntity 조회
→ 각 관계 엔티티 처리
→ 관계 엔티티 삭제
→ 그 관계가 가리키던 ArticleEntity 확보
→ ArticleSnapshotRepository에서 그 Article의 다른 관계 존재 여부 검사
→ 있으면 Article 유지
→ 없으면 Article 삭제
→ 마지막에 SnapshotEntity 삭제