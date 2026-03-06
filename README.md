# UserAuth Microservice

## Purpose
`userauth` is the Energy platform microservice for internal user-management and authorization-domain data.

This service is no longer an identity provider.

## What This Service Does
- Manages internal user profiles and lifecycle (`create`, `update`, `query`)
- Stores internal user status (`ACTIVE`, `INACTIVE`, `SUSPENDED`)
- Acts as source of truth for internal user records used by downstream business services

## What This Service Does Not Do
- Does not authenticate passwords
- Does not issue JWT access tokens
- Does not refresh tokens
- Does not manage JWT signing keys

Authentication and token issuance are delegated to Keycloak.

## Architecture
Hexagonal (ports and adapters):
- `domain`: business models/rules (`User`, validations)
- `application`: use cases and ports
- `infrastructure`: JPA adapters, resource-server security configuration
- `presentation`: controllers, DTO mapping, structured API errors

Dependency direction:
`presentation/infrastructure -> application -> domain`

## Security Model
- Service is configured as an OAuth2 Resource Server.
- It expects bearer access tokens already issued by Keycloak.
- In production, API Gateway should validate tokens before requests reach this service.

Configuration:
- `spring.security.oauth2.resourceserver.jwt.issuer-uri`
- Environment variable: `KEYCLOAK_ISSUER_URI`
- Local default: `http://localhost:8080/realms/energy-dev`
- Kubernetes example: `http://keycloak.security.svc.cluster.local:8080/realms/energy-dev`

## Main Tech Stack
- Java 17
- Spring Boot 3
- Spring Web, Spring Security, Spring Data JPA
- OAuth2 Resource Server
- PostgreSQL (H2 in tests)
- OpenAPI Generator
- Maven

## Migration Note
A legacy `users.password` column is temporarily retained at persistence level for schema compatibility.
Business APIs no longer accept or return passwords. Remove that column in a coordinated DB migration.

The former `identity_links` feature has been removed from the application layer and API contract.
If a backing `identity_links` table already exists in a deployed database, remove it with a coordinated DB migration.

## Run
```bash
./mvnw spring-boot:run
```

## Test
```bash
./mvnw test
```

## API Reference
See [docs/API.md](docs/API.md).
OpenAPI source: `src/main/resources/openapi/userApi.yaml`.
