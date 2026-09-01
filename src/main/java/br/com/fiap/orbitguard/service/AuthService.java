package br.com.fiap.orbitguard.service;

import br.com.fiap.orbitguard.domain.Usuario;
import br.com.fiap.orbitguard.dto.AuthRequest;
import br.com.fiap.orbitguard.dto.AuthResponse;
import br.com.fiap.orbitguard.dto.RegisterRequest;
import br.com.fiap.orbitguard.exception.BusinessException;
import br.com.fiap.orbitguard.repository.UsuarioRepository;
import br.com.fiap.orbitguard.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse registrar(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BusinessException("Ja existe usuario cadastrado com este e-mail.");
        }

        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .perfil(request.perfil())
                .build();
        usuarioRepository.save(usuario);
        return tokenResponse(usuario);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.senha()));
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException("Credenciais invalidas."));
        return tokenResponse(usuario);
    }

    private AuthResponse tokenResponse(Usuario usuario) {
        String token = jwtService.generateToken(usuario);
        return new AuthResponse(token, "Bearer", usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getPerfil().name());
    }
}
