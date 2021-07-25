def call(String repoUrl) {
  pipeline {
       agent any
       
       stages {
           
           stage("git") {
               steps {
                   git branch: 'master',
                       url: "${repoUrl}"
               }
           }
         stage('build') {
            steps {
                script {
                    def mvnhome = tool name: 'jenkins-maven', type: 'maven'
                    bat "${mvnhome}/bin/mvn clean install"
                }
            }
         }
            stage('sonar integration') {
              steps {
                  script {
                    def mvnhome = tool name: 'jenkins-maven', type: 'maven'
                    withSonarQubeEnv('Soanar_server') { 
                    sh "${mvnhome}/bin/mvn sonar:sonar"
                  }
                  }
              }
            }
        stage ('Server'){
                        steps {
                           rtServer (
                             id: "jenkins-jfrog",
                             url: 'http://localhost:8081/artifactory',
                             username: 'admin',
                              password: 'Chandu@48',
                              bypassProxy: true,
                               timeout: 300
                                    )
                        }
                    }
                    stage('Upload'){
                        steps{
                            rtUpload (
                             serverId:"jenkins-jfrog" ,
                              spec: '''{
                               "files": [
                                  {
                                  "pattern": "*.war",
                                  "target": "casestudy2"
                                  }
                                        ]
                                       }''',
                                    )
                        }
                    }
                    stage ('Publish build info') {
                        steps {
                            rtPublishBuildInfo (
                                serverId: "jenkins-jfrog"
                            )
                        }
                    }
         stage('tomcat server'){
           steps{
             deploy adapters: [tomcat9(credentialsId: 'd8033140-2500-4857-a267-2fede5427f84', path: '', url: 'http://localhost:8080/')], contextPath: 'examplewar', war: '**/*.war'
           }
       }
     }
    post {
failure {
  script {
    if (currentBuild.currentResult == 'FAILURE') {
      step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: "rachhachandana48@gmail.com", sendToIndividuals: true])
    }
  }
}
success{
    script{
    if (currentBuild.currentResult == 'SUCCESS') {
      step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: "rachhachandana48@gmail.com", sendToIndividuals: true])
    }
    }
}
  
}
   }
}
