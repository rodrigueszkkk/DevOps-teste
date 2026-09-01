package br.com.fiap.orbitguard.repository;

import br.com.fiap.orbitguard.domain.ObservacaoOrbital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservacaoOrbitalRepository extends JpaRepository<ObservacaoOrbital, Long> {
    long countByAreaId(Long areaId);
}
