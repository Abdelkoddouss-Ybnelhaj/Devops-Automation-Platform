# Integrating OWASP Dependency Check with Jenkins

This guide explains how to integrate [OWASP Dependency Check](https://owasp.org/www-project-dependency-check/) into a Jenkins pipeline to automate vulnerability scanning of project dependencies.

## Prerequisites

- Jenkins server with required plugins (e.g., Pipeline, HTML Publisher).
- OWASP Dependency Check plugin.
- API key for NVD (optional, but recommended for faster updates).

## Parameters
- **ODC**: The name of the configured Dependency Check tool installation in Jenkins.
- **PROJECT_NAME**: The display name of your project for reporting purposes.
- **PROJECT_PATH**: The file system path to the project directory to be scanned.
- **NVD_API_KEY**: The API key for the National Vulnerability Database, used to speed up data updates.

## Pipeline Example

Below is a sample Jenkins pipeline snippet that runs OWASP Dependency Check and publishes the HTML report:

```groovy
def call(String odc, String projectName, String projectPath, String nvdApiKey) {
    try {
        dependencyCheck odcInstallation: odc,
                        additionalArguments: "--scan ${projectPath} --format HTML --out ./reports --nvdApiKey ${nvdApiKey}"

        sh """
            mv ./reports/dependency-check-report.html ./reports/${projectName}-report.html
        """
    } catch (Exception e) {
        error("OWASP Dependency Check failed: ${e.message}")
    }
} 
```

## Explanation

- **Parameters**: Control whether to run the check, set the project name, and specify the scan path.
- **Environment**: Securely injects the NVD API key from Jenkins credentials.
- **OWASP Dependency Check Stage**: Runs the scan and renames the report for clarity.
- **Publish Report Stage**: Publishes the HTML report as a build artifact for easy review.

## Best Practices

- Regularly update the Dependency Check tool and its data feeds.
- Fail builds on high or critical vulnerabilities as needed.
- Integrate with notifications for visibility.

## References

- [OWASP Dependency Check Documentation](https://jeremylong.github.io/DependencyCheck/)
- [Jenkins Pipeline Syntax](https://www.jenkins.io/doc/book/pipeline/syntax/)
- [HTML Publisher Plugin](https://plugins.jenkins.io/htmlpublisher/)
