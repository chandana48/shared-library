import org.Constants.groovy;

def call(String action){
  steps{
      script {
              def mvnhome = tool "${MAVEN}";
              bat "${mvnhome}/bin/mvn ${action}"
         }
  }
}
