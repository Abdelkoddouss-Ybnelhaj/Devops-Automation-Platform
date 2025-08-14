# *Project Deployment*

## Step-by-Step Guide

### 1. Generate SSH Key Pair for Deployment Authentication

```bash
ssh-keygen -t rsa -b 4096 -f access_key
cat access_key.pub >> ~/.ssh/authorized_keys
```

### 2. Configure a Systemd Service

Create a new systemd service file:

```bash
sudo nano /etc/systemd/system/e-wallet.service
```

Add the following content:

```ini
[Unit]
Description=E-Wallet Spring Boot Application
After=network.target

[Service]
User=user
WorkingDirectory=/home/user/deployments/app
ExecStart=/usr/bin/java -jar /home/user/deployments/app/e-wallet.jar
SuccessExitStatus=143
Restart=always
RestartSec=5
StandardOutput=append:/home/user/deployments/app/app.log
StandardError=append:/home/user/deployments/app/app.err.log

[Install]
WantedBy=multi-user.target
```

### 3. Jenkins Pipeline Deployment Function

Below is a sample Groovy function for deploying your project using Jenkins:

```groovy
def call(String BUILD_TOOL, String NEXUS_URL, String GROUP_PATH, String SNAPSHOT_VERSION, String SSH_KEY, String REPOSITORY, 
         String PROJECT_NAME, String REMOTE_HOST, String REMOTE_USER, String TARGET_DIR, String ENV_VARS) {
    try {
        switch(BUILD_TOOL) {
            case 'maven':
                // Maven specific deployment steps
                deployMavenProject(NEXUS_URL, GROUP_PATH, SNAPSHOT_VERSION, SSH_KEY, REPOSITORY, PROJECT_NAME, REMOTE_HOST, REMOTE_USER, TARGET_DIR)
                break
            case 'gradle':
                // Gradle specific deployment steps
                break
            default:
                error "Unsupported build tool: ${BUILD_TOOL}"
        }
    } catch (Exception e) {
        error("Failed to deploy project: ${e.message}")
    }
    echo "✅ Deployment completed successfully for ${PROJECT_NAME} on ${REMOTE_HOST}"
}

```
### 4. Jenkins Pipeline Parameters

The following table explains each parameter used in the Jenkins pipeline:

| Parameter        | Type        | Description                                      |
|------------------|-------------|--------------------------------------------------|
| `BUILD_TOOL`     | Choice      | Select the build tool (`maven` or `gradle`).     |
| `NEXUS_URL`      | String      | Nexus repository URL.                            |
| `GROUP_PATH`     | String      | Group path for the artifact.                     |
| `SNAPSHOT_VERSION` | String    | Snapshot version to deploy.                      |
| `SSH_KEY`        | Credentials | SSH key credentials for remote server.           |
| `REPOSITORY`     | String      | Name of the repository to deploy from.           |
| `PROJECT_NAME`   | String      | Name of the project being deployed.              |
| `REMOTE_HOST`    | String      | Hostname or IP address of the remote server.     |
| `REMOTE_USER`    | String      | Username for the remote server.                  |
| `TARGET_DIR`     | String      | Target directory on the remote server.           |
| `ENV_VARS`       | String      | Environment variables for deployment (optional). |
