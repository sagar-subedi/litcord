
# LitCord (Discord Clone) Application

## Overview

This project is a simplified clone of the popular Discord platform, featuring real-time messaging and user interaction. The backend is powered by **Spring Boot**, and the frontend is built with **Angular**.

### Features:
- User authentication (login, registration)
- Real-time messaging with WebSockets
- Private and group channels
- User profile management
- Role-based permissions

---

## Table of Contents
1. [Tech Stack](#tech-stack)
2. [Architecture](#architecture)
3. [Setup](#setup)
4. [Running the Application](#running-the-application)
5. [API Endpoints](#api-endpoints)
6. [Frontend Overview](#frontend-overview)
7. [Contributing](#contributing)
8. [License](#license)

---

## Tech Stack

### Backend:
- **Java 17** or later
- **Spring Boot** (REST, WebSocket, Security, JPA)
- **MySQL/PostgreSQL** (or any other relational database)
- **WebSocket** for real-time communication

### Frontend:
- **Angular** 14+ (with TypeScript)
- **Bootstrap** or **Angular Material** for UI design
- **Socket.io** for WebSocket-based messaging

### Other Tools:
- **Maven/Gradle** for build management
- **Docker** for containerization (optional)
- **JWT** for authentication and session management
- **Redis** for caching (optional)

---

## Architecture

- **Backend**: The backend is designed using a microservices architecture with RESTful services for user authentication, messaging, and channel management. Real-time messaging is implemented using WebSockets.
- **Frontend**: The Angular frontend interacts with the backend using REST APIs and WebSockets for real-time communication.

---

## Setup

### Prerequisites:
- **Java 17** or later
- **Node.js** (with npm)
- **Angular CLI**
- **MySQL/PostgreSQL** (or any database)
- **Maven** (or Gradle)

### Backend Setup:
1. Clone the repository.
   ```bash
   git clone https://github.com/yourusername/discord-clone.git
   cd discord-clone/backend
   ```
2. Set up the database:
    - Create a database in MySQL/PostgreSQL named `discord_clone`.
    - Configure the `application.properties` (or `application.yml`) file with your database details.

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/discord_clone
   spring.datasource.username=yourusername
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Build and run the backend.
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

### Frontend Setup:
1. Navigate to the `frontend` directory.
   ```bash
   cd ../frontend
   ```
2. Install the dependencies.
   ```bash
   npm install
   ```
3. Serve the Angular application.
   ```bash
   ng serve
   ```

---

## Running the Application

### Backend:
- Once the backend is running, the REST APIs will be available at `http://localhost:8080/api`.
- WebSocket endpoints will be available at `ws://localhost:8080/ws`.

### Frontend:
- The Angular frontend can be accessed at `http://localhost:4200`.

---

## API Endpoints

### Authentication:
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login an existing user

### Messaging:
- `GET /api/channels/{channelId}/messages` - Retrieve messages from a channel
- `POST /api/channels/{channelId}/messages` - Send a message in a channel

### Channels:
- `GET /api/channels` - List all available channels
- `POST /api/channels` - Create a new channel

### Users:
- `GET /api/users/{userId}` - Retrieve user profile
- `PUT /api/users/{userId}` - Update user profile

### WebSockets:
- **Endpoint**: `ws://localhost:8080/ws`
- **Events**:
    - `message` - Send/receive messages
    - `channel_join` - Notify users when someone joins a channel

---

## Frontend Overview

The frontend allows users to:
- **Sign Up and Log In** using JWT authentication.
- **Join/Create Channels**: Users can join existing channels or create their own.
- **Real-time Messaging**: Users can send and receive messages in real time.
- **Manage User Profiles**: Users can update their profiles and view other users.

---

## Contributing

We welcome contributions! If you'd like to contribute, please fork the repository and submit a pull request.

Steps:
1. Fork the repo
2. Create your feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -am 'Add YourFeature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Create a new Pull Request

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

### Contact

If you have any questions, feel free to reach out to the project maintainer at [your-email@example.com].

---

Let me know if you'd like any changes or additional details!