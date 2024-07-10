package br.com.fiap.mslogin.controller;

import br.com.fiap.mslogin.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarUm(@PathVariable Long id) {

        return service.buscarUm(id);
    }
}
