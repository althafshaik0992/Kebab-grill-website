pipeline {
    agent any
    tools {
        jdk "JAVA_HOME"
        maven "M3"
        git "Git"
    }
    triggers {
        githubPush() // This triggers the build on GitHub events like PR updates
    }
    stages {
        stage('Git Checkout') {
            steps {
               git branch: 'main', credentialsId: 'ae6c819c-3898-49b5-9150-10823ad99f27', url: 'https://github.com/althafshaik0992/Kebab-grill-website.git'
            }
        }
        stage('Build') {
            steps {
                bat "mvn compile"  // Maven build; stops pipeline if compile fails
            }
        }
        stage('Test') {
            steps {
                bat "mvn test"  // Maven tests; stops pipeline if tests fail
            }
        }
    }
    post {
        always {
            echo 'Pipeline completed.'
        }
        failure {
            script {
                echo 'Pipeline failed. No next build will trigger.'
                currentBuild.result = 'FAILURE'  // Mark the build as failed explicitly
            }
        }
        success {
            echo 'Pipeline succeeded. Next build can proceed.'
        }
    }
}
