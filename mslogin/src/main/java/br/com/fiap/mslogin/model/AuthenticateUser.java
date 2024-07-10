package br.com.fiap.mslogin.model;

public record AuthenticateUser(
        Long id,

        String email,

        String token
) {
}
