package br.com.fiap.orbitguard.dto;

import jakarta.validation.constraints.*;

import java.time.OffsetDateTime;

public record ObservacaoOrbitalRequest(
        @NotNull Long areaId,
        @NotNull Long fonteDadoId,
        @NotNull @PastOrPresent OffsetDateTime capturadaEm,
        @NotNull @PositiveOrZero Double precipitacaoMm,
        @NotNull @DecimalMin("0.0") @DecimalMax("100.0") Double umidadeSoloPercentual,
        @NotNull Double temperaturaCelsius,
        @NotNull @DecimalMin("0.0") @DecimalMax("100.0") Double indiceRisco,
        @NotBlank @Size(max = 500) String analiseIa
) {
}
