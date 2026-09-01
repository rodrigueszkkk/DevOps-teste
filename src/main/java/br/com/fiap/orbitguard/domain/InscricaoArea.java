package br.com.fiap.orbitguard.domain;

import br.com.fiap.orbitguard.domain.enums.CanalNotificacao;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_inscricao_area")
public class InscricaoArea {

    @EmbeddedId
    private InscricaoAreaId id;

    @ManyToOne(optional = false)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @MapsId("areaId")
    @JoinColumn(name = "area_id")
    private AreaMonitorada area;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CanalNotificacao canal;

    @Column(nullable = false)
    private Boolean ativa;

    @Column(nullable = false)
    private OffsetDateTime criadaEm;

    @PrePersist
    void prePersist() {
        criadaEm = OffsetDateTime.now();
        if (ativa == null) {
            ativa = true;
        }
    }
}
