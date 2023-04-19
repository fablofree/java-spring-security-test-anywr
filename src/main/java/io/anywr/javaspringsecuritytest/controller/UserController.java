package io.anywr.javaspringsecuritytest.controller;

import io.anywr.javaspringsecuritytest.dto.AuthRequest;
import io.anywr.javaspringsecuritytest.dto.UserInfoDto;
import io.anywr.javaspringsecuritytest.service.UserService;
import io.anywr.javaspringsecuritytest.utils.UrlUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static io.anywr.javaspringsecuritytest.utils.MessageUtils.*;
@RestController
@RequestMapping(UrlUtils.BASE_API_URL+"users/")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public void ajoutUtilisateur(@RequestBody @Valid UserInfoDto userInfoDto){
        userService.enregistrerUtilisateur(userInfoDto);
    }

    @PostMapping("login")
    public String authenticateAndGetToken(@RequestBody @Valid AuthRequest authRequest) {
       return userService.authenticateAndGetToken(authRequest);
    }

    @GetMapping("user-info")
    public ResponseEntity<UserInfoDto> getUserDetails(){
        return ResponseEntity.ok(userService.getUserDetails());
    }

    @GetMapping("logout")
    public ResponseEntity<String> logOut() {
        return ResponseEntity.ok(LOGOUT_MESSAGE);
    }
}
