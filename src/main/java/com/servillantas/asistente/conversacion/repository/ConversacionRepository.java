package com.servillantas.asistente.conversacion.repository;
import com.servillantas.asistente.conversacion.entity.Conversacion;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface ConversacionRepository extends JpaRepository<Conversacion,UUID>{Page<Conversacion> findByUsuarioId(UUID usuarioId,Pageable pageable);}
