def call(String config) {
   
   mail bcc: '', body: '${config.body}', cc: '', from: '', replyTo: '', subject: '${config.subject}', to: '${config.to}'
   
}
