# READ ME

## 1. View </span>

## 2. 기능

## 3. 고급 기능

## 4. 테이블 생성

```sql
-- ROOT
CREATE USER 'security'@'%' IDENTIFIED BY 'security1234'; 
GRANT ALL PRIVILEGES ON *.* TO 'security'@'%';

-- SECURITY 접속
CREATE DATABASE blogappdb;

USE blogappdb;

create table user(
    users_id int primary KEY auto_increment,
    username VARCHAR(20) UNIQUE ,
    password VARCHAR(500),
    email VARCHAR(50) UNIQUE,
    nickname varchar(20) UNIQUE,
    profile_img LONGTEXT,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT current_timestamp
);

create table love(
    love_id int primary KEY auto_increment,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    UNIQUE uk_loves (user_id,post_id),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp,
    created_at TIMESTAMP NOT NULL DEFAULT current_timestamp
);


create table post(
    post_id int primary KEY auto_increment,
    post_title varchar(20),
    post_content longtext,
    post_thumnail longtext,
    user_id int,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp,
    created_at TIMESTAMP NOT NULL DEFAULT current_timestamp
);

create table subscribe(
   subscribe_id INT primary KEY auto_increment,
   from_user_id INT,
   to_user_id INT,
   posts_id INT,
   updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp,
   created_at TIMESTAMP NOT NULL DEFAULT current_timestamp
);

create table visit(
   visit_id INT primary KEY auto_increment,
   user_id INT,
   post_id INT,
   UNIQUE uk_visits (user_id,post_id),
   updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp,
   created_at TIMESTAMP NOT NULL DEFAULT current_timestamp
);
```

## 5. 더미데이터

</br>

```sql
-- 비밀번호 : 1234
-- 이미지 저장경로 : C:\Temp\img => 폴더없을시 자동생성
INSERT INTO user(username, password,email,nickname, profile_img ,updated_at,created_at) VALUES('ssar','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','ssar@nate.com','이순신','테스트이미지1.jpeg',NOW(),NOW() );
INSERT INTO user(username, password,email,nickname, profile_img ,updated_at,created_at) VALUES('cos','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','cos@nate.com','장보고','테스트이미지2.jpg',NOW(), NOW());
INSERT INTO user(username, password,email,nickname, profile_img ,updated_at,created_at) VALUES('tan','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','tan@nate.com','세종대왕','테스트이미지3.jpg',NOW(), NOW());

INSERT INTO love(love_id, post_id,user_id,updated_at,created_at) VALUES(1,1,2,NOW(), NOW());
INSERT INTO love(love_id, post_id,user_id,updated_at,created_at) VALUES(2,1,3,NOW(), NOW());
INSERT INTO love(love_id, post_id,user_id,updated_at,created_at) VALUES(3,2,1,NOW(), NOW());
INSERT INTO love(love_id, post_id,user_id,updated_at,created_at) VALUES(4,2,3,NOW(), NOW());
INSERT INTO love(love_id, post_id,user_id,updated_at,created_at) VALUES(5,3,1,NOW(), NOW());
INSERT INTO love(love_id, post_id,user_id,updated_at,created_at) VALUES(6,3,2,NOW(), NOW());

INSERT INTO post(post_id, post_title,post_content,post_thumnail,user_id,updated_at,created_at) VALUES(1,'제목1','내용1','테스트이미지4.jpg',1,NOW(), NOW());
INSERT INTO post(post_id, post_title,post_content,post_thumnail,user_id,updated_at,created_at) VALUES(2,'제목2','내용2','테스트이미지5.jpg',1,NOW(), NOW());
INSERT INTO post(post_id, post_title,post_content,post_thumnail,user_id,updated_at,created_at) VALUES(3,'제목3','내용3','테스트이미지6.jpg',2,NOW(), NOW());
INSERT INTO post(post_id, post_title,post_content,post_thumnail,user_id,updated_at,created_at) VALUES(4,'제목4','내용4','테스트이미지7.jpg',2,NOW(), NOW());
INSERT INTO post(post_id, post_title,post_content,post_thumnail,user_id,updated_at,created_at) VALUES(5,'제목5','내용5','테스트이미지8.jpeg',3,NOW(), NOW());
INSERT INTO post(post_id, post_title,post_content,post_thumnail,user_id,updated_at,created_at) VALUES(6,'제목6','내용6','테스트이미지9.jpg',3,NOW(), NOW());

INSERT INTO subscribe(subscribe_id, from_user_id,to_user_id,posts_id,updated_at,created_at) VALUES(1,1,2,3,NOW(),NOW());
INSERT INTO subscribe(subscribe_id, from_user_id,to_user_id,posts_id,updated_at,created_at) VALUES(2,1,3,5,NOW(),NOW());
INSERT INTO subscribe(subscribe_id, from_user_id,to_user_id,posts_id,updated_at,created_at) VALUES(3,2,1,1,NOW(),NOW());
INSERT INTO subscribe(subscribe_id, from_user_id,to_user_id,posts_id,updated_at,created_at) VALUES(4,2,3,5,NOW(),NOW());
INSERT INTO subscribe(subscribe_id, from_user_id,to_user_id,posts_id,updated_at,created_at) VALUES(5,3,1,2,NOW(),NOW());
INSERT INTO subscribe(subscribe_id, from_user_id,to_user_id,posts_id,updated_at,created_at) VALUES(6,3,2,4,NOW(),NOW());

INSERT INTO visit(visit_id, user_id,post_id,updated_at,created_at) VALUES(1,1,1,NOW(),NOW());
INSERT INTO visit(visit_id, user_id,post_id,updated_at,created_at) VALUES(2,1,2,NOW(),NOW());
INSERT INTO visit(visit_id, user_id,post_id,updated_at,created_at) VALUES(3,2,3,NOW(),NOW());
INSERT INTO visit(visit_id, user_id,post_id,updated_at,created_at) VALUES(4,2,4,NOW(),NOW());
INSERT INTO visit(visit_id, user_id,post_id,updated_at,created_at) VALUES(5,3,5,NOW(),NOW());
INSERT INTO visit(visit_id, user_id,post_id,updated_at,created_at) VALUES(6,3,6,NOW(),NOW());
```

## 6. 결과
