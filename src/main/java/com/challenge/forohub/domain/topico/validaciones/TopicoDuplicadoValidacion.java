package com.challenge.forohub.domain.topico.validaciones;

import com.challenge.forohub.domain.ValidacionException;
import com.challenge.forohub.domain.topico.DatosRegistroTopico;
import com.challenge.forohub.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicoDuplicadoValidacion implements ValidacionDeTopicos{

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DatosRegistroTopico datos) {
        var topicoExistente = topicoRepository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if (topicoExistente != null) {
            throw new ValidacionException("Ya existe un tópico con ese titulo y mensaje");
        }
    }
}
