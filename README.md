![Auto Assign](https://github.com/like-lion-3team/demo-repository/actions/workflows/auto-assign.yml/badge.svg)

![Proof HTML](https://github.com/like-lion-3team/demo-repository/actions/workflows/proof-html.yml/badge.svg)

# TourFinder

## 프로젝트 소개

여행 코스 기록, 공유 서비스.

<details>
  <summary> 시작하기</summary>
  프로젝트를 실행하기 위한 가이드 입니다.

### 프로젝트 다운로드 및 환경변수 설정

1. 소스 코드 복제:

   ````bash
   git clone https://github.com/like-lion-3team/trip.git
   ````

2. 환경변수 설정:


아래 환경 변수에  올바른 값을 추가 해주세요.
REDIRECT_URI 부분은 각 소셜 사업자 설정 화면에서 아래 주소와 똑같이 입력 해주세요.
````
DB_PASSWORD={데이터베이스 비밀번호};
DB_URL={데이터베이스 URL};
DB_USERNAME={데이터베이스 유저네임};
GOOGLE_CLIENT_ID={GCP 클라이언트 아이디};
GOOGLE_CLIENT_SECRET={GCP 시크릿키};
GOOGLE_REDIRECT_URI=http://localhost:8080/api/v1/oauth2/google/callback
JWT_SECRET={JWT 시크릿키로 사용하고 싶은 값 아무거나};
KAKAO_CLIENT_ID={카카오 로그인에 사용할 클라이언트 아이디};
KAKAO_CLIENT_SECRET={카카오 로그인에 사용할 시크릿키};
KAKAO_REDIRECT_URI=http://localhost:8080/api/v1/oauth2/kakao/callback;
KTO_SERVICE_KEY={한국 관광공사 서비스키};
NAVER_CLIENT_ID={네이버 로그인에 사용할 클라이언트 아이디};
NAVER_CLIENT_SECRET={네이버 로그인에 사용할 클라이언트 시크릿};
NAVER_REDIRECT_URI=http://localhost:8080/api/v1/oauth2/naver/callback;
NAVER_DEVELOPERS_CLIENT_ID={검색 api 사용을 위한 클라이언트 아이디};
NAVER_DEVELOPERS_CLIENT_SECRET={검색 api 사용을 위한 시크릿키};
NCP_CLIENT_ID={지도 활용을 위한 NCP 클라이언트 아이디};
NCP_SECRET={지도 활용을 위한 NCP 시크릿 키};
REDIS_HOST={레디스 호스트};
REDIS_PASSWORD={레디스 비밀번호};
REDIS_PORT={레디스 접속 포트};
REDIS_USERNAME={레디스 유저네임. 기본값이면 default 라고 써야함 };
SMTP_PASSWORD={구글 메일서버 비밀번호};
SMTP_USERNAME={구글 메일 보낼 때 발신자로 사용할 계정};
````

3. 실행

환경변수를 모두 입력 했으면 IDE에서 프로젝트를 시작 해주세요
- application.yaml 파일에 ${KEY} 형태로 환경변수를 참조하고 있습니다. 에러가 난다면 환경변수를 제대로 입력 했는지 확인 해주세요. <br />
- 프로젝트 실행 후 http://localhost:8080/swagger-ui/index.html 경로로 접속하면 API 문서를 볼 수 있습니다.
</details>









## 규칙
1. mater / main 브랜치에는 절대로 Push 하지 않는다.
2. 새로운 기능이나 수정사항은 dev 에서 파생된 브랜치에서 작업하고 dev로 merge 한다.
3. 3명이상 승인을 받은 후 pr을 merge 한다.

## 개발 플로우
1. clone
3. 개발할 기능 내용이 담긴 브랜치 생성 예) feat/login, fix/login
4. 개발 완료 후 깃허브 원격 저장소로 push
5. Pull Request 생성 dev <- feat/login
6. 팀원끼리 코드 리뷰 하고 머지
7. 로컬에서 작업중이던 팀원들은 로컬 브랜치 최신화

## 역할 분담
#### 김찬규 ( 팀장 )
- 코스 관련 기능
#### 박준수
- 회원 인증 인가 관련 기능 
#### 오시은
- 게시판 관련 기능
#### 민선익
- 코스 관련 기능
#### 김주홍
- 회원 인증 인가 관련 기능





