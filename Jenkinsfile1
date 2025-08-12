pipeline 
{
    agent any
    
    tools{
        maven 'maven'
        }

    stages 
    {
        stage('Build') 
        {
            steps
            {
                 git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                 bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post 
            {
                success
                {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        
        
        stage("Deploy to Dev"){
            steps{
                echo("deploy to Dev")
            }
        }
        
        stage('Sanity API Automation Test on DEV') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/kapilgurjar/ApiFramework.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/Regression.xml"
                    
                }
            }
        }
        
        
        
        stage("Deploy to QA"){
            steps{
                echo("deploy to qa done")
            }
        }
          
                
                
        stage('Regression API Automation Tests on QA') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                     git 'https://github.com/kapilgurjar/ApiFramework.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/Regression.xml"
                    
                }
            }
        }
                
     
        stage('Publish Allure Reports') {
           steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: '/allure-results']]
                    ])
                }
            }
        }
        
        

        stage("Deploy to Stage"){
            steps{
                echo("deploy to Stage")
            }
        }
        
        stage('Sanity API Automation Test on Stage') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/kapilgurjar/ApiFramework.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/Regression.xml"
                    
                }
            }
        }
        
                
        stage("Deploy to PROD"){
            steps{
                echo("deploy to PROD")
            }
        }
        
        stage('Sanity API Automation Test on PROD') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                  git 'https://github.com/kapilgurjar/ApiFramework.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/Regression.xml"
                    
                }
            }
        }
        
        
    }
}