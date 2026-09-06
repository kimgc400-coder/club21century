# 브랜치 작업 한 사이클

## 1. 시작
git switch main
git pull
git switch -c feat/무언가

## 2. 작업 후
git add .
git commit -m "feat: 무엇을 했는지"
git push -u origin feat/무언가

## 3. GitHub
- PR 생성
- Files changed 에서 diff 읽기   ← 이게 이 사이클의 목적
- Merge pull request
- **Delete branch 버튼 누르기**   ← 여기서 원격 정리 끝

## 4. 정리
git switch main
git pull
git branch -d feat/무언가        ← 이름은 `git branch` 로 확인 후 복사

## 접두사
- feat     새 기능
- fix      버그 수정
- hot fix 운영 긴급 수정
- refactor 동작 변경 없는 구조 개선 / 동작과 성능은 그래도
- chore    설정, 빌드, 문서
- docs 문서만 변경
- test 테스트 추가/수정
- perf 성능 개선 / 성능수치가 올라감
- build 빌드 시스템/의존성, build.gradle 변경 시
- revert 이전 커밋 되돌리기
- 형식: <type>: <무엇을 했는지>
- 스코프가 필요해지면: feat(member): ...

## 브랜치 이름
커밋 접두사와 같은 단어 + 슬래시
예: feat/member-signup, refactor/mybatis-xml

## 주의
- 브랜치 이름은 `git branch` 로 확인하고 복사한다. 기억에서 꺼내 쓰지 않는다.
- `git branch -d` 는 머지 확인 후 삭제. `-D` 는 강제라 작업을 날릴 수 있다.
- 원격 삭제는 머지 직후 GitHub 의 Delete branch 버튼으로 통일한다.