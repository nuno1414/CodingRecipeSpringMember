use mysql;

-- select * from user;


-- 사용자 추가 (권한추가)
-- create user user_codingrecipe;   // 사용자 추가

create user user_codingrecipe@'%' identified by '1234';

-- 사용자(user)를 추가하면서 패스워드까지 설정기존에 사용하던 계정에 외부 접근 권한을 부여하려면, Host를 '%' 로 하여 똑같은 계정을 추가한다
-- create user 'userid'@'%' identified by '비밀번호';  // '%' 의 의미는 외부에서의 접근을 허용

-- drop user user_codingrecipe@localhost;    // 사용자 삭제

-- select * from user; -- 사용자 확인  

show databases;     -- DB 목록 확인

create database db_codingrecipe default character set utf8; -- default character set을 지정하지 않으면 한글이 깨져서 나오므로 주의해야 한다.

-- drop database DB명;       -- 데이터베이스 삭제

grant all privileges on db_codingrecipe.* to user_codingrecipe@'%'; -- 전체 권한 부여 

flush privileges;  -- 변경된 내용을 메모리에 반영(권한 적용)

SHOW GRANTS FOR user_codingrecipe@'%';  -- userid 와 host명까지 붙여서 검색해야 함


