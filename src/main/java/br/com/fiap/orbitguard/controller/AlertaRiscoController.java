package br.com.fiap.orbitguard.controller;

import br.com.fiap.orbitguard.dto.AlertaRiscoRequest;
import br.com.fiap.orbitguard.dto.AlertaRiscoResponse;
import br.com.fiap.orbitguard.service.AlertaRiscoService;
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
@RequestMapping("/alertas")
@RequiredArgsConstructor
public class AlertaRiscoController {

    private final AlertaRiscoService service;

    @GetMapping
    public CollectionModel<EntityModel<AlertaRiscoResponse>> listar() {
        List<EntityModel<AlertaRiscoResponse>> alertas = service.listar().stream().map(this::toModel).toList();
        return CollectionModel.of(alertas, linkTo(methodOn(AlertaRiscoController.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<AlertaRiscoResponse> buscar(@PathVariable Long id) {
        return toModel(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<EntityModel<AlertaRiscoResponse>> criar(@RequestBody @Valid AlertaRiscoRequest request) {
        AlertaRiscoResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(response));
    }

    @PutMapping("/{id}")
    public EntityModel<AlertaRiscoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid AlertaRiscoRequest request) {
        return toModel(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }

    private EntityModel<AlertaRiscoResponse> toModel(AlertaRiscoResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(AlertaRiscoController.class).buscar(response.id())).withSelfRel(),
                linkTo(methodOn(AlertaRiscoController.class).listar()).withRel("alertas"),
                linkTo(methodOn(AreaMonitoradaController.class).buscar(response.areaId())).withRel("area"));
    }
}
