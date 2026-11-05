package com.servillantas.asistente.conversacion.dto.request;
import jakarta.validation.constraints.*;
public record CrearMensajeRequest(
        @NotBlank @Pattern(regexp = "(?i)USER", message = "El cliente solo puede crear mensajes USER")
        String rolMensaje,
        @NotBlank @Size(max = 10000, message = "El mensaje no puede superar 10000 caracteres")
        String contenido
) {}
