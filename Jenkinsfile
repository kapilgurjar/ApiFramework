pipeline {
    agent any

    tools {
        maven 'maven'
    }

    environment {
        DOCKER_IMAGE = "kapil/apiframework:${BUILD_NUMBER}"
        DOCKER_CREDENTIALS_ID = 'dockerhub_credentials'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/kapilgurjar/ApiFramework.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    if (isUnix()) {
                        sh "docker build -t ${DOCKER_IMAGE} ."
                    } else {
                        bat "docker build -t ${DOCKER_IMAGE} ."
                    }
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKER_CREDENTIALS_ID}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    script {
                        if (isUnix()) {
                            sh """
                                echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                                docker push ${DOCKER_IMAGE}
                            """
                        } else {
                            bat """
                                echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                                docker push ${DOCKER_IMAGE}
                            """
                        }
                    }
                }
            }
        }

        stage('Deploy to Dev') {
            steps {
                echo 'Deploying to Dev environment...'
            }
        }

        stage('Run Sanity Tests on Dev') {
            steps {
                script {
                    def cmd = """
                        docker run --rm -v ${isUnix() ? '$WORKSPACE' : '%WORKSPACE%'}:/app -w /app ${DOCKER_IMAGE} \
                        mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/Regression.xml
                    """
                    if (isUnix()) {
                        sh cmd
                    } else {
                        bat cmd
                    }
                }
            }
        }

        stage('Deploy to QA') {
            steps {
                echo 'Deploying to QA environment...'
            }
        }

        stage('Run Regression Tests on QA') {
            steps {
                script {
                    def cmd = """
                        docker run --rm -v ${isUnix() ? '$WORKSPACE' : '%WORKSPACE%'}:/app -w /app ${DOCKER_IMAGE} \
                        mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/Regression.xml
                    """
                    if (isUnix()) {
                        sh cmd
                    } else {
                        bat cmd
                    }
                }
            }
        }

        stage('Publish Allure Reports') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }

        stage('Deploy to Stage') {
            steps {
                echo 'Deploying to Stage environment...'
            }
        }

        stage('Run Sanity Tests on Stage') {
            steps {
                script {
                    def cmd = """
                        docker run --rm -v ${isUnix() ? '$WORKSPACE' : '%WORKSPACE%'}:/app -w /app ${DOCKER_IMAGE} \
                        mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/Regression.xml
                    """
                    if (isUnix()) {
                        sh cmd
                    } else {
                        bat cmd
                    }
                }
            }
        }

        stage('Deploy to Prod') {
            steps {
                echo 'Deploying to Prod environment...'
            }
        }

        stage('Run Sanity Tests on Prod') {
            steps {
                script {
                    def cmd = """
                        docker run --rm -v ${isUnix() ? '$WORKSPACE' : '%WORKSPACE%'}:/app -w /app ${DOCKER_IMAGE} \
                        mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/Regression.xml
                    """
                    if (isUnix()) {
                        sh cmd
                    } else {
                        bat cmd
                    }
                }
            }
        }
    }
}
