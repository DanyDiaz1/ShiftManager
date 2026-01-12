📄 ShiftManager – Backend API
🧾 Description

ShiftManager is a REST API developed with Java and Spring Boot for managing appointments in a hair salon.
It allows managing users, services, and appointments while enforcing business rules such as time availability and date validations.

The project is designed with a scalable architecture, prepared to support multiple businesses in the future.

🛠️ Technologies

Java 17

Spring Boot

Spring Security + JWT

Spring Data JPA

MySQL

Hibernate

JUnit 5

Mockito

Maven

🏗️ Architecture

The project follows a layered architecture:

controller → service → repository → database


Key design decisions:

Thin controllers

Business logic handled in services

DTOs for request and response

Dedicated mappers

Centralized exception handling

Unit tests using Mockito

🔐 Security

JWT-based authentication

Protected endpoints with role-based access

Proper HTTP status codes (401, 403, 400)

📦 Project Structure
com.danidev.ShiftManager
 ├─ appointment
 │   ├─ controller
 │   ├─ dto
 │   ├─ entity
 │   ├─ mapper
 │   ├─ repository
 │   └─ service
 ├─ auth
 ├─ user
 ├─ service
 ├─ exception
 └─ config

🚀 How to Run the Project
1️⃣ Clone the repository
git clone https://github.com/your-username/shift-manager.git
cd shift-manager

2️⃣ Database configuration

Create a MySQL database:

CREATE DATABASE shift_manager;


Configure application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/shift_manager
spring.datasource.username=root
spring.datasource.password=your_password

3️⃣ Run the application
mvn spring-boot:run


The API will be available at:

http://localhost:8080

🧪 Run Tests
mvn test


The tests focus on the service layer, using JUnit 5 and Mockito to validate business rules.

📡 Main Endpoints (examples)
🔑 Authentication

POST /auth/register

POST /auth/login

📅 Appointments

POST /appointments

GET /appointments/daily?date=2026-01-10

📌 Project Status

✔ JWT Authentication
✔ Appointment CRUD
✔ Business validations
✔ DTOs and Mappers
✔ Unit tests
✔ Ready for future multi-business support

🧠 Future Improvements

Multi-business support

Advanced roles (admin / employee)

Integration tests

Docker support

Frontend application

👨‍💻 Author

Dani Dev
Personal project focused on practicing backend development with Java and Spring Boot.
