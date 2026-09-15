1. Ordering
    - Section 순서
    - Source 순서

2. Pagination
    - Snapshot 날짜 기준으로 이전/다음 페이지
    - getNews()를 특정 날짜 Snapshot 읽기로 확장

3. Refresh
    - Controller의 syncNews() 제거
    - 정해진 주기로 syncNews() 실행
    - 서버 시작 직후 아직 Snapshot이 없을 때 처리

4. Production DB
    - PostgreSQL 접속정보 환경변수화
    - ddl-auto 설정 점검
    - 운영 DB 초기 schema 생성 방식 확인

5. Deploy
    - 서버 배포
    - DB 연결
    - 실제 RSS fetch 되는지 확인
    - HTML/CSS/JS 포함해서 smoke test