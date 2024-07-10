package br.com.fiap.mslogin.controller;

import br.com.fiap.mslogin.model.AuthenticateUser;
import br.com.fiap.mslogin.model.AuthenticationRequest;
import br.com.fiap.mslogin.model.SingUpRequest;
import br.com.fiap.mslogin.model.UserDTO;
import br.com.fiap.mslogin.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createNewUser(@RequestBody SingUpRequest singUpRequest) {
        return ResponseEntity.status(201).body(authService.createUser(singUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticateUser> login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validate(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader.contains("Bearer ")) {
            authorizationHeader = authorizationHeader.replace("Bearer ","");
        }
        authService.validateToken(authorizationHeader);
        return ResponseEntity.ok("Validated");
    }
}
