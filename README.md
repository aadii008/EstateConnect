# EstateConnect

EstateConnect is a hybrid web application designed to streamline property management and estate-related services. It integrates a dynamic Angular frontend with a robust Spring Boot backend, ensuring secure and scalable operations.

---

## 📦 Application Modules

### Admin Modules
- Register
- Login
- Home
- Insights
- Manage Materials
- View Requests
- Manage Feedback

### User Modules
- Register
- Login
- View Property
- Submit Feedback
- View My Requests

### Role-Specific Navigation
- AdminNav and UserNav components
- Auth Guard and HTTP Interceptor for secure routing

---

## 🛠 Technologies Used

### Frontend
- Angular 10+
- HTML5
- CSS3

### Backend
- Java with Spring Boot
- MySQL

### Authentication
- JWT (JSON Web Token) for secure, role-based access control

---

## 🔐 Authorization & Security

- **JWT Login**: Token-based authentication with role claims
- **Role-Based Routing**: Angular Auth Guards using `canActivate`
- **Session Security**: Logout clears token and redirects to login; unauthorized access is blocked on both client and server sides

---

## ✅ Utilities & Enhancements

- **Client-Side Validation**: HTML5 + Regex for validating email, password, and phone
- **Backend Layer**: Spring Boot REST APIs with JWT and MySQL
- **Custom Error Pages**: User-friendly 404 and 500 pages

---

## 🧠 Architecture

- **Frontend Layer**: Angular handles UI/UX and API communication
- **Backend Layer**: Spring Boot REST APIs with JWT and MySQL
- **Communication Protocol**: HTTP/HTTPS with JSON payloads

---

## 📁 Folder Structure

- `angularapp/` – Angular frontend
- `springapp/` – Spring Boot backend

---

## 🖥️ Hardware Requirements

- Standard development machine capable of running Angular and Spring Boot applications

---

## 🚀 Getting Started

### Frontend Setup

```bash
cd angularapp
npm install
npm start
```

### Backend Setup


cd springapp
mvn spring-boot:run
