crEATE TABLE rty_community (
	com_no number(10) PRIMARY KEY,
	com_writer varchar2(30) NOT NULL,
	com_title varchar2(100) NOT NULL,
	com_text varchar2(1000) NOT NULL,
	com_date DATE NOT NULL,
	com_like NUMBER(10) NOT NULL,
	com_img1 varchar2(1000),
	com_img2 varchar2(1000),
	com_img3 varchar2(1000),
	com_img4 varchar2(1000),
	com_img5 varchar2(1000),
	com_type number(2) NOT NULL,
	com_rcpno number(30),
	com_cate varchar2(100),
	constraint rty_community_writer
		foreign key(com_writer) references rty_MEMBER(mem_id)
		on delete cascade
);

CREATE SEQUENCE rty_comlike_seq;

SELECT * FROM rty_community;
SELECT * FROM rty_com_like;
SELECT * FROM rty_member;
SELECT * FROM rty_today_recipe;
SELECT * FROM rty_comment;

DROP TABLE rty_com_like CASCADE CONSTRAINT purge;
DROP SEQUENCE rty_comlike_seq;
DELETE FROM rty_community WHERE com_no = 195;

UPDATE rty_community SET com_like=1 WHERE com_no=150;

CREATE TABLE rty_comment (
	rc_no number(15) PRIMARY KEY,
	rc_writer varchar2(30) NOT NULL,
	rc_text varchar2(100) NOT NULL,
	rc_date DATE NOT NULL,
	rc_com_no number(10) NOT NULL,
	rc_com_type number(1) NOT NULL,
	constraint rty_comment_writer
		foreign key(rc_writer) references rty_MEMBER(mem_id)
		on delete CASCADE,
	CONSTRAINT rty_post_no
		foreign key(rc_com_no) references rty_community(com_no)
		on delete CASCADE
);

CREATE TABLE rty_reply (
	rr_no number(15) PRIMARY KEY,
	rr_writer varchar2(30) NOT NULL,
	rr_text varchar2(100) NOT NULL,
	rr_date DATE NOT NULL,
	rr_par_no number(15) NOT NULL,
	CONSTRAINT rty_reply_writer
		FOREIGN key(rr_writer) REFERENCES rty_member(mem_id)
		ON DELETE CASCADE,
	CONSTRAINT rty_comment_no
		foreign key(rr_par_no) references rty_comment(rc_no)
		on delete CASCADE
);

CREATE TABLE rty_com_like (
	rcl_no number(15) PRIMARY KEY,
	rcl_mem_id varchar2(30) NOT NULL,
	rcl_post_no number(10) NOT NULL,
	rcl_post_type number(1) NOT NULL,
	CONSTRAINT rty_comlike_user
		FOREIGN key(rcl_mem_id) REFERENCES rty_member(mem_id)
		ON DELETE CASCADE,
	CONSTRAINT rty_comlike_post
		FOREIGN key(rcl_post_no) REFERENCES rty_community(com_no)
		ON DELETE CASCADE
);

CREATE TABLE rty_today_recipe (
	rtr_no number(30) PRIMARY KEY,
	rtr_name varchar2(50) NOT NULL,
	rtr_rcpno number(10) NOT null
);

create SEQUENCE rty_today_seq;

CREATE TABLE rty_search_rank (
    rtr_id NUMBER PRIMARY KEY,       
    rtr_keyword VARCHAR2(255) NOT NULL, 
    rtr_search_count NUMBER DEFAULT 1
);

-- 추천 및 즐겨찾기 테이블
CREATE TABLE rty_member_recipe (
	rmr_no number(30) PRIMARY KEY,
	rmr_member_id varchar2(30) NOT NULL,
	rmr_recipe_id number(6) NOT NULL,
	rmr_favorite number(1) DEFAULT 0,
	UNIQUE (rmr_member_id, rmr_recipe_id)
);
CREATE  SEQUENCE rty_member_recipe_seq;

CREATE SEQUENCE rty_search_seq;
DROP TABLE rty_search_rank;

SELECT rtr_keyword, SUM(rtr_search_count)AS total_search_count
FROM rty_search_rank
GROUP BY rtr_keyword
ORDER BY total_search_count DESC
LIMIT 10;

SELECT *
FROM rty_search_rank;
DELETE FROM rty_search_rank
WHERE rtr_keyword = '망치';

SELECT *
FROM(
SELECT rtr_keyword,SUM(rtr_search_count) AS tsc
FROM rty_search_rank 
GROUP BY rtr_keyword 
ORDER BY tsc DESC
)
WHERE rownum <= 10; 

DELETE FROM rty_search_rank WHERE rtr_keyword = 'undefined';



CREATE TABLE rty_product_AnalDTA(
	rpA_prodid varchar2(255) PRIMARY KEY,
	rpA_schkey varchar2(255 char) NOT NULL, 
	rpA_title varchar2(255) NOT NULL,
	rPA_price number(10) NOT NULL,
	rPA_img varchar2(3000 char) NOT NULL,
	rPA_link varchar2(255 char) NOT null
);

DROP TABLE rty_product_AnalDTA;

SELECT 
    rPA_schkey, 
    rPA_title, 
    AVG(rPA_price) AS 평균가격, 
    MIN(rPA_price) AS 최소가격, 
    MAX(rPA_price) AS 최대가격,
    ROUND(STDDEV(rPA_price), 2) AS 표준편차, 
    COUNT(*) AS 데이터수
FROM 
    rty_product_AnalDTA
WHERE 
    rPA_title LIKE '%과자%' -- '과자'를 포함하는 모든 데이터 검색
GROUP BY 
    rPA_schkey, rPA_title
ORDER BY 
    rPA_schkey; -- 필요에 따라 정렬 기준 추가

SELECT *
FROM rty_product_AnalDTA;

SELECT *
FROM RTY_MARKET_KEYWORD_PRICE;

SELECT 
    rPA_title, 
    AVG(rPA_price) AS 평균가격, 
    MIN(rPA_price) AS 최소가격, 
    MAX(rPA_price) AS 최대가격,
    ROUND(STDDEV(rPA_price), 2) AS 표준편차, 
    COUNT(*) AS 데이터수
FROM 
    rty_product_AnalDTA
WHERE 
    rPA_title LIKE '%과자%' -- '과자'를 포함하는 모든 데이터 검색
GROUP BY 
    rPA_title

    SELECT 
    rPA_title,
    AVG(rPA_price) AS 평균가격,
    MIN(rPA_price) AS 최소가격,
    MAX(rPA_price) AS 최대가격,
    ROUND(STDDEV(rPA_price), 2) AS 표준편차,
    COUNT(*) AS 데이터수
FROM 
    rty_product_AnalDTA
WHERE 
    rPA_title LIKE '%과자%'
GROUP BY 
    rPA_title;

SELECT *
FROM (
    SELECT 
        rPA_title,
        rPA_img,
        rPA_link,
        PERCENTILE_CONT(0.15) WITHIN GROUP (ORDER BY rPA_price) AS lwp
    FROM 
        rty_product_AnalDTA
    WHERE 
        rPA_schkey like '%과자%'
    GROUP BY 
        rPA_title, rPA_img, rPA_link
)
WHERE rownum <= 10;

    
SELECT 
    AVG(rPA_price) AS 평균가격,
   	PERCENTILE_CONT(0.15) WITHIN GROUP (ORDER BY rPA_price),
	PERCENTILE_CONT(0.85) WITHIN GROUP (ORDER BY rPA_price),
    ROUND(STDDEV(rPA_price), 2) AS 표준편차,
    COUNT(*) AS 데이터수
FROM 
    rty_product_AnalDTA
WHERE 
    rPA_title LIKE '%사과%';

CREATE TABLE rty_product_price_history (
    rph_id NUMBER PRIMARY KEY,
    rph_prodid VARCHAR2(255) NOT NULL,
    rph_price NUMBER(10) NOT NULL,
    rph_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (rph_prodid) REFERENCES rty_product_AnalDTA(rpA_prodid)
);

DROP TABLE rty_product_price_history;

SELECT *
FROM rty_product_AnalDTA;


UPDATE rty_product_AnalDTA
SET rPA_title = '무설탕 현미<b>과자</b> 대용량 구운 쌀<b>과자</b> 현미칩 사무실 탕비실 어르신 임산부 건강한 곡물 간식',
	rPA_price = 2200,
	rPA_img = 'https://shopping-phinf.pstatic.net/main_8532062/85320626880.6.jpg',
	rPA_link = 'https://smartstore.naver.com/main/products/7776126558'
WHERE rPA_prodid = '85320626880'


SELECT *
FROM rty_product_price_history;

SELECT 
rPA_schkey
, AVG(rPA_price)
, PERCENTILE_CONT(0.15) WITHIN GROUP (ORDER BY rPA_price)
, PERCENTILE_CONT(0.85) WITHIN GROUP (ORDER BY rPA_price)
, ROUND(STDDEV(rPA_price), 2)
, COUNT(*) 
FROM rty_product_AnalDTA 
WHERE rPA_schkey = '사과'
GROUP BY rpa_schkey;

CREATE TABLE 

DROP TABLE rty_product_AnalDTA;

SELECT *
FROM(
SELECT rpA_title ,rPA_img , rPA_link ,PERCENTILE_CONT(0.15) WITHIN GROUP (ORDER BY rPA_price) AS lwp 
FROM rty_product_AnalDTA
WHERE rPA_schkey = '과자'
GROUP BY rPA_title, rPA_img, rPA_link
ORDER BY lwp ASC)
WHERE rownum <= 10;

SELECT PERCENTILE_CONT(0.15) WITHIN GROUP (ORDER BY rPA_price) AS lwp
FROM rty_product_AnalDTA;

SELECT rpA_title, rPA_price, rPA_img, rPA_link
FROM rty_product_AnalDTA
WHERE rpA_schkey = '과자'



WITH ranked_data AS (
    SELECT 
        rpA_title,
        rPA_price,
        ROW_NUMBER() OVER (PARTITION BY rpA_title ORDER BY rpA_prodid) AS rn
    FROM rty_product_AnalDTA
    WHERE rpA_title = '고기'
);

DROP TABLE rty_product_AnalDTA;

CREATE TABLE rty_Market_Keyword_Price(
	rMKP_id number(30) PRIMARY KEY,
	rMKP_title varchar2(255 char) NOT NULL,
	rMKP_schkey varchar2(255 char) NOT NULL,
	rMKP_aver number(30) NOT NULL,
	rMKP_low number(30) NOT NULL,
	rMKP_high number(30) NOT NULL,
	rMKP_pyun number(30) NOT NULL,
	rMKP_DTACT number(30) NOT NULL,
	rMKP_DATE DATE
);

DROP TABLE rty_Market_Keyword_Price;


SELECT 
    TRUNC(rMKP_DATE) AS 날짜, -- 날짜만 추출하여 그룹화
    PERCENTILE_CONT(0.50) WITHIN GROUP (ORDER BY rMKP_aver),
    PERCENTILE_CONT(0.15) WITHIN GROUP (ORDER BY rMKP_low),
	PERCENTILE_CONT(0.90) WITHIN GROUP (ORDER BY rMKP_high),
    AVG(rMKP_pyun) AS 표준편차,
    SUM(rMKP_DTACT) AS 데이터수	
FROM 
    rty_Market_Keyword_Price
WHERE 
    rMKP_schkey LIKE '%과자%' -- 부분 일치 검색
GROUP BY 
    TRUNC(rMKP_DATE) -- 날짜 단위로 그룹화
ORDER BY 
    TRUNC(rMKP_DATE); -- 날짜 기준으로 정렬

    SELECT *
    FROM rty_market_keyword_price;

SELECT count(RMKP_ID)
FROM rty_Market_Keyword_Price
ORDER BY RMKP_ID DESC;

 SELECT *
 FROM rty_market_keyword_Price;

SELECT 
	rMKP_DATE AS 날짜,
    AVG(rMKP_aver) AS 평균가격,
    MIN(rMKP_low) AS 최저가격,
    MAX(rMKP_high) AS 최고가격,
    ROUND(STDDEV(rMKP_pyun), 2) AS 표준편차,
    SUM(rMKP_DTACT) AS 데이터수
FROM 
    rty_Market_Keyword_Price
WHERE 
    rMKP_schkey LIKE '과자' -- schkey에 inputkeyword가 포함된 데이터만 필터링
GROUP BY 
    rMKP_DATE
ORDER BY 
    RMKP_DATE;

SELECT file_name
FROM dba_data_files
WHERE tablespace_name = 'SYSTEM';


SELECT 
 AVG(rPA_price) AS 평균가격, 
    MIN(rPA_price) AS 최소가격, 
    PERCENTILE_CONT(0.85) WITHIN GROUP (ORDER BY rPA_price),
    ROUND(STDDEV(rPA_price), 2) AS 표준편차, 
    COUNT(*) AS 데이터수
FROM rty_product_AnalDTA
WHERE rpA_schkey = '사과'

SELECT *
FROM rty_market_keyword_price;



SELECT 
	RMKP_SCHKEY,
	rMKP_DATE,
    AVG(rMKP_aver) AS 평균가격,
    MIN(rMKP_low) AS 최저가격,
    MAX(rMKP_high) AS 최고가격,
    ROUND(STDDEV(rMKP_pyun), 2) AS 표준편차,
    SUM(rMKP_DTACT) AS 데이터수
FROM 
    rty_Market_Keyword_Price
WHERE 
    rMKP_schkey LIKE '과자' -- schkey에 inputkeyword가 포함된 데이터만 필터링
GROUP BY 
    rMKP_schkey, rMKP_DATE
ORDER BY 
    rMKP_schkey, rMKP_DATE
SELECT
	RMKP_TITLE,
    TO_CHAR(rMKP_DATE, 'YYYY-MM-DD') AS 날짜,
    rMKP_aver AS 평균가격,
    rMKP_low AS 최저가격,
    rMKP_high AS 최고가격,
    rMKP_pyun AS 표준편차,
    rMKP_DTACT AS 데이터수
FROM 
    rty_Market_Keyword_Price
WHERE 
    rMKP_schkey LIKE '과자' -- 특정 키워드 포함 필터링
ORDER BY 
    TO_CHAR(rMKP_DATE, 'YYYY-MM-DD');


INSERT INTO rty_market_keyword_price
VALUES(0, 'tt', 'tt', 30, 30, 30, 30, 30, sysdate);

DROP TABLE rty_market_Keyword_price;

SELECT *
FROM rty_market_Keyword_price;



ALTER TABLE rty_Market_Keyword_Price
DROP COLUMN rMKP_tt;

SELECT DISTINCT rMKP_schkey FROM
ALTER TABLE rty_Market_Keyword_Price
ADD rMKP_TT VARCHAR2(255 CHAR);


UPDATE rty_Market_Keyword_Price
SET _tt = '기본값'
WHERE rMKP_tt IS NULL;

ALTER TABLE rty_Market_Keyword_Price
MODIFY RMKP_TT VARCHAR2(255 CHAR) NOT NULL;


ALTER TABLE rty_Market_Keyword_Price
MODIFY rMKP_TT VARCHAR2(255 CHAR) NOT NULL;

UPDATE rty_Market_Keyword_Price
SET rMKP_TT = '';

SELECT *
FROM rty_market_Keyword_price;



SELECT rMKP_DATE, rMKP_aver, rMKP_low, rMKP_high, rMKP_pyun, rMKP_DTACT
FROM rty_Market_keyword_price
WHERE rMKP_schkey = '과자';

DROP TABLE rty_MarketkeywordPrice;
CREATE SEQUENCE rMKP_seq;

SELECT *
FROM rty_Market_keyword_price;


SELECT 
    rpA_title,
    AVG(rPA_price) AS 평균가격,
    PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY rPA_price) AS 중간가격,
    MIN(rPA_price) AS 최저가격,
    MAX(rPA_price) AS 최고가격,
    ROUND(STDDEV(rPA_price), 2) AS 가격표준편차,
    COUNT(*) AS 데이터수
FROM ranked_data
WHERE rn <= 500
GROUP BY rpA_title;

SELECT *
FROM rty_product_AnalDTA;

SELECT 
    rPA_schkey,
    rPA_title, 
    AVG(rPA_price) AS 평균가격, 
    MIN(rPA_price) AS 최소가격, 
    MAX(rPA_price) AS 최대가격, 
    ROUND(STDDEV(rPA_price), 2) AS 표준편차, 
    COUNT(*) AS 데이터수
FROM rty_product_AnalDTA
WHERE rPA_title LIKE '%라면%'
GROUP BY rPA_schkey, rPA_title;



SELECT 
    rPA_title, 
    AVG(rPA_price) AS 평균가격, 
    MIN(rPA_price) AS 최소가격, 
    MAX(rPA_price) AS 최대가격, 
    ROUND(STDDEV(rPA_price), 2) AS 표준편차, 
    COUNT(*) AS 데이터수
FROM rty_product_AnalDTA
WHERE rPA_title LIKE '%라면%'
GROUP BY rPA_title;

SELECT *
FROM rty_market_keyword_price

SELECT 
    rPA_schkey, 
    AVG(rPA_price) AS 평균가격, 
    MIN(rPA_price) AS 최소가격, 
    MAX(rPA_price) AS 최대가격, 
    ROUND(STDDEV(rPA_price), 2) AS 표준편차, 
    COUNT(*) AS 데이터수
FROM rty_product_AnalDTA
WHERE rpA_title LIKE '%라면%'
GROUP BY rPA_schkey;


SELECT rPA_schkey, AVG(rPA_price), MIN(rPA_price), MAX(rPA_price),  ROUND(STDDEV(rPA_price), 2), COUNT(*) AS 데이터수
FROM rty_product_AnalDTA
WHERE rpA_title LIKE '%라면%';
GROUP BY rpa_title;

SELECT * 
FROM rty_product_AnalDTA
WHERE rpA_title LIKE '%라면%';


SELECT 
    AVG(rPA_price) AS 평균가격,
    MIN(rPA_price) AS 최저가격,
    MAX(rPA_price) AS 최고가격,
    ROUND(STDDEV(rPA_price), 2) AS 가격표준편차,
    COUNT(*) AS 데이터수
FROM rty_product_AnalDTA
WHERE rpA_title = '고기'
GROUP BY rpA_title

DELETE FROM rty_product_AnalDTA
WHERE rpa_title = '망고';

SELECT *
FROM rty_product_AnalDTA;

SELECT AVG(rpa_price)
FROM rty_product_AnalDTA
WHERE rpa_title = '과자';

SELECT 
    AVG(rpa_price) as average,
    MEDIAN(rpa_price) as MEDIAN_PRICE,
    STDDEV(rpa_price) AS devstr,
    COUNT(*) as counter
FROM rty_product_AnalDTA
WHERE rpa_title = '과자';

SELECT 
    AVG(rpa_price) as 평균가,
    STDDEV(rpa_price) as 표준편차,
    COUNT(*) as 데이터수
    
FROM rty_product_AnalDTA
WHERE rpa_title = '고기';

SELECT DISTINCT rMKP_schkey from rty_market_keyword_price;

SELECT *
FROM rty_product_analDTA;
INSERT INTO rty_product_AnalDTA VALUES('1234891284', '망고', 4000);

SELECT RPA_schkey, AVG(rpa_price), MIN(rPA_price), 
PERCENTILE_CONT(0.85) WITHIN GROUP (ORDER BY rPA_price),  
ROUND(STDDEV(rPA_price), 2)
FROM rty_product_AnalDTA
WHERE rpa_schkey = '과자'
GROUP BY rpa_schkey;

SELECT RMKP_schkey, RMKP_title, RMKP_AVER, RMKP_LOW, RMKP_HIGH, RMKP_PYUN, RMKP_DTACT, RMKP_DATE
PERCENTILE_CONT(0.85) WITHIN GROUP (ORDER BY rPA_price) AS 백분위가격, 
ROUND(STDDEV(rPA_price), 2) AS 표준편차, COUNT(*) AS 데이터수 
FROM  rty_market_keyword_price 
WHERE RMKP_title LIKE '과자'
GROUP BY RMKP_schkey, RMKP_title

SELECT *
FROM rty_market_keyword_price;

SELECT RMKP_SCHKEY, TO_CHAR(rMKP_DATE, 'YYYY-MM-DD') AS 날짜, 
       AVG(rMKP_aver) AS 평균가격, MIN(rMKP_low) AS 최저가격,
       MAX(rMKP_high) AS 최고가격, ROUND(STDDEV(rMKP_pyun), 2) AS 표준편차,
       SUM(rMKP_DTACT) AS 데이터수
FROM rty_Market_Keyword_Price
WHERE rMKP_schkey LIKE '%과자%'
GROUP BY RMKP_SCHKEY, TO_CHAR(rMKP_DATE, 'YYYY-MM-DD')
ORDER BY RMKP_SCHKEY, TO_CHAR(rMKP_DATE, 'YYYY-MM-DD');

SELECT TO_CHAR(rMKP_DATE, 'YYYY-MM-DD') AS 날짜,
       AVG(rMKP_aver) AS 평균가격, MIN(rMKP_low) AS 최저가격,
       MAX(rMKP_high) AS 최고가격, ROUND(STDDEV(rMKP_pyun), 2) AS 표준편차,
       SUM(rMKP_DTACT) AS 데이터수
FROM rty_Market_Keyword_Price
WHERE rMKP_schkey LIKE '%과자%'
GROUP BY RMKP_SCHKEY, TO_CHAR(rMKP_DATE, 'YYYY-MM-DD')
ORDER BY RMKP_SCHKEY, TO_CHAR(rMKP_DATE, 'YYYY-MM-DD');

SELECT rMKP_DATE AS 날짜, 
       AVG(rMKP_aver) AS 평균가격, MIN(rMKP_low) AS 최저가격, 
       MAX(rMKP_high) AS 최고가격, ROUND(STDDEV(rMKP_pyun), 2) AS 표준편차, 
       SUM(rMKP_DTACT) AS 데이터수
FROM rty_Market_Keyword_Price
WHERE rMKP_schkey LIKE '과자'
GROUP BY RMKP_SCHKEY, rMKP_DATE
ORDER BY RMKP_SCHKEY, rMKP_DATE;

CREATE TABLE PRODUCT (
    PRODUCT_ID      VARCHAR2(50)    NOT NULL,       -- 상품 ID (기본 키)
    TITLE           VARCHAR2(500)   NOT NULL,       -- 상품명
    PRICE			number(10)		NOT NULL,
    LINK            VARCHAR2(500),                  -- 상품 링크
    IMAGE           VARCHAR2(500),                  -- 상품 이미지 URL
    MALL_NAME       VARCHAR2(100),                  -- 쇼핑몰 이름
    BRAND           VARCHAR2(100),                  -- 브랜드
    MAKER           VARCHAR2(100),                  -- 제조사
    CATEGORY1       VARCHAR2(100),                  -- 카테고리 1
    CATEGORY2       VARCHAR2(100),                  -- 카테고리 2
    CATEGORY3       VARCHAR2(100),                  -- 카테고리 3
    CATEGORY4       VARCHAR2(100),                  -- 카테고리 4
    PRODUCT_TYPE    NUMBER(2),                      -- 상품 유형 (1: 일반상품, 2: 중고상품)
    REGISTERED_DATE DATE DEFAULT SYSDATE,          -- 등록일
    PRIMARY KEY (PRODUCT_ID)
);

SELECT * 
FROM PRODUCT;

CREATE TABLE PRICE_HISTORY (
    PRODUCT_ID      VARCHAR2(50)    NOT NULL,       -- 상품 ID (외래 키)
    PRICE_DATE      DATE            NOT NULL,       -- 가격 측정 날짜
    LPRICE          NUMBER(15, 2)   NOT NULL,       -- 최저 가격
    HPRICE          NUMBER(15, 2),                  -- 최고 가격
    AVG_PRICE       NUMBER(15, 2),                  -- 평균 가격
    CATEGORY2       VARCHAR2(100),                  -- 카테고리 2
    CATEGORY3       VARCHAR2(100),   
    CREATED_AT      TIMESTAMP DEFAULT SYSDATE,      -- 기록 생성 시간
    
    PRIMARY KEY (PRODUCT_ID),
    FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(PRODUCT_ID)
);

SELECT *
FROM PRICE_HISTORY;

DROP TABLE PRODUCT;
DROP TABLE price_history;



