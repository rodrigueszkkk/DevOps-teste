package br.com.fiap.orbitguard.dto;

import br.com.fiap.orbitguard.domain.enums.NivelVulnerabilidade;
import jakarta.validation.constraints.*;

public record AreaMonitoradaRequest(
        @NotBlank @Size(max = 120) String nome,
        @NotBlank @Size(max = 120) String cidade,
        @NotBlank @Size(min = 2, max = 2) String uf,
        @NotBlank @Size(max = 500) String descricao,
        @NotNull @DecimalMin("-90.0") @DecimalMax("90.0") Double latitude,
        @NotNull @DecimalMin("-180.0") @DecimalMax("180.0") Double longitude,
        @NotNull NivelVulnerabilidade nivelVulnerabilidade
) {
}
