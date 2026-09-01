package br.com.fiap.orbitguard.service;

import br.com.fiap.orbitguard.domain.AlertaRisco;
import br.com.fiap.orbitguard.domain.ObservacaoOrbital;
import br.com.fiap.orbitguard.dto.AlertaRiscoRequest;
import br.com.fiap.orbitguard.dto.AlertaRiscoResponse;
import br.com.fiap.orbitguard.exception.ResourceNotFoundException;
import br.com.fiap.orbitguard.repository.AlertaRiscoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertaRiscoService {

    private final AlertaRiscoRepository repository;
    private final AreaMonitoradaService areaService;
    private final ObservacaoOrbitalService observacaoService;

    @Transactional(readOnly = true)
    public List<AlertaRiscoResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AlertaRisco buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Alerta de risco", id));
    }

    @Transactional(readOnly = true)
    public AlertaRiscoResponse buscar(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public AlertaRiscoResponse criar(AlertaRiscoRequest request) {
        AlertaRisco alerta = new AlertaRisco();
        aplicarDados(alerta, request);
        return toResponse(repository.save(alerta));
    }

    @Transactional
    public AlertaRiscoResponse atualizar(Long id, AlertaRiscoRequest request) {
        AlertaRisco alerta = buscarEntidade(id);
        aplicarDados(alerta, request);
        return toResponse(repository.save(alerta));
    }

    @Transactional
    public void remover(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private void aplicarDados(AlertaRisco alerta, AlertaRiscoRequest request) {
        alerta.setTitulo(request.titulo());
        alerta.setDescricao(request.descricao());
        alerta.setSeveridade(request.severidade());
        alerta.setStatus(request.status());
        alerta.setArea(areaService.buscarEntidade(request.areaId()));
        alerta.setProbabilidadePercentual(request.probabilidadePercentual());
        alerta.setRecomendacao(request.recomendacao());
        alerta.setValidoAte(request.validoAte());
        ObservacaoOrbital observacao = request.observacaoBaseId() == null ? null : observacaoService.buscarEntidade(request.observacaoBaseId());
        alerta.setObservacaoBase(observacao);
    }

    private AlertaRiscoResponse toResponse(AlertaRisco alerta) {
        Long observacaoId = alerta.getObservacaoBase() == null ? null : alerta.getObservacaoBase().getId();
        return new AlertaRiscoResponse(
                alerta.getId(),
                alerta.getTitulo(),
                alerta.getDescricao(),
                alerta.getSeveridade(),
                alerta.getStatus(),
                alerta.getArea().getId(),
                alerta.getArea().getNome(),
                observacaoId,
                alerta.getProbabilidadePercentual(),
                alerta.getRecomendacao(),
                alerta.getCriadoEm(),
                alerta.getValidoAte());
    }
}
