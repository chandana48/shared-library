def call(String repoUrl, String branch){
    script{
    git branch: "${branch}",
      url: "${repoUrl}"
    }
}
