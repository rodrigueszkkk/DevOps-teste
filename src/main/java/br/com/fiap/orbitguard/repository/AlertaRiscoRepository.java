package br.com.fiap.orbitguard.repository;

import br.com.fiap.orbitguard.domain.AlertaRisco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRiscoRepository extends JpaRepository<AlertaRisco, Long> {
    long countByAreaId(Long areaId);
}
