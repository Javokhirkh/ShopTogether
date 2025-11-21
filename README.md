# ShopTogether

ShopTogether is a collaborative shopping-list application built with **Java Spring Boot**. It allows families to create shared shopping lists, invite members, and collaborate in real-time. Each family can manage their items together, categorize groceries, track bought items, and receive updates instantly.

---

## Features

- Create, update, and delete families and shopping lists
- Invite users to families (accept, decline, resend, cancel)
- Real-time updates via WebSocket
- Item categories and bought/unbought tracking
- Basic security and input validation
- Health and metrics endpoints via Spring Actuator

---

## Technologies Used

- **Java 17**
- **Spring Boot 3.x** (Web, WebSocket, Data JPA, Security, Validation, Actuator)
- **Spring Data JPA** (Hibernate)
- **PostgreSQL** (database)
- **MapStruct** (DTO mapping)
- **Lombok** (boilerplate reduction)
- **Maven** (build tool)
- **JUnit + Spring Test** (unit and integration tests)
- **JaCoCo** (code coverage)

---
## Ready-to-Run Docker Setup

This setup allows you to start ShopTogether with PostgreSQL quickly using Docker Compose.

---

### 1. Create a `.env` file

Create a `.env` file in the **project root** (next to `docker-compose.yml`) by copying `.env.example`:

```bash
cp .env.example .env

