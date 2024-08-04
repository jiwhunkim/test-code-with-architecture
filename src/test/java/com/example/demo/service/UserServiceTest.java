package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    @DisplayName("getByEmail 은 ACTIVE 상태인 유저를 찾아 수 있다.")
    void getByEmailActive() {
        var email = "kok202@naver.com";

        var result = userService.getByEmail(email);

        assertThat(result.getNickname()).isEqualTo("kok202");
    }

    @Test
    @DisplayName("getByEmail 은 PENDING 상태인 유저를 찾아 수 있다.")
    void getByEmailPending() {
        var email = "kok303@naver.com";


        assertThatThrownBy(() -> {
            userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }
}
