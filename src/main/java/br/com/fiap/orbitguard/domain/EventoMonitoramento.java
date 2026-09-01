package br.com.fiap.orbitguard.domain;

import br.com.fiap.orbitguard.domain.enums.Severidade;
import br.com.fiap.orbitguard.domain.enums.StatusEvento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_evento_monitoramento")
public abstract class EventoMonitoramento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 140)
    private String titulo;

    @Column(nullable = false, length = 700)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Severidade severidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusEvento status;

    @Column(nullable = false)
    private OffsetDateTime criadoEm;

    @ManyToOne(optional = false)
    @JoinColumn(name = "area_id", nullable = false)
    private AreaMonitorada area;

    @PrePersist
    void prePersist() {
        criadoEm = OffsetDateTime.now();
        if (status == null) {
            status = StatusEvento.ABERTO;
        }
    }
}
