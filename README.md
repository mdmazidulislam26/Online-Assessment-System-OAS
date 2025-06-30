# OAS Microservices Project
The university's online assessment system inspired this project. <br>
This project is a microservices-based application designed using Spring Boot and related technologies. It consists of three main components:

1. **Service Registry**
2. **Questions Service**
3. **Quiz Service**

---

## Project Features

### General Capabilities:
- **Data Storage**: The application uses a MySQL database to store data.
- **Inter-Service Communication**: Microservices communicate effectively using tools like OpenFeign and Eureka.
- **Service Discovery**: The Eureka Server facilitates dynamic discovery of services.

### Tools Used:
- **Spring Boot**: Simplifies the development of Java applications.
- **Spring Data JPA**: Eases database interactions.
- **MySQL Driver**: Connects the application to MySQL.
- **Spring Web**: Enables the development of web applications.
- **OpenFeign**: Supports declarative REST client development.
- **Eureka Discovery Client**: Provides service discovery.
---

## API Endpoints

### Service Registry
- **URL**: `localhost:8761`
  - The Service Registry provides dynamic service discovery for microservices.

### Questions Service

#### Base URL: `localhost:2020/questions`

- **GET** `/questions`: Retrieve all questions.
- **GET** `/questions/{category}`: Retrieve questions by category.
- **POST** `/questions`: Add a new question.
- **DELETE** `/questions/{id}`: Delete a question by ID.
- **GET** `/questions/generate`: Generate questions for a quiz based on category and number.
- **POST** `/questions/getQuestions`: Fetch questions by IDs.
- **POST** `/questions/getScore`: Calculate the score based on responses.

### Quiz Service

#### Base URL: `localhost:3030/quiz`

- **POST** `/quiz/create`: Create a new quiz.
- **GET** `/quiz/get/{id}`: Retrieve questions for a specific quiz.
- **POST** `/quiz/submit/{id}`: Submit responses and calculate the quiz result.

---

## How to Run

1. Start the **Service Registry** by running `ServiceRegistryApplication.java`.
2. Start the **Questions Service** by running `QuestionsApplication.java`.
3. Start the **Quiz Service** by running `QuizApplication.java`.
4. Access the APIs using the specified base URLs and endpoints.

---

## Dependencies

The project dependencies are managed using Maven. Key dependencies include:
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-cloud-starter-netflix-eureka-server
- spring-cloud-starter-netflix-eureka-client
- spring-cloud-starter-openfeign
- mysql-connector-j
- lombok

---

## 🐳 Dockerized Setup

### Prerequisites
- Docker
- Docker Compose

### Steps to Run

```bash
# Clone the project
git clone https://github.com/mdmazidulislam26/Online-Assessment-System-OAS.git

# Go to project directory
cd Online-Assessment-System-OAS/OAS

# Setup environment variables
cp .env

# Build and run all services
docker compose up --build


---

## Project Structure

```
OAS
├── service-registry
│   ├── src
│   │   ├── main/java/com/example/service_registry
│   │   │   └── ServiceRegistryApplication.java
│   │   └── resources/application.properties
│   └── pom.xml
├── questions
│   ├── src
│   │   ├── main/java/com/mazidul/questions
│   │   │   ├── controller/QuestionController.java
│   │   │   ├── dao/QuestionDao.java
│   │   │   ├── model/{Question, QuestionWrapper, Response}.java
│   │   │   └── service/QuestionService.java
│   │   └── resources/application.properties
│   └── pom.xml
├── quiz
│   ├── src
│   │   ├── main/java/com/mazidul/quiz
│   │   │   ├── controller/QuizController.java
│   │   │   ├── dao/QuizDao.java
│   │   │   ├── feign/QuizInterface.java
│   │   │   ├── model/{Quiz, QuestionWrapper, QuizDto, Response}.java
│   │   │   └── service/QuizService.java
│   │   └── resources/application.properties
│   └── pom.xml
```

---

## Authors
Developed by Mazidul Islam 


