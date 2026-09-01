package br.com.fiap.orbitguard.service;

import br.com.fiap.orbitguard.domain.FonteDado;
import br.com.fiap.orbitguard.domain.MetricasClimaticas;
import br.com.fiap.orbitguard.domain.ObservacaoOrbital;
import br.com.fiap.orbitguard.dto.ObservacaoOrbitalRequest;
import br.com.fiap.orbitguard.dto.ObservacaoOrbitalResponse;
import br.com.fiap.orbitguard.exception.ResourceNotFoundException;
import br.com.fiap.orbitguard.repository.FonteDadoRepository;
import br.com.fiap.orbitguard.repository.ObservacaoOrbitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ObservacaoOrbitalService {

    private final ObservacaoOrbitalRepository repository;
    private final AreaMonitoradaService areaService;
    private final FonteDadoRepository fonteDadoRepository;

    @Transactional(readOnly = true)
    public List<ObservacaoOrbitalResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ObservacaoOrbital buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Observacao orbital", id));
    }

    @Transactional(readOnly = true)
    public ObservacaoOrbitalResponse buscar(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public ObservacaoOrbitalResponse criar(ObservacaoOrbitalRequest request) {
        ObservacaoOrbital observacao = new ObservacaoOrbital();
        aplicarDados(observacao, request);
        return toResponse(repository.save(observacao));
    }

    @Transactional
    public ObservacaoOrbitalResponse atualizar(Long id, ObservacaoOrbitalRequest request) {
        ObservacaoOrbital observacao = buscarEntidade(id);
        aplicarDados(observacao, request);
        return toResponse(repository.save(observacao));
    }

    @Transactional
    public void remover(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private void aplicarDados(ObservacaoOrbital observacao, ObservacaoOrbitalRequest request) {
        FonteDado fonte = fonteDadoRepository.findById(request.fonteDadoId())
                .orElseThrow(() -> new ResourceNotFoundException("Fonte de dados", request.fonteDadoId()));
        observacao.setArea(areaService.buscarEntidade(request.areaId()));
        observacao.setFonteDado(fonte);
        observacao.setCapturadaEm(request.capturadaEm());
        observacao.setMetricas(new MetricasClimaticas(
                request.precipitacaoMm(),
                request.umidadeSoloPercentual(),
                request.temperaturaCelsius(),
                request.indiceRisco()));
        observacao.setAnaliseIa(request.analiseIa());
    }

    private ObservacaoOrbitalResponse toResponse(ObservacaoOrbital observacao) {
        return new ObservacaoOrbitalResponse(
                observacao.getId(),
                observacao.getArea().getId(),
                observacao.getArea().getNome(),
                observacao.getFonteDado().getId(),
                observacao.getFonteDado().getNome(),
                observacao.getCapturadaEm(),
                observacao.getMetricas().getPrecipitacaoMm(),
                observacao.getMetricas().getUmidadeSoloPercentual(),
                observacao.getMetricas().getTemperaturaCelsius(),
                observacao.getMetricas().getIndiceRisco(),
                observacao.getAnaliseIa());
    }
}
