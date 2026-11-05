package com.servillantas.asistente.controller;

import com.servillantas.asistente.domain.*;
import com.servillantas.asistente.repository.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController @RequestMapping("/api/v1/conversaciones-ia")
public class ConversacionesController {
 private final ConversacionRepository conversaciones; private final MensajeRepository mensajes;
 public ConversacionesController(ConversacionRepository c,MensajeRepository m){conversaciones=c;mensajes=m;}
 public record CrearConversacion(@Size(max=200) String titulo){}
 public record CrearMensaje(@NotBlank @Pattern(regexp="(?i)USER|ASSISTANT|SYSTEM") String rolMensaje,@NotBlank String contenido){}
 public record DetalleConversacion(Conversacion conversacion,List<Mensaje> mensajes){}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Conversacion crear(@RequestHeader("X-User-Id") UUID usuarioId,@Valid @RequestBody CrearConversacion r){return conversaciones.save(new Conversacion(usuarioId,limpiarTitulo(r.titulo())));}
 @GetMapping public Page<Conversacion> listar(@RequestHeader("X-User-Id") UUID usuarioId,@PageableDefault(sort="createdAt",direction=Sort.Direction.DESC) Pageable p){Pageable segura=PageRequest.of(Math.max(0,p.getPageNumber()),Math.min(Math.max(1,p.getPageSize()),100),p.getSort());return conversaciones.findByUsuarioId(usuarioId,segura);}
 @GetMapping("/{id}") public DetalleConversacion detalle(@PathVariable UUID id,@RequestHeader("X-User-Id") UUID usuarioId){Conversacion c=buscarPropia(id,usuarioId);return new DetalleConversacion(c,mensajes.findByConversacionIdOrderByCreatedAtAsc(id));}
 @PostMapping("/{id}/mensajes") @ResponseStatus(HttpStatus.CREATED) @Transactional public Mensaje agregar(@PathVariable UUID id,@RequestHeader("X-User-Id") UUID usuarioId,@Valid @RequestBody CrearMensaje r){buscarPropia(id,usuarioId);if(!"USER".equalsIgnoreCase(r.rolMensaje()))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"El cliente solo puede crear mensajes USER");return mensajes.save(new Mensaje(id,"USER",r.contenido().trim()));}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Transactional public void eliminar(@PathVariable UUID id,@RequestHeader("X-User-Id") UUID usuarioId){conversaciones.delete(buscarPropia(id,usuarioId));}
 private Conversacion buscarPropia(UUID id,UUID usuarioId){Conversacion c=buscar(id);if(!c.usuarioId.equals(usuarioId))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"La conversación pertenece a otro usuario");return c;}
 private String limpiarTitulo(String titulo){if(titulo==null||titulo.isBlank())return null;return titulo.trim();}
 private Conversacion buscar(UUID id){return conversaciones.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Conversación no encontrada"));}
}
