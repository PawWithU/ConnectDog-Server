# ConnectDog-Server

## 팀원

|민경혁|한호정|
|:------:|:------:|
| <img width="160px" src="https://avatars.githubusercontent.com/u/80199502?v=4"/> | <img width="160px" src="https://avatars.githubusercontent.com/u/98451999?v=4"/> | 
|[kyeong-hyeok](https://github.com/kyeong-hyeok)|[hojeong2747](https://github.com/hojeong2747)|
<br>

## **🔍 System Architecture**


<img src="https://github.com/PawWithU/.github/assets/80199502/0bffa118-c37f-4192-9426-6c7416618bd9"  width="830">


## **💻 Technology**

- Server
    - ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000.svg?style=flat-square&logo=intellij-idea&logoColor=white)
      ![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=flat-square&logo=Java&logoColor=white)
      ![Springboot](https://img.shields.io/badge/Springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
      ![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat-square&logo=Spring%20Security&logoColor=white)
      ![Shell Script](https://img.shields.io/badge/Shell_Script-%23121011.svg?style=flat-square&logo=gnu-bash&logoColor=white)
      ![JWT](https://img.shields.io/badge/JWT-black?style=flat-square&logo=JSON%20web%20tokens)
      ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=flat-square&logo=Gradle&logoColor=white)
      ![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=flat-square&logo=swagger&logoColor=white)
    - ![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat-square&logo=spring&logoColor=white)
      ![QueryDSL](https://img.shields.io/badge/QueryDSL-007ACC?style=flat-square&logo=kotlin&logoColor=white)
    - ![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=flat-square&logo=mariadb&logoColor=white)
      ![Redis](https://img.shields.io/badge/Redis-%23DD0031.svg?style=flat-square&logo=redis&logoColor=white)
      ![RDS](https://img.shields.io/badge/AWS%20RDS-527FFF?style=flat-square&logo=Amazon%20RDS&logoColor=white)
      ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=flat-square&logo=Hibernate&logoColor=white)
    - ![GitHub Actions](https://img.shields.io/badge/Github%20Actions-%232671E5.svg?style=flat-square&logo=githubactions&logoColor=white)
      ![EC2](https://img.shields.io/badge/AWS%20EC2-FF9900?style=flat-square&logo=Amazon%20EC2&logoColor=white)
      ![AWS S3](https://img.shields.io/badge/AWS%20S3-569A31?style=flat-square&logo=amazons3&logoColor=white)
      ![AWS CodeDeploy](https://img.shields.io/badge/AWS%20CodeDeploy-003545?style=flat-square&logo=AWS%20CodeDeploy&logoColor=white)
      ![Docker](https://img.shields.io/badge/Docker-%230db7ed.svg?style=flat-square&logo=docker&logoColor=white)
      ![Nginx](https://img.shields.io/badge/Nginx-%23009639.svg?style=flat-square&logo=nginx&logoColor=white)
  
- Co-working Tool
    - ![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=flat-square&logo=notion&logoColor=white)
      ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=postman&logoColor=white)
      ![Slack](https://img.shields.io/badge/Slack-4A154B?style=flat-square&logo=slack&logoColor=white)

<br>

### **🐾 기술 스택 선정 이유**

- IntelliJ는 Spring Boot 개발을 위해 필요한 모듈과 기능을 간편하게 사용할 수 있는 IDE입니다.
- Spring Boot는 Spring framework를 간편하게 사용할 수 있게 해주며 RDBMS와의 편리한 연동, 라이브러리의 버전 관리 자동화 등으로 Java 기반 애플리케이션 서버 개발에 특화되어 있습니다. 또한 자체적인 웹 서버를 내장하고 있어, 빠르고 간편하게 배포를 진행할 수 있다는 장점이 있고 Jar 파일로 프로젝트를 빌드 할 수 있어 클라우드 서비스 및 도커와 같은 가상화 환경에 빠르게 배포할 수 있습니다.
- Spring Security는 Spring 기반의 애플리케이션의 보안을 담당하는 스프링 하위 프레임워크입니다. 인증과 권한에 대한 부분을 필터 흐름에 따라 처리하여, 보안에 관련해 체계적으로 많은 옵션을 제공해 줍니다.
- Spring Data JPA는 JPA를 사용하기 편하도록 만들어 놓은 모듈로 repository 인터페이스를 제공해 간편하게 데이터에 접근할 수 있도록 도와줍니다.
- QueryDSL은 쿼리를 타입에 맞게 안전하게 생성 및 관리해 주는 라이브러리입니다. JPA는 복잡한 동적 쿼리를 구현하는 데 있어 한계가 있는데, QueryDSL은 자바 코드로 SQL문을 작성할 수 있어 컴파일 시에 오류가 발생해 잘못된 쿼리가 실행되는 것을 방지할 수 있습니다.
- MariaDB는 MySQL 데이터베이스 시스템을 기반으로 한 서비스로, MySQL과 호환성이 높고 MySQL에서 찾을 수 없는 수많은 강력한 기능과 유용성, 보안 및 성능 개선사항이 함께 제공됩니다. 또한 MySQL에 비해 확장성이 뛰어나고 쿼리 속도가 더 빠르다는 장점이 있습니다. 비용이 저렴하고 성능이 더 좋으며 라이선스 문제가 없는 MariaDB를 이번 프로젝트에 사용합니다.
- AWS EC2는 가상 컴퓨터를 임대 받아 그 위에 컴퓨터 애플리케이션들을 실행할 수 있게 하고, RDS는 관계형 데이터베이스를 제공하는 서비스로 DB의 설정, 운영, 백업 등의 기능을 편하게 이용할 수 있게 해줍니다.
- JWT는 Json Web Token으로 Json 포맷을 이용하여 이동봉사자에 대한 속성을 저장하는 Claim 기반의 Web Token입니다. 주로 클라이언트와 서버 사이에서 통신할 때 이동봉사자의 인증 또는 인가 정보를 안전하게 주고받기 위해서 사용되며, 안전하고 효율적으로 Stateless한 서버를 구현할 수 있습니다.
- Redis는 Key, Value 구조의 데이터를 저장하고 관리하는 오픈 소스 기반의 인메모리 데이터 구조 저장소입니다. 로그인 과정에서 발급되는 JWT인 Access Token과 Refresh Token 중 Refresh Token을 캐싱하여 이동봉사자가 빠르게 Access Token을 재발급 받을 수 있게 하기 위해 Redis를 사용하였습니다. JWT로 구현된 Access Token은 탈취 당하더라도 유저 정보는 안전하지만, 이를 그대로 활용하여 로그인할 수 있기 때문에 만료 시간을 짧게 설정하고 Refresh Token을 이용하여 이동봉사자의 불편을 줄였습니다.
- 무료로 사용할 수 있으며 비밀 값을 Repository Secret에 넣어 안전하게 관리할 수 있는 Github Actions를 사용하여 CI/CD를 구현했습니다.
- Github Actions를 통해 성공적으로 빌드된 프로젝트는 AWS S3에 업로드 되게 되고, Github Actions가 CodeDeploy에 배포를 요청하며, CodeDeploy는 S3로부터 zip 파일을 받아 배포를 진행합니다.
- AWS S3에 압축 파일을 전송하여 CI/CD를 구현하는 것은 효율적이고 빠른 배포 프로세스를 갖게 되며, 일관성, 보안, 롤백 용이성 등 다양한 이점을 제공합니다.
- AWS CodeDeploy는 신속하고 안정적인 애플리케이션 배포를 가능하게 하며, 배포 중 문제가 발생한 경우 롤백을 수행할 수 있고 해당 문제를 모니터링하고 로깅할 수 있습니다. 또한 배포 그룹을 관리할 수 있고, 여러 배포 방식을 지원하여 애플리케이션의 규모와 특성에 따라 유연하게 적용할 수 있습니다. 이러한 장점들과 확장성을 고려하여 AWS CodeDeploy를 이용하였습니다.
- CI/CD 구현 및 컨테이너화를 위해 Docker를 사용하였습니다. Docker Compose를 통해 빌드된 JAR 파일을 EC2 상에 컨테이너를 생성해 배포하였습니다. Docker는 컨테이너 기반의 오픈소스 가상화 플랫폼으로, 환경에 구애받지 않고 애플리케이션을 신속하게 구축, 테스트 및 배포할 수 있습니다. 다양한 프로그램들과 실행환경을 컨테이너로 규격화시켜 프로그램의 배포 및 관리를 단순화할 수 있어 편리합니다.
- 서버의 자원을 더욱 효율적으로 사용하기 위해 nginx를 사용하였고, Blue/Green 배포를 구현해 Nginx의 프록시 패스를 사용하여 요청을 블루 환경 또는 그린 환경으로 라우팅했습니다.

<br>

## 🔖 Naming Rules

#### `Java`
- Class & Interface: `UpperCamelCase`
    - Class 이름은 일반적으로 명사, 명사구
    - Interface 이름은 명사, 명사구도 되지만 형용사, 형용사구도 됨
- Package: `Lowercase` 복합단어 사용 금지
- Method & Parameter: `LowerCamelCase`
    - Method 이름은 동사, 동사구
- Constant: `UpperSnakeCase`
    - 모두 대문자로 밑줄(_)로 각 단어 구분
- Non-constant field names & Local variable: `LowerCamelCase`
- Camel case: defined
    - 구를 일반 ASCII로 변환하고 어퍼스트로피 제거
    - 단어로 나누고 공백과 나머지 구두점으로 분리
    - 각 단어를 `UpperCamelCase`로 표시하거나 첫 단어 제외한 각 단어는 `LowerCamelCase`
    - 모든 단어를 단일 식별자로 결합
<br>

## **🗂️ Commit Convention**

```
<type>(<scope>): <short summary>
<BLANK LINE>
<body>
<BLANK LINE>
<footer>
```

### **📌 Type**

| commit 명 | commit 뜻 |
| --- | --- |
| feat | 새로운 기능 추가 |
| fix | 버그 수정 |
| docs | 문서 수정 |
| style | 스타일 수정 (인덴테이션, 세미콜론) |
| refactor | 코드 리펙토링 (결과 변경x 코드 구조 재조정) |
| test | 테스트 코드, 리펙토링 테스트 코드 추가 |
| chore | 빌드 스크립트 수정, 기타 변경 사항 |


<br>

## **🐬 Git Flow**

<img src="https://github.com/PawWithU/ConnectDog-AOS/assets/63611804/1e05edea-7b3a-49aa-8dd8-70faca37be28"  width="650">

<br>
