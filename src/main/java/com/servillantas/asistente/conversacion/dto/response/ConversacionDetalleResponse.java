package com.servillantas.asistente.conversacion.dto.response;
import java.util.List;
public record ConversacionDetalleResponse(ConversacionResponse conversacion, List<MensajeResponse> mensajes) {}
