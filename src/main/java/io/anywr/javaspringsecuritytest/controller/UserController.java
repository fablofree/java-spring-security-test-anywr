package io.anywr.javaspringsecuritytest.controller;

import io.anywr.javaspringsecuritytest.dto.UserInfoDto;
import io.anywr.javaspringsecuritytest.repository.TokenRepository;
import io.anywr.javaspringsecuritytest.service.JwtService;
import io.anywr.javaspringsecuritytest.service.UserService;
import io.anywr.javaspringsecuritytest.utils.UrlUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlUtils.BASE_API_URL+"users")
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;

    public UserController(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenRepository tokenRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("")
    public void ajoutUtilisateur(@RequestBody @Valid UserInfoDto userInfoDto){
        userService.enregistrerUtilisateur(userInfoDto);
    }
}
