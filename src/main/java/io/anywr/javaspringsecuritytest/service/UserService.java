package io.anywr.javaspringsecuritytest.service;

import io.anywr.javaspringsecuritytest.dto.UserInfoDto;
import io.anywr.javaspringsecuritytest.entity.User;
import io.anywr.javaspringsecuritytest.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface UserService {

public void enregistrerUtilisateur(UserInfoDto userInfoDto);

    Optional<User> isUserExits(String username);

    UserInfoDto getUserDetails();
}
