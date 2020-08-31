def participants = [
    [
        name: "exemplo frontend (consumer)",
        description: "Consumer Angular APP",
        jenkinsfilePath: "example/frontend/consumer/Jenkinsfile",
        repository: {
            git {
                id("frontend-consumer-sample")
                remote("https://github.com/ZupIT/pact-framework-examples")
            }
        }
    ],
    [
        name: "exemplo frontend (provider)",
        description: "Provider Node APP",
        jenkinsfilePath: "example/frontend/old_provider/Jenkinsfile",
        repository: {
            git {
                id("frontend-provider-sample")
                remote("https://github.com/ZupIT/pact-framework-examples")
            }
        }
    ],
    [
        name: "exemplo CI Jenkins - passo 1 - (consumer)",
        description: "Java Consumer API",
        jenkinsfilePath: "example/ci/jenkins/pact-consumer-sample/Jenkinsfile",
        repository: {
            git {
                id("ci-jenkins-consumer-sample")
                remote("https://github.com/ZupIT/pact-framework-examples")
            }
        }
    ],
    [
        name: "exemplo CI Jenkins - passo 2 - (provider)",
        description: "Java Provider API",
        jenkinsfilePath: "example/ci/jenkins/pact-provider-sample/Jenkinsfile",
        repository: {
            git {
                id("ci-jenkins-provider-sample")
                remote("https://github.com/ZupIT/pact-framework-examples")
            }
        }
    ],
    [
        name: "exemplo CI Jenkins - passo 3 - can I deploy (consumer)",
        description: "Java Consumer API - Can I Deploy?",
        jenkinsfilePath: "example/ci/jenkins/pact-consumer-sample/Jenkinsfile-can-i-deploy",
        repository: {
            git {
                id("ci-jenkins-consumer-sample")
                remote("https://github.com/ZupIT/pact-framework-examples")
            }
        }
    ],
    [
        name: "exemplo CI Jenkins - check integration (provider)",
        description: "Java provider API - check integration",
        jenkinsfilePath: "example/ci/jenkins/pact-provider-sample/Jenkinsfile-can-i-deploy",
        repository: {
            git {
                id("ci-jenkins-provider-sample")
                remote("https://github.com/ZupIT/pact-framework-examples")
            }
        }
    ],
]

participants.each { service ->
    println("Criando multipipeline para:: $service.name")

    multibranchPipelineJob("${service.name}") {

        displayName(service.name)

        description(service.description)

        branchSources service.repository

        factory {
            workflowBranchProjectFactory {
                scriptPath(service.jenkinsfilePath)
            }
        }
    }
}