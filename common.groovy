def call(string stageName){
    if("${stageName}" == "Build")
    {
        sh "mvn clean package"
    }
    else if ("${stageName}" == "sonarqube Report")
    {
        sh "mvn clean sonar:sonar"
    }
    else if ("${stageName}" == "Upload Into Nexus")
    {
        sh "mvn clean deploy"
    }
}    
