package io.anywr.javaspringsecuritytest.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDto {
    private String userName;
    @Email(message = "Adresse email non valide")
    private String email;
    private String password;
    private String roles;
}
