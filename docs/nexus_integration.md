## Jenkins Pipeline: Nexus Integration

This guide explains how to integrate your Jenkins pipeline with a Nexus repository for artifact deployment, supporting multiple build tools.

---

### Step-by-Step Guide

1. **Prepare Your Project**
    - Ensure your project contains the necessary build files (`pom.xml` for Maven, `build.gradle` for Gradle, etc.).

2. **Configure Jenkins Agent**
    - Install required build tools (Maven, Gradle, etc.) on your Jenkins agent.
    - Set up credentials for accessing Nexus.

3. **Add the Shared Library Step**
    - Use the provided shared library step in your Jenkins pipeline.

4. **Call the Step in Your Pipeline**
    - Pass the appropriate parameters for your build tool and Nexus repository.

---

### Example Shared Library Step

```groovy
def call(String BUILD_TOOL, String PROJECT_PATH, String NEXUS_URL, String REPOSITORY) {
     try {
          dir(PROJECT_PATH) {
                echo "🔍 Checking for project files in ${PROJECT_PATH}"
                switch(BUILD_TOOL.toLowerCase()) {
                     case "maven":
                          pushMavenToNexus(NEXUS_URL, REPOSITORY)
                          break
                     case "gradle":
                          pushGradleToNexus(NEXUS_URL, REPOSITORY)
                          break
                     default:
                          error("Unsupported build tool: ${BUILD_TOOL}")
                }
          }
          echo "✅ Successfully pushed to Nexus Repository ${NEXUS_URL}"
     } catch (Exception e) {
          error("Failed to push to Nexus: ${e.message}")
     }
}

```

---

### Usage

Call this step from your Jenkins pipeline, providing the required parameters:

- `BUILD_TOOL`: The build tool used (e.g., `"maven"`, `"gradle"`).
- `PROJECT_PATH`: Path to your project directory.
- `NEXUS_URL`: URL of your Nexus server.
- `REPOSITORY`: Nexus repository name.

**Example:**
```groovy
nexusIntegration(
     BUILD_TOOL: 'gradle',
     PROJECT_PATH: 'my-app',
     NEXUS_URL: 'http://nexus.example.com',
     REPOSITORY: 'gradle-releases'
)
```

---

## Maven Support

To deploy Maven artifacts to Nexus, follow these steps:

#### 1. Obtain Nexus Credentials

- Create or use an existing Nexus user with deployment permissions.
    - `username`: admin
    - `password`: /nexus-data/admin.password
- Note the username and password (or token) for use in Jenkins.

#### 2. Configure Maven Settings

- On your Jenkins agent, edit or create the `~/.m2/settings.xml` file.
- Add a `<server>` entry for your Nexus repository:

```xml
<settings>
    <servers>
        <server>
            <id>nexus</id>
            <username>${NEXUS_USERNAME}</username>
            <password>${NEXUS_PASSWORD}</password>
        </server>
    </servers>
</settings>
```

- Replace `${NEXUS_USERNAME}` and `${NEXUS_PASSWORD}` with your actual credentials or use Jenkins credentials binding.

#### 3. Create a Nexus Maven Repository

- In Nexus, create a Maven hosted repository (e.g., `maven-releases` or `maven-snapshots`).
- Note the repository name and URL for use in your pipeline.
#### 4. Configure Maven Mirror

- Instead of updating `<distributionManagement>`, configure a mirror in your `~/.m2/settings.xml` to direct all repository requests to Nexus:

```xml
<mirrors>
    <mirror>
        <id>nexus</id>
        <mirrorOf>*</mirrorOf>
        <url>http://nexus.example.com/repository/maven-public/</url>
    </mirror>
</mirrors>
```

- This ensures all dependencies and plugin downloads go through your Nexus server.
- Adjust the `<url>` to match your Nexus repository group (e.g., `maven-public`).

#### 5. Jenkins Pipeline Integration

- Use the shared library step as shown above.
- Make sure Jenkins has access to the required credentials and `settings.xml`.

---


```groovy
private def pushMavenToNexus(String NEXUS_URL, String REPOSITORY) {
     if (!fileExists("./pom.xml")) {
          error("No pom.xml found in the specified project path")
     }
     sh """
          mvn clean deploy \
            -DaltDeploymentRepository=nexus::default::${NEXUS_URL}/repository/${REPOSITORY}/
     """
}
```
