pipeline {
    agent any

    tools {
        nodejs 'node' // Ensure NodeJS is configured in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/sagar-subedi/litcord.git', branch: 'test'
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
                script {
                    sh 'pwd; ls;'
                }
                sshPublisher(publishers: [
            sshPublisherDesc(
                configName: 'Litcord EC2', // This is a predefined SSH configuration in Jenkins.
                transfers: [
                    sshTransfer(
                        sourceFiles: '/frontend-angular/dist/litcord**', // Specifies the files to transfer. `dist/**` includes all files and folders in the `dist` directory.
                        removePrefix: 'dist/litcord', // Removes the `dist` prefix from the path during transfer, placing files directly in the target directory.
                        remoteDirectory: '/usr/share/nginx/html/litcord' // Specifies the destination directory on the EC2 server.
                    )
                ],
                verbose: true // Enables detailed output in the build logs for debugging.
            )
        ])
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
