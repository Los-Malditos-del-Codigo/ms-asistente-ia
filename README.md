# MS Asistente IA

Administra el historial conversacional que utilizará el asistente de Servillantas. Su alcance actual es persistencia y consulta; no llama todavía a un proveedor externo de modelos.

## Responsabilidades

- Propietario de `conversaciones_ia` y `mensajes_ia`.
- Aísla las conversaciones por usuario usando `X-User-Id`.
- Permite al cliente crear únicamente mensajes con rol `USER`.
- Lista, consulta y elimina conversaciones propias.

## API

- `POST/GET /api/v1/conversaciones-ia`.
- `GET/DELETE /api/v1/conversaciones-ia/{id}`.
- `POST /api/v1/conversaciones-ia/{id}/mensajes`.

## Configuración y ejecución

Se registra como `ms-asistente-ia`, usa el puerto `8089` y obtiene `ms-asistente-ia.yml` desde Config Server. Requiere PostgreSQL, Eureka y las variables `SPRING_DATASOURCE_URL`, `DB_USER` y `DB_PASSWORD`.

```bash
./mvnw test
./mvnw spring-boot:run
```
