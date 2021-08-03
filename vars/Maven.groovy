//import org.Constants.groovy;

def call(String action){
      script {
            def mvnhome = tool name: 'jenkins-maven', type: 'maven'
              bat "${mvnhome}/bin/mvn ${action}"
         }
}
