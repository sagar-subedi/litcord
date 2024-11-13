pipeline {
    agent any

    tools {
        nodejs "node" // Ensure NodeJS is configured in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/sagar-subedi/litcord.git', branch: 'feature/6-setup-homepage'
            }
        }

        stage('Install Dependencies') {
            steps {
                script {
                    sh 'cd ./frontend-angular && npm install'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'cd ./frontend-angular && npm run build'
                }
            }
        }

        // stage('Test') {
        //     steps {
        //         script {
        //             sh 'cd ./frontend-angular && npm test'
        //         }
        //     }
        // }

        stage('Archive Build Artifacts') {
            steps {
                archiveArtifacts artifacts: 'build/**', allowEmptyArchive: true
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploy stage - customize deployment here'
                // Example: copy build files to a directory for local deployment
                // sh 'cp -r build/* /path/to/deployment/directory'
            }
        }
    }

    post {
        always {
            cleanWs() // Clean workspace after build
        }
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}