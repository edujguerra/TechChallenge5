package br.com.fiap.mslogin.entity;

import br.com.fiap.mslogin.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "ds_email")
    private String email;

    @Column(name = "ds_senha")
    private String password;

    @Column(name = "nm_usuario")
    private String name;

    @Column(name = "tp_role")
    private UserRole role;

}
