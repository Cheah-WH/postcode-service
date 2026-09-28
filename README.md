# Postcode Service
A Spring Boot REST service that calculates the straight-line distance between two UK postcodes using their latitude and longitude coordinates.

The service uses PostgreSQL to store UK postcode data and provides an additional REST endpoint for updating postcode coordinates.

## Features Included
* Calculate the straight-line distance between two UK postcodes
* Return postcode, latitude, and longitude for both locations
* PostgreSQL persistence
* REST endpoint for updating postcode coordinates
* HTTP Basic Authentication
* Structured JSON logging
* Global exception handling
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
* PostgreSQL 18

Maven does not need to be installed separately because the project includes the Maven Wrapper.

## Database Setup
Create the PostgreSQL database:
```bash
createdb postcode_service
```

Replace with your PostgreSQL username and connection configuration in `application.properties` accordingly.

## Postcode Data
The postcode dataset is sourced from FreeMapTools:

https://www.freemaptools.com/download-uk-postcode-lat-lng.htm

The CSV file is intentionally excluded from this repository because of its size.

Download the postcode dataset and place it at:
```text
seed/ukpostcodes.csv
```

### Import the Postcode Data
Make sure PostgreSQL is running, then import the CSV:

```bash
psql -d postcode_service -c "\copy postcodes(id, postcode, latitude, longitude) FROM 'seed/ukpostcodes.csv' WITH (FORMAT csv, HEADER true)"
```

Verify the imported data:

```bash
psql -d postcode_service -c "SELECT COUNT(*) FROM postcodes;"
```

## Configuration
The application configuration is located at:

```text
src/main/resources/application.properties
```

The application uses environment variables for HTTP Basic Authentication credentials.

Set them before starting the application:

```bash
export APP_USERNAME=admin
export APP_PASSWORD=password12345
```

The values are read by Spring Boot through:

```properties
spring.security.user.name=${APP_USERNAME}
spring.security.user.password=${APP_PASSWORD}
```

No credentials are stored in the repository.

## Running the Application
Start the application using the Maven Wrapper:

```bash
./mvnw spring-boot:run
```

The service will start on:

```text
http://localhost:8080
```

## API Endpoints
### Calculate Distance

```http
GET /distance?from={postcode}&to={postcode}
```

Example:

```bash
curl -u admin:password12345 \
  "http://localhost:8080/distance?from=SW1A1AA&to=EC1A1BB"
```

Example response:

```json
{
  "from": {
    "postcode": "SW1A 1AA",
    "latitude": 51.501009,
    "longitude": -0.141588
  },
  "to": {
    "postcode": "EC1A 1BB",
    "latitude": 51.519276,
    "longitude": -0.101342
  },
  "distance": 3.59216683731761,
  "unit": "km"
}
```

### Update Postcode Coordinates

```http
PUT /postcodes/{postcode}
```

Example:

```bash
curl -u admin:password12345 \
  -X PUT \
  -H "Content-Type: application/json" \
  -d '{"latitude":51.501009,"longitude":-0.141588}' \
  "http://localhost:8080/postcodes/SW1A1AA"
```

## Error Handling
The service returns appropriate HTTP status codes for invalid requests.
If a postcode cannot be found, the service returns:

```http
HTTP 404 Not Found
```

Application exceptions are handled centrally through `GlobalExceptionHandler`.

## Running Tests
Run the test suite using the Maven Wrapper:

```bash
./mvnw test
```

The test suite includes unit tests for the Haversine distance calculation and Spring Boot application context testing.

## Design
The application follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

### Responsibilities
* `PostcodeController` — handles HTTP requests and responses
* `PostcodeService` — handles postcode lookup and update logic
* `PostcodeRepository` — handles database access
* `DistanceCalculator` — calculates geographic distance using the Haversine formula
* `Postcode` — JPA database entity
* `Location` — represents postcode location data
* `DistanceResponse` — represents the distance API response
* `UpdatePostcodeRequest` — represents postcode coordinate update requests
* `SecurityConfig` — configures HTTP Basic Authentication
* `GlobalExceptionHandler` — converts application exceptions into HTTP responses

## Notes

The application requires PostgreSQL to be running and the postcode dataset to be imported before postcode lookups can be performed.

The postcode CSV dataset is excluded from version control through `.gitignore`.
