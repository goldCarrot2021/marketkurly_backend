# 항해99 마켓컬리 클론코딩 프로젝트 백엔드입니다.

### 프론트엔드 Repository: https://github.com/doitfor10/marcketkurly-clone


## 개요
- 프로젝트 주제: 마켓컬리 클론코딩
- 개발 인원 4명 (Front-end: 강영은, 강지헌 // Back-end: 이은지, 채진욱)
- 개발 기간: 2021.04.02 ~ 2021.04.08
- `Client`: React // `Server`: Spring-boot

## 개발 환경
- Framwork:<br>`Spring`
- Server:<br>`Amazon EC2 Ubuntu`
- DB:<br>`Amazon RDS Mysql`
- Server:<br>`Amazon EC2 Ubuntu`


## 주요기능
### 회원가입
- 회원 가입 
- 유저 아이디 중복 체크 api

### 로그인
-  jwt 방식으로 로그인 구현
  
### 상품 목록
- 상품 목록 api

### 장바구니
- 장바구니 상품 추가 
- 장바구니 조회
- 장바구니 상품 개수 변경
- 
### Entity 설계
- User
```
uid
username
password
email
name
address
```
- Product
```
pid
title
price
subtext
img
```
- Cart
```
cid
product
user
count

```
