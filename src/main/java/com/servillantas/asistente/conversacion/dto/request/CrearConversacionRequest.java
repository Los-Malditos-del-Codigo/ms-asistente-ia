package com.servillantas.asistente.conversacion.dto.request;
import jakarta.validation.constraints.Size;
public record CrearConversacionRequest(@Size(max = 200) String titulo) {}
