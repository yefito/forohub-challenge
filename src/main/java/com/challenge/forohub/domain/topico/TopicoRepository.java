package com.challenge.forohub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Topico findByTituloAndMensaje(String titulo, String mensaje);
}
