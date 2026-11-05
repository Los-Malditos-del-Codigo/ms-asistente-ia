package com.servillantas.asistente.conversacion.dto.response;
import java.time.OffsetDateTime;
import java.util.UUID;
public record MensajeResponse(UUID id, UUID conversacionId, String rolMensaje, String contenido,
        OffsetDateTime createdAt) {}
