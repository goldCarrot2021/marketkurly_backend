
<br>
<br>

## 프로젝트 소개 
마켓컬리 클론코딩을 목적으로 상품 상세페이지와 장바구니를 클론코딩한 프로젝트입니다.

<br>

#### 프론트엔드 Repository: 
https://github.com/doitfor10/marcketkurly-clone

<br>
<br>

## 개요
- 프로젝트 주제: 마켓컬리 클론코딩 ( 상품 페이지 , 장바구니 페이지 )
- 개발 인원 : 4명 (Front-end: 강영은, 강지헌 // Back-end: 이은지, 채진욱)
- 개발 기간 : 2021.04.02 ~ 2021.04.08
- 개발 환경 : React,SpringBoot ,Python
- 배포 환경 : AWS EC2 , RDS ,MYSQL
- 담당 역활 : 
  * 이은지 : 회원가입 & 상품 페이지 조회 & 크롤링(python,셀레니움) , 장바구니 구현 ,배포 
  * 채진욱 : jwt 로그인 방식 구현 
- 형상 관리 툴 : git
- 시연 영상 : https://www.youtube.com/watch?v=I_a46SRcIL0

<br>
<br>

### API 설계
![image](https://user-images.githubusercontent.com/78028746/120097230-5283de00-c16a-11eb-88e8-2525662225fe.png)

<br>
<br>

### 테이블 설계 
![image](https://user-images.githubusercontent.com/78028746/120097238-6596ae00-c16a-11eb-92eb-207b48e3181f.png)

<br>
<br>

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

<br>
<br>

## 기술 소개
- 상품 크롤링
   * 셀레니움을 이용해 마켓컬리 상품 크롤링
 
- 장바구니 구현
  * CRUD 구현 상품 수량 및 회원이 담은 상품 조회 
