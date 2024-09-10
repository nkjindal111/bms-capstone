# bms-capstone

## Introduction

The `bms-capstone` application is a ticket booking system for movies, events, and shows. It allows users to browse available shows, select seats, and book tickets. This project is built using Spring Boot and Java 17 and is containerized using Docker.

## Features

- Browse available movies, events, and shows.
- View details of shows, including timing and available seats.
- Book tickets and select seats.
- User authentication and authorization.
- Admin panel to manage shows and bookings.

## Technologies Used

- **Backend**: Spring Boot, Java 17
- **Database**: PostgreSQL
- **Containerization**: Docker
- **Build Tools**: Maven

## Getting Started

### Prerequisites

- Java 17
- Maven 3.8+
- Docker
- PostgreSQL

### Setup Instructions

#### 1. Clone the Repository

```bash
git clone https://github.com/nkjindal111/bms-capstone.git
cd bms-capstone
```

#### 2. Configure the Database

```bash
- Update the database configuration in src/main/resources/application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/bms
spring.datasource.username=postgres
spring.datasource.password=password
```
#### 3. Build the Application

```bash
mvn clean package -DskipTests
```

#### 4. Run the Application

```bash
docker build -t bms-capstone .
docker run -p 8080:8080 bms-capstone
```
#### Running Tests

```bash
mvn test
```

### Usage

Once the application is running, you can access it at http://localhost:8080. Use the provided endpoints to interact with the system.

### Contributing

```
1. Fork the repository.
2. Create a new branch (git checkout -b feature-branch).
3. Make your changes.
4 .Commit your changes (git commit -m 'Add some feature').
5. Push to the branch (git push origin feature-branch).
6 .Open a pull request.
```