-- ===============================
-- TABLE CREATION (MariaDB/MySQL)
-- ===============================

CREATE TABLE IF NOT EXISTS local_govs (
                            local_no SMALLINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                            local_district VARCHAR(50) NOT NULL,
                            local_name VARCHAR(50) NULL,
                            local_contact VARCHAR(15) NOT NULL,
                            local_email VARCHAR(50) NOT NULL,
                            UNIQUE KEY uq_district_name (local_district, local_name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS members (
                         member_no BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                         member_name VARCHAR(50) NOT NULL,
                         member_email VARCHAR(255) NOT NULL,
                         member_role VARCHAR(20) NOT NULL,
                         member_pwd VARCHAR(50) NOT NULL,
                         member_phone VARCHAR(20) NOT NULL,
                         member_birth DATE NOT NULL,
                         created_at DATETIME NOT NULL,
                         updated_at DATETIME NULL,
                         withdrawal_at DATETIME NULL
);

CREATE TABLE IF NOT EXISTS accounts (
                          account_no BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                          member_no BIGINT NOT NULL,
                          bank_name VARCHAR(100) NOT NULL,
                          account_number VARCHAR(50) NOT NULL,
                          CONSTRAINT FK_members_TO_accounts FOREIGN KEY (member_no)
                              REFERENCES members(member_no)
);

CREATE TABLE IF NOT EXISTS events_categories (
                                   category_no BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                                   category_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS events (
                        event_no BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                        local_no SMALLINT NOT NULL,
                        category_no BIGINT NOT NULL,
                        event_title VARCHAR(255) NOT NULL,
                        event_address VARCHAR(255) NOT NULL,
                        event_start_date DATE NOT NULL,
                        event_end_date DATE NOT NULL,
                        event_start_time TIME NOT NULL,
                        event_end_time TIME NOT NULL,
                        event_url TEXT NOT NULL,
                        event_spot VARCHAR(50) NOT NULL,
                        event_fee VARCHAR(50) NOT NULL,
                        event_host VARCHAR(50) NOT NULL,
                        event_inquiry VARCHAR(50) NOT NULL,
                        event_description TEXT NULL,
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS recruitments (
                              recruitment_no BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                              event_no BIGINT NOT NULL,
                              local_no SMALLINT NOT NULL,
                              local_depart_time TIME NOT NULL,
                              local_return_time TIME NOT NULL,
                              local_amount TINYINT NOT NULL,
                              local_min_headcount TINYINT NOT NULL,
                              local_max_headcount TINYINT NOT NULL
);

CREATE TABLE IF NOT EXISTS event_registration (
                                    registration_no BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                                    recruitment_no BIGINT NULL,
                                    member_no BIGINT NULL,
                                    registration_date DATE NOT NULL,
                                    is_canceled TINYINT(1) NOT NULL DEFAULT 0,
                                    is_refunded TINYINT(1) NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS reviews (
                         review_no BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                         registration_no BIGINT NULL,
                         review_img VARCHAR(255) NULL,
                         review_content VARCHAR(255) NOT NULL,
                         review_hidden TINYINT(1) NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS local_requests (
                                request_no BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                                member_no BIGINT NOT NULL,
                                request_type VARCHAR(20) NOT NULL,
                                local_name VARCHAR(50) NOT NULL,
                                local_email VARCHAR(255) NOT NULL,
                                local_password VARCHAR(50) NOT NULL,
                                local_phonenum VARCHAR(50) NOT NULL,
                                local_account_num VARCHAR(50) NOT NULL,
                                local_account_name VARCHAR(50) NOT NULL,
                                request_state TINYINT NOT NULL,
                                rejection_reason VARCHAR(100) NULL
);

CREATE TABLE IF NOT EXISTS stopover (
                          stopover_no BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                          local_no SMALLINT NOT NULL,
                          stopover_name VARCHAR(255) NOT NULL,
                          stopover_order TINYINT NOT NULL UNIQUE,
                          stopover_deletion TINYINT(1) NOT NULL DEFAULT 0,
                          deleted_at DATETIME NULL
);

CREATE TABLE IF NOT EXISTS notices (
                         notice_no BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                         member_no BIGINT NOT NULL,
                         notice_title VARCHAR(255) NOT NULL,
                         notice_content TEXT NOT NULL,
                         created_at DATETIME NOT NULL,
                         is_fixed TINYINT(1) NOT NULL DEFAULT 0
);

-- ===============================
-- SAMPLE INSERTS
-- ===============================

INSERT INTO local_govs (local_district, local_name, local_contact, local_email)
VALUES
    ('서울특별시', NULL, '055-111-222', 'kangnam@asdf.com'),
    ('경상남도', '진주시', '055-111-333', 'jinju@asdf.com');

INSERT INTO events_categories (category_name)
VALUES ('기타');

INSERT INTO events (
    local_no, category_no, event_title, event_address, event_start_date, event_end_date,
    event_start_time, event_end_time, event_url, event_spot, event_fee, event_host, event_inquiry, event_description
)
VALUES
    (1, 126, '고메 잇 강남 서울야장', '서울특별시', '2025-08-17', '2025-08-31',
     '11:00', '23:30',
     'https://korean.visitkorea.or.kr/kfes/detail/fstvlDetail.do?fstvlCntntsId=013783e6-09c4-46a2-bf05-35219cef7b4f',
     '서울 코엑스 동측광장', '무료', '코엑스', '퀸즈스마일 관리자 -',
     '도심 한가운데에서 펼쳐지는 레트로 감성의 F&B 야장 페스티벌이다.'),

    (440, 126, '진주 남강 유등축제', '경상남도 진주시 남강로', '2025-10-01', '2025-10-14',
     '18:00', '23:00',
     'https://jinjulights.or.kr/',
     '남강둔치', '무료', '진주시청', '055-123-4567',
     '진주의 대표적인 가을 축제로, 남강 위에 수많은 유등이 밝혀지는 아름다운 행사이다.'),

    (1, 126, '서울 불꽃축제', '서울특별시 여의도 한강공원', '2025-10-05', '2025-10-05',
     '19:00', '21:00',
     'https://hanwhafireworks.co.kr/',
     '여의도 한강공원', '무료', '한화그룹', '02-789-1234',
     '한강 위에서 펼쳐지는 대규모 불꽃쇼로, 서울의 가을을 대표하는 축제이다.');

       
