def call(Map config) {
    node {
      mail bcc: '', body: '${config.body}', cc: '', from: '${config.from}', replyTo: '', subject: '${config.subject}', to: '{config.to}'
    }
}
