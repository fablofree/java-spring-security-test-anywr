package io.anywr.javaspringsecuritytest.controller;

import io.anywr.javaspringsecuritytest.dto.AuthRequest;
import io.anywr.javaspringsecuritytest.dto.UserInfoDto;
import io.anywr.javaspringsecuritytest.entity.Token;
import io.anywr.javaspringsecuritytest.entity.TokenType;
import io.anywr.javaspringsecuritytest.entity.User;
import io.anywr.javaspringsecuritytest.exception.EntityNotFoundException;
import io.anywr.javaspringsecuritytest.helper.UserHelper;
import io.anywr.javaspringsecuritytest.repository.TokenRepository;
import io.anywr.javaspringsecuritytest.service.JwtService;
import io.anywr.javaspringsecuritytest.service.UserInfoUserDetails;
import io.anywr.javaspringsecuritytest.service.UserService;
import io.anywr.javaspringsecuritytest.utils.UrlUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(UrlUtils.BASE_API_URL+"users/")
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

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public void ajoutUtilisateur(@RequestBody @Valid UserInfoDto userInfoDto){
        userService.enregistrerUtilisateur(userInfoDto);
    }

    @PostMapping("login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Optional<User> user = userService.isUserExits(authRequest.getUsername());
        if(user.isPresent()) {
            try {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
                System.out.println(authentication.isAuthenticated());
                if (authentication.isAuthenticated()) {

                    tokenRepository.findAllTokenByUser(user.get().getId()).forEach(token ->
                    {
                        token.setRevoked(true);
                        token.setExpired(true);
                        tokenRepository.save(token);
                    });

                    String token = jwtService.generateToken(authRequest.getUsername());

                    Token tokenCre = Token.builder()
                            .token(token)
                            .tokenType(TokenType.BEARER)
                            .expired(false)
                            .revoked(false)
                            .user(user.get())
                            .build();
                    tokenRepository.save(tokenCre);

                    return token;
                } else {
                    throw new EntityNotFoundException("Nom d'utilisateur "+ authRequest.getUsername() +" ou mot de passe invalide !");
                }
            }catch(RuntimeException e){
                System.out.println(e);
                throw new EntityNotFoundException("Nom d'utilisateur ou mot de passe incorrect !");
            }

        }

        throw new EntityNotFoundException("L'utiliser "+ authRequest.getUsername() +" n'existe pas !");
    }

    @GetMapping("user-info")
    public ResponseEntity<UserInfoDto> getUserDetails(){
        return ResponseEntity.ok(userService.getUserDetails());
    }
}
