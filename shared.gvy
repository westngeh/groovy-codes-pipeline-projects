pipeline{
  agent any 
  tools {
    maven "maven3.8.6"
  }
  stages{  
    stage('1GetCode'){
      steps{
        sh "echo 'cloning the latest application version' "
        git "https://github.com/elvis-ngeh/maven-web-application"
      }
    }
stage('2build'){
    steps{
        sh "echo 'JUnit test & build now'"
        sh "mvn clean package"
    }
}
stage('3sonarqube'){
    steps{
        sh "echo 'running code analyses now'"
        sh "mvn sonar:sonar"
    }
}
stage('4Nexus'){
    steps{
        sh "echo 'pushing artifacts to nexus now'"
        sh "mvn deploy"
    }
}
stage('5UATTest'){
    steps{
        sh "echo 'pushing to tomcat for internal test'"
        deploy adapters: [tomcat9(credentialsId: 'tomcat-credentials', path: '', url: 'http://10.0.1.148:8080')], contextPath: null, war: 'target/*war'
    }
}
stage('6ApprovalGate'){
    steps{
        sh "echo 'Pls review and approve'"
        timeout(time: 5 , unit:'DAYS'){
            input message:'pls review and approve, for deployment to complete'
        }
    }
}
stage('7deploytoProd'){
    steps{
        sh "echo 'we are finally pushing to prod'"
        deploy adapters: [tomcat9(credentialsId: 'tomcat-credentials', path: '', url: 'http://10.0.1.148:8080')], contextPath: null, war: 'target/*war'
    }
}
stage('8notification'){
    steps{
        emailext body: 'Thanks for the hard work ', subject: 'deploying to Prod', to: 'elvis.bantar@gmail.com'
    }
}
}
}