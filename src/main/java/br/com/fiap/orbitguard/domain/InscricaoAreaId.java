package br.com.fiap.orbitguard.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class InscricaoAreaId implements Serializable {
    private Long usuarioId;
    private Long areaId;
}
