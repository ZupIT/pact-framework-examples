def repository = "https://github.com/ZupIT/pact-framework-examples";
def workbranch = "feature/fix-jenkins-examples";

def jobs = [
        [
                name: "EXEMPLO_FRONTEND_Consumer",
                description: "Consumer Angular APP",
                scriptPath: "example/frontend/consumer/Jenkinsfile",
                resources: {
                    remote {
                        url(repository)
                    }
                    branch("${workbranch}")
                    extensions {}
                }
        ],
        [
                name: "EXEMPLO_FRONTEND_Provider",
                description: "Provider Node APP",
                scriptPath: "example/frontend/provider/Jenkinsfile",
                resources: {
                    remote {
                        url(repository)
                    }
                    branch("${workbranch}")
                    extensions {}
                }
        ],
        [
                name: "cenario_1_passo_1",
                description: "Consumidor cria contrato e publica no Pact Broker",
                scriptPath: "example/ci/jenkins/cenario_1/pact-consumer-sample/Jenkinsfile",
                resources: {
                    remote {
                        url(repository)
                    }
                    branch("${workbranch}")
                    extensions {}
                }
        ],
        [
                name: "cenario_1_passo_2",
                description: "O provider testa a validade do contrato e publica o resultado no Pact Broker",
                scriptPath: "example/ci/jenkins/cenario_1/pact-provider-sample/Jenkinsfile",
                resources: {
                    remote {
                        url(repository)
                    }
                    branch("${workbranch}")
                    extensions {}
                }
        ],
        [
                name: "cenario_1_passo_3",
                description: "O consumer obtém informações do broker para validar se a integração está coesa",
                scriptPath: "example/ci/jenkins/cenario_1/pact-consumer-sample/Jenkinsfile-can-i-deploy",
                resources: {
                    remote {
                        url(repository)
                    }
                    branch("${workbranch}")
                    extensions {}
                }
        ],
        [
                name: "cenario_2",
                description: "O provider altera sua API e valida novamente os contratos",
                scriptPath: "example/ci/jenkins/cenario_2/pact-provider-sample/Jenkinsfile",
                resources: {
                    remote {
                        url(repository)
                    }
                    branch("${workbranch}")
                    extensions {}
                }
        ],
        [
                name: "cenario_3_passo_1",
                description: "Consumidor altera seu contrato e publica no Pact Broker",
                scriptPath: "example/ci/jenkins/cenario_3/pact-consumer-sample/Jenkinsfile",
                resources: {
                    remote {
                        url(repository)
                    }
                    branch("${workbranch}")
                    extensions {}
                }
        ],
        [
                name: "cenario_3_passo_2",
                description: "O provider altera novamente e testa a validade do contrato",
                scriptPath: "example/ci/jenkins/cenario_3/pact-provider-sample/Jenkinsfile",
                resources: {
                    remote {
                        url(repository)
                    }
                    branch("${workbranch}")
                    extensions {}
                }
        ],
        [
                name: "can-i-deploy_Consumer",
                description: "Utilitário can-i-deploy diz se o contrato está seguro",
                scriptPath: "example/ci/jenkins/cenario_1/pact-consumer-sample/Jenkinsfile-can-i-deploy",
                resources: {
                    remote {
                        url(repository)
                    }
                    branch("${workbranch}")
                    extensions {}
                }
        ],
        [
                name: "can-i-deploy_Provider",
                description: "Utilitário can-i-deploy diz se o contrato está seguro",
                scriptPath: "example/ci/jenkins/cenario_1/pact-provider-sample/Jenkinsfile-can-i-deploy",
                resources: {
                    remote {
                        url(repository)
                    }
                    branch("${workbranch}")
                    extensions {}
                }
        ],
]

jobs.each { job ->

    println("Criando pipeline para: ${job.name}")

    pipelineJob(job.name) {

        displayName(job.name)
        description(job.description)
        definition {
            cpsScm {
                scm {
                    git job.resources
                }
                scriptPath(job.scriptPath)
            }
        }
    }
}