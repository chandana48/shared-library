import org.Constants.groovy;

def call(String action){
  script {
          def mvnhome = tool "${MAVEN}";
          bat "${mvnhome}/bin/mvn ${action}"
     }
}
