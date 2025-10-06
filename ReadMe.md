````
# 🕒 TickMate – Task Management App

**TickMate** is a lightweight task management web application built with **Spring Boot (backend)** and **React (frontend)**.
It allows users to **create, update, view, and delete tasks** with priorities, due dates, and statuses — following clean code and production-grade best practices.

---

## 🚀 Features

✅ Create new tasks with title, description, due date, and priority.
✅ Update or delete existing tasks.
✅ Mark tasks as **Completed** — completed tasks are automatically hidden from the UI.
✅ View only the **6 most recent active (non-completed)** tasks in the UI.
✅ Paginated and sorted task list via REST API.
✅ Backend follows **Spring Boot best practices** with **unit & integration tests**.
✅ Fully containerized using **Docker Compose**.

---

## 🧩 Tech Stack

| Layer              | Technology |
|--------------------| -------------------------------------------------
| **Frontend**       | React + Vite + TypeScript + TailwindCSS / ShadCN |
| **Backend**        | Spring Boot (Java 17+)  |
| **Database**       | PostgreSQL 18 (Alpine)  |
| **Build & Deploy** | Docker + Docker Compose |
| **API**            | RESTful Endpoints       |

---

## 🧠 Database Schema

```sql
CREATE TABLE IF NOT EXISTS tasks (
    id              UUID PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    description     TEXT,
    status          VARCHAR(50) DEFAULT 'PENDING' CHECK (status IN ('PENDING','IN_PROGRESS','COMPLETED','ARCHIVED')),
    priority        VARCHAR(20) DEFAULT 'MEDIUM' CHECK (priority IN ('LOW','MEDIUM','HIGH')),
    due_date        TIMESTAMP(3) NULL,
    completed_at    TIMESTAMP(3) NULL,
    created_at      TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
);
````

---

## ⚙️ REST API Endpoints

| Method     | Endpoint          | Description                               |
| ---------- | ----------------- | ----------------------------------------- |
| **POST**   | `/api/tasks`      | Create a new task                         |
| **GET**    | `/api/tasks`      | Get all tasks (with pagination & sorting) |
| **GET**    | `/api/tasks/{id}` | Get task by ID                            |
| **PUT**    | `/api/tasks/{id}` | Update task details                       |
| **DELETE** | `/api/tasks/{id}` | Delete task                               |

---

## 🐳 Running with Docker

### 1️⃣ Copy Environment Example

```bash
cp .env.example .env
```

### 2️⃣ Start Services

```bash
docker compose up -d --build
```

This will start:

- `db` → PostgreSQL 18 (Alpine)
- `backend` → Spring Boot app on port **8080**
- `frontend` → React app on port **5173**

### 3️⃣ Access the App

- Frontend → [http://localhost:5173](http://localhost:5173)
- Backend API → [http://localhost:8080/api/tasks](http://localhost:8080/api/tasks)

### 4️⃣ Stop Containers

```bash
docker compose down
```

---

## 🧭 Architecture Overview

```
+-------------+           +---------------+           +--------------+
|   Frontend  |  --->     |   API Server  |  --->     |  PostgreSQL  |
| (React/Vite)|           | (Spring Boot) |           |   Database   |
+-------------+           +---------------+           +--------------+
```

- **Frontend:** Fetches tasks via REST API.
- **Backend:** Manages task lifecycle & persistence.
- **Database:** Stores task records.

---

## 🧹 Code Quality & Best Practices

- ✅ Layered architecture (Controller → Service → Repository → Entity)
- ✅ DTOs for API request/response separation
- ✅ Enum-based validation for `status` and `priority`
- ✅ Error handling using `@ControllerAdvice`
- ✅ JPA auditing for timestamps
- ✅ Docker-based environment parity
- ✅ Proper test coverage for repository and service layers

---

## 🧑‍💻 Author

**Mohammed Insaf Nilam**
💻 Full Stack Developer | 🧠 Builder of scalable microservices
📧 [LinkedIn](https://linkedin.com/in/insafnilam) • [GitHub](https://github.com/insafnilam)

---

## 🪪 License

This project is licensed under the **MIT License**.

---

### 🧡 Summary

TickMate is a clean, containerized, and testable demo task management app built using modern best practices.
It’s designed as a reference for building **Spring Boot + React** projects with **Docker, PostgreSQL**, and **CI-ready test coverage**.

```

```
