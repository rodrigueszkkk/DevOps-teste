package br.com.fiap.orbitguard.controller;

import br.com.fiap.orbitguard.dto.AreaMonitoradaRequest;
import br.com.fiap.orbitguard.dto.AreaMonitoradaResponse;
import br.com.fiap.orbitguard.service.AreaMonitoradaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/areas")
@RequiredArgsConstructor
public class AreaMonitoradaController {

    private final AreaMonitoradaService service;

    @GetMapping
    public CollectionModel<EntityModel<AreaMonitoradaResponse>> listar() {
        List<EntityModel<AreaMonitoradaResponse>> areas = service.listar().stream().map(this::toModel).toList();
        return CollectionModel.of(areas, linkTo(methodOn(AreaMonitoradaController.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<AreaMonitoradaResponse> buscar(@PathVariable Long id) {
        return toModel(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<EntityModel<AreaMonitoradaResponse>> criar(@RequestBody @Valid AreaMonitoradaRequest request) {
        AreaMonitoradaResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(response));
    }

    @PutMapping("/{id}")
    public EntityModel<AreaMonitoradaResponse> atualizar(@PathVariable Long id, @RequestBody @Valid AreaMonitoradaRequest request) {
        return toModel(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }

    private EntityModel<AreaMonitoradaResponse> toModel(AreaMonitoradaResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(AreaMonitoradaController.class).buscar(response.id())).withSelfRel(),
                linkTo(methodOn(AreaMonitoradaController.class).listar()).withRel("areas"),
                linkTo(methodOn(ObservacaoOrbitalController.class).listar()).withRel("observacoes"),
                linkTo(methodOn(AlertaRiscoController.class).listar()).withRel("alertas"));
    }
}
