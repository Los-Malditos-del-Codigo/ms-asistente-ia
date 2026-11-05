package com.servillantas.asistente.conversacion.entity;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
@Entity @Table(name="mensajes_ia")
public class Mensaje {
 @Id @GeneratedValue(strategy=GenerationType.UUID) public UUID id;
 @Column(name="conversacion_id",nullable=false) public UUID conversacionId;
 @Column(name="rol_mensaje",nullable=false,length=20) public String rolMensaje;
 @Column(nullable=false,columnDefinition="text") public String contenido;
 @Column(name="created_at",nullable=false,insertable=false,updatable=false) public OffsetDateTime createdAt;
 protected Mensaje(){} public Mensaje(UUID c,String r,String x){conversacionId=c;rolMensaje=r;contenido=x;}
}
