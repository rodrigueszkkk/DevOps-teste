package br.com.fiap.orbitguard.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MetricasClimaticas {

    @Column(nullable = false)
    private Double precipitacaoMm;

    @Column(nullable = false)
    private Double umidadeSoloPercentual;

    @Column(nullable = false)
    private Double temperaturaCelsius;

    @Column(nullable = false)
    private Double indiceRisco;
}
