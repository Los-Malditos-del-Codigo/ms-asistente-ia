package com.servillantas.asistente.conversacion.controller;
import com.servillantas.asistente.conversacion.dto.request.*;
import com.servillantas.asistente.conversacion.dto.response.*;
import com.servillantas.asistente.conversacion.service.ConversacionService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/conversaciones-ia")
public class ConversacionesController {
    private final ConversacionService service;
    public ConversacionesController(ConversacionService service) { this.service = service; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public ConversacionResponse crear(@RequestHeader("X-User-Id") UUID usuarioId,
            @Valid @RequestBody CrearConversacionRequest request) { return service.crear(usuarioId, request); }
    @GetMapping
    public Page<ConversacionResponse> listar(@RequestHeader("X-User-Id") UUID usuarioId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.listar(usuarioId, pageable);
    }
    @GetMapping("/{id}")
    public ConversacionDetalleResponse detalle(@PathVariable UUID id, @RequestHeader("X-User-Id") UUID usuarioId) {
        return service.detalle(id, usuarioId);
    }
    @PostMapping("/{id}/mensajes") @ResponseStatus(HttpStatus.CREATED)
    public MensajeResponse agregar(@PathVariable UUID id, @RequestHeader("X-User-Id") UUID usuarioId,
            @Valid @RequestBody CrearMensajeRequest request) { return service.agregarMensaje(id, usuarioId, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable UUID id, @RequestHeader("X-User-Id") UUID usuarioId) {
        service.eliminar(id, usuarioId);
    }
}
