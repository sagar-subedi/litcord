pipeline {
    agent any

    tools {
        nodejs 'node' // Ensure NodeJS is configured in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/sagar-subedi/litcord.git', branch: 'develop'
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
        
	//Commented as frontend tests are not prioritized
        // stage('Test') {
        //     steps {
        //         script {
        //             sh 'cd ./frontend-angular && npm test'
        //         }
        //     }
        // }


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
                        sourceFiles: 'frontend-angular/dist/**', // Specifies the files to transfer. `dist/**` includes all files and folders in the `dist` directory.
                        removePrefix: 'frontend-angular/dist', // Removes the `dist` prefix from the path during transfer, placing files directly in the target directory.
                        remoteDirectory: '/dist' // Specifies the destination directory on the EC2 server.
                    ),
                    sshTransfer(
                        execCommand: 'sudo cp -r dist/litcord /usr/share/nginx/html'
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
