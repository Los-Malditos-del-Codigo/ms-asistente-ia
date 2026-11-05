package com.servillantas.asistente.conversacion.repository;
import com.servillantas.asistente.conversacion.entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface MensajeRepository extends JpaRepository<Mensaje,UUID>{List<Mensaje> findByConversacionIdOrderByCreatedAtAsc(UUID conversacionId);}
