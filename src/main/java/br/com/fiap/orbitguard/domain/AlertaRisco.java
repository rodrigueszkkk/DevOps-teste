package br.com.fiap.orbitguard.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_alerta_risco")
public class AlertaRisco extends EventoMonitoramento {

    @Column(nullable = false)
    private Integer probabilidadePercentual;

    @Column(nullable = false, length = 700)
    private String recomendacao;

    @Column(nullable = false)
    private OffsetDateTime validoAte;

    @ManyToOne
    @JoinColumn(name = "observacao_orbital_id")
    private ObservacaoOrbital observacaoBase;
}
