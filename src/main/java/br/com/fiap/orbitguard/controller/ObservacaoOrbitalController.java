package br.com.fiap.orbitguard.controller;

import br.com.fiap.orbitguard.dto.ObservacaoOrbitalRequest;
import br.com.fiap.orbitguard.dto.ObservacaoOrbitalResponse;
import br.com.fiap.orbitguard.service.ObservacaoOrbitalService;
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
@RequestMapping("/observacoes")
@RequiredArgsConstructor
public class ObservacaoOrbitalController {

    private final ObservacaoOrbitalService service;

    @GetMapping
    public CollectionModel<EntityModel<ObservacaoOrbitalResponse>> listar() {
        List<EntityModel<ObservacaoOrbitalResponse>> observacoes = service.listar().stream().map(this::toModel).toList();
        return CollectionModel.of(observacoes, linkTo(methodOn(ObservacaoOrbitalController.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ObservacaoOrbitalResponse> buscar(@PathVariable Long id) {
        return toModel(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<EntityModel<ObservacaoOrbitalResponse>> criar(@RequestBody @Valid ObservacaoOrbitalRequest request) {
        ObservacaoOrbitalResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(response));
    }

    @PutMapping("/{id}")
    public EntityModel<ObservacaoOrbitalResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ObservacaoOrbitalRequest request) {
        return toModel(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }

    private EntityModel<ObservacaoOrbitalResponse> toModel(ObservacaoOrbitalResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(ObservacaoOrbitalController.class).buscar(response.id())).withSelfRel(),
                linkTo(methodOn(ObservacaoOrbitalController.class).listar()).withRel("observacoes"),
                linkTo(methodOn(AreaMonitoradaController.class).buscar(response.areaId())).withRel("area"),
                linkTo(methodOn(AlertaRiscoController.class).listar()).withRel("alertas"));
    }
}
