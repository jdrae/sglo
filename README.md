# SGLO: 스그로

`스`프링으로 `그`럴듯한 서비스를 만들기 위해 필요한 `로`그인 외 여러 API 모음

[Spring Boot Starter OAuth2 Resource Server](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-oauth2-resource-server) 로 JWT 기반의 토큰 인증방식 구현

참고한 자료: [NOTE.md](https://github.com/jdrae/sglo/blob/master/NOTE.md)

## 요구사항

### 완료한것 
* 회원인증
  * 로그인 시 JWT 토큰 발급
    * 찾아볼것: body 에 응답 vs 헤더에 응답
  * refresh 토큰으로 access 토큰 발급
    * 찾아볼것: api 링크로 요청 vs auth filter 에서 재발급
* 회원가입
  * 필드 조건 Validation 으로 검증 후 예외 처리
  * 중복 회원 검증 후 예외 처리
* 기타
  * Swagger 문서화 적용 (springdoc-openapi)

### 추가할것 ➕
* 회원인증
  * 메일로 2차 인증하기
* 회원가입
  * SNS 로 가입/인증하기
  * 찾아볼것: 기본 유저클래스 상속받아서 필드 재구성 하기?
  * 찾아볼것: 회원 가입 후 자동 로그인?
* 회원서비스
  * CRUD 구현
  * 권한 확인 후 인가
  * 아이디/비밀번호 찾기
  * 비밀번호 변경
* 기타
  * 찾아볼것: 인증서버와 리소스서버의 분리
  * 예외 메세지 다국화
  * Docker