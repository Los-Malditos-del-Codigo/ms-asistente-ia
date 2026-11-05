package com.servillantas.asistente.conversacion.dto.request;
import jakarta.validation.constraints.*;
public record CrearMensajeRequest(@NotBlank @Pattern(regexp = "(?i)USER|ASSISTANT|SYSTEM") String rolMensaje,
        @NotBlank String contenido) {}
