package com.servillantas.asistente.conversacion.entity;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
@Entity @Table(name="conversaciones_ia")
public class Conversacion {
 @Id @GeneratedValue(strategy=GenerationType.UUID) public UUID id;
 @Column(name="usuario_id",nullable=false) public UUID usuarioId;
 @Column(length=200) public String titulo;
 @Column(name="created_at",nullable=false,insertable=false,updatable=false) public OffsetDateTime createdAt;
 @Column(name="updated_at",nullable=false,insertable=false,updatable=false) public OffsetDateTime updatedAt;
 protected Conversacion(){} public Conversacion(UUID u,String t){usuarioId=u;titulo=t;}
}
