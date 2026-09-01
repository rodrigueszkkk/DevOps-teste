package br.com.fiap.orbitguard.dto;

import java.time.OffsetDateTime;

public record ObservacaoOrbitalResponse(
        Long id,
        Long areaId,
        String areaNome,
        Long fonteDadoId,
        String fonteDadoNome,
        OffsetDateTime capturadaEm,
        Double precipitacaoMm,
        Double umidadeSoloPercentual,
        Double temperaturaCelsius,
        Double indiceRisco,
        String analiseIa
) {
}
