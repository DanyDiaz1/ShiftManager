# ShiftManager API

ShiftManager is a backend REST API built with **Java and Spring Boot** for managing appointments (shifts) in a hair salon.
The project is designed as a **first professional backend project**, following good practices such as layered architecture, DTOs, validations, security, testing, and Dockerization.

---

## 🚀 Features

* User authentication with **JWT**
* Appointment (shift) management
* Business rules validation
* Global exception handling
* DTOs for clean API responses
* Unit tests with JUnit & Mockito
* Dockerized environment (Spring Boot + MySQL)
* Environment-based configuration using `.env`

---

## 🛠 Tech Stack

* **Java 17**
* **Spring Boot**

  * Spring Web
  * Spring Data JPA
  * Spring Security
* **MySQL 8**
* **JWT (JSON Web Tokens)**
* **JUnit 5 & Mockito**
* **Docker & Docker Compose**
* **Maven**

---

## 📐 Architecture

The project follows a layered architecture:

* **controller** – REST endpoints
* **service** – business logic and validations
* **repository** – data access (JPA)
* **dto** – request/response objects
* **mapper** – entity ↔ DTO conversions
* **exception** – custom exceptions and global handler
* **security** – JWT authentication and filters

This separation makes the code easier to test, maintain, and scale.

---

## 🧠 Business Rules

* Appointments cannot be created:

  * In past dates
  * On the current day with a time earlier than the current time
  * Outside business hours
  * If the time slot is already taken
* Validations are handled:

  * At controller level (request validation)
  * At service level (business rules)

---

## 🔐 Security

* Authentication using **JWT**
* Stateless sessions
* Protected endpoints
* Custom JWT filter integrated into Spring Security

---

## 🧪 Testing

The project includes **unit tests for the service layer** using:

* JUnit 5
* Mockito

Covered scenarios include:

* Successful appointment creation
* Invalid dates and times
* Service not found
* Conflicting appointments
* Business rule violations

Builders are intentionally avoided in tests to keep them explicit and easy to read.

---

## 🐳 Docker Setup

The application is fully dockerized using **Docker Compose**.

### Services

* **app** – Spring Boot application
* **mysql** – MySQL 8 database

The application communicates with MySQL through Docker's internal network.

---

## ⚙️ Environment Variables

Configuration is externalized using a `.env` file.

Example `.env`:

```
MYSQL_DATABASE=shift_manager
MYSQL_ROOT_PASSWORD=root
MYSQL_USER=shift_user
MYSQL_PASSWORD=shift_pass

SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/shift_manager
SPRING_DATASOURCE_USERNAME=shift_user
SPRING_DATASOURCE_PASSWORD=shift_pass
```

⚠️ The `.env` file is **not committed** to version control.

---

## ▶️ Running the Project

### Prerequisites

* Docker
* Docker Compose

### Build the application

```
./mvnw clean package -DskipTests
```

### Start containers

```
docker-compose up --build
```

The API will be available at:

```
http://localhost:8080
```

---

## 📄 API Usage

* Register a user
* Login to obtain a JWT
* Use the JWT in the `Authorization` header:

```
Authorization: Bearer <token>
```

* Access protected endpoints

---

## 🌍 Profiles

* **local** – default profile
* **docker** – used when running inside Docker

The active profile is controlled with:

```
SPRING_PROFILES_ACTIVE=docker
```

---

## 📌 Future Improvements

* Controller integration tests (MockMvc)
* Swagger / OpenAPI documentation
* Pagination and filtering
* Multi-business (multi-tenant) support
* CI pipeline

---

## 👨‍💻 Author

Developed as a learning and portfolio project to demonstrate backend skills with Java and Spring Boot.

---

## 📄 License

This project is for educational purposes.

