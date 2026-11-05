package com.servillantas.asistente.conversacion.dto.response;
import java.time.OffsetDateTime;
import java.util.UUID;
public record ConversacionResponse(UUID id, UUID usuarioId, String titulo,
        OffsetDateTime createdAt, OffsetDateTime updatedAt) {}
