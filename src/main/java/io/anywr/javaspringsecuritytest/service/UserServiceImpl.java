package io.anywr.javaspringsecuritytest.service;

import io.anywr.javaspringsecuritytest.dto.UserInfoDto;
import io.anywr.javaspringsecuritytest.entity.User;
import io.anywr.javaspringsecuritytest.exception.EntityNotFoundException;
import io.anywr.javaspringsecuritytest.helper.UserHelper;
import io.anywr.javaspringsecuritytest.repository.UserRepository;
import io.anywr.javaspringsecuritytest.service.mapper.UserMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public void enregistrerUtilisateur(UserInfoDto userInfoDto) {
        if(!isUserExits(userInfoDto.getUserName()).isEmpty()){
            throw new EntityNotFoundException("Cette adresse email a déjà été pris");
        }

        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        userRepository.save(userMapper.toEntity(userInfoDto));
    }

    public Optional<User> isUserExits(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public UserInfoDto getUserDetails() {
        UserInfoUserDetails user = UserHelper.getUserDetails();
        return userRepository.findByUserName(user.getUsername())
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur inexistant"));
    }


}
