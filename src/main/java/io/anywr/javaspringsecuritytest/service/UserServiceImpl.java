package io.anywr.javaspringsecuritytest.service;

import io.anywr.javaspringsecuritytest.dto.UserInfoDto;
import io.anywr.javaspringsecuritytest.entity.User;
import io.anywr.javaspringsecuritytest.exception.EntityNotFoundException;
import io.anywr.javaspringsecuritytest.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void enregistrerUtilisateur(UserInfoDto userInfoDto) {
        if(!isUserExits(userInfoDto.getUserName()).isEmpty()){
            throw new EntityNotFoundException("Cette adresse email a déjà été pris");
        }

        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        userRepository.save(toEntity(userInfoDto));
    }

    public Optional<User> isUserExits(String username) {
        return userRepository.findByUserName(username);
    }

    private User toEntity(UserInfoDto user) {
        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .userName(user.getUserName())
                .build();
    }
}
