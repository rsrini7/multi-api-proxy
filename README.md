<h1 align="center">Welcome to multi-api-proxy 👋</h1>
<p>
  This project serves as a template and proof-of-concept (POC) for exposing data models (JPA entities) via multiple API styles – specifically JSON-API and GraphQL – using Spring Boot and Elide. It demonstrates how to provide a unified API interface for underlying data sources.
</p>
<p>
  In its current configuration, the project exposes a single example entity called "Test", which has `id` and `name` attributes. This data is stored in an in-memory H2 database, which is re-initialized every time the application starts.
</p>
<p>
  The name "multi-api-proxy" refers to its capability to act as a proxy to your data layer, presenting it through multiple API formats (JSON-API, GraphQL), rather than proxying external third-party APIs.
</p>

## Core Technologies Used

This project leverages several key technologies:

*   **Spring Boot:** Provides the core application framework, simplifying setup and development of web applications.
*   **Elide:** Enables the rapid creation of JSON-API and GraphQL compliant web services from JPA entities.
*   **Spring Data JPA:** Simplifies data access layers by providing an abstraction over JPA (Java Persistence API).
*   **H2 Database Engine:** An in-memory SQL database used for storing the application's data during development and testing.
*   **Swagger/OpenAPI:** Used for designing, building, and documenting RESTful APIs. Provides the `/swdocs` UI.
*   **Lombok:** A Java library used to reduce boilerplate code (e.g., getters, setters, constructors) via annotations.
*   **Java 8:** The programming language and runtime environment.
*   **Maven:** Used for project build management and dependencies.

## Features

This project exposes its data ("Test" entity) through multiple API styles and includes several helpful features:

*   **JSON-API Compliant API:**
    *   **Endpoint:** `/api/test` (for the "Test" entity).
    *   **Supported Operations:**
        *   `GET /api/test`: Fetches a collection of "Test" entities.
        *   `GET /api/test/{id}`: Fetches a single "Test" entity by its ID.
        *   `POST /api/test`: Creates a new "Test" entity.
    *   **Note:** `PATCH` (update) and `DELETE` operations are currently disabled by default in the configuration (`application.yml`).
    *   The older `/json-api/<entity>` path may still work depending on Elide's version and configuration but `/api/<entity>` is the configured prefix.

*   **GraphQL API:**
    *   **Endpoint:** `/api/graphql`
    *   **Capabilities:** Allows querying the "Test" entity. Based on Elide's behavior and the enabled POST operation for JSON-API, mutations (e.g., for creating "Test" entities) should also be available.
    *   **GraphQL UI:** A user interface for interacting with the GraphQL API is available at `/graphql-ui.html`.

*   **Swagger API Documentation:**
    *   **Endpoint:** `/swdocs`
    *   Provides interactive documentation for the JSON-API.

*   **Basic Web UI:**
    *   **Endpoint:** `/index.html`
    *   A simple landing page for the application.

## Configuration

The primary application configuration is managed in the `src/main/resources/config/application.yml` file.

Key configurable aspects include:

*   **API Settings (Elide):**
    *   `elide.prefix`: The base path for all Elide APIs (default: `/api`).
    *   `elide.default-page-size` / `elide.max-page-size`: Controls pagination in API responses.
    *   `elide.mvc.get`, `elide.mvc.post`, `elide.mvc.patch`, `elide.mvc.delete`: Booleans to enable or disable these HTTP methods for entity manipulation via JSON-API. (Currently, PATCH and DELETE are `false`).
    *   `elide.mvc.graphql`: Enables the GraphQL endpoint (default: `true`).

*   **Database (Spring Datasource):**
    *   `spring.datasource.url`: JDBC URL for the database connection (default: H2 in-memory `jdbc:h2:mem:localhost;DB_CLOSE_ON_EXIT=FALSE`).
    *   `spring.datasource.driver-class-name`, `username`, `password`: Standard datasource properties.

*   **Logging:**
    *   `logging.level.com.github.rsrini7`: Sets the log level for the application's packages (default: `debug`).
    *   `logging.pattern.console`: Defines the log output format.

*   **Application Specific Properties:**
    *   Properties prefixed with `app` can be defined in `application.yml` and accessed via the `com.github.rsrini7.api.config.AppProperties` class. Currently, this is mainly used to determine the `app.version`.

To modify the application's behavior, such as connecting to a different database or changing API settings, you would typically edit this `application.yml` file.

## Building and Running the Project

This project uses Apache Maven for building and managing dependencies.

**Prerequisites:**
*   Java JDK 8 or higher
*   Apache Maven

**Building the Project:**

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd multi-api-proxy
    ```
2.  **Build the project using Maven:**
    This command will compile the code, run tests, and package the application into a `.war` file in the `target/` directory.
    ```bash
    mvn clean package
    ```

**Running the Project:**

There are a couple of ways to run the application:

1.  **Using the Spring Boot Maven Plugin (Recommended for development):**
    This command starts the application directly using Maven.
    ```bash
    mvn spring-boot:run
    ```

2.  **Running the packaged .war file:**
    After building the project, you can run the generated WAR file.
    ```bash
    java -jar target/multi-api-proxy-0.0.1-alpha.war
    ```

Once the application starts, it will typically be accessible at `http://localhost:8080` by default, unless a different port is configured in `application.yml` (e.g., via `server.port`).

## API Usage Examples

Once the application is running, you can interact with the APIs using tools like `curl` or Postman. The base URL for the APIs is `http://localhost:8080/api` by default.

### JSON-API Examples (`/api/test`)

**1. Fetch all "Test" entities:**
```bash
curl -H "Accept: application/vnd.api+json" http://localhost:8080/api/test
```
*Expected Response (example if one entity exists):*
```json
{
  "data": [
    {
      "type": "test",
      "id": "1",
      "attributes": {
        "name": "First Test Item"
      },
      "relationships": {},
      "links": {
        "self": "http://localhost:8080/api/test/1"
      }
    }
  ],
  "included": [],
  "meta": {
    "page": {
      "number": 1,
      "size": 20,
      "totalElements": 1,
      "totalPages": 1
    }
  },
  "links": {
    "self": "http://localhost:8080/api/test?page[size]=20&page[number]=1"
  }
}
```

**2. Fetch a single "Test" entity by ID (e.g., ID 1):**
```bash
curl -H "Accept: application/vnd.api+json" http://localhost:8080/api/test/1
```
*Expected Response:*
```json
{
  "data": {
    "type": "test",
    "id": "1",
    "attributes": {
      "name": "First Test Item"
    },
    "relationships": {},
    "links": {
      "self": "http://localhost:8080/api/test/1"
    }
  },
  "included": []
}
```

**3. Create a new "Test" entity:**
```bash
curl -X POST -H "Content-Type: application/vnd.api+json" -H "Accept: application/vnd.api+json" -d '{
  "data": {
    "type": "test",
    "attributes": {
      "name": "My New Test Item"
    }
  }
}' http://localhost:8080/api/test
```
*Expected Response (example):*
```json
{
  "data": {
    "type": "test",
    "id": "2",  // ID will be auto-generated
    "attributes": {
      "name": "My New Test Item"
    },
    "relationships": {},
    "links": {
      "self": "http://localhost:8080/api/test/2"
    }
  },
  "included": []
}
```

### GraphQL Example (`/api/graphql`)

You can use the GraphQL UI at `/graphql-ui.html` or send POST requests to `/api/graphql`.

**1. Query to fetch all "Test" entities (ID and name):**
```graphql
query {
  test {
    edges {
      node {
        id
        name
      }
    }
  }
}
```
*Send as a JSON payload in a POST request:*
```bash
curl -X POST -H "Content-Type: application/json" -d '{ "query": "query { test { edges { node { id name } } } }" }' http://localhost:8080/api/graphql
```
*Expected Response (example):*
```json
{
  "data": {
    "test": {
      "edges": [
        {
          "node": {
            "id": "1",
            "name": "First Test Item"
          }
        },
        {
          "node": {
            "id": "2",
            "name": "My New Test Item"
          }
        }
      ]
    }
  }
}
```
(Note: The exact structure of the GraphQL response might vary slightly based on Elide's GraphQL dialect and pagination settings.)

## Show your support

Give a ⭐️ if this project helped you!

