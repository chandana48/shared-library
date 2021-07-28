def call(Map config) {
    node {
      mail bcc: '', body: '${config.body}', cc: '', from: '', replyTo: '', subject: '${config.subject}', to: '${config.to}'
    }
}
