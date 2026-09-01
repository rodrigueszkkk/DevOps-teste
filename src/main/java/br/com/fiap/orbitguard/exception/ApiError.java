package br.com.fiap.orbitguard.exception;

import java.time.OffsetDateTime;
import java.util.List;

public record ApiError(
        OffsetDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        String path,
        List<String> detalhes
) {
}
