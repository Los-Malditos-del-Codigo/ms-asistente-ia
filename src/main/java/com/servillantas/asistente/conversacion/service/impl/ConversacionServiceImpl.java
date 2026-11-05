package com.servillantas.asistente.conversacion.service.impl;
import com.servillantas.asistente.conversacion.dto.request.*;
import com.servillantas.asistente.conversacion.dto.response.*;
import com.servillantas.asistente.conversacion.entity.*;
import com.servillantas.asistente.conversacion.mapper.ConversacionMapper;
import com.servillantas.asistente.conversacion.repository.*;
import com.servillantas.asistente.conversacion.service.ConversacionService;
import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
@Service public class ConversacionServiceImpl implements ConversacionService {
    private static final Set<String> SORT_FIELDS = Set.of("createdAt", "updatedAt", "titulo");
    private final ConversacionRepository conversaciones; private final MensajeRepository mensajes;
    private final ConversacionMapper mapper;
    public ConversacionServiceImpl(ConversacionRepository conversaciones, MensajeRepository mensajes,
            ConversacionMapper mapper) { this.conversaciones = conversaciones; this.mensajes = mensajes; this.mapper = mapper; }
    @Override @Transactional
    public ConversacionResponse crear(UUID usuarioId, CrearConversacionRequest r) {
        return mapper.toResponse(conversaciones.save(new Conversacion(usuarioId, limpiarTitulo(r.titulo()))));
    }
    @Override @Transactional(readOnly = true)
    public Page<ConversacionResponse> listar(UUID usuarioId, Pageable p) {
        p.getSort().forEach(order -> {
            if (!SORT_FIELDS.contains(order.getProperty()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Campo de ordenamiento inválido: " + order.getProperty());
        });
        Pageable segura = PageRequest.of(Math.max(0, p.getPageNumber()), Math.min(Math.max(1, p.getPageSize()), 100), p.getSort());
        return conversaciones.findByUsuarioId(usuarioId, segura).map(mapper::toResponse);
    }
    @Override @Transactional(readOnly = true)
    public ConversacionDetalleResponse detalle(UUID id, UUID usuarioId) {
        Conversacion c = buscarPropia(id, usuarioId);
        List<MensajeResponse> items = mensajes.findByConversacionIdOrderByCreatedAtAsc(id).stream().map(mapper::toResponse).toList();
        return new ConversacionDetalleResponse(mapper.toResponse(c), items);
    }
    @Override @Transactional
    public MensajeResponse agregarMensaje(UUID id, UUID usuarioId, CrearMensajeRequest r) {
        buscarPropia(id, usuarioId);
        if (!"USER".equalsIgnoreCase(r.rolMensaje()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El cliente solo puede crear mensajes USER");
        return mapper.toResponse(mensajes.save(new Mensaje(id, "USER", r.contenido().trim())));
    }
    @Override @Transactional
    public void eliminar(UUID id, UUID usuarioId) { conversaciones.delete(buscarPropia(id, usuarioId)); }
    private Conversacion buscarPropia(UUID id, UUID usuarioId) {
        Conversacion c = conversaciones.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Conversación no encontrada"));
        if (!c.usuarioId.equals(usuarioId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La conversación pertenece a otro usuario");
        return c;
    }
    private String limpiarTitulo(String titulo) { return titulo == null || titulo.isBlank() ? null : titulo.trim(); }
}
