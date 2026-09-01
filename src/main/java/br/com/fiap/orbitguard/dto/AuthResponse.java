package br.com.fiap.orbitguard.dto;

public record AuthResponse(
        String token,
        String tipo,
        Long usuarioId,
        String nome,
        String email,
        String perfil
) {
}
