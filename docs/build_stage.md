# Parameterized Build Stage for Jenkins pipeline

This document describes the parameterized build stage for a Jenkins pipeline, supporting multiple build tools.

## Parameters

- **BUILD_TOOL**  
    *Type:* Choice  
    *Choices:* `maven`, `gradle`, `.net`, `npm`  
    *Description:* Select the build tool to use for building the project.

- **PROJECT_PATH**  
    *Type:* String  
    *Default:* `./`  
    *Description:* Specify the path to the project directory.

## Build Stage Logic

The build stage executes the appropriate build command based on the selected `BUILD_TOOL`:

| Build Tool | Command Executed |
|------------|-----------------|
| maven      | `mvn clean install -DskipTests` |
| gradle     | `./gradlew build -x test`       |
| .net       | `dotnet build`                  |
| npm        | `npm run build`                 |

All commands are run in the directory specified by `PROJECT_PATH`.

## Error Handling

If the build fails, the pipeline sets `FAILURE_REASON` to `build_failed`.  
If an unknown build tool is selected, the pipeline will fail with an error message.

## Example Usage

```groovy
choice(name: 'BUILD_TOOL', choices: ['maven', 'gradle', '.net', 'npm'], description: 'Select build tool')
string(name: 'PROJECT_PATH', defaultValue: './', description: 'Specify the maven project path')

stage('build') {
        steps {
                script {
                        try {
                                switch (params.BUILD_TOOL) {
                                        case 'maven':
                                                sh "cd ${params.PROJECT_PATH} && mvn clean install -DskipTests"
                                                break
                                        case 'gradle':
                                                sh "cd ${params.PROJECT_PATH} && ./gradlew build -x test"
                                                break
                                        case '.net':
                                                sh "cd ${params.PROJECT_PATH} && dotnet build"
                                                break
                                        case 'npm':
                                                sh "cd ${params.PROJECT_PATH} && npm run build"
                                                break
                                        default:
                                                error "Unknown build tool: ${params.BUILD_TOOL}"
                                }
                        } catch(Exception e) {
                                FAILURE_REASON = 'build_failed'
                        }
                }
        }
}
```

