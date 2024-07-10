package br.com.fiap.mslogin.model;

import br.com.fiap.mslogin.entity.User;
import br.com.fiap.mslogin.enums.UserRole;

public record UserDTO(
        Long id,
        String name,
        String email,
        UserRole role
) {

    public static UserDTO fromUser(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
