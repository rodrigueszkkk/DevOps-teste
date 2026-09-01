package br.com.fiap.orbitguard.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_observacao_orbital")
public class ObservacaoOrbital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "area_id", nullable = false)
    private AreaMonitorada area;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fonte_dado_id", nullable = false)
    private FonteDado fonteDado;

    @Column(nullable = false)
    private OffsetDateTime capturadaEm;

    @Embedded
    private MetricasClimaticas metricas;

    @Column(nullable = false, length = 500)
    private String analiseIa;
}
