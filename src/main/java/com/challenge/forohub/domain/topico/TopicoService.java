package com.challenge.forohub.domain.topico;

import com.challenge.forohub.domain.ValidacionException;
import com.challenge.forohub.domain.topico.validaciones.ValidacionDeTopicos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private List<ValidacionDeTopicos> validadores;

    public DatosDetalleTopico registrar(DatosRegistroTopico datos) {
        // Ejecutar todas las validaciones
        validadores.forEach(v -> v.validar(datos));

        // Convertir el DTO a la entidad Topico
        Topico topico = new Topico(datos);

        // Guardar el tópico en la base de datos
        topico = topicoRepository.save(topico);

        // Devolver un DTO con los detalles del tópico registrado
        return new DatosDetalleTopico(topico);
    }

    public Page<Topico> listarTodos(Pageable paginacion) {
        return topicoRepository.findAll(paginacion);
    }

    public Topico obtenerPorId(Long id) {
        return topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No se encontró el tópico con el ID proporcionado."));
    }

    public DatosDetalleTopico actualizarTopico(DatosActualizacionTopico datos) {
        // 1. Verificar si el tópico existe
        Topico topico = topicoRepository.findById(datos.id())
                .orElseThrow(() -> new ValidacionException("No se encontró el tópico con el ID proporcionado para actualizar."));

        if (datos.titulo() != null && !datos.titulo().equals(topico.getTitulo())) {
            if (topicoRepository.findByTituloAndMensaje(datos.titulo(), topico.getMensaje()) != null) {
                throw new ValidacionException("Ya existe un tópico con este título y mensaje (al actualizar título).");
            }
            topico.setTitulo(datos.titulo());
        }
        if (datos.mensaje() != null && !datos.mensaje().equals(topico.getMensaje())) {
            if (topicoRepository.findByTituloAndMensaje(topico.getTitulo(), datos.mensaje()) != null) {
                throw new ValidacionException("Ya existe un tópico con este título y mensaje (al actualizar mensaje).");
            }
            topico.setMensaje(datos.mensaje());
        }

        if (datos.titulo() != null && !datos.titulo().equals(topico.getTitulo())) {
            topico.setTitulo(datos.titulo());
        }
        if (datos.mensaje() != null && !datos.mensaje().equals(topico.getMensaje())) {
            topico.setMensaje(datos.mensaje());
        }
        if (datos.autor() != null && !datos.autor().equals(topico.getAutor())) {
            topico.setAutor(datos.autor());
        }
        if (datos.curso() != null && !datos.curso().equals(topico.getCurso())) {
            topico.setCurso(datos.curso());
        }
        if (datos.status() != null && !datos.status().equals(topico.getStatus())) {
            topico.setStatus(datos.status());
        }

        // JPA/Hibernate detecta los cambios y los persiste automáticamente en una transacción @Transactional
        return new DatosDetalleTopico(topico);
    }

    public void eliminarTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new ValidacionException("No se encontró el tópico con el ID proporcionado para eliminar.");
        }
        topicoRepository.deleteById(id);
    }
}
