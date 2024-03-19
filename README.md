![Auto Assign](https://github.com/like-lion-3team/demo-repository/actions/workflows/auto-assign.yml/badge.svg)

![Proof HTML](https://github.com/like-lion-3team/demo-repository/actions/workflows/proof-html.yml/badge.svg)

# TourFinder
여행 코스 기록, 공유 서비스


## 규칙
1. mater / main 브랜치에는 절대로 Push 하지 않는다.
2. 개발중인 내용은 dev브랜치로 merge한다.


## 흐름 예시
1. clone 
2. 로컬에서도 dev브랜치 생성 dev브랜치로 이동
3. 개발할 기능 내용이 담긴 브랜치 생성 예) feat/login,  fix/login
4. 개발 완료 후 깃허브 원격 저장소로 push
5. Pull Request 생성  dev <- feat/login
6. 팀원끼리 코드 리뷰 하고 머지
7. 로컬에서 작업중이던 팀원들은 로컬 브랜치 최신화  git pull origin dev.


## 가이드
1. application.yaml 파일에 ${KEY} 형태로 환경변수를 참조하고 있습니다. 환경 변수 세팅에 해당 Key에 맞게 값을 설정 해주세요. <br />
2. 프로젝트 실행 후 http://localhost:8080/swagger-ui/index.html 경로로 접속하면 API 문서를 볼 수 있습니다.
3. `SwaggerConfig` 파일에 스웨거 관련 설정을 추가할 수 있습니다. 