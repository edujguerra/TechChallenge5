package br.com.fiap.mslogin.service;

import java.util.List;

import br.com.fiap.mslogin.entity.User;
import br.com.fiap.mslogin.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UserRepository usuarioRepository;

    public UsuarioService(UserRepository repository) {
        this.usuarioRepository = repository;
    }

    public List<User> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public ResponseEntity<Object> buscarUm(Long id ) {

        User cliente = usuarioRepository.findById(id).orElse(null);

        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado.");
        }
    }
}
