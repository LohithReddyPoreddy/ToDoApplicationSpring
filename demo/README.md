# Task Tracker API

The **Task Tracker API** is a RESTful service for managing tasks. It supports operations such as creating, updating, deleting, and retrieving tasks based on various filters such as priority, deadline, creation date, status, and task name.

## Features
- Create a new task
- Retrieve a task by ID
- List all tasks
- Update an existing task
- Delete a task by ID
- Filter tasks by:
  - Priority
  - Deadline
  - Creation date
  - Completion status
  - Task name

## Technologies Used
- **Spring Boot**: Backend framework
- **Swagger/OpenAPI**: API documentation
- **H2 Database**: In-memory database for testing
- **PostgreSQL**: Primary database for production
- **Java**: Programming language

## Getting Started

### Prerequisites
- Java 17 or later
- Maven 3.6 or later
- PostgreSQL (if running in production)

### Setup
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-folder>
   ```
2. Update the `application.properties` file for your database configuration (if using PostgreSQL):
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   spring.jpa.hibernate.ddl-auto=update
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Testing with H2
To use the in-memory H2 database for testing, ensure the following settings in `application.properties`:
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create
```

## API Endpoints

| HTTP Method | Endpoint                  | Description                                     | Request Body | Response Status |
|-------------|---------------------------|-------------------------------------------------|--------------|-----------------|
| POST        | `/api/tasks`              | Create a new task                               | `Task` JSON  | `201 Created`   |
| GET         | `/api/tasks/{id}`         | Retrieve a task by ID                          | N/A          | `200 OK`/`404 Not Found` |
| GET         | `/api/tasks`              | Retrieve all tasks                             | N/A          | `200 OK`/`404 Not Found` |
| PUT         | `/api/tasks/{id}`         | Update a task by ID                            | `Task` JSON  | `200 OK`        |
| DELETE      | `/api/tasks/{id}`         | Delete a task by ID                            | N/A          | `204 No Content` |
| GET         | `/api/tasks/priority/{priority}` | Retrieve tasks by priority                    | N/A          | `200 OK`/`204 No Content` |
| GET         | `/api/tasks/deadline/{date}` | Retrieve tasks by deadline (YYYY-MM-DD)       | N/A          | `200 OK`/`400 Bad Request` |
| GET         | `/api/tasks/creationdate/{date}` | Retrieve tasks by creation date (YYYY-MM-DD) | N/A          | `200 OK`/`400 Bad Request` |
| GET         | `/api/tasks/status/{status}` | Retrieve tasks by completion status           | N/A          | `200 OK`/`400 Bad Request` |
| GET         | `/api/tasks/taskName/{name}` | Retrieve a task by name                       | N/A          | `200 OK`/`404 Not Found` |

## Sample Task JSON
Here’s an example of a task object for POST or PUT requests:
```json
{
  "taskName": "Complete API documentation",
  "priority": "HIGH",
  "deadline": "2024-12-31",
  "creationDate": "2024-12-14",
  "completed": false
}
```

## Swagger/OpenAPI Documentation
The API is documented using Swagger and can be accessed at:
```
http://localhost:8080/swagger-ui.html
```

## Future Enhancements
- Add user authentication and authorization
- Pagination and sorting for task listings
- Support for multiple users managing their own tasks
- Integration with frontend UI

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.

