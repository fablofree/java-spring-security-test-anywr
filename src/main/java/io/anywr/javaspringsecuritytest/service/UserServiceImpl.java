package io.anywr.javaspringsecuritytest.service;

import io.anywr.javaspringsecuritytest.dto.AuthRequest;
import io.anywr.javaspringsecuritytest.dto.UserInfoDto;
import io.anywr.javaspringsecuritytest.entity.Token;
import io.anywr.javaspringsecuritytest.entity.TokenType;
import io.anywr.javaspringsecuritytest.entity.User;
import io.anywr.javaspringsecuritytest.exception.EntityNotFoundException;
import io.anywr.javaspringsecuritytest.helper.UserHelper;
import io.anywr.javaspringsecuritytest.repository.TokenRepository;
import io.anywr.javaspringsecuritytest.repository.UserRepository;
import io.anywr.javaspringsecuritytest.service.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static io.anywr.javaspringsecuritytest.utils.MessageUtils.*;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           UserMapper userMapper, AuthenticationManager authenticationManager,
                           TokenRepository tokenRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void enregistrerUtilisateur(UserInfoDto userInfoDto) {
        if(!isUserExits(userInfoDto.getUserName()).isEmpty()){
            throw new EntityNotFoundException(ADRESSE_EMAIL_DEJA_PRIS);
        }
        System.out.println(userInfoDto.getPassword());
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
                .orElseThrow(() -> new EntityNotFoundException(UTILISATEUR_INEXISTANT));
    }

    @Override
    public String authenticateAndGetToken(AuthRequest authRequest) {
        Optional<User> user = isUserExits(authRequest.getUsername());
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
                    throw new EntityNotFoundException(NOM_UTILISATEUR_MOT_PASSE_INVALIDE);
                }
            }catch(RuntimeException e){
                System.out.println(e);
                throw new EntityNotFoundException(NOM_UTILISATEUR_MOT_PASSE_INCORRECT);
            }

        }

        throw new EntityNotFoundException(NOM_UTILISATEUR_INEXISTANT+ authRequest.getUsername());
    }


}
