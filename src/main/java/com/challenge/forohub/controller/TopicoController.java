package com.challenge.forohub.controller;

import com.challenge.forohub.domain.topico.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos,
                                                              UriComponentsBuilder uriComponentsBuilder) {
        // Llamamos al servicio para registrar el tópico y obtener los detalles
        DatosDetalleTopico detalleTopico = topicoService.registrar(datos);

        // Construir la URI para la respuesta 201 Created
        // Esto es una buena práctica RESTful
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(detalleTopico.id()).toUri();

        // Devolvemos una respuesta 201 Created con el DTO del tópico creado
        return ResponseEntity.created(url).body(detalleTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleTopico>> listarTopicos(@PageableDefault(size = 10, sort = "fechaCreacion")Pageable paginacion) {
        Page<DatosDetalleTopico> page = topicoService.listarTodos(paginacion).map(DatosDetalleTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> retornarDatosTopico(@PathVariable Long id) {
        Topico topico = topicoService.obtenerPorId(id);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @PutMapping
    @Transactional // Es importante para que los cambios en el objeto 'topico' persistan
    public ResponseEntity<DatosDetalleTopico> actualizarTopico(@RequestBody @Valid DatosActualizacionTopico datos) {
        DatosDetalleTopico topicoActualizado = topicoService.actualizarTopico(datos);
        return ResponseEntity.ok(topicoActualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
