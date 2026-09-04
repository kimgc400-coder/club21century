CREATE TABLE member (
                        member_id   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '회원 ID',
                        login_id    VARCHAR(50)  NOT NULL COMMENT '로그인 아이디',
                        password    VARCHAR(255) NOT NULL COMMENT 'BCrypt 해시 (60자)',
                        email       VARCHAR(255) NOT NULL COMMENT '비밀번호 찾기용 이메일',
                        status      VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE' COMMENT '상태 판단 기준 - ACTIVE, SUSPENDED, WITHDRAWN',
                        created_at  DATETIME     NOT NULL COMMENT '생성일',
                        modified_at DATETIME     NOT NULL COMMENT '수정일',
                        deleted_at  DATETIME     NULL     COMMENT '탈퇴 시각 - 기록용',

                        PRIMARY KEY (member_id),
                        UNIQUE KEY uk_member_login_id (login_id),
                        UNIQUE KEY uk_member_email (email),
                        CONSTRAINT ck_member_withdrawn
                            CHECK ((status = 'WITHDRAWN') = (deleted_at IS NOT NULL))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT '회원';