/******************************************** Build & Test Pipeline ********************************************/

@Library('my-shared-lib') _


pipeline {
    agent any
    
    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'master', description: 'Git branch to checkout')
        string(name: 'GIT_URL', defaultValue: 'https://github.com/Abdelkoddouss-Ybnelhaj/e-wallet.git', description: 'scm repo url')
        string(name: 'CRENDANTAILS_ID',description: 'Jenkins credential ID for Git') 
        choice(name: 'BUILD_TOOL', choices: ['maven', 'gradle', '.net' , 'npm'], description: 'Select build tool')
        booleanParam(name: 'RUN_OWASP_DEPENDENCY_CHECK', defaultValue: true, description: 'Scan for dependency vulnerabilities?')
        string(name: 'PROJECT_PATH', defaultValue: './backend', description: 'Specify the maven project path')
        string(name: 'PROJECT_NAME', defaultValue: 'e-wallet', description: 'project name')
        booleanParam(name: 'RUN_UNIT_TESTS', defaultValue: true, description: 'Run unit tests?')
        booleanParam(name: 'INTEGRATE_STATIC_CODE_ANALYSIS', defaultValue: true, description: 'Integrate static code analysis ?')
        string(name: 'SONARQUBE_CONNECTION',defaultValue: 'sonarqube-server', description: 'sonarqube server connection')
        string(name: 'PROJECT_KEY',defaultValue: 'e-wallet-backend', description: 'project key')
    }
    
    environment {
        NVD_KEY = 'be18959a-0b20-4330-b685-0c3325f612f5'
        ODC = 'Default'
    }
    
    tools{
        maven 'Maven-3.9.8'
    }
    
    stages {
        stage('Checkout') {
            steps {
                script {
                    checkoutRepo(params.BRANCH_NAME, params.GIT_URL, params.CRENDANTAILS_ID)
                }
            }
        }
        
        stage('Dependency Check') {
            when {
                expression { params.RUN_OWASP_DEPENDENCY_CHECK }
            }
            steps {
                owaspDepCheck(env.ODC, params.PROJECT_NAME, params.PROJECT_PATH, env.NVD_KEY)
            }
            
            post {
                always {
                    publishFile(
                        './reports',
                        "${params.PROJECT_NAME}-report.html",
                        "OWASP Dependency-Check - ${params.PROJECT_NAME}"
                    )
                }
            }
        }
        
        stage('Unit Tests'){
            when {
                expression { params.RUN_UNIT_TESTS }
            }
            steps{
                script {
                    unitTest(params.PROJECT_PATH, params.BUILD_TOOL)
                }
            }
        }
        
        stage('Static code analysis') {
            when {
                expression { return params.INTEGRATE_STATIC_CODE_ANALYSIS }
            }
            steps{
                sonarQube(params.SONARQUBE_CONNECTION , params.BUILD_TOOL, params.PROJECT_PATH, params.PROJECT_KEY)
            }
            post {
                success {
                    script {
                        sonarQualityGates()
                    }
                }
            }
        }
        
    }
    
    post {
        always {
            script {
                emailNotif()
                echo "Clearing workspace....."
                deleteDir()
            }
        }
    }
}


/******************************************************* Integration Pipeline *******************************************************/

@Library('my-shared-lib') _

pipeline {
    agent any
    
    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'master', description: 'Git branch to checkout')
        string(name: 'GIT_URL', defaultValue: 'https://github.com/Abdelkoddouss-Ybnelhaj/e-wallet.git', description: 'scm repo url')
        string(name: 'CRENDANTAILS_ID',description: 'Jenkins credential ID for Git') 
        choice(name: 'BUILD_TOOL', choices: ['maven', 'gradle', '.net' , 'npm'], description: 'Select build tool')
        booleanParam(name: 'PUSH_TO_NEXUS', defaultValue: true, description: 'Storing Artifacts in Nexus')
        string(name: 'PROJECT_PATH', defaultValue: './backend', description: 'Specify the maven project path')
        string(name: 'PROJECT_NAME', defaultValue: 'e-wallet', description: 'Specify the maven project name')
        string(name: 'NEXUS_URL', defaultValue: 'http://192.168.11.107:8081', description: 'Specify the Nexus URL')
        string(name: 'GROUP_PATH', defaultValue: 'com/github/yildizmy/e-wallet', description: 'Specify the Nexus repository')
        string(name: 'REPOSITORY', defaultValue: 'maven-snapshots', description: 'Specify the Nexus repository')
        string(name: 'SNAPSHOT_VERSION', defaultValue: '0.0.1-SNAPSHOT', description: 'Specify the Nexus repository')
        string(name: 'SSH_KEY', defaultValue: 'vm-key', description: 'Specify the Nexus repository')
        string(name: 'REMOTE_HOST', defaultValue: '192.168.122.120', description: 'Specify the Nexus repository')
        string(name: 'REMOTE_USER', defaultValue: 'user', description: 'Specify the Nexus repository')
        string(name: 'TARGET_DIR', defaultValue: '/home/user/deployments', description: 'Specify the Nexus repository')
        string(name: 'ENV_VARS', defaultValue: 'env-vars', description: 'Specify the Nexus repository')
    }

    tools {
        maven 'Maven-3.9.8'
    }

    environment {
        NEXUS_URL = 'http://192.168.11.107:8081'
        SNAPSHOT_VERSION = '0.0.1-SNAPSHOT'
        GROUP_PATH = 'com/github/yildizmy/e-wallet'
    }

    stages {

        stage('Checkout') {
            steps {
                script {
                    checkoutRepo(params.BRANCH_NAME, params.GIT_URL, params.CRENDANTAILS_ID)
                }
            }
        }

        stage('Package Project & Push Artifact') {
            steps {
                script {
                    pushToNexus(params.BUILD_TOOL, params.PROJECT_PATH, params.NEXUS_URL, params.REPOSITORY)
                }
            }
        }
        
        stage('Deploy Project') {
            steps {
                deployProject(params.BUILD_TOOL, params.NEXUS_URL, params.GROUP_PATH, params.SNAPSHOT_VERSION,  params.SSH_KEY, params.REPOSITORY, 
                                params.PROJECT_NAME,  params.REMOTE_HOST,  params.REMOTE_USER,  params.TARGET_DIR, params.ENV_VARS)
            }
        }

        stage('Wait for Application') {
            steps {
                script {
                    delay("http://192.168.122.120:8080/swagger-ui/index.html")
                }
            }
        }

        stage('Run API Tests') {
            steps {
                script {
                    apiTests("${JENKINS_HOME}/api_tests.json")
                }
            }
        }

        stage('Publish Newman HTML Report') {
            steps {
                publishFile(
                    '.',
                    'newman-report.html',
                    'Newman API Test Report'
                )
            }
        }

        stage('Run E2E Tests') {
            steps {
                echo "Running E2E tests..."
                // add e2e test logic here
            }
        }
    }
    
    post {
        always {
            script {
                echo "Clearing workspace....."
                deleteDir()
            }
        }
    }
}
