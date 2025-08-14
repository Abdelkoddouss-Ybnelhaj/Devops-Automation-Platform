# Parameterized Checkout Stage for Jenkins Pipeline

This document describes a parameterized checkout stage for a Jenkins Pipeline.

### Parameters

- **BRANCH_NAME**: The name of the Git branch to checkout. Defaults to `main`.
- **GIT_URL**: The URL of the source control repository to clone.
- **CRENDANTAILS_ID**: The Jenkins credentials ID used for authenticating with the Git repository.

### Stages

- **Checkout**: Checks out the specified branch from the provided Git repository URL using the given credentials.

### Example Usage

```groovy
def call(String branch, String gitUrl, String credentialsId) {
    try {
        echo "🔍 Checking out branch: ${branch} from ${gitUrl}"

        checkout([
            $class: 'GitSCM',
            branches: [[name: "origin/${branch}"]],
            userRemoteConfigs: [[
                url: gitUrl,
                credentialsId: credentialsId
            ]]
        ])

        echo "✅ Successfully checked out branch: ${branch}"
    } catch (Exception e) {
        error("Checkout failed: ${e.message}")
    }
}

```