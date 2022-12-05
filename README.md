# READ ME

## 1. View </span>

## 2. 기능

## 3. 고급 기능

## 4. 테이블 생성

```sql
USE blogappdb;

create table user(
    users_id int primary KEY auto_increment,
    username VARCHAR(20),
    password varchar(20),
    email VARCHAR(50),
    nickname varchar(20),
    profile_img LONGTEXT,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT current_timestamp
);

create table love(
    love_id int primary KEY auto_increment,
    posts_id INT NOT NULL,
    usesr_id INT NOT NULL,
    UNIQUE uk_loves (users_id,posts_id),
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
   user_id INT,
   users_id INT,
   updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp,
   created_at TIMESTAMP NOT NULL DEFAULT current_timestamp
);
```

## 5. 더미데이터

</br>

```sql
INSERT INTO user(username, password,email,nickname, profile_img ,updated_at,created_at) VALUES('ssar','1234','ssar@nate.com','이순신','testimg1',NOW(),NOW() );
INSERT INTO user(username, password,email,nickname, profile_img ,updated_at,created_at) VALUES('cos','1234','cos@nate.com','장보고','testimg2',NOW(), NOW());
INSERT INTO user(username, password,email,nickname, profile_img ,updated_at,created_at) VALUES('tan','1234','tan@nate.com','세종대왕','testimg3',NOW(), NOW());

INSERT INTO love(love_id, post_id,user_id,updated_at,created_at) VALUES(1,1,2,NOW(), NOW());
INSERT INTO love(love_id, post_id,user_id,updated_at,created_at) VALUES(2,1,3,NOW(), NOW());
INSERT INTO love(love_id, post_id,user_id,updated_at,created_at) VALUES(3,2,1,NOW(), NOW());
INSERT INTO love(love_id, post_id,user_id,updated_at,created_at) VALUES(4,2,3,NOW(), NOW());
INSERT INTO love(love_id, post_id,user_id,updated_at,created_at) VALUES(5,3,1,NOW(), NOW());
INSERT INTO love(love_id, post_id,user_id,updated_at,created_at) VALUES(6,3,2,NOW(), NOW());

INSERT INTO post(post_id, post_title,post_content,post_thumnail,user_id,updated_at,created_at) VALUES(1,'제목1','내용1','테스트이미지1',1,NOW(), NOW());
INSERT INTO post(post_id, post_title,post_content,post_thumnail,user_id,updated_at,created_at) VALUES(2,'제목2','내용2','테스트이미지2',1,NOW(), NOW());
INSERT INTO post(post_id, post_title,post_content,post_thumnail,user_id,updated_at,created_at) VALUES(3,'제목3','내용3','테스트이미지3',2,NOW(), NOW());
INSERT INTO post(post_id, post_title,post_content,post_thumnail,user_id,updated_at,created_at) VALUES(4,'제목4','내용4','테스트이미지4',2,NOW(), NOW());
INSERT INTO post(post_id, post_title,post_content,post_thumnail,user_id,updated_at,created_at) VALUES(5,'제목5','내용5','테스트이미지5',3,NOW(), NOW());
INSERT INTO post(post_id, post_title,post_content,post_thumnail,user_id,updated_at,created_at) VALUES(6,'제목6','내용6','테스트이미지6',3,NOW(), NOW());

INSERT INTO subscribe(subscribe_id, user_id,users_id,updated_at,created_at) VALUES(1,1,2,NOW(),NOW());
INSERT INTO subscribe(subscribe_id, user_id,users_id,updated_at,created_at) VALUES(2,1,3,NOW(),NOW());
INSERT INTO subscribe(subscribe_id, user_id,users_id,updated_at,created_at) VALUES(3,2,1,NOW(),NOW());
INSERT INTO subscribe(subscribe_id, user_id,users_id,updated_at,created_at) VALUES(4,2,3,NOW(),NOW());
INSERT INTO subscribe(subscribe_id, user_id,users_id,updated_at,created_at) VALUES(5,3,1,NOW(),NOW());
INSERT INTO subscribe(subscribe_id, user_id,users_id,updated_at,created_at) VALUES(6,3,2,NOW(),NOW());
```

## 6. 결과
