# UserAuth API

OpenAPI source: `src/main/resources/openapi/userApi.yaml`

## Endpoints

### `POST /users`
Create an internal user profile.

Request:
```json
{
  "userName": "john",
  "email": "john@example.com",
  "status": "ACTIVE"
}
```

Response `200`:
```json
{
  "id": 1,
  "userName": "john",
  "email": "john@example.com",
  "status": "ACTIVE",
  "createdAt": "2026-03-06T12:00:00Z",
  "updatedAt": "2026-03-06T12:00:00Z"
}
```

### `GET /users?id={id}`
Get one user by id.

Response `200`: array with one user.

### `GET /users`
Get all users.

Response `200`: array of users.

### `PUT /users?id={id}`
Update internal user data.

Request:
```json
{
  "userName": "john",
  "email": "john+updated@example.com",
  "status": "INACTIVE"
}
```

Response `200`: updated user.

## Error Format
Errors are structured JSON:
```json
{
  "timestamp": "2026-03-06T12:10:00Z",
  "status": 409,
  "error": "Conflict",
  "message": "User already exists for the provided unique fields.",
  "path": "/users"
}
```

Authentication/authorization failures are also JSON:
```json
{
  "timestamp": "2026-03-06T12:10:00Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Authentication required or bearer token is invalid.",
  "path": "/users"
}
```

## Security
- This service is a resource server and expects bearer access tokens.
- Tokens must be issued by Keycloak (or another configured OIDC provider).
- The service does not provide login or refresh-token endpoints.
- Configure issuer validation with `KEYCLOAK_ISSUER_URI`.
- Local default issuer: `http://localhost:8080/realms/energy-dev`.
