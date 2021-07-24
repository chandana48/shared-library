def call(String repoUrl) {
  pipeline {
         agent any

         stages {

             stage("git") {
                 steps {
                     git branch: 'master',
                         url: "https://github.com/chandana48/simple-java-maven-app.git"
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
                                    "pattern": "*.jar",
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
                }
  }
}
