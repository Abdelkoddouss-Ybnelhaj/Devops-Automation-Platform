## ⚙️ Jenkins Configuration Checklist (via JCasC)
### 1. ✅ Plugin Installation
Install all necessary plugins via plugins.txt or JCasC plugin block. Suggested list:
- workflow-aggregator (Pipeline)
- git (Git integration)
- blueocean (UI for pipelines)
- credentials-binding
- email-ext
- mailer
- job-dsl
- configuration-as-code
- ansicolor
- pipeline-stage-view
- pipeline-github-lib (for Shared Libraries)
- ssh-agent
- matrix-auth (Role-based access)
- sonar (SonarQube Scanner)
- nexus-jenkins-plugin or nexus-artifact-uploader
- owasp-dependency-check-jenkins-plugin
- maven-plugin

### 2. 🛠️ Tool Configuration
Tools managed via Jenkins global configuration:

- Maven:
    - Define a Maven installation (e.g., Maven 3.8.7)
    - Set MAVEN_HOME, auto-install option
- OWASP Dependency-Check CLI:
    - Define a tool or pipeline logic that downloads it
- Sonar Scanner:
    - Configure SonarQube Scanner under tools (name/version)

- Optionally add Gradle or Node.js if needed


### 3. 🔐 Secrets / Credentials Setup
Configure Jenkins credentials via JCasC or CLI:
- Git credentials (username/password or SSH)
- Docker Hub credentials (if needed for pipeline builds)
- SonarQube tokens
- Nexus upload credentials
- SMTP credentials for notifications
- SSH keys for agent connections (if using slaves)

### 4. 🤝 SonarQube Integration
- Configure SonarQube Server connection:
    - Server URL
    - Authentication token
    - Name to be referenced in pipeline steps
- Add Sonar scanner under tool configuration
- Ensure pipelines can use withSonarQubeEnv('name')

### 5. 📦 Nexus Integration
- Define Nexus repository URL
- Add upload credentials to Jenkins
- Use plugin or pipeline logic to upload artifacts to Nexus

### 6. 📚 Shared Library Configuration
- Add Git repository (GitHub or GitLab) as a global library
- Define:
    - name
    - defaultVersion
    - retriever method (e.g., modern SCM)
    - allowOverride, implicit flags

1 _ sonarqube:
    - provision ✅
    - configure vm.max_map_count for Elasticsearch ✅
    - Change SonarQube Password ✅
    - extract token ✅
    - create webhook for jenkins ✅
    - create users 
    - configure permissions
    - Configure a Personalized Quality Quate
    - Backup (base + config)
    - Configure HTTPs 
2 _ install nexus
    
3 _ jenkins
    - install plugins ✅
    - install tools ✅
    - configure secrects: 
        - sonarqube ✅
        - VMs keys
        - shared library git repo ✅
        - app git repo
        - SMTP credentials for notifications ✅
    - system:
        - sonarqube ✅
        - email notification ✅
        - jenkins url 
        - shared library ✅
    - tools:
        - jdk
        - maven ✅
        - sonarscanner ✅
        - dependency-check ✅
    - Nodes
    - create a folder job ✅
    - mount jenkins pipelines ✅ (still need to staging/prod pipelines)
    - configure .m2/settings.xml 
4 _ Setup a reverse proxy
