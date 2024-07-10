package br.com.fiap.mslogin.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthenticationRequest(

        @NotNull(message = "Email não pode ser vazio.")
        @Email
        String email,

        @NotNull(message = "Password não pode ser vazio.")
        @Size(min = 6, message = "Mínimo de 6 characters.")
        String password
) {
}
