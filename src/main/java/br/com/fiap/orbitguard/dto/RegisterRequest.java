package br.com.fiap.orbitguard.dto;

import br.com.fiap.orbitguard.domain.enums.PerfilUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank @Size(max = 120) String nome,
        @NotBlank @Email @Size(max = 160) String email,
        @NotBlank @Size(min = 6, max = 80) String senha,
        @NotNull PerfilUsuario perfil
) {
}
