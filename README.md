**Overview**
This is a Spring Boot-based backend application for managing tasks within projects. It supports user registration, project creation, member management, task assignment, and status tracking with proper access control and business rules.

**Tech Stack**
Java 17+
Spring Boot
Spring Data JPA (Hibernate)
PostgreSQL
Maven
Postman (API testing)

**Setup Instructions**
Clone the repository:

git clone https://github.com/your-username/task-management-backend.git
Open the project in IntelliJ

Create a PostgreSQL database:

taskdb

Update application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update

Run the application:

TaskmanagementApplication.java

**Features Implemented**
**User Management**
Register user
Basic login functionality
Password masking in API response (********)
**Project Management**
Create project
Add members to project
Remove members from project
**Task Management**
Create task
Assign task to project member
Update task status
Restrict updates:
Only assigned user or admin can update
Prevent invalid status transitions (e.g., DONE → IN_PROGRESS)
**Comments Feature (Addon)**
Add comment to task
View all comments for a task
**Pagination (Addon)**

Fetch tasks with pagination:

GET /tasks/paged?page=0&size=5
**Business Rules**
- Only project members can access tasks
- Only assigned user or admin can update tasks
- Assigned user must belong to the project
- Task status transitions must be valid
- Sensitive data like passwords are masked
**API Endpoints**
-Auth
POST /auth/register
POST /auth/login
-Projects
POST /projects
POST /projects/{id}/members
DELETE /projects/{id}/members/{userId}
-Tasks
POST /tasks
PUT /tasks/{id}
GET /tasks?projectId=1
GET /tasks/paged?page=0&size=5
-Comments
POST /tasks/{id}/comments
GET /tasks/{id}/comments

**Add-ons Implemented**
Comments on tasks
Pagination for tasks

**Security Note**

Currently, user-based validation is implemented using request data. JWT-based authentication can be added to enhance security and remove the need for passing user IDs in requests.

**Project Structure**
src/ ├── controller ├── service ├── repository ├── entity ├── dto ├── config

**Postman Collection**
A Postman collection is included in this repository for testing the APIs.

Import the file:
'task-management-postman-collection.json'
into Postman to test all endpoints.

**Author**
**Chaturya Konakati**
