# Hospital Management System

## 1. Project Overview

This project is a robust and secure Hospital Management System built using the Spring Boot framework. It provides a comprehensive set of RESTful APIs to efficiently manage various aspects of a hospital, including patient records, doctor information, and appointment scheduling. The system is designed with a strong emphasis on security, integrating Spring Security for authentication and authorization, JSON Web Tokens (JWT) for stateless session management, and Role-Based Access Control (RBAC) to ensure that users only access resources relevant to their roles. Additionally, it supports seamless integration with OAuth2 providers like Google and GitHub for convenient social logins.

The primary goal of this system is to streamline hospital operations, improve data management, and provide a secure platform for healthcare professionals and patients to interact.

## 2. Key Features

*   **User Authentication & Authorization**:
    *   **Secure Registration & Login**: Users can create accounts and log in using traditional email/password combinations.
    *   **OAuth2 Integration**: Supports social logins via popular providers like Google and GitHub, offering a convenient alternative to traditional registration.
    *   **JWT-based Sessions**: Utilizes JSON Web Tokens for secure, stateless session management, enhancing scalability and security.
*   **Role-Based Access Control (RBAC)**:
    *   The system defines distinct roles: `ADMIN`, `DOCTOR`, and `PATIENT`, each with specific permissions.
    *   **ADMIN**: Possesses full administrative privileges, including managing patient records and onboarding new doctors.
    *   **DOCTOR**: Can view and manage their assigned appointments.
    *   **PATIENT**: Can book new appointments, view their personal profile, and manage their medical history.
*   **Patient Management**:
    *   Admins have the ability to view, add, update, and delete patient records.
    *   Patients can access and update their own profile information.
*   **Doctor Management**:
    *   Admins can onboard new doctors, assigning them to departments and specialties.
    *   Doctors can manage their schedules and view their patient appointments.
*   **Appointment Scheduling**:
    *   Patients can easily book appointments with available doctors.
    *   Doctors can view their upcoming and past appointments.
*   **Department Management**: Organize doctors into various hospital departments.
*   **Insurance Management**: Track patient insurance details.

## 3. Technologies Used

This project leverages a modern and robust technology stack to ensure performance, scalability, and maintainability.

*   **Backend Framework**:
    *   **Java 21**: The core programming language.
    *   **Spring Boot**: Provides a powerful and opinionated framework for building production-ready Spring applications quickly. It simplifies configuration and deployment.
    *   **Spring Security**: The industry-standard framework for authentication and authorization in Spring applications. It provides comprehensive security services.
    *   **Spring Data JPA**: Simplifies database interactions by providing an abstraction layer over JDBC, allowing developers to work with objects instead of SQL queries.
*   **Database**:
    *   **PostgreSQL**: A powerful, open-source relational database system known for its reliability, feature robustness, and performance.
*   **Authentication & Authorization**:
    *   **JWT (JSON Web Token)**: A compact, URL-safe means of representing claims to be transferred between two parties. Used for stateless authentication.
    *   **OAuth2**: An authorization framework that enables applications to obtain limited access to user accounts on an HTTP service (like Google or GitHub).
*   **Build Tool**:
    *   **Maven**: A project management and comprehension tool that provides a complete build lifecycle framework.
*   **Utility Libraries**:
    *   **Lombok**: A Java library that automatically plugs into your build process to "spice up" your Java classes by adding annotations that generate boilerplate code (getters, setters, constructors, etc.).
    *   **ModelMapper**: A convention-based object mapping library that simplifies the mapping between different object models (e.g., DTOs to Entities).
    *   **jjwt**: A Java library for creating and consuming JSON Web Tokens.

## 4. Getting Started

Follow these steps to set up and run the Hospital Management System on your local machine.

### 4.1. Prerequisites

Ensure you have the following software installed:

*   **Java Development Kit (JDK) 21**:
    *   Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or use a package manager like SDKMAN.
*   **Apache Maven**:
    *   Download from [Maven Official Site](https://maven.apache.org/download.cgi) or use a package manager.
*   **PostgreSQL Database**:
    *   Download and install from [PostgreSQL Official Site](https://www.postgresql.org/download/).
    *   **Database Setup**:
        *   Create a new PostgreSQL database (e.g., `hospital_db`).
        *   Create a database user with appropriate permissions (e.g., `hospital_user` with password `password`).

### 4.2. Installation

1.  **Clone the repository**:
    Open your terminal or command prompt and execute:
    ```bash
    git clone https://github.com/your-username/hospital-management.git
    cd hospital-management
    ```
    (Replace `https://github.com/your-username/hospital-management.git` with the actual repository URL if different).

2.  **Build the project**:
    Navigate to the project root directory (`hospital-management`) and run the Maven build command:
    ```bash
    mvn clean install
    ```
    This command compiles the code, runs tests, and packages the application into a JAR file.

### 4.3. Configuration

Before running the application, you need to configure database connection details, JWT secret, and OAuth2 credentials.

1.  **Database Configuration (`src/main/resources/application.properties`)**:
    Open the `application.properties` file and update the following lines with your PostgreSQL database details:
    ```properties
    # PostgreSQL Database Configuration
    spring.datasource.url=jdbc:postgresql://localhost:5432/hospital_db
    spring.datasource.username=hospital_user
    spring.datasource.password=password
    spring.jpa.hibernate.ddl-auto=update # 'update' for development, 'none' for production
    spring.jpa.show-sql=true
    ```
    *   `spring.datasource.url`: The JDBC URL for your PostgreSQL database.
    *   `spring.datasource.username`: The username for your database.
    *   `spring.datasource.password`: The password for your database user.
    *   `spring.jpa.hibernate.ddl-auto`: Set to `update` to automatically create/update database tables based on your JPA entities (useful for development). For production, consider `none` and manage schema migrations manually.

2.  **JWT Secret Configuration (`src/main/resources/application.properties`)**:
    Add a strong, unique secret key for signing and verifying JWTs. This should be a long, random string.
    ```properties
    # JWT Secret Key (MUST be strong and kept secret!)
    jwt.secret=YourVeryStrongAndSecretJWTKeyThatNoOneCanGuess12345!@#$%^&*()
    ```
    *   **Important**: Never expose this secret key in public repositories or client-side code. Use environment variables in production.

3.  **OAuth2 Configuration (`src/main/resources/application.yml`)**:
    If you plan to use Google or GitHub for social logins, you need to register your application with these providers to obtain `client-id` and `client-secret`.
    *   **Google**: Follow the instructions [here](https://developers.google.com/identity/protocols/oauth2/web-server#creatingcred) to create OAuth 2.0 credentials.
    *   **GitHub**: Follow the instructions [here](https://docs.github.com/en/developers/apps/building-oauth-apps/creating-an-oauth-app) to create an OAuth App.

    Update the `application.yml` file with your obtained credentials:
    ```yaml
    spring:
      security:
        oauth2:
          client:
            registration:
              google:
                client-id: your-google-client-id # Replace with your Google Client ID
                client-secret: your-google-client-secret # Replace with your Google Client Secret
                scope:
                  - email
                  - profile
              github:
                client-id: your-github-client-id # Replace with your GitHub Client ID
                client-secret: your-github-client-secret # Replace with your GitHub Client Secret
                scope:
                  - user:email
    ```

### 4.4. Running the Application

After configuring, you can run the Spring Boot application:

1.  **From the command line**:
    Navigate to the project root directory and run:
    ```bash
    java -jar target/hospitalmanagement-0.0.1-SNAPSHOT.jar
    ```
    (The JAR file name might vary slightly based on the version in `pom.xml`).

2.  **From an IDE (e.g., IntelliJ IDEA, Eclipse)**:
    *   Import the project as a Maven project.
    *   Locate the `HospitalManagementApplication.java` file (usually in `src/main/java/hospitalManagement/`).
    *   Right-click on the file and select "Run HospitalManagementApplication".

The application will start on `http://localhost:8080` by default.

## 5. Database Schema Details

The application's data model is designed to manage various hospital entities and their relationships.

*   **`app_user` Table**:
    *   Stores core user authentication information.
    *   `id`: Primary key, unique identifier for the user.
    *   `username`: Unique username (often email), used for login.
    *   `password`: Hashed password for traditional logins.
    *   `provider_id`: Identifier from an OAuth2 provider (e.g., Google ID).
    *   `provider_type`: Type of OAuth2 provider (e.g., `EMAIL`, `GOOGLE`, `GITHUB`).
    *   `roles`: A collection of roles assigned to the user (e.g., `ADMIN`, `DOCTOR`, `PATIENT`).
*   **`patient` Table**:
    *   Stores demographic and medical information for patients.
    *   `id`: Primary key, linked to `app_user.id`.
    *   `name`: Patient's full name.
    *   `birth_date`: Patient's date of birth.
    *   `email`: Patient's email address (unique).
    *   `gender`: Patient's gender.
    *   `blood_group`: Patient's blood group (e.g., A+, O-).
    *   `created_at`: Timestamp when the patient record was created.
    *   `patient_insurance_id`: Foreign key to the `insurance` table.
*   **`doctor` Table**:
    *   Stores professional details for doctors.
    *   `id`: Primary key, linked to `app_user.id`.
    *   `name`: Doctor's full name.
    *   `specialization`: Doctor's medical specialization (e.g., Cardiology, Pediatrics).
    *   `email`: Doctor's email address (unique).
*   **`appointment` Table**:
    *   Records scheduled appointments between patients and doctors.
    *   `id`: Primary key.
    *   `appointment_time`: Date and time of the appointment.
    *   `reason`: Reason for the appointment.
    *   `patient_id`: Foreign key to the `patient` table.
    *   `doctor_id`: Foreign key to the `doctor` table.
*   **`department` Table**:
    *   Defines different departments within the hospital.
    *   `id`: Primary key.
    *   `name`: Name of the department (e.g., "Emergency", "Cardiology").
    *   `head_doctor_id`: Foreign key to the `doctor` table, indicating the head of the department.
*   **`insurance` Table**:
    *   Stores details about a patient's insurance policy.
    *   `id`: Primary key.
    *   `policy_number`: Unique insurance policy number.
    *   `provider`: Insurance provider's name.
    *   `valid_until`: Date until the policy is valid.
    *   `created_at`: Timestamp when the insurance record was created.

**Entity Relationships (Simplified)**:
*   A `User` entity forms the base for both `Patient` and `Doctor` entities (one-to-one relationship, sharing the same ID).
*   A `Patient` can have one `Insurance` policy (one-to-one) and can have many `Appointments` (one-to-many).
*   A `Doctor` can belong to multiple `Departments` (many-to-many) and can have many `Appointments` (one-to-many).
*   An `Appointment` connects one `Patient` to one `Doctor` (many-to-one from Appointment to Patient, and many-to-one from Appointment to Doctor).

## 6. API Endpoints

The application exposes a set of RESTful APIs. All endpoints are secured and require authentication unless explicitly stated as `Public`. For authenticated endpoints, include a `Bearer` token in the `Authorization` header (e.g., `Authorization: Bearer <YOUR_JWT_TOKEN>`).

### 6.1. Authentication Endpoints (`/auth`)

These endpoints handle user registration and login.

*   **`POST /auth/signup`**
    *   **Description**: Registers a new user as a `PATIENT` in the system.
    *   **Access**: Public
    *   **Request Body Example**:
        ```json
        {
            "username": "newpatient@example.com",
            "password": "securePassword123",
            "name": "John Doe",
            "birthDate": "1990-01-15",
            "gender": "Male",
            "bloodGroup": "A_POSITIVE"
        }
        ```
    *   **Response Body Example (200 OK)**:
        ```json
        {
            "userId": 101,
            "username": "newpatient@example.com"
        }
        ```

*   **`POST /auth/login`**
    *   **Description**: Authenticates an existing user and returns a JWT.
    *   **Access**: Public
    *   **Request Body Example**:
        ```json
        {
            "username": "existinguser@example.com",
            "password": "securePassword123"
        }
        ```
    *   **Response Body Example (200 OK)**:
        ```json
        {
            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
            "userId": 102
        }
        ```
    *   **Error Response (401 Unauthorized)**: If credentials are invalid.

### 6.2. Admin Endpoints (`/admin`)

These endpoints are restricted to users with the `ADMIN` role.

*   **`GET /admin/patients`**
    *   **Description**: Retrieves a paginated list of all registered patients.
    *   **Access**: `ADMIN`
    *   **Query Parameters**:
        *   `page` (optional, default 0): Page number.
        *   `size` (optional, default 10): Number of patients per page.
    *   **Response Body Example (200 OK)**:
        ```json
        [
            {
                "id": 1,
                "name": "John Doe",
                "email": "john.doe@example.com",
                "birthDate": "1990-01-15",
                "gender": "Male",
                "bloodGroup": "A_POSITIVE"
            },
            {
                "id": 2,
                "name": "Jane Smith",
                "email": "jane.smith@example.com",
                "birthDate": "1985-03-20",
                "gender": "Female",
                "bloodGroup": "B_NEGATIVE"
            }
        ]
        ```

*   **`POST /admin/onBoardNewDoctor`**
    *   **Description**: Registers a new doctor in the system.
    *   **Access**: `ADMIN`
    *   **Request Body Example**:
        ```json
        {
            "username": "dr.smith@example.com",
            "password": "doctorPassword123",
            "name": "Dr. Alice Smith",
            "specialization": "Cardiology",
            "email": "dr.smith@example.com"
        }
        ```
    *   **Response Body Example (201 Created)**:
        ```json
        {
            "id": 201,
            "name": "Dr. Alice Smith",
            "specialization": "Cardiology",
            "email": "dr.smith@example.com"
        }
        ```
    *   **Error Response (403 Forbidden)**: If the authenticated user is not an `ADMIN`.

### 6.3. Doctor Endpoints (`/doctors`)

These endpoints are restricted to users with the `DOCTOR` or `ADMIN` role.

*   **`GET /doctors/appointments`**
    *   **Description**: Retrieves all appointments scheduled for the authenticated doctor.
    *   **Access**: `DOCTOR`, `ADMIN`
    *   **Response Body Example (200 OK)**:
        ```json
        [
            {
                "id": 501,
                "appointmentTime": "2025-10-27T10:00:00",
                "reason": "Routine check-up",
                "patientName": "John Doe"
            },
            {
                "id": 502,
                "appointmentTime": "2025-10-27T11:30:00",
                "reason": "Follow-up",
                "patientName": "Jane Smith"
            }
        ]
        ```
    *   **Error Response (403 Forbidden)**: If the authenticated user is not a `DOCTOR` or `ADMIN`.

### 6.4. Patient Endpoints (`/patients`)

These endpoints are restricted to users with the `PATIENT` role.

*   **`POST /patients/appointments`**
    *   **Description**: Creates a new appointment for the authenticated patient.
    *   **Access**: `PATIENT`
    *   **Request Body Example**:
        ```json
        {
            "patientId": 101,
            "doctorId": 201,
            "appointmentTime": "2025-11-01T09:00:00",
            "reason": "Annual physical"
        }
        ```
    *   **Response Body Example (201 Created)**:
        ```json
        {
            "id": 601,
            "appointmentTime": "2025-11-01T09:00:00",
            "reason": "Annual physical",
            "patientName": "John Doe",
            "doctorName": "Dr. Alice Smith"
        }
        ```
    *   **Error Response (403 Forbidden)**: If the authenticated user is not a `PATIENT`.

*   **`GET /patients/profile`**
    *   **Description**: Retrieves the profile details of the authenticated patient.
    *   **Access**: `PATIENT`
    *   **Response Body Example (200 OK)**:
        ```json
        {
            "id": 101,
            "name": "John Doe",
            "email": "john.doe@example.com",
            "birthDate": "1990-01-15",
            "gender": "Male",
            "bloodGroup": "A_POSITIVE",
            "insurance": {
                "policyNumber": "INS-12345",
                "provider": "HealthCare Inc.",
                "validUntil": "2026-12-31"
            }
        }
        ```
    *   **Error Response (403 Forbidden)**: If the authenticated user is not a `PATIENT`.

## 7. Security Implementation Details

The security of this application is built upon Spring Security, enhanced with JWT for API authentication and a robust Role-Based Access Control (RBAC) system. OAuth2 is integrated for flexible social logins.

### 7.1. Spring Security: The Core Guardian

Spring Security acts as the primary security framework, handling all authentication and authorization concerns. It's like the bouncer at the club, deciding who gets in and what they can do.

*   **`WebSecurityConfig.java`**: This is the central configuration class for Spring Security. It defines the `SecurityFilterChain`, which is a series of filters that process incoming HTTP requests.
    *   It configures which URLs are `permitAll()` (accessible to anyone) and which require authentication or specific roles/permissions.
    *   It integrates our custom `JwtAuthFilter` into the chain.
    *   It sets up session management to be `STATELESS`, crucial for JWT-based authentication.
    *   It configures OAuth2 login handling.
    *   It defines custom handlers for `AuthenticationFailure` and `AccessDenied` scenarios to provide consistent error responses.

### 7.2. JWT (JSON Web Token) Authentication: Stateless & Scalable

JWTs are used to securely transmit information between parties as a JSON object. They are particularly well-suited for RESTful APIs because they are stateless, meaning the server doesn't need to store session information.

*   **How it Works**:
    1.  **Login**: When a user successfully logs in (via `/auth/login` or OAuth2), the `AuthService` generates a JWT.
    2.  **Token Structure**: A JWT consists of three parts, separated by dots (`.`):
        *   **Header**: Contains metadata about the token, like the algorithm used for signing (e.g., `HS256`).
        *   **Payload**: Contains claims (statements) about the user, such as their `username`, `roles`, and `expiration time`.
        *   **Signature**: Created by combining the encoded header, encoded payload, a secret key, and the algorithm specified in the header. This signature is used to verify that the token hasn't been tampered with.
    3.  **Client Storage**: The generated JWT is sent back to the client (e.g., web browser, mobile app), which stores it (e.g., in local storage).
    4.  **Subsequent Requests**: For every subsequent request to a secured endpoint, the client includes the JWT in the `Authorization` header as a `Bearer` token (e.g., `Authorization: Bearer <YOUR_JWT_TOKEN>`).
    5.  **Validation**: The `JwtAuthFilter` intercepts these requests, extracts the token, and validates its signature and expiration. If valid, it extracts the user's identity and roles.

*   **`AuthService.java`**: Responsible for user authentication and generating JWTs upon successful login.
*   **`AuthUtil.java`**: A utility class that handles the creation, parsing, and validation of JWTs. It uses the `jjwt` library.
*   **`JwtAuthFilter.java`**: This is a custom Spring Security filter that executes `OncePerRequestFilter`. It intercepts every incoming HTTP request to:
    *   Check for the `Authorization` header.
    *   Extract the JWT.
    *   Validate the JWT using `AuthUtil`.
    *   If the token is valid, it fetches the `User` details from the `UserRepository` and sets the `Authentication` object in Spring Security's `SecurityContextHolder`. This makes the user's identity and authorities available to subsequent security checks and controllers.

### 7.3. Role-Based Access Control (RBAC): Granular Permissions

RBAC ensures that users can only perform actions and access resources that are explicitly allowed by their assigned roles.

*   **`RoleType` Enum**: Defines the high-level roles in the system: `ADMIN`, `DOCTOR`, `PATIENT`.
*   **`PermissionType` Enum**: Defines granular permissions, such as `PATIENT_READ`, `APPOINTMENT_WRITE`, `USER_MANAGE`.
*   **`RolePermissionMapping.java`**: This static class acts as the central registry for mapping `RoleType` to a set of `PermissionType`s.
    *   For example, an `ADMIN` role might have `USER_MANAGE` and `APPOINTMENT_DELETE` permissions, while a `PATIENT` might only have `APPOINTMENT_READ` and `APPOINTMENT_WRITE`.
    *   When a user's authorities are loaded (e.g., during JWT validation), this mapping is used to grant specific `SimpleGrantedAuthority` objects for each permission and role.
*   **Authorization in `WebSecurityConfig`**: The `WebSecurityConfig` uses these roles and permissions to define access rules for different URL patterns:
    *   `requestMatchers("/admin/**").hasRole(ADMIN.name())`: Only users with the `ADMIN` role can access any endpoint under `/admin`.
    *   `requestMatchers("/doctors/**").hasAnyRole(DOCTOR.name(), ADMIN.name())`: Endpoints under `/doctors` are accessible to both `DOCTOR` and `ADMIN` roles.
    *   `requestMatchers(HttpMethod.DELETE, "/admin/**").hasAnyAuthority(APPOINTMENT_DELETE.name(), USER_MANAGE.name())`: This demonstrates even more granular control, allowing DELETE requests under `/admin` only if the user has `APPOINTMENT_DELETE` or `USER_MANAGE` permission, regardless of their primary role.

### 7.4. OAuth2 Integration: Social Logins

The application supports logging in via external identity providers like Google and GitHub, simplifying the user registration and login process.

*   **Configuration**: The `application.yml` file contains the `client-id` and `client-secret` obtained from registering the application with Google and GitHub.
*   **Flow (Simplified)**:
    1.  A user clicks "Login with Google" (or GitHub) on the client application.
    2.  The client redirects the user to the OAuth2 provider's authorization page.
    3.  The user grants permission to the Hospital Management System.
    4.  The OAuth2 provider redirects the user back to our application with an authorization code.
    5.  Spring Security's OAuth2 client handles the exchange of this code for an access token from the provider.
    6.  The `OAuth2SuccessHandler.java` then takes over:
        *   It extracts user details (like email, name) from the OAuth2 provider's response.
        *   It checks if a user with that provider ID and type already exists in our `app_user` table.
        *   If not, it performs a "just-in-time" registration, creating a new `app_user` and `patient` record, assigning the `PATIENT` role by default.
        *   If the user exists, it logs them in.
        *   Finally, it generates a JWT for the user, allowing them to access the application's APIs.

### 7.5. Low-Level Security Flow (Step-by-Step)

Let's trace a typical request from a client to a secured endpoint:

1.  **Client Request**: A client (e.g., a web browser or mobile app) sends an HTTP request to a secured endpoint, for example, `GET /patients/profile`. This request includes an `Authorization` header with a valid JWT: `Authorization: Bearer <JWT_TOKEN>`.
2.  **`JwtAuthFilter` Interception**: The `JwtAuthFilter` (configured in `WebSecurityConfig`) is the first to process this request.
    *   It extracts the JWT from the `Authorization` header.
    *   It uses `AuthUtil` to validate the token's signature and ensure it hasn't expired.
    *   If validation passes, `AuthUtil` extracts the `username` (e.g., email) and `roles` from the JWT's payload.
3.  **User Details Loading**: The `JwtAuthFilter` then uses the `username` to fetch the full `User` object from the `UserRepository`. This `User` object implements Spring Security's `UserDetails` interface.
4.  **Authority Assignment**: The `User` object's `getAuthorities()` method is called. This method, in conjunction with `RolePermissionMapping`, converts the user's `RoleType`s into a collection of `SimpleGrantedAuthority` objects (e.g., `ROLE_PATIENT`, `PATIENT_READ`, `APPOINTMENT_WRITE`).
5.  **Authentication Object Creation**: A `UsernamePasswordAuthenticationToken` is created using the `User` object (as the principal), `null` for credentials (since JWT is stateless), and the collected `authorities`.
6.  **`SecurityContextHolder` Population**: This `Authentication` object is then placed into the `SecurityContextHolder`. This is crucial because it makes the authenticated user's details and authorities available globally throughout the application for the duration of the request.
7.  **Authorization Check**: As the request proceeds through the Spring Security filter chain, it reaches the authorization manager.
    *   The authorization manager consults the rules defined in `WebSecurityConfig` (e.g., `requestMatchers("/patients/**").hasRole(PATIENT.name())`).
    *   It checks if the `Authentication` object in the `SecurityContextHolder` contains the required role (`ROLE_PATIENT`) or permissions (e.g., `PATIENT_READ`).
8.  **Access Granted/Denied**:
    *   If the user has the necessary role/permissions, the request is allowed to proceed to the `PatientController`.
    *   If not, an `AccessDeniedException` is thrown, which is caught by the custom `AccessDeniedHandler` configured in `WebSecurityConfig`, resulting in a `403 Forbidden` response.
9.  **Controller Execution**: If access is granted, the `PatientController`'s `getPatientProfile()` method executes, retrieves the patient's data, and returns it to the client.

## 8. Error Handling

The application includes a `GlobalExceptionHandler` (`src/main/java/hospitalManagement/error/GlobalExceptionHandler.java`) to provide consistent and informative error responses. This ensures that API consumers receive structured error messages instead of generic server errors. Custom exceptions are mapped to appropriate HTTP status codes and error messages.

## 9. Future Enhancements

*   **Doctor Scheduling**: Allow doctors to manage their availability and schedule.
*   **Medical Records**: Implement a system for storing and retrieving patient medical records.
*   **Billing and Payments**: Integrate a billing system for hospital services.
*   **Notifications**: Add email or in-app notifications for appointments and updates.
*   **Frontend Application**: Develop a user-friendly web or mobile interface to interact with these APIs.
