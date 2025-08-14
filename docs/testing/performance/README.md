# Performance Testing

**Performance testing is a non-functional testing technique used to determine how a system performs in terms of responsiveness, stability, scalability, and speed under a particular workload.**

### It helps answer questions like:
---
    - How fast does the system respond?
    - Can the application handle expected and peak user loads?
    - Is the system stable under stress or prolonged use?


### 🔍 Objectives of Performance Testing
---
    - Ensure the application meets performance benchmarks
    - Identify bottlenecks or slow points
    - Validate system scalability and reliability
    - Guarantee a smooth user experience under real-world usage

### 📊 Types of Performance Testing
| Type                    | Description                                                                  |
| ----------------------- | ---------------------------------------------------------------------------- |
| **Load Testing**        | Evaluates how the system behaves under expected user loads.                  |
| **Stress Testing**      | Determines the system’s breaking point by increasing the load beyond limits. |
| **Spike Testing**       | Tests the system’s response to sudden large spikes in traffic.               |
| **Endurance Testing**   | Assesses stability over an extended period under a typical load.             |
| **Scalability Testing** | Verifies the system’s ability to scale up/down based on user demand.         |


### 📐 Performance Testing Strategies
---
A well-structured performance testing strategy ensures that tests are realistic, repeatable, and actionable. Below are some core strategies to guide your testing process:

**1. Define Clear Performance Goals**

Set measurable KPIs such as:

- Maximum response time (e.g., < 2 seconds for 95% of requests)
- Throughput (e.g., 1000 transactions/sec)
- Error rate (e.g., < 1%)

**2. Test Early, Test Often**

Shift performance testing left in your SDLC:

- Run baseline tests early in development
- Integrate performance tests into CI/CD pipelines

**3. Simulate Realistic User Behavior**

Use production-like scenarios:

- Simulate user think times, login sessions, and request patterns
- Use realistic data sets to avoid caching effects


**4. Baseline First, Then Scale**
- Start with baseline testing (single user or minimal load)
- Incrementally increase the load to measure system scalability

**5. Monitor Everything**
- Use tools like Prometheus, Grafana, New Relic, or Datadog
- Monitor CPU, memory, disk I/O, DB queries, and network latency during tests

**6. Include Negative Testing**
Don’t just test for success. Intentionally:

- Introduce high latency
- Inject faults or simulate resource exhaustion
- Observe system behavior under stress

**7. Repeat and Compare**

- Re-run the same tests after code changes
- Compare results to detect regressions or improvements
