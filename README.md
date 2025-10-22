# Hospital Management System

## Project Description

This is a hospital management system built with Spring Boot. It provides a RESTful API for managing patients, doctors, and appointments. The application uses Spring Security to secure the API with JWT and role-based access control. It also supports authentication via OAuth2 providers (e.g., Google, GitHub).

The system has three main roles:

*   **ADMIN**: Can manage patients and onboard new doctors.
*   **DOCTOR**: Can view their appointments.
*   **PATIENT**: Can book appointments and view their profile.

## Project Flow

1.  **Authentication**:
    *   Users can sign up for a new account using their email and password. By default, they are assigned the `PATIENT` role.
    *   Users can also log in using their credentials or through an OAuth2 provider.
    *   Upon successful authentication, the server generates a JWT and returns it to the client.

2.  **Authorization**:
    *   The client must include the JWT in the `Authorization` header of subsequent requests (e.g., `Authorization: Bearer <token>`).
    *   A `JwtAuthFilter` on the server intercepts each request, validates the token, and sets the user's authentication details in the `SecurityContext`.
    *   The application uses role-based access control to restrict access to certain endpoints based on the user's role.

3.  **Functionality**:
    *   **Patients**: Can create new appointments and view their profile information.
    *   **Doctors**: Can view all appointments assigned to them.
    *   **Admins**: Have full access to manage patients and onboard new doctors.

## Security Implementation

### Spring Security

The project uses Spring Security to handle authentication and authorization. The core of the security configuration is in the `WebSecurityConfig` class, which defines the security filter chain.

### JWT (JSON Web Token)

JWT is used for stateless authentication. The `AuthService` generates a token upon successful login. The `AuthUtil` class contains utility functions for generating and validating tokens. The `JwtAuthFilter` validates the token on each request.

The JWT contains claims about the user, such as their username and roles, which are used for authorization.

### Role-Based Access Control (RBAC)

The application implements RBAC to control access to its resources. There are three roles defined in the `RoleType` enum: `PATIENT`, `DOCTOR`, and `ADMIN`.

The `RolePermissionMapping` class maps each role to a set of permissions (defined in the `PermissionType` enum). These permissions are then used to secure the endpoints in the `WebSecurityConfig` class.

For example, the `/admin/**` endpoints are only accessible to users with the `ADMIN` role.

### Low-Level Security Flow

1.  A user sends a login request with their credentials to `/auth/login`.
2.  The `AuthController` calls the `AuthService` to authenticate the user.
3.  The `AuthService` uses Spring Security's `AuthenticationManager` to validate the credentials.
4.  If the credentials are valid, the `AuthService` generates a JWT using the `AuthUtil` class.
5.  The JWT is returned to the user in the response body.
6.  For subsequent requests, the user includes the JWT in the `Authorization` header.
7.  The `JwtAuthFilter` intercepts the request and extracts the token.
8.  The filter uses `AuthUtil` to validate the token and extract the username.
9.  The filter fetches the user's details from the database using the `UserRepository`.
10. A `UsernamePasswordAuthenticationToken` is created with the user's details and authorities (roles and permissions).
11. The authentication token is set in the `SecurityContextHolder`.
12. Spring Security's authorization mechanism then checks if the user has the required role or authority to access the requested resource, based on the configuration in `WebSecurityConfig`.

### OAuth2 Integration

The application also supports OAuth2 authentication. The `WebSecurityConfig` class is configured to handle OAuth2 login. The `OAuth2SuccessHandler` is responsible for processing the user's information after a successful OAuth2 login. It either creates a new user account or logs in an existing user.
