package br.com.fiap.orbitguard.service;

import br.com.fiap.orbitguard.domain.AreaMonitorada;
import br.com.fiap.orbitguard.domain.Coordenadas;
import br.com.fiap.orbitguard.dto.AreaMonitoradaRequest;
import br.com.fiap.orbitguard.dto.AreaMonitoradaResponse;
import br.com.fiap.orbitguard.exception.ConflictException;
import br.com.fiap.orbitguard.exception.ResourceNotFoundException;
import br.com.fiap.orbitguard.repository.AlertaRiscoRepository;
import br.com.fiap.orbitguard.repository.AreaMonitoradaRepository;
import br.com.fiap.orbitguard.repository.InscricaoAreaRepository;
import br.com.fiap.orbitguard.repository.ObservacaoOrbitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaMonitoradaService {

    private final AreaMonitoradaRepository repository;
    private final ObservacaoOrbitalRepository observacaoRepository;
    private final AlertaRiscoRepository alertaRepository;
    private final InscricaoAreaRepository inscricaoRepository;

    @Transactional(readOnly = true)
    public List<AreaMonitoradaResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AreaMonitorada buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Area monitorada", id));
    }

    @Transactional(readOnly = true)
    public AreaMonitoradaResponse buscar(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public AreaMonitoradaResponse criar(AreaMonitoradaRequest request) {
        AreaMonitorada area = new AreaMonitorada();
        aplicarDados(area, request);
        return toResponse(repository.save(area));
    }

    @Transactional
    public AreaMonitoradaResponse atualizar(Long id, AreaMonitoradaRequest request) {
        AreaMonitorada area = buscarEntidade(id);
        aplicarDados(area, request);
        return toResponse(repository.save(area));
    }

    @Transactional
    public void remover(Long id) {
        AreaMonitorada area = buscarEntidade(id);
        validarRemocao(id);
        repository.delete(area);
    }

    private void validarRemocao(Long id) {
        long observacoes = observacaoRepository.countByAreaId(id);
        long alertas = alertaRepository.countByAreaId(id);
        long inscricoes = inscricaoRepository.countByAreaId(id);

        if (observacoes > 0 || alertas > 0 || inscricoes > 0) {
            throw new ConflictException("Area monitorada nao pode ser removida porque possui observacoes, alertas ou inscricoes vinculadas. Remova os registros dependentes antes ou teste o DELETE com uma area criada sem vinculos.");
        }
    }

    private void aplicarDados(AreaMonitorada area, AreaMonitoradaRequest request) {
        area.setNome(request.nome());
        area.setCidade(request.cidade());
        area.setUf(request.uf().toUpperCase());
        area.setDescricao(request.descricao());
        area.setCoordenadas(new Coordenadas(request.latitude(), request.longitude()));
        area.setNivelVulnerabilidade(request.nivelVulnerabilidade());
    }

    private AreaMonitoradaResponse toResponse(AreaMonitorada area) {
        return new AreaMonitoradaResponse(
                area.getId(),
                area.getNome(),
                area.getCidade(),
                area.getUf(),
                area.getDescricao(),
                area.getCoordenadas().getLatitude(),
                area.getCoordenadas().getLongitude(),
                area.getNivelVulnerabilidade(),
                area.getCriadaEm());
    }
}
