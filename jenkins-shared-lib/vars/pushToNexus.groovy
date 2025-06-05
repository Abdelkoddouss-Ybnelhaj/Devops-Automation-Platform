def pushToNexus(String BUILD_TOOL, String PROJECT_PATH, String NEXUS_URL, String REPOSITORY) {
    
    echo '🚀 Pushing to Nexus Repository'
    try {
        switch(BUILD_TOOL) {
            case 'maven':
                mavenNexus(PROJECT_PATH, NEXUS_URL, REPOSITORY)
                break
        }
    } catch (Exception e) {
        error("Failed to push to Nexus: ${e.message}")
    }
}
