package br.com.fiap.mslogin.model;

import br.com.fiap.mslogin.entity.User;
import br.com.fiap.mslogin.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public record SingUpRequest (

        @NotNull(message = "Nome é obrigatório")
        @Size(min = 5, message = "Nome deve ter no mínimo 5 caracteres")
        String name,

        @NotNull(message = "Senha é obrigatório")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String password,

        @NotNull(message = "Email é obrigatório")
        @Email(message = "Email é inválido")
        String email,
        UserRole role
) {
    public User toUser() {
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .role(Objects.nonNull(role) ? role : UserRole.CUSTOMER)
                .build();
    }
}
