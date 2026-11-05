package com.servillantas.asistente.conversacion.mapper;
import com.servillantas.asistente.conversacion.dto.response.*;
import com.servillantas.asistente.conversacion.entity.*;
import org.springframework.stereotype.Component;
@Component public class ConversacionMapper {
    public ConversacionResponse toResponse(Conversacion v) { return new ConversacionResponse(v.id, v.usuarioId,
            v.titulo, v.createdAt, v.updatedAt); }
    public MensajeResponse toResponse(Mensaje v) { return new MensajeResponse(v.id, v.conversacionId,
            v.rolMensaje, v.contenido, v.createdAt); }
}
