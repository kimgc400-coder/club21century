package com.toyproject.club21century.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberUpdateRequestTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @Test
    @DisplayName("듈 다 null 이어도 검증은 통과한다. - null은 '안 바꿈'을 뜻한다.")
    void nullPassesValidation() {
        MemberUpdateRequest request = new MemberUpdateRequest(null, null);

        assertThat(validator.validate(request)).isEmpty();
        assertThat(request.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("값이 있으면 형식을 검사한다.")
    void validatesWhenPresent() {
        MemberUpdateRequest request = new MemberUpdateRequest("이건이메일아님", "가");

        assertThat(validator.validate(request)).extracting(v -> v.getPropertyPath().toString())
                .containsExactlyInAnyOrder("email", "nickname");
    }
}
