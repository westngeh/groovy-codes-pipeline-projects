def call (String  stageName){
    if ("${stageName}" == "Build"){
    sh "mvn clean package"
    }
    else if ("${stageName}" == "Sonarqube Analyses"){
    sh "mvn sonar:sonar"
    }
    else if ("${stageName}" == "Upload Artifacts"){
    sh "mvn deploy"
    }
}
