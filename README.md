# 💸 PicPay Clone API

A REST API inspired by the basic functionality of PicPay, built with **Kotlin + Spring Boot** as a portfolio project.

## 🚀 Tech Stack

- **Kotlin**
- **Spring Boot**
- **Spring Security + JWT**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **SpringDoc OpenAPI (Swagger)**
- **JUnit 5 + Mockito**
- **Gradle (Kotlin DSL)**

## 📐 Architecture

The project follows **Clean Architecture** principles, separating responsibilities into layers.

## 🔐 Authentication

The API uses **JWT Bearer Token**. Flow:

1. Create a user at `POST /users`
2. Login at `POST /auth/login`
3. Use the returned token in the header `Authorization: Bearer <token>`

## 📦 Endpoints

### Auth
| Method | Route | Description | Auth |
|--------|-------|-------------|------|
| POST | `/auth/login` | Login and JWT token generation | ❌ |

### Users
| Method | Route | Description | Auth |
|--------|-------|-------------|------|
| POST | `/users` | Create user | ❌ |
| GET | `/users` | List all users | ✅ |
| GET | `/users/{id}` | Get user by ID | ✅ |
| PUT | `/users/{id}` | Update user | ✅ |
| DELETE | `/users/{id}` | Delete user | ✅ |

### Transactions
| Method | Route | Description | Auth |
|--------|-------|-------------|------|
| POST | `/transactions` | Perform transfer | ✅ |
| GET | `/transactions` | List all transactions | ✅ |
| GET | `/transactions/{id}` | Get transaction by ID | ✅ |
| GET | `/transactions/sender/{id}` | Transactions by sender | ✅ |
| GET | `/transactions/receiver/{id}` | Transactions by receiver | ✅ |

## 💡 Business Rules

- Only `COMMON` users can send transfers
- `MERCHANT` users can only receive transfers
- The sender must have sufficient balance
- Self-transfers are not allowed
- Passwords are stored using **BCrypt**
- Transfers use **pessimistic locking** to prevent race conditions

## 🧪 Tests

Unit test coverage for the service layer:

- `UserServiceTest` — creation, retrieval, deletion and validations
- `TransactionServiceTest` — transfers and all business rules
- `JwtServiceTest` — token generation and validation

## 👨‍💻 Author

**João** — [@joaodddev](https://github.com/joaodddev)