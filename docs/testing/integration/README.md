# Integration Testing

- Integration testing is a type of software testing in which individual units or components are combined and tested as a group. The primary goal is to verify the **interactions between components or systems —** ensuring that they work together as expected.

- It sits between unit testing (which tests individual components in isolation) and end-to-end testing (which tests full workflows or systems).

### 🔍 Key Goals of Integration Testing
- Validate data flow between modules (e.g., passing data from the service layer to the database).
- Ensure API contracts between microservices are respected.

- Detect issues like:
    - Incorrect API responses
    - Misconfigured service communication
    - Database schema mismatches
    - Security/auth issues between layers



### 🏗️ Common Types of Integration Testing

| Type                | Description                                                                 |
| ------------------- | --------------------------------------------------------------------------- |
| **Big Bang**        | All modules are integrated together and tested as a whole.                  |
| **Top-down**        | Testing starts from the top-level modules and gradually adds lower modules. |
| **Bottom-up**       | Testing begins from the lower-level modules and moves upward.               |
| **Sandwich/Hybrid** | Combines top-down and bottom-up approaches.                                 |



## 🌍 Real-World Use Cases

### 1. Spring Boot App Testing with a Real Database (PostgreSQL)
- You write integration tests using Testcontainers to spin up a real PostgreSQL instance.

- Purpose: Validate that JPA/Hibernate mappings, SQL queries, and repository methods work as expected.

**✅ Example:** Ensure that a UserService.createUser() correctly persists a user to the database and can be retrieved via UserRepository.findByEmail().


### 2. Microservices Communication
- Services communicate via REST APIs or message queues (Kafka, RabbitMQ).
- Integration tests verify:
    - The client sends valid requests
    - The server returns valid responses
    - Serialization/deserialization works correctly

**✅ Example:** Service A calls Service B’s REST API — integration test mocks real Service B or spins it up in a container to test contract validity.

### 3. External API Integration (e.g., Stripe, Twilio, Google Maps)
- You write tests that hit a mock or sandbox version of an external service.
- Goal: Verify your code handles:
    - API rate limits
    - Response formats
    - Webhooks/events

**✅ Example:** When using Stripe, test that your webhook handler correctly processes a payment_succeeded event.
