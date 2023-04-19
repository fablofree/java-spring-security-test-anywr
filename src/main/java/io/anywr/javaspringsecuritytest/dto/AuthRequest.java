package io.anywr.javaspringsecuritytest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotEmpty(message = "Le nom d'utilisateur ne doit pas être vide")
    private String username;
    @NotEmpty(message = "Le mot de passe ne doit pas être vide")
    private String password;
}
