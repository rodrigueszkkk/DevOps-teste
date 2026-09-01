package br.com.fiap.orbitguard.dto;

import br.com.fiap.orbitguard.domain.enums.Severidade;
import br.com.fiap.orbitguard.domain.enums.StatusEvento;

import java.time.OffsetDateTime;

public record AlertaRiscoResponse(
        Long id,
        String titulo,
        String descricao,
        Severidade severidade,
        StatusEvento status,
        Long areaId,
        String areaNome,
        Long observacaoBaseId,
        Integer probabilidadePercentual,
        String recomendacao,
        OffsetDateTime criadoEm,
        OffsetDateTime validoAte
) {
}
