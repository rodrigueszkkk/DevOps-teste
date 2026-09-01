package br.com.fiap.orbitguard.controller;

import br.com.fiap.orbitguard.dto.AuthRequest;
import br.com.fiap.orbitguard.dto.AuthResponse;
import br.com.fiap.orbitguard.dto.RegisterRequest;
import br.com.fiap.orbitguard.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registrar(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid AuthRequest request) {
        return service.login(request);
    }
}
