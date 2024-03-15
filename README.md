![Auto Assign](https://github.com/like-lion-3team/demo-repository/actions/workflows/auto-assign.yml/badge.svg)

![Proof HTML](https://github.com/like-lion-3team/demo-repository/actions/workflows/proof-html.yml/badge.svg)

# Welcome to your organization's demo respository
This code repository (or "repo") is designed to demonstrate the best GitHub has to offer with the least amount of noise.

The repo includes an `index.html` file (so it can render a web page), two GitHub Actions workflows, and a CSS stylesheet dependency.



1. mater / main 브랜치에는 절대로 Push 하지 않는다.
2. 개발중인 내용은 dev브랜치로 merge한다.


흐름 예시
1. clone 
2. 로컬에서도 dev브랜치 생성 dev브랜치로 이동
3. 개발할 기능 내용이 담긴 브랜치 생성 예) feat/login,  fix/login
4. 개발 완료 후 깃허브 원격 저장소로 push
5. Pull Request 생성  dev <- feat/login
6. 팀원끼리 코드 리뷰 하고 머지
7. 로컬에서 작업중이던 팀원들은 로컬 브랜치 최신화  git pull origin dev
