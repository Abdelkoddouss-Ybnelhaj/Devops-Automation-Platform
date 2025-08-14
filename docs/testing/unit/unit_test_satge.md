# Unit Test Stage in Jenkins Pipeline

This document describes the **Unit Tests** stage in a parameterized Jenkins pipeline. The stage is responsible for running unit tests using the specified build tool.

## Parameters

```groovy
parameters {
    booleanParam(name: 'RUN_UNIT_TESTS', defaultValue: true)
}
```

## Pipeline Stage Example

```groovy
def call(String projectPath, String buildTool){
    try{
        switch(buildTool) {
            case "maven":
                unitTestMaven(projectPath)
                break
            default:
                error("Unsupported build tool: ${buildTool}")
        }
    }catch(Exception e){
        error("Unit Tests failed: ${e.message}")
    }
}
```

## How It Works

1. **Check if Unit Tests Should Run:**  
   The stage checks if `RUN_UNIT_TESTS` is `true`.

2. **Select Build Tool:**  
   Based on `BUILD_TOOL`, it runs the appropriate test command in the specified `PROJECT_PATH`.

3. **Error Handling:**  
   If any error occurs, the pipeline sets `FAILURE_REASON` and fails the build with an error message.

## Supported Build Tools

- **Maven:** `mvn test`
- **Gradle:** `./gradlew test`
- **.NET:** `dotnet test`
- **npm:** `npm run test`

## Customization

You can extend the `switch` statement to support additional build tools as needed.


## Troubleshooting

- Ensure the correct `BUILD_TOOL` is selected.
- Verify `PROJECT_PATH` is accurate.
- Check build logs for detailed error messages.
