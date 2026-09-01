package br.com.fiap.orbitguard.domain;

import br.com.fiap.orbitguard.domain.enums.TipoFonteDado;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_fonte_dado")
public class FonteDado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String provedor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoFonteDado tipo;

    @Column(nullable = false)
    private Boolean ativa;
}
