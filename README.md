# To-Do Application

A Spring Boot-based **To-Do Application** that provides a RESTful API to manage tasks with functionalities like priorities, deadlines, and statuses. This application is integrated with PostgreSQL for data persistence and Swagger for easy API documentation and testing.

---

## Features

- Perform CRUD operations for **Tasks**.
- Filter tasks by:
  - **Priority**
  - **Deadline**
  - **Creation Date**
  - **Completion Status**
- **Swagger UI integration** for interactive API documentation.
- Comprehensive service and controller layer tests for ensuring reliability.

---

## Prerequisites

Ensure you have the following installed before proceeding:

1. **Java 21** (or a compatible version from the Java SDK).
2. **PostgreSQL** installed and running.
3. **Maven** for build and dependency management.

---

## Installation

Follow these steps to set up the project locally:

1. Clone this repository:
   ```bash
   git clone <repository_url>
   cd demo
   ```

2. Configure the **application.properties** file with your PostgreSQL database connection details:
   - Navigate to `src/main/resources/application.properties`.
   - Update the following fields:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/<database_name>
     spring.datasource.username=<your_username>
     spring.datasource.password=<your_password>
     ```
     Replace `<database_name>`, `<your_username>`, and `<your_password>` with your database details.

3. Start the PostgreSQL service.

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

---

## Accessing the Application

### API Endpoints

The application exposes the following REST API endpoints to manage tasks:

| HTTP Method | Endpoint                      | Description                                    |
|-------------|-------------------------------|------------------------------------------------|
| GET         | `/api/tasks`                 | Get a list of all tasks.                      |
| GET         | `/api/tasks/{id}`            | Get details of a specific task by `taskId`.   |
| GET         | `/api/tasks/status/{status}` | Get tasks based on their completion status.   |
| GET         | `/api/tasks/deadline/{date}` | Get tasks that match a specific deadline.     |
| GET         | `/api/tasks/creationdate/{date}` | Get tasks by their creation date.          |
| GET         | `/api/tasks/priority/{priority}` | Filter tasks by priority (HIGH, MEDIUM, LOW). |
| GET         | `/api/tasks/taskName/{name}` | Retrieve a task based on its name.            |
| POST        | `/api/tasks`                 | Create a new task.                            |
| PUT         | `/api/tasks/{id}`            | Update an existing task by `taskId`.          |
| DELETE      | `/api/tasks/{id}`            | Delete a task by `taskId`.                    |

### Accessing Swagger Documentation

Swagger (powered by SpringDoc OpenAPI) is integrated to provide an interactive documentation interface for all APIs.

1. Start the application using:
   ```bash
   mvn spring-boot:run
   ```

2. Open your browser and navigate to:
   ```
   http://localhost:8080/swagger-ui.html
   ```
   Or:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

3. Explore and test the available endpoints directly from the Swagger interface.

---

## Running Tests

This project includes **unit tests** for the service layer and **integration tests** for controllers to ensure application reliability.

1. To run all tests:
   ```bash
   mvn test
   ```

2. Key test files:
   - `TaskServiceTest`: Tests the functionality of the service layer.
   - `TaskControllerTest`: Validates integration and correct responses from all controller endpoints.

---

## Technologies Used

- **Spring Boot 3.4.0**: A modern framework for creating web applications in Java.
- **Spring Data JPA**: For interacting with the PostgreSQL database.
- **Swagger (SpringDoc OpenAPI)**: For in-app API documentation and testing.
- **Lombok**: Reduces boilerplate code like getters, setters, etc.
- **PostgreSQL**: Relational database storage.
- **JUnit 5** and **Mockito**: Used for writing and running tests.

---

## Project Structure

The project's structure is as follows:

```bash
src
├── main
│   ├── java
│   │   ├── com.example.demo
│   │   │   ├── controller   # Controller layer to handle API endpoints
│   │   │   ├── entity       # Data models (Task, TaskPriority Enum)
│   │   │   ├── repository   # Spring Data JPA repository interfaces
│   │   │   ├── service      # Business/service logic
│   │   │   ├── configuration # Swagger OpenAPI configuration
│   ├── resources
│       └── application.properties # Application configuration properties
├── test
│   └── java
│       └── com.example.demo       # Unit and integration tests for services/controllers
```

---

## Endpoints and Functions Overview

Below is a quick overview of the features provided by the API:

1. **Task Management Endpoints**:
   - Create, update, and delete tasks.
   - Retrieve tasks by specific criteria like:
     - Priority (HIGH, MEDIUM, LOW).
     - Status (Completed or Not).
     - Deadlines for tracking schedule adherence.
     - Creation date for filtering recent or old entries.

2. **Error Handling**:
   - Returns appropriate `HTTP Status Codes` for various operations:
     - **200 OK**: For successful retrieval or updates.
     - **201 CREATED**: When a new task is successfully created.
     - **204 NO CONTENT**: When a task is successfully deleted.
     - **404 NOT FOUND**: When no task matches the requested criteria.

3. **Swagger Documentation**:
   - Detailed documentation for every API endpoint. Includes:
     - Request body descriptions.
     - Response examples.
     - HTTP status codes with descriptions.

---

## Setting Up Swagger UI Locally

Follow these steps to explore and test APIs using Swagger:

1. Ensure the application is running by executing:
   ```bash
   mvn spring-boot:run
   ```

2. Open your browser and visit:
   ```
   http://localhost:8080/swagger-ui.html
   ```

3. You will find an interactive API interface for testing all routes. Swagger will enable you to send requests and view responses effortlessly.

---

## Future Enhancements

The following features are proposed for future updates:

1. **User Authentication**:
   - Secure APIs with authentication (e.g., OAuth2, JWT).

2. **Task Assignment**:
   - Add support for assigning tasks to specific users.

3. **Deployment**:
   - Deploy the application to platforms like AWS, Heroku, or GCP.

---

**Happy Coding!**
