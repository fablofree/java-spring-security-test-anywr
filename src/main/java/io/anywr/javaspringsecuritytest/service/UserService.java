package io.anywr.javaspringsecuritytest.service;

import io.anywr.javaspringsecuritytest.dto.UserInfoDto;
import io.anywr.javaspringsecuritytest.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {

public void enregistrerUtilisateur(UserInfoDto userInfoDto);

}
