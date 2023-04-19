package io.anywr.javaspringsecuritytest.service.mapper;

import io.anywr.javaspringsecuritytest.dto.UserInfoDto;
import io.anywr.javaspringsecuritytest.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserInfoDto user) {
        return User.builder()
                .email(user.getEmail())
                .roles(user.getRoles())
                .userName(user.getUserName())
                .build();
    }

    public UserInfoDto toDto(User user) {
        return UserInfoDto.builder()
                .email(user.getEmail())
                .roles(user.getRoles())
                .userName(user.getUserName())
                .build();
    }
}
