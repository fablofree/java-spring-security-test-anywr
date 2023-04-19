package io.anywr.javaspringsecuritytest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDto {
    @NotEmpty(message = "Le nom d'utilisateur ne doit pas être vide")
    private String userName;
    @Email(message = "Adresse email non valide")
    private String email;
    @NotEmpty(message = "Le mot de passe ne doit pas être vide")
    private String password;
    @NotEmpty(message = "Le role ne doit pas être vide")
    private String roles;
}
