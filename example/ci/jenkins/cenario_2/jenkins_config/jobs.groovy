def consumerUrl = 'https://github.com/vinirib/pact-consumer-sample'
def providerUrl = 'https://github.com/vinirib/pact-provider-sample'
def consumerAppName = 'client-api'
def providerAppName = 'account-api'

pipelineJob("1-PACT-FLOW-PROVIDER-TEST-CONTRACT-client-api") {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(providerUrl)
                    }
                    branch('feature/endpoint-improvements')
                    extensions {}
                }
            }
             scriptPath("Jenkinsfile")
        }
    }
}

pipelineJob("2-PACT-FLOW-CONSUMER-CAN-I-DEPLOY") {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(consumerUrl)
                    }
                    branch('master')
                    extensions {}
                }
            }
             scriptPath("Jenkinsfile-can-i-deploy")
        }
    }
}