-- login_id는 영문/숫자만 허용하므로, 화면에 표시할 이름을 따로 둔다.
-- NULL 허용 : 기존 회원과 아직 닉네임을 정하지 않는 회원을 표현한다.
-- MySQL은 UNIQUE 컬럼에 여러 개의 NULL을 허용하므로 둘이 공존한다.
-- UNIQUE: 같은 닉네임이 여럿이면 화면에서 사칭, 혼동이 생긴다.
-- 중복시 메시지 구분은 별도 확인 API로 해결한다.

ALTER TABLE member
    ADD COLUMN  nickname VARCHAR(50) NULL COMMENT '화면 표시용 이름' AFTER login_id,
    ADD UNIQUE KEY uk_member_nickname (nickname);