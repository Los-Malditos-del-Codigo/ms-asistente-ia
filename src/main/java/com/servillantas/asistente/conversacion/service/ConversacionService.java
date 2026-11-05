package com.servillantas.asistente.conversacion.service;
import com.servillantas.asistente.conversacion.dto.request.*;
import com.servillantas.asistente.conversacion.dto.response.*;
import java.util.UUID;
import org.springframework.data.domain.*;
public interface ConversacionService {
    ConversacionResponse crear(UUID usuarioId, CrearConversacionRequest request);
    Page<ConversacionResponse> listar(UUID usuarioId, Pageable pageable);
    ConversacionDetalleResponse detalle(UUID id, UUID usuarioId);
    MensajeResponse agregarMensaje(UUID id, UUID usuarioId, CrearMensajeRequest request);
    void eliminar(UUID id, UUID usuarioId);
}
