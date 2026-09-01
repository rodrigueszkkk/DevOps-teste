package br.com.fiap.orbitguard.repository;

import br.com.fiap.orbitguard.domain.InscricaoArea;
import br.com.fiap.orbitguard.domain.InscricaoAreaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscricaoAreaRepository extends JpaRepository<InscricaoArea, InscricaoAreaId> {
    long countByAreaId(Long areaId);
}
