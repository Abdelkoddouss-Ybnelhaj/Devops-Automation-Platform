
# **SonarQube Analysis**

**> Communication Flow**

- Jenkins builds your project

- Jenkins uses the sonar-scanner to push analysis to SonarQube

- SonarQube processes the report

- SonarQube sends the result back to Jenkins via the webhook

- Jenkins receives it and the pipeline continues

**> 3.1 Create a token in SonarQube**

- Go to your sonarqube server
- Select the Profile > MyAccount
- Security > generate token


**> 3.2 Configure Jenkins to Talk to SonarQube**

- Go to Manage Jenkins → Configure System

- Scroll to SonarQube servers

- Click Add SonarQube

- Name: sonarqube-server (you will reference it by this name)

- Server URL: e.g., http://localhost:9000 or http://sonarqube:9000 (if Dockerized)

- Authentication Token:

    - Create it from SonarQube UI:
My Account → Security → Generate Token

    - Store the token in Jenkins as a "Secret Text" credential

- Select the token in the "Server authentication token" field

✅ Tick "Environment variables" checkbox if available

**> 3.3 Setup SonarQube Webhook for Quality Gate**

For `waitForQualityGate` to work:

- Go to SonarQube → Administration → Configuration → Webhooks

- Add webhook:

    - Name: Jenkins

    - URL: If Jenkins runs as a Docker service: http://jenkins:8080/sonarqube-webhook/ Or your Jenkins public/host IP

Test the URL manually or from SonarQube logs to ensure it reaches Jenkins.

**> Parameters**

| Parameter                       | Type         | Description                                 | Example Value            |
|----------------------------------|--------------|---------------------------------------------|-------------------------|
| `INTEGRATE_STATIC_CODE_ANALYSIS` | Boolean      | Enable or disable static code analysis      | `true`                  |
| `SONARQUBE_CONNECTION`           | String       | SonarQube server connection name in Jenkins | `sonarqube-server`      |
| `PROJECT_KEY`                    | String       | Unique key for your project in SonarQube    | `my-app`                |

**> 3.4 Code Snippet**

```groovy
def call(String sonarQubeConnection, String buildTool, String projectPath, String projectKey){
    try{
        switch(buildTool){
            case "maven":
                SonarScannerMaven(projectPath, sonarQubeConnection, projectKey)
                break;
            default:
                error "Unknown build tool: ${buildTool}"

        }
    } catch (Exception e) {
        error("SonarQube analysis failed: ${e.message}")
    }
}
```