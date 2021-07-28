def call(Map config) {
   
   mail bcc: '', body: '${config.body}', cc: '', from: '', replyTo: '', subject: '${config.subject}', to: '${config.to}'
   
}
