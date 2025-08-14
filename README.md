# 🧩 Parameterized Jenkins Pipeline

A modular, parameterized Jenkins CI/CD pipeline designed for dynamic and reusable builds, tests, and deployments. This repository also includes Docker-based installation instructions for Jenkins and SonarQube, as well as detailed documentation of pipeline parameters and structure.

---

## 📌 Features

- ✅ Declarative, parameterized `Jenkinsfile`
- ✅ Clean separation of concerns using external scripts
- ✅ Docker-based setup for Jenkins and SonarQube
- ✅ Detailed documentation (`docs/`) for easy onboarding
- ✅ Scalable and reusable CI/CD structure

---

## 📂 Repository Structure

```bash
parameterized-Jenkins-pipeline/
├── Jenkinsfile                # Main pipeline definition
├── scripts/                   # Shell scripts for build/test/deploy
├── docs/                      # Documentation
│   ├── parameters.md
│   ├── stages.md
│   └── installation/
│       ├── jenkins.md
│       ├── sonarqube.md
│       └── prerequisites.md
├── docker/                    # Docker and Docker Compose setup
│   ├── docker-compose.yml
│   └── jenkins/
│       └── Dockerfile


```

## Architecture Overview

![](./docs/support/diagram-export-6-2-2025-4_00_03-PM.png)