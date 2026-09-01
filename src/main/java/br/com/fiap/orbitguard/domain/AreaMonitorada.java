package br.com.fiap.orbitguard.domain;

import br.com.fiap.orbitguard.domain.enums.NivelVulnerabilidade;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_area_monitorada")
public class AreaMonitorada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 120)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String uf;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Embedded
    private Coordenadas coordenadas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NivelVulnerabilidade nivelVulnerabilidade;

    @Column(nullable = false)
    private OffsetDateTime criadaEm;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ObservacaoOrbital> observacoes = new ArrayList<>();

    @PrePersist
    void prePersist() {
        criadaEm = OffsetDateTime.now();
    }
}
