package com.toyproject.club21century.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class SignupRequestTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    //이것과 BeforEach는 무슨차이인가? 언제 각각 쓰이는지?
    //BeforeAll은 해당 테스트 클래스가 실행될 떄 가장 먼저, 딱 한 번만 실행되는 초기화 메서드를 정의하는 JUnit5 어노테이션 -> 반드시 static 메서드여야 한다. / 모든 테스트가 공유하는 무거운 자원 초기화
    //Validator처럼 상태가 변하지 않는(Stateless) 스레드 세이프한 객체를 초기화할 때만 사용하는 것이 좋은 설계

    //BeforeEach는 각 테스트 메서드가 실행될 때 마다 매번 실행된다. -> 일반 메서드로 작성 / 각 테스트가 서로 영향을 주지 않도록 독립적인 상태/객체 초기화
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    //이것과 AfterEach는 무슨차이인가? 언제 각각 쓰이는지?
    static void tearDown() {
        factory.close();
    }

    @Test
    @DisplayName("정상 입력은 위반이 없다.")
    void validRequest() {
        SignupRequest request = new SignupRequest("usinKim", "password1234", "usin@example.com");
        assertThat(validator.validate(request)).isEmpty();
        //validate()는 위반 목록을 반환해준다. 그래서 isEmpty()를 사용해서 비어있으면 위반한 항목이 없다는 것이다.
        //또한 isValid()를 사용하면 true/false만 반환하는데 이것을 사용하지 않는 이유는 어떤 것 때문에 실패했는지 알아야 하기 때문이다.
    }

    @Test
    @DisplayName("아이디에 특수문자가 들어가면 거부된다.")
    void loginIdPattern() {
        SignupRequest request = new SignupRequest("usin-Kim", "password1234", "usin@example.com");
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);
        assertThat(violations)
                .extracting(v -> v.getPropertyPath().toString())
                .contains("loginId");

        /*
            ConstraintViolation 타입이란?
            자바의 Bean Validation API에서 제공하는 인터페이스 타입이다.
            객체에 지정한 제약조건(유효성 검증)을 위반했을 때, 어떤 필드가 어떤 이유로 실패했는지에 대한 상세한 에러 정보를 담는 그릇 역할을 한다.

            이 타입의 객체를 활용하면 에러의 원인을 상세하게 파악할 수 있어, 클라이언트에게 돌려줄 커스텀 에러 메시지를 만들 떄 자주사용한다.
                getPropertyPath() : 검증에 실패한 필드(프로퍼티)의 경로 및 이름을 가져온다. - 예 : loginId
                getInvalidValue() : 사용자가 입력했던 잘못된 원본 값을 가져온다.
            ================================================================================================================
            extracting() : AssertJ의 기능, 컬렉션의 각 요소에서 값을 뽑아 새 컬렉션을 만든다. map()과 같은 일을 한다.

         */
    }

    @Test
    @DisplayName("아이디와 비밀번호가 동시에 짧으면 위반이 2개다")
    void multipleViolations() {
        SignupRequest request = new SignupRequest("kim", "pas", "kim@example.com");
        assertThat(validator.validate(request))
                .extracting(v -> v.getInvalidValue().toString())
                .containsExactlyInAnyOrder("kim", "pas");
    }


}
