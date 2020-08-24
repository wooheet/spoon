# Spoon Radip DJ/FAN Follow Rest API

## Contents
* [Specifications](#chapter-1)
* [How to run](#chapter-2)
* [Requirement](#chapter-3) 
* [Strategy](#chapter-4)
* [Domain](#chapter-5)
* [Entity](#chapter-6)
* [Architecture](#chapter-7)
* [Api Feature list](#chapter-8)
* [Api Endpoint](#chapter-9)
* [Performance Test](#chapter-10)

### <a name="chapter-1"></a>Specifications 
````
 OpenJDK11
 Spring Boot 2.3.1.RELEASE
 Spring Data Jpa
 Spring security
 Json web token
 Gradle
 lombok
 MySql / h2Database
 Swagger2
 Domain Driven Design
````

### <a name="chapter-2"></a>How to Run
```
Profile
- dev(MySql), none(h2)

1. 실행
./gradlew bootrun

# Using IntelliJ
1. Sync gradle
2. Run Application

2. Test 
./gradlew test

3. MySql Database ddl
./scripts/database
```

### <a name="chapter-3"></a>Requirement 
````
• Listener는 DJ를 Follow 하여 Fan이 될 수 있습니다.
• DJ가 Listener를 차단하거나 Listener가 DJ를 차단하면 Fan이 해제됩니다.
• Listener가 DJ의 프로필을 조회하여 DJ의 Fan Count를 확인할 수 있습니다.
• 차단된 관계에서는 서로의 프로필을 조회할 수 없습니다.
• DJ가 본인의 프로필을 조회하면 Fan Count와 Fan List(User List)를 확인할 수 있습니다.
````

### <a name="chapter-4"></a>Strategy 
```` 
- DDD(Domain Driven Design) 적용
- User, User Role 도메인으로 분리 / JPA fetch join 적용
- Following, Follower 도메인 
- API 기능명세에서 제시한 기능 구현
- 로그인 구현 ( 로그인 후 JWT 토큰 제공 ) 
- Jwt 적용  
- API 호출 시 token 값 유효성 여부 검증 
````

### <a name="chapter-5"></a>Domain 
```
유저(User)
   유저아이디
   이메일 
   패스워드
   유저롤
   팬리스트
   차단리스트
   생성일
   업데이트일 

유저롤(User_Roles)
   롤아이디
   유저아이디
   롤

팬(User_Fans)
   팬아이디
   유저아이디
   팬아이디

차단(User_Blocks)
   차단아이디
   유저아이디
   차단아아디
```

## <a name="chapter-6"></a>Entity
```
유저(User)
   유저아이디
   이메일 
   패스워드
   유저롤
   팬리스트
   차단리스트
   생성일
   업데이트일 

유저롤(User_Roles)
   롤아이디
   유저아이디
   롤

팬(User_Fans)
   팬아이디
   유저아이디
   팬아이디

차단(User_Blocks)
   차단아이디
   유저아이디
   차단아아디
```

### <a name="chapter-8"></a>Api Feature list 
```
- 회원 가입, Dj, Listener
- Listener -> Dj fan 신청
- Listener -> Dj 차단
- 프로필 조회 
``` 

### <a name="chapter-9"></a>Api Endpoint
```
API 실행 && 테스트 절차
1. DJ와 Listener 회원가입을 합니다.
2. Listener는 Dj에게 Fan 신청을 합니다.
3. DJ는 자신의 프로필을 조회합니다.
4. Listener는 이미 Fan인 DJ를 차단합니다.
5. DJ는 자신의 프로필을 조회합니다.


EndPoint : /v1/signup
Method : POST
{
    "email": "woo@listner.com",
    "password": "1234567",
    "passwordRE": "1234567",
    "roles": ["USER"]
},
{
    "email": "woo@dj.com",
    "password": "1234567",
    "passwordRE": "1234567",
    "roles": ["USER","DJ"]
}
Description : 로그인
{
    "success": true,
    "code": 0,
    "msg": "성공하였습니다.",
    "data": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZWR5QHIydi5pbyIsInJvbGVzIjpbeyJpZCI6MSwicm9sZSI6IlVTRVIifV0sImlhdCI6MTU5MjUxMDMzOSwiZXhwIjoxNTkyNTk2NzM5fQ.11Dnk3US5ZHuCmbae4qsWIJJyTF81_kFrgkGW3AcO8o"
}

Payload Example (required parameters)

|-----------|--------------|---------------------------------------------------|
| Parameter |Parameter Type| Description                                       |
|-----------|--------------|---------------------------------------------------|
| email     | @RequestBody |                                                   |
|-----------|--------------|---------------------------------------------------|
| password  | @RequestBody |                                                   |
|-----------|--------------|---------------------------------------------------|
| passwordRe| @RequestBody |                                                   |
|-----------|--------------|---------------------------------------------------|
| roles     | @RequestBody |                                                   |
|-----------|--------------|---------------------------------------------------|


----------------------------------------------------------------------------------------------------


EndPoint : /v1/signin
Method : POST
{
    "email": "woo@listner.com",
    "password": "1234567"
}
Description : 로그인
{
    "success": true,
    "code": 0,
    "msg": "성공하였습니다.",
    "data": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZWR5QHIydi5pbyIsInJvbGVzIjpbeyJpZCI6MSwicm9sZSI6IlVTRVIifV0sImlhdCI6MTU5MjUxMDMzOSwiZXhwIjoxNTkyNTk2NzM5fQ.11Dnk3US5ZHuCmbae4qsWIJJyTF81_kFrgkGW3AcO8o"
}

Payload Example (required parameters)

|-----------|--------------|---------------------------------------------------|
| Parameter |Parameter Type| Description                                       |
|-----------|--------------|---------------------------------------------------|
| email     | @RequestBody |                                                   |
|-----------|--------------|---------------------------------------------------|
| password  | @RequestBody |                                                   |
|-----------|--------------|---------------------------------------------------|


----------------------------------------------------------------------------------------------------


EndPoint : /v1/following
Method : POST
{
    "userId": 1,
}
Description : 팬 신청 

Payload Example (required parameters)

|-----------|--------------|---------------------------------------------------|
| Parameter |Parameter Type| Description                                       |
|-----------|--------------|---------------------------------------------------|
| userId    | @RequestBody | user id                                           |
|-----------|--------------|---------------------------------------------------|


----------------------------------------------------------------------------------------------------


EndPoint : /v1/block
Method : POST
{
    "userId": 1,
}
Description : 사용자 차단 

Payload Example (required parameters)

|-----------|--------------|---------------------------------------------------|
| Parameter |Parameter Type| Description                                       |
|-----------|--------------|---------------------------------------------------|
| userId    | @RequestBody | user id                                           |
|-----------|--------------|---------------------------------------------------|


----------------------------------------------------------------------------------------------------


EndPoint : /v1/user/{id}
Method : POST

Description : 사용자 조회

Payload Example (required parameters)

|-----------|--------------|---------------------------------------------------|
| Parameter |Parameter Type| Description                                       |
|-----------|--------------|---------------------------------------------------|
| id        | @PathVariable| user id                                           |
|-----------|--------------|---------------------------------------------------|


----------------------------------------------------------------------------------------------------