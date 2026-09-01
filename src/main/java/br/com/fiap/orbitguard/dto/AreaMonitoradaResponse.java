package br.com.fiap.orbitguard.dto;

import br.com.fiap.orbitguard.domain.enums.NivelVulnerabilidade;

import java.time.OffsetDateTime;

public record AreaMonitoradaResponse(
        Long id,
        String nome,
        String cidade,
        String uf,
        String descricao,
        Double latitude,
        Double longitude,
        NivelVulnerabilidade nivelVulnerabilidade,
        OffsetDateTime criadaEm
) {
}
