package br.com.fiap.orbitguard.dto;

import br.com.fiap.orbitguard.domain.enums.Severidade;
import br.com.fiap.orbitguard.domain.enums.StatusEvento;
import jakarta.validation.constraints.*;

import java.time.OffsetDateTime;

public record AlertaRiscoRequest(
        @NotBlank @Size(max = 140) String titulo,
        @NotBlank @Size(max = 700) String descricao,
        @NotNull Severidade severidade,
        StatusEvento status,
        @NotNull Long areaId,
        Long observacaoBaseId,
        @NotNull @Min(0) @Max(100) Integer probabilidadePercentual,
        @NotBlank @Size(max = 700) String recomendacao,
        @NotNull @Future OffsetDateTime validoAte
) {
}
