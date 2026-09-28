# Postcode Service
This is Spring Boot REST service that calculates the straight-line distance between two UK postcodes using their latitude and longitude coordinates.

The service uses PostgreSQL to store UK postcode data and provides an additional REST endpoint for updating postcode coordinates.

## Features
* Calculate straight-line distance between two UK postcodes
* Return postcode, latitude and longitude for both locations
* Haversine distance calculation
* PostgreSQL persistence
* REST endpoint for updating postcode coordinates
* Structured JSON logging
* Basic username/password authentication
* Unit tests for distance calculation
* Invalid postcode handling with HTTP 404

## Technology Stack
* Java 21
* Spring Boot 4.1.1
* Spring Web MVC
* Spring Data JPA
* Spring Security
* PostgreSQL 18
* Maven
* JUnit 5

## Prerequisites
Make sure the following are installed:
* Java 21
* Maven
* PostgreSQL 18

## Database Setup
Create the PostgreSQL database:

```bash
createdb postcode_service
```
Update `application.properties` if your PostgreSQL username or connection details are different.

## Postcode Data
Download the UK postcode data from FreeMapTools:

https://www.freemaptools.com/download-uk-postcode-lat-lng.htm

Place the downloaded CSV at:
- seed/ukpostcodes.csv

### Import the postcode data
Start PostgreSQL & import the CSV:

```bash
psql -d postcode_service -c "\copy postcodes(id, postcode, latitude, longitude) FROM 'seed/ukpostcodes.csv' WITH (FORMAT csv, HEADER true)"
```

Verify the data:

```bash
psql -d postcode_service -c "SELECT COUNT(*) FROM postcodes;"
```

# Setup
`application.properties` contains the database and application configuration.

### Authentication Configuration
The API uses HTTP Basic Authentication.

Set the username and password as environment variables before starting the application:
Example:
```bash
export APP_USERNAME=admin
export APP_PASSWORD=password12345
```

These values are read by Spring Boot through:
```properties
spring.security.user.name=${APP_USERNAME}
spring.security.user.password=${APP_PASSWORD}
```

Start the application:
```bash
mvn spring-boot:run
```

## Design

The application follows a simple layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

Responsibilities:

* `PostcodeController` — handles HTTP requests and responses
* `PostcodeService` — handles postcode lookup and update logic
* `PostcodeRepository` — handles database access
* `DistanceCalculator` — calculates geographic distance
* `Postcode` — JPA database entity
* `Location` — API/domain representation of a postcode location
* `DistanceResponse` — API response object
* `SecurityConfig` — configures HTTP Basic Authentication
* `GlobalExceptionHandler` — converts application exceptions into HTTP responses

The application requires PostgreSQL to be running and the postcode data to be imported before postcode lookups can be performed.
