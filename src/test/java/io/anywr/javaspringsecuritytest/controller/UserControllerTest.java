package io.anywr.javaspringsecuritytest.controller;

import io.anywr.javaspringsecuritytest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() throws Exception {
        passwordEncoder = getPassword();
    }


    @Test
    void checkPasswordTest(){
        String password = "a5dfg45fd";
        String encodedPassword = passwordEncoder.encode(password);
        System.out.println(encodedPassword);

        assertTrue(passwordEncoder.matches(password, encodedPassword));

    }

    @Test
    void checkPasswordDoenstMatchTest(){
        String password = "a5dfg45fd";
        String encodedPassword = passwordEncoder.encode("f5gdfg");

        assertFalse(passwordEncoder.matches(password, encodedPassword));

    }

    public PasswordEncoder getPassword() {
        return new BCryptPasswordEncoder();
    }

}